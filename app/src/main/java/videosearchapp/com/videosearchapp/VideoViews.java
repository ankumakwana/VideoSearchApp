package videosearchapp.com.videosearchapp;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class VideoViews {
    @Id
    long id;
    int increaseview;
    int decreaseview;
    String url;

    public VideoViews(long id, int increaseview, int decreaseview,String url) {
        this.id = id;
        this.increaseview = increaseview;
        this.decreaseview = decreaseview;
        this.url = url;
    }

    public VideoViews() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIncreaseview() {
        return increaseview;
    }

    public void setIncreaseview(int increaseview) {
        this.increaseview = increaseview;
    }

    public int getDecreaseview() {
        return decreaseview;
    }

    public void setDecreaseview(int decreaseview) {
        this.decreaseview = decreaseview;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}