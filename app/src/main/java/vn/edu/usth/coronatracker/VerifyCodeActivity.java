package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.LocalDate;

import vn.edu.usth.coronatracker.model.User;

public class VerifyCodeActivity extends AppCompatActivity {
    private static final String TAG = "VERIFY CODE ACTIVITY";
    private String OTP, pin;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private PinView pinView;
    private Button btnVerify;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        initView();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        OTP = getIntent().getExtras().getString("OTP");
        Log.d(TAG, OTP);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPin();
                if (checkPin()) {
                    progressBar.setVisibility(View.VISIBLE);
                    verifyPin(pin);
                }
            }
        });

    }

    private boolean checkPin() {

        pin = pinView.getText().toString();
        if (TextUtils.isEmpty(pin)) {
            pinView.setError("Enter the pin");
            return false;
        } else if (pin.length() < 6) {
            pinView.setError("Enter valid pin");
            return false;
        } else {
            pinView.setError(null);
            return true;
        }
    }

    private void verifyPin(String pin) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, pin);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {


        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (getIntent().getExtras().getString("family_name").equals("")) {
                        Intent intent = new Intent(VerifyCodeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(data -> {

                            String token = data.getResult();
                            User userModel = new User(getIntent().getExtras().getString("family_name"), getIntent().getExtras().getString("name"),
                                    firebaseAuth.getCurrentUser().getPhoneNumber(),
                                    "https://firebasestorage.googleapis.com/v0/b/blogapp-f320d.appspot.com/o/profile_images%2Fdefault_image_icon_xhdpi.jpg?alt=media&token=44f8ce79-3f27-416a-8414-1ce9f68f8ab8",
                                    firebaseAuth.getUid(), token, "", "Male", "1/1/2021");
                            databaseReference.child(firebaseAuth.getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(VerifyCodeActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                        progressBar.setVisibility(View.GONE);

                                    } else {
                                        Log.d("VerifyCodeActivity", task.getException().toString());
                                        Toast.makeText(VerifyCodeActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                        });
                    }


                } else {
                    Log.d("VerifyCodeActivity", task.getException().toString());
                    Toast.makeText(VerifyCodeActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        pinView = findViewById(R.id.otp_text_view);
        btnVerify = findViewById(R.id.btnVerify);
    }
}