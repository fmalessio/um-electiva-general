package com.electiva_general.um.misnumeritos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.electiva_general.um.misnumeritos.business.Score;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // UI
    private Button playButton;
    private Button scoresButton;
    private Button instructionsButton;
    private Button aboutButton;
    private Button logOutButton;
    private Button revokeButton;

    private TextView userNameTextView;
    private TextView userIdTextView;


    // Silent LogIn
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameTextView = (TextView) findViewById(R.id.nameTextView);
        userIdTextView = (TextView) findViewById(R.id.idTextView);

        playButton = findViewById(R.id.playButton);
        scoresButton = findViewById(R.id.scoresButton);
        instructionsButton = findViewById(R.id.instructionsButton);
        aboutButton = findViewById(R.id.aboutButton);
        logOutButton = findViewById(R.id.logOutButton);
        revokeButton = findViewById(R.id.revokeButton);




        //*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //*/

        addListeners();

    }

    private void addListeners() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameActivity);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        userNameTextView.setText("Usuario");
        userIdTextView.setText("0");

        //*
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
        else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
        //*/

    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            userNameTextView.setText("Hola "+ account.getGivenName()+"!");
            userIdTextView.setText(account.getId());

            //*
            //TODO : delete
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.child("scores").push().setValue(new Score(account.getDisplayName(),6));

            //*/

        }
        else {
            goLogInScreen();
        }

    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){
        Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Toast.makeText(getApplicationContext(), "Se cerró la sesión de tu cuenta", Toast.LENGTH_SHORT).show();
                    sessionClosed();                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke(View view){
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Toast.makeText(getApplicationContext(), "Se revocaron los permisos de la app", Toast.LENGTH_SHORT).show();
                    sessionClosed();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.not_revoke, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sessionClosed(){
        userNameTextView.setText("Usuario Anónimo");
        userIdTextView.setText("0");

        playButton.setEnabled(false);
        scoresButton.setEnabled(false);
        instructionsButton.setEnabled(false);
        aboutButton.setEnabled(false);
        logOutButton.setEnabled(false);
        revokeButton.setEnabled(false);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goLogInScreen();
            }
        }, 3000);
    }
}
