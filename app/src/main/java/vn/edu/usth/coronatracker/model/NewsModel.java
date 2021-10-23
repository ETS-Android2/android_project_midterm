package vn.edu.usth.coronatracker.model;

public class NewsModel {
    private String title, desc, date, image, url;

    public NewsModel(String title, String desc, String date, String image, String url) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.image = image;
        this.url=url;
    }

    public NewsModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
