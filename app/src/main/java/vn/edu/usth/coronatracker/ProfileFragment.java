package vn.edu.usth.coronatracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import vn.edu.usth.coronatracker.databinding.FragmentProfileBinding;
import vn.edu.usth.coronatracker.model.User;


public class ProfileFragment extends Fragment {
    public static final String TAG = "PROFILE FRAGMENT";
    private FirebaseUser fUser;
    private DatabaseReference mRef;
    private FragmentProfileBinding fragmentProfileBinding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(getActivity().getLayoutInflater(), container, false);
        init();
        loadInfo();
        fragmentProfileBinding.relInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        fragmentProfileBinding.relLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        fragmentProfileBinding.relReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ReportActivity.class));
            }
        });


        return fragmentProfileBinding.getRoot();
    }

    private void loadInfo() {
        mRef.child("Users").child(fUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                fragmentProfileBinding.fullName.setText(user.getFamilyName() + "" + user.getName());
                fragmentProfileBinding.phoneNumber.setText(user.getPhoneNumber());
                Picasso.get().load(user.getImageurl()).into(fragmentProfileBinding.imageProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
    }
}