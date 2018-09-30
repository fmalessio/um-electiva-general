package com.electiva_general.um.misnumeritos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.electiva_general.um.misnumeritos.MainActivity;
import com.electiva_general.um.misnumeritos.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;


public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 3;
    private String sessionId;
    private String sessionUser;

    // UI
    private SignInButton signInButton;
    private Button closeAppButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        closeAppButton = (Button) findViewById(R.id.closeAppButton);
        closeAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Volvé a jugar cuando quieras! Te esperamos!", Toast.LENGTH_SHORT).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                        System.exit('0');
                    }
                },1500);
            }
        });
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){
        Toast.makeText(this, R.string.not_login_session, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handledSignInResult(result);
        }
    }

    private void handledSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            Toast.makeText(this, R.string.successful_connection, Toast.LENGTH_SHORT).show();
            GoogleSignInAccount account = result.getSignInAccount();
            sessionId = account.getId();
            sessionUser = account.getGivenName();
            goMainScreen();
        }
        else{
            Toast.makeText(this, R.string.not_login_session, Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("EXTRA_SESSION_ID", sessionId);
        //intent.putExtra("EXTRA_SESSION_USER", sessionUser);
        startActivity(intent);
        finish();
    }


}
