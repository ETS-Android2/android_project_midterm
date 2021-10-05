package vn.edu.usth.coronatracker.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.coronatracker.model.CoronaModel;
import vn.edu.usth.coronatracker.model.CountryModel;

public interface CoronaApi {

    String BASE_URL = "https://disease.sh/v3/covid-19/";

    @GET("all")
    Call<CoronaModel> getWorldCorona();

    @GET("countries")
    Call<List<CountryModel>>getCountryLists();


}
