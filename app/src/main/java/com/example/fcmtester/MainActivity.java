package com.example.fcmtester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fcmtester.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {


    //declare constants here
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "MainActivity";
    public static final String FULL_NAME = "fullName";
    public static final String EMAIL = "email";
    public static final String IMAGE_URL = "imageUrl";

    //declare globals here
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        try {
            //google signin options
            checkForIntent();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            signInButton = findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            signInButton.setOnClickListener(view -> signIn());
            initFirebaseInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForIntent() {
        try {
            if(getIntent().hasExtra("type")){
                Log.d("intent", "has type");
                String title = getIntent().hasExtra("title")?getIntent().getStringExtra("title"):"Alert";
                String message = getIntent().hasExtra("message")?getIntent().getStringExtra("message"):"198";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCancelable(true);
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.show();
            }else{
                Log.d("intent", "does not have type");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        checkForIntent();
    }

    private void initFirebaseInstance() {
        try {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast
                            //String msg = "Token: " + token;
                            SharedprefManager.getInstance(getApplicationContext()).storeToken(token);
                            Log.d("token", token);
                            Toast.makeText(MainActivity.this, "Token generated!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void signIn() {
        try {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra(FULL_NAME, account.getDisplayName());
            intent.putExtra(EMAIL, account.getEmail());
            intent.putExtra(IMAGE_URL, account.getPhotoUrl().toString());
            startActivity(intent);
            //Log.i("debug", "debug");
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }
}
