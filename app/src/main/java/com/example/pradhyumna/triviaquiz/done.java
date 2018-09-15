package com.example.pradhyumna.triviaquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pradhyumna.triviaquiz.Common.Common;
import com.example.pradhyumna.triviaquiz.model.Question_Score;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.medal.MedalAnimation;
import com.squareup.picasso.Picasso;

public class done extends AppCompatActivity {
    Button btntry,btnquit;
    TextView txtscore , txttotalques;
    ProgressBar progbar;
    ImageView iv;
    FirebaseDatabase database;
    DatabaseReference ques_score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        ques_score = database.getReference("Question_Score");
        iv=findViewById(R.id.imageView2);
        Picasso.get().load(R.drawable.medal6).fit().into(iv);
        txtscore = findViewById(R.id.totalscore);
        txttotalques = findViewById(R.id.totalquestions);
        progbar = findViewById(R.id.progbar);
        btntry = findViewById(R.id.btntryagain);
        btnquit=findViewById(R.id.btnquit);
        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
        btntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goback = new Intent(done.this , HomeNavigation.class);
                startActivity(goback);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            int score = extra.getInt("SCORE");
            int totalquestion = extra.getInt("TOTAL");
            int correctans = extra.getInt("CORRECT");
            if(correctans>=8)
            {
                iv.setVisibility(View.VISIBLE);
                MedalAnimation medalAnimation = new MedalAnimation.Builder()
                        .setDirection(MedalAnimation.LEFT)
                        .setDegreeX(360)
                        .setDegreeZ(360)
                        .setSpeed(4200)
                        .setTurn(4)
                        .setLoop(10)
                        .build();
                iv.startAnimation(medalAnimation);
            }
            txtscore.setText(String.format("SCORE : %d",score));
            txttotalques.setText(String.format("PASSED : %d/%d" , correctans , totalquestion));
            progbar.setMax(totalquestion);
            progbar.setProgress(correctans);
            String user=Common.currentuser.getUsername().replace(".",",");

            ques_score.child(String.format("%s_%s" , user , Common.categoryid))
                    .setValue(new Question_Score(String.format("%s_%s" , Common.currentuser.getUsername() , Common.categoryid) ,
                            Common.currentuser.getUsername() , String.valueOf(score)));

        }

    }
}
