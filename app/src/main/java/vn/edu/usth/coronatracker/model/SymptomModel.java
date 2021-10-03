package vn.edu.usth.coronatracker.model;

public class SymptomModel {

    private String symptomText;
    private String symptomDetail;
    private int image;

    public SymptomModel(String symptomText, String symptomDetail, int image) {
        this.symptomText = symptomText;
        this.symptomDetail = symptomDetail;
        this.image = image;
    }

    public SymptomModel() {
    }

    public String getSymptomText() {
        return symptomText;
    }

    public void setSymptomText(String symptomText) {
        this.symptomText = symptomText;
    }

    public String getSymptomDetail() {
        return symptomDetail;
    }

    public void setSymptomDetail(String symptomDetail) {
        this.symptomDetail = symptomDetail;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
