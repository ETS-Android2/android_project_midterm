package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

public class DashboardFragment extends Fragment {

    private TableRow covidTracker, covidMap;
    public static final String TAG = "DASHBOARD FRAGMENT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView(view);
        covidTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment = new GlobalFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(
//                        R.anim.slide_in_right,
//                        R.anim.slide_out_left,
//                        R.anim.slide_in_left,
//                        R.anim.slide_out_right
//                );
//                fragmentTransaction.replace(R.id.framelayout, fragment).addToBackStack(null).commit();
                MyApplication.addToBackStack(R.id.navigation_global);
                Navigation.findNavController(view).navigate(R.id.navigation_global);
            }
        });
//
        covidMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });
        return view;
    }

    private void initView(View view) {
        covidTracker = view.findViewById(R.id.covidTracker);
        covidMap = view.findViewById(R.id.covidMap);
    }
}