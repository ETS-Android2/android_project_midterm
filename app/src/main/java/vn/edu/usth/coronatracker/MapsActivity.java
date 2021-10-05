package vn.edu.usth.coronatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import net.sharewire.googlemapsclustering.Cluster;
import net.sharewire.googlemapsclustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.coronatracker.model.CoronaLocationModel;
import vn.edu.usth.coronatracker.model.CountryModel;
import vn.edu.usth.coronatracker.services.CoronaApi;
import vn.edu.usth.coronatracker.services.RetrofitClient;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private List<CountryModel> countryLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap google) {
        mMap = google;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

            }
        });

        ClusterManager<CoronaLocationModel> clusterManager = new ClusterManager<>(this, mMap);
        clusterManager.setCallbacks(new ClusterManager.Callbacks<CoronaLocationModel>() {
            @Override
            public boolean onClusterClick(@NonNull Cluster<CoronaLocationModel> cluster) {
                Log.d(TAG, "onClusterClick");
                return false;
            }

            @Override
            public boolean onClusterItemClick(@NonNull CoronaLocationModel clusterItem) {
                Log.d(TAG, "onClusterItemClick");
                return false;
            }
        });
        mMap.setOnCameraIdleListener(clusterManager);
        List<CoronaLocationModel> clusterItems = new ArrayList<>();
        fetchData(clusterItems, clusterManager);

    }

    private void fetchData(List<CoronaLocationModel> clusterItems, ClusterManager<CoronaLocationModel> clusterManager) {
        CoronaApi api = RetrofitClient.getClient().create(CoronaApi.class);
        Call<List<CountryModel>> call = api.getCountryLists();
        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if (response.body() != null) {
                    countryLists = new ArrayList<>(response.body());
                    Log.i(TAG, "Successful");
                    Log.i(TAG, countryLists.get(0).toString());
                    for (int i = 0; i < countryLists.size(); i++) {
                        LatLng latLng = new LatLng(countryLists.get(i).getCountryInfo().getLatitude(), countryLists.get(i).getCountryInfo().getLongitude());
                        ;
                        Log.i(TAG, String.valueOf(countryLists.get(i).getCountryInfo().getLatitude()));
                        clusterItems.add(new CoronaLocationModel(latLng,
                                "Cases: " + countryLists.get(i).getCases() +
                                        "  Deaths: " + countryLists.get(i).getDeaths()));
                    }
                    clusterManager.setItems(clusterItems);
                }

            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {

            }
        });
    }

}