package vn.edu.usth.coronatracker.model;

public class SymptomModel {

    private String symptomText;
    private int image;

    public SymptomModel(String symptomText, int image) {
        this.symptomText = symptomText;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

