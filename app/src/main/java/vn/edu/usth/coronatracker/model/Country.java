package vn.edu.usth.coronatracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {
    @SerializedName("updated")
    @Expose
    private long updated;
    @SerializedName("cases")
    @Expose
    private String cases;
    @SerializedName("todayCases")
    @Expose
    private String todayCases;
    @SerializedName("deaths")
    @Expose
    private String deaths;
    @SerializedName("todayDeaths")
    @Expose
    private String todayDeaths;
    @SerializedName("recovered")
    @Expose
    private String recovered;
    @SerializedName("todayRecovered")
    @Expose
    private String todayRecovered;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("critical")
    @Expose
    private String critical;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("flag")
    @Expose
    private String flag;

    @SerializedName("lat")
    @Expose
    private double latitude;

    @SerializedName("long")
    @Expose
    private double longitude;

    public Country(long updated, String cases, String todayCases, String deaths, String todayDeaths, String recovered, String todayRecovered, String active, String critical, String country, String flag, double latitude, double longitude) {
        this.updated = updated;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.critical = critical;
        this.country = country;
        this.flag = flag;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Country() {
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Country{" +
                "updated=" + updated +
                ", cases='" + cases + '\'' +
                ", todayCases='" + todayCases + '\'' +
                ", deaths='" + deaths + '\'' +
                ", todayDeaths='" + todayDeaths + '\'' +
                ", recovered='" + recovered + '\'' +
                ", todayRecovered='" + todayRecovered + '\'' +
                ", active='" + active + '\'' +
                ", critical='" + critical + '\'' +
                ", country='" + country + '\'' +
                ", flag='" + flag + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
