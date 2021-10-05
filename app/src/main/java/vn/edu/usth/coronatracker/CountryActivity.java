package vn.edu.usth.coronatracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.coronatracker.adapter.CountryAdapter;
import vn.edu.usth.coronatracker.model.CountryModel;
import vn.edu.usth.coronatracker.services.CoronaApi;
import vn.edu.usth.coronatracker.services.RetrofitClient;

public class CountryActivity extends AppCompatActivity {
    private static final String TAG = "COUNTRY ACTIVITY";
    private List<CountryModel> countryLists;
    private CountryAdapter countryAdapter;
    private RecyclerView countryRecyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        initView();
        fetchData();

        searchView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countryAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.filter(newText);
                return false;
            }
        });

    }

    private void fetchData() {
        CoronaApi api = RetrofitClient.getClient().create(CoronaApi.class);
        Call<List<CountryModel>>call = api.getCountryLists();
        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if(response.body()!=null){
                    progressBar.setVisibility(View.VISIBLE);
                    countryLists=new ArrayList<>(response.body());

                    countryAdapter = new CountryAdapter(CountryActivity.this, response.body());
                    countryRecyclerView.setAdapter(countryAdapter);
                    countryAdapter.setCountryLists(countryLists);
                    countryAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    Log.i(TAG, "Successful");
                }

            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {

            }
        });

    }


    private void initView() {
        searchView = findViewById(R.id.search_view);
        progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        countryRecyclerView = (RecyclerView)findViewById(R.id.countryList);
        countryRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        countryRecyclerView.setLayoutManager(manager);
    }
}