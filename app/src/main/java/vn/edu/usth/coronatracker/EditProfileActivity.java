package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;

import vn.edu.usth.coronatracker.databinding.ActivityEditProfileBinding;
import vn.edu.usth.coronatracker.model.User;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding activityEditProfileBinding;
    private FirebaseUser fUser;
    private StorageTask uploadTask;
    private StorageReference storageRef;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Uri mImageUri;
    private DatePickerDialog picker;
    private String url;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(activityEditProfileBinding.getRoot());
        initFirebase();
        setInfo();
        setSupportActionBar(activityEditProfileBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityEditProfileBinding.date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(EditProfileActivity.this, R.style.SpinnerDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                activityEditProfileBinding.date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        activityEditProfileBinding.changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);
            }
        });
        activityEditProfileBinding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);
            }
        });
        activityEditProfileBinding.saveInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                uploadInfo();
                finish();

            }
        });
    }

    private void setInfo() {
        mRef.child("Users").child(fUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                activityEditProfileBinding.familyName.setText(user.getFamilyName());
                activityEditProfileBinding.name.setText(user.getName());
                activityEditProfileBinding.phoneNumber.setText(user.getPhoneNumber());
                activityEditProfileBinding.idNumber.setText(user.getIdCard());
                activityEditProfileBinding.date.setText(user.getDateOfBirth());
                if (user.getGender().equals("Male")) {
                    activityEditProfileBinding.simpleRadioButton.setChecked(true);
                } else {
                    activityEditProfileBinding.simpleRadioButton1.setChecked(true);
                }
                Picasso.get().load(user.getImageurl()).into(activityEditProfileBinding.imageProfile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference().child("profile_images");
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    private void uploadInfo() {
        activityEditProfileBinding.progressBar.setVisibility(View.VISIBLE);
        if (mImageUri != null) {
            final StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "_" + fUser.getUid());
            uploadTask = fileRef.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        url = downloadUri.toString();
                        int selectedId = activityEditProfileBinding.radioGroup.getCheckedRadioButtonId();
                        radioButton = findViewById(selectedId);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("familyName", activityEditProfileBinding.familyName.getText().toString());
                        map.put("name", activityEditProfileBinding.name.getText().toString());
                        map.put("idCard", activityEditProfileBinding.idNumber.getText().toString());
                        map.put("imageurl", url);
                        map.put("gender", radioButton.getText().toString());
                        map.put("dateOfBirth", activityEditProfileBinding.date.getText().toString());
                        mRef.child("Users").child(fUser.getUid()).updateChildren(map);
                        activityEditProfileBinding.progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();
            activityEditProfileBinding.imageProfile.setImageURI(mImageUri);
        } else {
            Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}