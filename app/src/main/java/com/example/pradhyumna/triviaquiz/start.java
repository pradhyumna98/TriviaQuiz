package com.example.pradhyumna.triviaquiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pradhyumna.triviaquiz.Common.Common;
import com.example.pradhyumna.triviaquiz.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class start extends AppCompatActivity {

    Button btnplay;
    ImageView imageView;
    FirebaseDatabase database;
    DatabaseReference questions,dateReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        supportPostponeEnterTransition();

        Intent intent=getIntent();
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        imageView=findViewById(R.id.ImagePoster);

        dateReference=questions.child(thisDate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = intent.getStringExtra("Image_transition_name");
            imageView.setTransitionName(imageTransitionName);
        }
        String url=intent.getStringExtra("Image_Url");
        Picasso.get().load(url).noFade().into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });
                btnplay = findViewById(R.id.btnplay);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(start.this , playing.class);
                startActivity(game);
                finish();
            }
        });
        loadquestion(Common.categoryid);
    }

    private void loadquestion(String categoryid) {

        if(Common.questionlist.size() > 0){
            Common.questionlist.clear();
        }
            dateReference.orderByChild("CategoryId").equalTo(categoryid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        Toast.makeText(start.this, "No Quiz Scheduled", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                        Question ques = postsnapshot.getValue(Question.class);
                        Common.questionlist.add(ques);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//        else{
//            Toast.makeText(start.this, "No Quiz Scheduled", Toast.LENGTH_LONG).show();
//            finish();
//        }
        Collections.shuffle(Common.questionlist);
    }
}
