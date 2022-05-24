package com.example.luyentapthread_bai03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.luyentapthread_bai03.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    LinearLayout.LayoutParams layoutParams;
    Random random;
    int n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addEvents();
    }

    private void addEvents() {
        mainBinding.btnVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n=Integer.parseInt(mainBinding.edtNumber.getText().toString().trim());
                buttonTask buttonTask=new buttonTask();
                buttonTask.execute(n);
            }
        });


    }

    private void addControl() {
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        layoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        random=new Random();
    }

    class buttonTask extends AsyncTask<Integer,Integer,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mainBinding.txtPercent.setText("0%");
            mainBinding.llButton.removeAllViews();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            mainBinding.txtPercent.setText("100%");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int percent=values[0];
            int value=values[1];
            mainBinding.txtPercent.setText(percent+"%");
            Button button=new Button(MainActivity.this);
            button.setText(value+"");
            button.setLayoutParams(layoutParams);
            mainBinding.llButton.addView(button);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int n=integers[0];
            for (int i = 0; i < n; i++) {
                int percent=i*100/n;
                int value= random.nextInt(100);

                publishProgress(percent,value);

                SystemClock.sleep(100);
            }
            return null;
        }
    }
}