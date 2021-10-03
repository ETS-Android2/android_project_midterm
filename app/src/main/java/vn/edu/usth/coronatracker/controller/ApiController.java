//package vn.edu.usth.coronatracker.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import vn.edu.usth.coronatracker.model.CoronaModel;
//import vn.edu.usth.coronatracker.model.Country;
//import vn.edu.usth.coronatracker.model.CountryList;
//import vn.edu.usth.coronatracker.services.RetrofitClient;
//import android.util.Log;
//
//public class ApiController {
//
//    private CoronaModel coronaModel=new CoronaModel();
//    private static final String TAG="CORONA LOGGER";
//    private List<Country>countryLists;
//    public CoronaModel getWorldCorona(){
//        Call<CoronaModel> call = RetrofitClient.getInstance().getMyApi().getWorldCorona();
//        call.enqueue(new Callback<CoronaModel>() {
//            @Override
//            public void onResponse(Call<CoronaModel> call, Response<CoronaModel> response) {
//                if(!response.isSuccessful()){
//                    Log.i(TAG, "Error");
//                }else{
//                    if(response.body()!=null){
//                        coronaModel = response.body();
//                        Log.i(TAG, "Successful");
//                    }else{
//                        Log.i(TAG, "NULL");
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CoronaModel> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + call.toString() + t.getMessage());
//            }
//
//        });
//
//        return coronaModel;
//    }
//
//    public List<Country> getCountryLists(){
//        Call<CountryList>call=RetrofitClient.getInstance().getMyApi().getCountryLists();
//        call.enqueue(new Callback<CountryList>() {
//            @Override
//            public void onResponse(Call<CountryList> call, Response<CountryList> response) {
//                if(!response.isSuccessful()){
//                    Log.i(TAG, "Error");
//                }else{
//                    if(response.body()!=null){
//                        countryLists=response.body().getCountryLists();
//                        Log.i(TAG, "Successful");
//                    }else{
//                        Log.i(TAG, "NULL");
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CountryList> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + call.toString() + t.getMessage());
//            }
//        });
//        return countryLists;
//    }
//}
