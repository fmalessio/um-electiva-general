package com.electiva_general.um.misnumeritos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // Silent LogIn
    private GoogleApiClient googleApiClient;

    private String sessionId;
    private String sessionUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }



    @Override
    protected void onStart() {
        super.onStart();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
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
            }
        },3000);


    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){
        Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            sessionId = account.getId();
            sessionUser = account.getGivenName();

            goMainScreen();
        }
        else {
            goLogInScreen();
        }

    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        intent.putExtra("EXTRA_SESSION_USER", sessionUser);
        startActivity(intent);
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
