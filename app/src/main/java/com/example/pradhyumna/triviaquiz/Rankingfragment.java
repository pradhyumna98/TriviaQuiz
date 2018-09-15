package com.example.pradhyumna.triviaquiz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pradhyumna.triviaquiz.Common.Common;
import com.example.pradhyumna.triviaquiz.Interface.ItemClickListener;
import com.example.pradhyumna.triviaquiz.Interface.RankingCallBack;
import com.example.pradhyumna.triviaquiz.model.Question_Score;
import com.example.pradhyumna.triviaquiz.model.Ranking;
import com.example.pradhyumna.triviaquiz.viewholder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.medal.MedalAnimation;
import com.squareup.picasso.Picasso;


public class Rankingfragment extends Fragment {
    View myfragment;
    int i=1;
    RecyclerView rankinglist;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking , RankingViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference question_score , rankingtbl;

    int sumscore;


    public static Rankingfragment newInstance() {

        Rankingfragment rank = new Rankingfragment();
        return rank;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");
        rankingtbl = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myfragment = inflater.inflate(R.layout.fragment_rankingfragment, container, false);
        final MedalAnimation medalAnimation = new MedalAnimation.Builder()
                .setDirection(MedalAnimation.LEFT)
                .setDegreeX(360)
                .setDegreeZ(360)
                .setSpeed(4200)
                .setTurn(4)
                .setLoop(1000)
                .build();
        rankinglist = myfragment.findViewById(R.id.rankinglist);
        layoutManager = new LinearLayoutManager(getActivity());
        rankinglist.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankinglist.setLayoutManager(layoutManager);

        updatescore(Common.currentuser.getUsername(), new RankingCallBack<Ranking>() {
            @Override
            public void callback(Ranking ranking) {
                rankingtbl.child(ranking.getUsername()).setValue(ranking);
                //showranking();

            }
        });

        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(Ranking.class ,
                R.layout.layout_ranking , RankingViewHolder.class , rankingtbl.orderByChild("score")) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, Ranking model, int position) {
                String user=model.getUsername().replace(",",".");
                viewHolder.txt_name.setText(user);
                viewHolder.txt_score.setText(String.valueOf(model.getScore()));
               Picasso.get().load(R.drawable.medal7).fit().into(viewHolder.iv);

                viewHolder.iv.startAnimation(medalAnimation);
                viewHolder.setItemclicklistener(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int position, boolean islongclick) {

                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        rankinglist.setAdapter(adapter);
        return myfragment;
    }



    private void updatescore(final String username , final RankingCallBack<Ranking> callback) {
        final String user=Common.currentuser.getUsername().replace(".",",");
        question_score.orderByChild("user").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Question_Score ques = data.getValue(Question_Score.class);
                    sumscore+= Integer.parseInt(ques.getScore());
                }

                Ranking ranking = new Ranking(user , sumscore);
                callback.callback(ranking);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
