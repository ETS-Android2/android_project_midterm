package vn.edu.usth.coronatracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryList {

//    @SerializedName("countryLists")
//    private Country[] countryLists;
//
//    public CountryList(Country[] countryLists) {
//        this.countryLists = countryLists;
//    }
//
//    public Country[] getCountryLists() {
//        return countryLists;
//    }
//
//    public void setCountryLists(Country[] countryLists) {
//        this.countryLists = countryLists;
//    }
    @SerializedName("countryLists")
    private List<Country> countryLists;

    public CountryList(List<Country> countryLists) {
        this.countryLists = countryLists;
    }

    public List<Country> getCountryLists() {
        return countryLists;
    }

    public void setCountryLists(List<Country> countryLists) {
        this.countryLists = countryLists;
    }
}
