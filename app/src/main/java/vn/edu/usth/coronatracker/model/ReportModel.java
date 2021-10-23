package vn.edu.usth.coronatracker.model;

public class ReportModel {
    private String patientName, patientAddress, reportPersonName, reportPersonMobile;
    private Double latitude, longitude;
    private String time;
    private boolean isVerified;

    public ReportModel(String patientName, String patientAddress, String reportPersonName, String reportPersonMobile, Double latitude, Double longitude, String time, boolean isVerified) {
        this.patientName = patientName;
        this.patientAddress = patientAddress;
        this.reportPersonName = reportPersonName;
        this.reportPersonMobile = reportPersonMobile;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time=time;
        this.isVerified = isVerified;
    }

    public ReportModel() {
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getReportPersonName() {
        return reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
    }

    public String getReportPersonMobile() {
        return reportPersonMobile;
    }

    public void setReportPersonMobile(String reportPersonMobile) {
        this.reportPersonMobile = reportPersonMobile;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
