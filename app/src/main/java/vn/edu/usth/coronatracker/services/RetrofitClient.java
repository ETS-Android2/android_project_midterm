package vn.edu.usth.coronatracker.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static vn.edu.usth.coronatracker.services.CoronaApi.BASE_URL;

public class RetrofitClient {

//    private static RetrofitClient instance = null;
//    private CoronaApi myApi;
    private static Retrofit retrofit = null;
    private CoronaApi myApi;
//    private RetrofitClient() {
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();
//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.interceptors().add(logging);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(myApi.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(httpClient.build())
//                .build();
//
//        myApi = retrofit.create(CoronaApi.class);
//    }

    public static Retrofit getClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(logging);

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
//
//    public static RetrofitClient getInstance() {
//        if (instance == null) {
//            instance = new RetrofitClient();
//        }
//        return instance;
//    }
//
//    public CoronaApi getMyApi() {
//        return myApi;
//    }
}