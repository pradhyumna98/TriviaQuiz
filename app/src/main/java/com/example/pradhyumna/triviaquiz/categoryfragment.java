package com.example.pradhyumna.triviaquiz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pradhyumna.triviaquiz.Common.Common;
import com.example.pradhyumna.triviaquiz.Interface.ItemClickListener;
import com.example.pradhyumna.triviaquiz.model.Category;
import com.example.pradhyumna.triviaquiz.viewholder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class categoryfragment extends Fragment {




    View myfragment;
    RecyclerView listcategory;
    RecyclerView.LayoutManager layoutm;
    FirebaseRecyclerAdapter <Category , CategoryViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categories;


    public static categoryfragment newInstance(){

        categoryfragment category = new categoryfragment();
        return category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myfragment = inflater.inflate(R.layout.fragment_categoryfragment , container , false);

        listcategory = myfragment.findViewById(R.id.listcategory);
        listcategory.setHasFixedSize(true);
        layoutm = new LinearLayoutManager(container.getContext());
        listcategory.setLayoutManager(layoutm);

        loadcategories();

        return myfragment;
    }

    private void loadcategories() {

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.layout_category,
                CategoryViewHolder.class ,
                categories
        ) {
            @Override
            protected void populateViewHolder(final CategoryViewHolder viewHolder, final Category model, int position) {

                viewHolder.category_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.category_image);
                ViewCompat.setTransitionName(viewHolder.category_image,model.getName());
                viewHolder.setItemcl(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int position, boolean islongclick) {
                        //Toast.makeText(getActivity(),String.format("%s|%s" , adapter.getRef(position).getKey() , model.getName()), Toast.LENGTH_SHORT).show();
                        Intent startgame = new Intent(getActivity() , start.class);
                        startgame.putExtra("Image_transition_name",model.getName());
                        startgame.putExtra("Image_Url",model.getImage());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(),
                                viewHolder.category_image,
                                ViewCompat.getTransitionName(viewHolder.category_image));
                        Common.categoryid = adapter.getRef(position).getKey();

                        startActivity(startgame,options.toBundle());
                        getActivity().finish();
                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        listcategory.setAdapter(adapter);
    }
}
