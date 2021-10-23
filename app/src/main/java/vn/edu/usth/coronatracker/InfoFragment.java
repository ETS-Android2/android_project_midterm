package vn.edu.usth.coronatracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class InfoFragment extends Fragment {
    public static final String TAG = "C";
    private Button learnMore;
    public InfoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info, container, false);
        initView(view);
        learnMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CoronaInfoActivity.class));
            }
        });
        return view;
    }

    private void initView(View view){
        learnMore=view.findViewById(R.id.button_learn_more);
    }
}