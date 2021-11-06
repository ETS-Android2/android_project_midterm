package vn.edu.usth.coronatracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import vn.edu.usth.coronatracker.adapter.NewsAdapter;
import vn.edu.usth.coronatracker.constants.Constants;
import vn.edu.usth.coronatracker.model.NewsModel;

public class NewsFragment extends Fragment {
    public static final String TAG = "B";
    private ArrayList<NewsModel> newsList;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private Calendar calendar;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initView(view);
        fetchData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        return view;
    }

    private void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Constants.NEWSURL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.VISIBLE);
                        try {
                            JSONArray newsArray = response.getJSONArray("articles");
                            Log.i("NEWS FRAGMENT", newsArray.toString());
                            for (int i = 0; i < newsArray.length(); i++) {
                                JSONObject newsObj = newsArray.getJSONObject(i);
                                NewsModel news = new NewsModel();
                                news.setTitle(newsObj.getString("title"));
                                news.setDesc(newsObj.getString("description"));
                                news.setImage(newsObj.getString("urlToImage"));
                                news.setUrl(newsObj.getString("url"));
                                String publishedAt = newsObj.getString("publishedAt");
                                String date = publishedAt.substring(0, 10);
                                String time = publishedAt.substring(11, publishedAt.length() - 1);
                                String newsDate = date + " " + time;
                                news.setDate(newsDate);
                                Log.d(TAG, publishedAt);
                                newsList.add(news);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        newsAdapter = new NewsAdapter(getContext(), newsList);
                        newsRecyclerView.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                        Log.i("NEWS FRAGMENT", "Successful");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0");
                return params;
            }
        };
        ;


        queue.add(jsonObjectRequest);
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.loading_spinner);
        refreshLayout = view.findViewById(R.id.refresh_news);
        newsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_news);
        newsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(manager);
        newsList = new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "Fragment News.onDestroyView() has been called.");
    }
}