package vn.edu.usth.coronatracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.edu.usth.coronatracker.CountryDetailActivity;
import vn.edu.usth.coronatracker.R;
import vn.edu.usth.coronatracker.model.CountryModel;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private static final String TAG = "COUNTRY ADAPTER";
    private List<CountryModel> countryLists;
    private List<CountryModel> countryCopy;
    private Context mContext;

    public CountryAdapter(Context mContext, List<CountryModel> countryLists) {
        this.countryLists = countryLists;
        this.mContext = mContext;
        this.countryCopy = new ArrayList<>();
        this.countryCopy.addAll(countryLists);
    }

    @NonNull
    @Override
    public CountryAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View studentView = inflater.inflate(R.layout.country_item, parent, false);
        CountryViewHolder viewHolder = new CountryViewHolder(studentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryViewHolder holder, int position) {
        CountryModel country = countryLists.get(position);
        holder.cases.setText(country.getCases());
        holder.recovered.setText(country.getRecovered());
        holder.deaths.setText(country.getDeaths());
        holder.country_name.setText(country.getCountry());
//        Log.i(TAG, country.getFlag());
        Picasso.get().load(country.getCountryInfo().getFlag()).into(holder.country_flag);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CountryDetailActivity.class);
                intent.putExtra("countryDetail", country);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryLists.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView cases, recovered, deaths, country_name;
        private ImageView country_flag;
        private LinearLayout linearLayout;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            cases = itemView.findViewById(R.id.cases);
            recovered = itemView.findViewById(R.id.recovered);
            deaths = itemView.findViewById(R.id.deaths);
            country_name = itemView.findViewById(R.id.country_name);
            country_flag = itemView.findViewById(R.id.countryFlag);
            linearLayout = itemView.findViewById(R.id.country_layout);
        }
    }

    public void setCountryLists(List<CountryModel> countryLists) {
        this.countryLists = countryLists;
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        countryLists.clear();
        if (charText.length() == 0) {
            countryLists.addAll(countryCopy);
        } else {
            for (CountryModel country : countryCopy) {
                if (country.getCountry().toLowerCase(Locale.getDefault()).startsWith(charText)) {
                    Log.i(TAG, country.getCountry().toLowerCase());
                    countryLists.add(country);
                }
            }
        }

        notifyDataSetChanged();
    }
}
