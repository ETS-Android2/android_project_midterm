package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import static java.security.AccessController.getContext;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "REGISTER ACTIVITY";
    private TextView tvPhoneNumber, tvFamilyName, tvName, moveToLogin;
    private Button register;
    private String number;
    private ProgressBar progressBar;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNumber();
                if (checkNumber()) {
                    String phoneNumber = ccp.getSelectedCountryCodeWithPlus() + number;
                    sendOTP(phoneNumber);
                }
            }
        });
        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        tvPhoneNumber = (TextView) findViewById(R.id.phone_number);
        tvFamilyName = (TextView) findViewById(R.id.family_name);
        tvName = (TextView) findViewById(R.id.name);
        register = (Button) findViewById(R.id.register);
        moveToLogin = (TextView) findViewById(R.id.loginUser);
        progressBar = findViewById(R.id.progressBar);
        ccp = findViewById(R.id.countryCodePicker);

    }

    private boolean checkNumber() {
        number = tvPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            tvPhoneNumber.setError("Enter number");
            return false;
        } else if (number.length() < 8) {
            tvPhoneNumber.setError("Enter valid number");
            return false;
        } else {
            tvPhoneNumber.setError(null);

            return true;
        }
    }

    private void sendOTP(String phoneNumber) {
        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(40L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(RegisterActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    if (e instanceof FirebaseAuthInvalidCredentialsException)
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    else if (e instanceof FirebaseTooManyRequestsException)
                        Toast.makeText(RegisterActivity.this, "The SMS quota for the project has been exceeded ", Toast.LENGTH_LONG).show();


                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);


                }


                @Override
                public void onCodeSent(@NonNull String s,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    Toast.makeText(RegisterActivity.this, "Verification code sent..", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, VerifyCodeActivity.class);
                    intent.putExtra("OTP", s);
                    intent.putExtra("family_name", tvFamilyName.getText().toString());
                    intent.putExtra("name", tvName.getText().toString());
                    Log.d(TAG, s);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);


                }
            };


}