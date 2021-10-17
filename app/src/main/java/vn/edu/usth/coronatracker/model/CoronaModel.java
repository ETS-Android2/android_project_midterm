package vn.edu.usth.coronatracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoronaModel {
    @SerializedName("updated")
    private long updated;
    @SerializedName("cases")
    private String cases;
    @SerializedName("todayCases")
    private String todayCases;
    @SerializedName("deaths")
    private String deaths;
    @SerializedName("todayDeaths")
    private String todayDeaths;
    @SerializedName("recovered")
    private String recovered;
    @SerializedName("todayRecovered")
    private String todayRecovered;
    @SerializedName("active")
    private String active;
    @SerializedName("critical")
    private String critical;
    @SerializedName("casesPerOneMillion")
    private String casesPerOneMillion;
    @SerializedName("deathsPerOneMillion")
    private String deathsPerOneMillion;
    @SerializedName("tests")
    private String tests;
    @SerializedName("testsPerOneMillion")
    private Double testsPerOneMillion;
    @SerializedName("affectedCountries")
    private String affectedCountries;

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

    public String getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(String casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public String getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(String deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public Double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(Double testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public String getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(String affectedCountries) {
        this.affectedCountries = affectedCountries;
    }

    @Override
    public String toString() {
        return "AllCorornaResult{" +
                "updated=" + updated +
                ", cases='" + cases + '\'' +
                ", todayCases='" + todayCases + '\'' +
                ", deaths='" + deaths + '\'' +
                ", todayDeaths='" + todayDeaths + '\'' +
                ", recovered='" + recovered + '\'' +
                ", active='" + active + '\'' +
                ", critical='" + critical + '\'' +
                ", casesPerOneMillion='" + casesPerOneMillion + '\'' +
                ", deathsPerOneMillion='" + deathsPerOneMillion + '\'' +
                ", tests='" + tests + '\'' +
                ", testsPerOneMillion=" + testsPerOneMillion +
                ", affectedCountries='" + affectedCountries + '\'' +
                '}';
    }
}
