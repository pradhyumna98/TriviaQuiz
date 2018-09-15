package com.example.pradhyumna.triviaquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pradhyumna.triviaquiz.Common.Common;
import com.example.pradhyumna.triviaquiz.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Username extends AppCompatActivity {
EditText usersp , passsp , emailsp;
EditText usersi , passsi;
Button login , signup;
FirebaseDatabase database;
SharedPreferences preferences;
public static final String USERNAME_KEY="username key",PASSWORD_KEY="password key",EMAIL_KEY="email key",TYPE="type"
        ,FIREBASE="firebase",NORMAL="normal";
DatabaseReference users;
    SignInButton signIn;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 7;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        signIn=findViewById(R.id.sign_in_button);

        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        usersi = findViewById(R.id.useret);
        passsi = findViewById(R.id.passet);

        login = findViewById(R.id.logbt);
        signup = findViewById(R.id.signbt);
        preferences=getSharedPreferences(MainActivity.preference,MODE_PRIVATE);
        String userName=preferences.getString(USERNAME_KEY,""),email=preferences.getString(EMAIL_KEY,""),password=preferences.getString(PASSWORD_KEY,"");
        if(userName!=""&&password!=""&&email!="")
        {
            User login=new User(userName,password,email);
            Common.currentuser=login;

            nextActivity(NORMAL);
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showsignupdialog();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin(usersi.getText().toString() , passsi.getText().toString());
            }
        });
    }

    private void nextActivity(String string) {
        Intent homeactivity = new Intent(Username.this , HomeNavigation.class);
        Common.type=string;
        Toast.makeText(Username.this , "Login Success" , Toast.LENGTH_SHORT).show();
        startActivity(homeactivity);
        finish();
    }

    private void signin(final String user, final String pass) {

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){
                    if(!user.isEmpty())
                    {
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pass)){
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString(EMAIL_KEY,login.getEmail());
                            editor.putString(USERNAME_KEY,login.getUsername());
                            editor.putString(PASSWORD_KEY,login.getPassword());
                            editor.commit();
                            Common.currentuser = login;
                            nextActivity(NORMAL);

                        }
                        else
                            Toast.makeText(Username.this , "Incorrect Password" , Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(Username.this , "Enter The Username" , Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Username.this , "User Doesn't Exists!!" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showsignupdialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Username.this);
        alert.setTitle("Sign Up!");
        alert.setMessage("Please Fill Up the Deatials");

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up , null);

        usersp = sign_up_layout.findViewById(R.id.useret1);
        passsp = sign_up_layout.findViewById(R.id.passet1);
        emailsp = sign_up_layout.findViewById(R.id.emailet1);

        alert.setView(sign_up_layout);
        alert.setIcon(R.drawable.ic_account_circle_black_24dp);


        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User(usersp.getText().toString() , passsp.getText().toString() , emailsp.getText().toString());
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                            Toast.makeText(Username.this , "User Already Exists!!" ,Toast.LENGTH_SHORT ).show();

                        else {
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(Username.this , "Registration Successful" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }
    void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Login", "Google sign in failed", e);
                // ...
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("Login", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email=user.getEmail();
                            String[] arr=email.split("@");
                            User user1=new User(arr[0],arr[0],user.getEmail());
                            Common.currentuser=user1;
                            nextActivity(FIREBASE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Username.this,"Auth Failed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      if(currentUser!=null)
      {
          String email=currentUser.getEmail();
          String[] arr=email.split("@");
          User user1=new User(arr[0],arr[0],currentUser.getEmail());
          Common.currentuser=user1;
          nextActivity(FIREBASE);
      }
    }

}
