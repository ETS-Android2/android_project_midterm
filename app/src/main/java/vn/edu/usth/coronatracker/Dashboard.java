package vn.edu.usth.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;

public class Dashboard extends AppCompatActivity {

    private TableRow covidTracker, covidMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();

        covidTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, MainActivity.class));
            }
        });

        covidMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, MapsActivity.class));
            }
        });
    }

    private void initView() {
        covidTracker = findViewById(R.id.covidTracker);
        covidMap = findViewById(R.id.covidMap);
    }
}