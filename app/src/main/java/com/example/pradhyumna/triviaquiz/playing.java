package com.example.pradhyumna.triviaquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pradhyumna.triviaquiz.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

public class playing extends AppCompatActivity implements View.OnClickListener {
    String answerMarked;
    final static long interval = 1000;
    final static long to = 10060;
    int progressvalue = 1;
    CountDownTimer mcount;
    int index = 0, score = 0, thisquestion = 0, totalquestion, correectanswer;

    ProgressBar pbar;
    ImageView question_image;
    MediaPlayer player;
    Button btna, btnb, btnc, btnd;
    TextView txtscore, questionnum, questext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        player= MediaPlayer.create(this,R.raw.thetune);
        player.setLooping(true); // Set looping
        player.setVolume(1000,1000);
        player.start();
        txtscore = findViewById(R.id.txtscore);
        questionnum = findViewById(R.id.txttotalques);
        questext = findViewById(R.id.questiontext);
        question_image = findViewById(R.id.questionimage);

        pbar = findViewById(R.id.progress);

        btna = findViewById(R.id.btnansa);
        btnb = findViewById(R.id.btnansb);
        btnc = findViewById(R.id.btnansc);
        btnd = findViewById(R.id.btnansd);

        btna.setOnClickListener(this);
        btnb.setOnClickListener(this);
        btnc.setOnClickListener(this);
        btnd.setOnClickListener(this);

    }@Override
    public void onClick(View view) {

        if (index < totalquestion) {
            Button clickedbutton = (Button) view;
            clickedbutton.setBackground(getResources().getDrawable(R.drawable.color_black));
            answerMarked=clickedbutton.getText().toString();

            btna.setEnabled(false);
           btnb.setEnabled(false);
           btnc.setEnabled(false);
           btnd.setEnabled(false);


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player!=null)
        {

            player.release();}
    }

    private void showquestion(int index) {
        btna.setEnabled(true);
        btnb.setEnabled(true);
        btnc.setEnabled(true);
        btnd.setEnabled(true);
        btna.setBackground(getResources().getDrawable(R.drawable.color_pink));
        btnb.setBackground(getResources().getDrawable(R.drawable.color_pink));
        btnc.setBackground(getResources().getDrawable(R.drawable.color_pink));
        btnd.setBackground(getResources().getDrawable(R.drawable.color_pink));
        answerMarked="";
        if (index < totalquestion) {
            thisquestion++;
            questionnum.setText(String.format("%d/%d", thisquestion, totalquestion));
            pbar.setProgress(10);
            progressvalue = 0;

            if(Common.questionlist.get(index).getIsImageQuestion().equals("true")){
                Picasso.get().load(Common.questionlist.get(index).getQuestion()).fit().into(question_image);
                question_image.setVisibility(View.VISIBLE);
                questext.setVisibility(View.INVISIBLE);

            }
            else{
                questext.setText(Common.questionlist.get(index).getQuestion());

                question_image.setVisibility(View.INVISIBLE);
                questext.setVisibility(View.VISIBLE);
            }
            btna.setText(Common.questionlist.get(index).getAnswerA());
            btnb.setText(Common.questionlist.get(index).getAnswerB());
            btnc.setText(Common.questionlist.get(index).getAnswerC());
            btnd.setText(Common.questionlist.get(index).getAnswerD());
            mcount.start();
        }
        else {
            Intent intent = new Intent(playing.this , done.class);
            Bundle datasend = new Bundle();
            datasend.putInt("SCORE" , score);
            datasend.putInt("TOTAL" , totalquestion);
            datasend.putInt("CORRECT" , correectanswer);
            intent.putExtras(datasend);
            startActivity(intent);
            finish();
        }

    }

   @Override
   protected void onResume() {
       super.onResume();
       totalquestion = Common.questionlist.size();
       mcount = new CountDownTimer(to , interval) {
           @Override
           public void onTick(long minisec) {

               pbar.setProgress(progressvalue);
               progressvalue++;

           }

           @Override
           public void onFinish() {
               progressvalue++;
               pbar.setProgress(progressvalue);
               mcount.cancel();
               btna.setEnabled(false);
               btnb.setEnabled(false);
               btnc.setEnabled(false);
               btnd.setEnabled(false);
               if (answerMarked.equals(Common.questionlist.get(index).getCorrectAnswer())) {
                   score = score + 10;
                   correectanswer++;

                   if(btna.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btna.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
                   else if(btnb.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btnb.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
                   else if(btnc.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btnc.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
                   else if(btnd.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btnd.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
               }
               else
               {
                   score = score - 20;
//                Intent intent = new Intent(this , done.class);
//                Bundle datasend = new Bundle();
//                datasend.putInt("SCORE" , score);
//                datasend.putInt("TOTAL" , totalquestion);
//                datasend.putInt("CORRECT" , correectanswer);
//                intent.putExtras(datasend);
//                startActivity(intent);
//                finish();
                   if(btna.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btna.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
                   else if(btnb.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btnb.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
                   else if(btnc.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btnc.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
                   else if(btnd.getText().toString().equals(Common.questionlist.get(index).getCorrectAnswer()))
                   {
                       btnd.setBackground(getResources().getDrawable(R.drawable.color_correct));
                   }
                   if(btna.getText().toString().equals(answerMarked))
                   {
                       btna.setBackground(getResources().getDrawable(R.drawable.color_wrong));
                   }
                   else if(btnb.getText().toString().equals(answerMarked))
                   {
                       btnb.setBackground(getResources().getDrawable(R.drawable.color_wrong));
                   }
                   else if(btnc.getText().toString().equals(answerMarked))
                   {
                       btnc.setBackground(getResources().getDrawable(R.drawable.color_wrong));
                   }
                   else if(btnd.getText().toString().equals(answerMarked))
                   {
                       btnd.setBackground(getResources().getDrawable(R.drawable.color_wrong));
                   }
               }
               txtscore.setText(String.format("%d", score));
               Handler handler=new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       showquestion(++index);
                   }
               },2000);


           }
       };
       showquestion(index);
   }


}
