package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.coronatracker.adapter.CountryAdapter;
import vn.edu.usth.coronatracker.model.CountryModel;
import vn.edu.usth.coronatracker.services.CoronaApi;
import vn.edu.usth.coronatracker.services.RetrofitClient;

public class CountryFragment extends Fragment {
    public static final String TAG = "COUNTRY FRAGMENT";
    private List<CountryModel> countryLists;
    private CountryAdapter countryAdapter;
    private RecyclerView countryRecyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private ImageView casesAscended, casesDescended, recoveredDescended, recoveredAscended, deathsAscended, deathsDescended;
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        initView(view);
        fetchData();
        setHasOptionsMenu(true);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_sort));
        searchView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                searchView.onActionViewExpanded();
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
        setClickListener();
        return view;
    }

    private void fetchData() {
        CoronaApi api = RetrofitClient.getClient().create(CoronaApi.class);
        Call<List<CountryModel>> call = api.getCountryLists();
        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if (response.body() != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    countryLists = new ArrayList<>(response.body());
                    Log.d(TAG, countryLists.toString());

                    countryAdapter = new CountryAdapter(getContext(), countryLists);
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


    private void initView(View view) {
        casesDescended = view.findViewById(R.id.cases_descended);
        casesAscended = view.findViewById(R.id.cases_ascended);
        recoveredAscended = view.findViewById(R.id.recovered_ascended);
        deathsAscended = view.findViewById(R.id.deaths_ascended);
        deathsDescended = view.findViewById(R.id.deaths_descended);
        recoveredDescended = view.findViewById(R.id.recovered_descended);

        searchView = view.findViewById(R.id.search_view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_spinner);
        countryRecyclerView = (RecyclerView) view.findViewById(R.id.countryList);
        countryRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        countryRecyclerView.setLayoutManager(manager);
    }

    private void setClickListener() {
        casesAscended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(countryLists, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        if (Integer.parseInt(countryModel.getCases()) > Integer.parseInt(t1.getCases())) {
                            return -1;
                        } else {
                            return 1;
                        }

                    }
                });
                countryAdapter.notifyDataSetChanged();
            }
        });
        casesDescended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(countryLists, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        if (Integer.parseInt(countryModel.getCases()) > Integer.parseInt(t1.getCases())) {
                            return 1;
                        } else {
                            return -1;
                        }

                    }
                });
                countryAdapter.notifyDataSetChanged();
            }
        });
        recoveredAscended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(countryLists, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        if (Integer.parseInt(countryModel.getRecovered()) > Integer.parseInt(t1.getRecovered())) {
                            return -1;
                        } else {
                            return 1;
                        }

                    }
                });
                countryAdapter.notifyDataSetChanged();
            }
        });
        recoveredDescended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(countryLists, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        if (Integer.parseInt(countryModel.getRecovered()) > Integer.parseInt(t1.getRecovered())) {
                            return 1;
                        } else {
                            return -1;
                        }

                    }
                });
                countryAdapter.notifyDataSetChanged();
            }
        });
        deathsAscended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(countryLists, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        if (Integer.parseInt(countryModel.getDeaths()) > Integer.parseInt(t1.getDeaths())) {
                            return -1;
                        } else {
                            return 1;
                        }

                    }
                });
                countryAdapter.notifyDataSetChanged();
            }
        });
        deathsDescended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(countryLists, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        if (Integer.parseInt(countryModel.getDeaths()) > Integer.parseInt(t1.getDeaths())) {
                            return 1;
                        } else {
                            return -1;
                        }

                    }
                });
                countryAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "Fragment Country.onDestroyView() has been called.");
    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        inflater.inflate(R.menu.main_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//
//        switch (item.getItemId()) {
//            case R.id.sortCases:
//                Collections.sort(countryLists, new Comparator<CountryModel>() {
//                    @Override
//                    public int compare(CountryModel countryModel, CountryModel t1) {
//                        if (Integer.parseInt(countryModel.getCases()) > Integer.parseInt(t1.getCases())) {
//                            return -1;
//                        } else {
//                            return 1;
//                        }
//
//                    }
//                });
//                countryAdapter.notifyDataSetChanged();
//                return true;
//            case R.id.sortDeaths:
//                Collections.sort(countryLists, new Comparator<CountryModel>() {
//                    @Override
//                    public int compare(CountryModel countryModel, CountryModel t1) {
//                        if (Integer.parseInt(countryModel.getDeaths()) > Integer.parseInt(t1.getDeaths())) {
//                            return -1;
//                        } else {
//                            return 1;
//                        }
//
//                    }
//                });
//                countryAdapter.notifyDataSetChanged();
//
//                return true;
//            case R.id.sortAlphabet:
//                Collections.sort(countryLists, new Comparator<CountryModel>() {
//                    @Override
//                    public int compare(CountryModel countryModel, CountryModel t1) {
//                        return countryModel.getCountry().compareTo(t1.getCountry());
//
//                    }
//                });
//                countryAdapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}