package vn.edu.usth.coronatracker.model;


import java.util.List;

public class CountryList {

    private List<CountryModel> countryLists;

    public CountryList(List<CountryModel> countryLists) {
        this.countryLists = countryLists;
    }

    public List<CountryModel> getCountryLists() {
        return countryLists;
    }

    public void setCountryLists(List<CountryModel> countryLists) {
        this.countryLists = countryLists;
    }
}
