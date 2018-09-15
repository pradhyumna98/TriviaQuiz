package com.example.pradhyumna.triviaquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pradhyumna.triviaquiz.Common.Common;
import com.example.pradhyumna.triviaquiz.R;
import com.example.pradhyumna.triviaquiz.Rankingfragment;
import com.example.pradhyumna.triviaquiz.categoryfragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;

public class HomeNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
SharedPreferences preferences;
TextView textView;
String type;
MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        player= MediaPlayer.create(this,R.raw.back);
        player.setLooping(true); // Set looping
        player.setVolume(1000,1000);
        player.start();
        preferences=getSharedPreferences(MainActivity.preference,MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setDefaultfragment();
        textView=navigationView.getHeaderView(0).findViewById(R.id.textViewEmail);
        type=Common.type;
        textView.setText(Common.currentuser.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player!=null)
        {

            player.release();}
    }

    private void setDefaultfragment() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame , categoryfragment.newInstance());
        transaction.commit();

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment selectedfragment = null;
        switch (item.getItemId()){
            case R.id.action_category:
                selectedfragment = categoryfragment.newInstance();
                break;

            case R.id.action_ranking:
                selectedfragment = Rankingfragment.newInstance();
                break;
            case R.id.Aboutus:selectedfragment=AboutUsFragment.newInstance();
                break;
            case R.id.Instructions:
                selectedfragment=InstructionsFragment.newInstance();
                break;
                case R.id.feedback:
                    Intent intent =new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                Uri uri=Uri.parse("mailto:shu12.sg@gmail.com");
                intent.setData(uri);
                startActivity(intent);
                return true;
            case R.id.logout:
                if(type.equals(Username.FIREBASE))
                {
                    FirebaseAuth.getInstance().signOut();
                }
                else if(type.equals(Username.NORMAL)) {
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(Username.EMAIL_KEY,"");
                    editor.putString(Username.USERNAME_KEY,"");
                    editor.putString(Username.PASSWORD_KEY,"");
                    editor.commit();

                }
                Intent intent1=new Intent(HomeNavigation.this,Username.class);
                finish();
                startActivity(intent1);
                return  true;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame , selectedfragment);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
