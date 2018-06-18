package videosearchapp.com.videosearchapp.model;

import java.io.Serializable;

/**
 * Created by Ankita on 6/13/2018.
 */

public class SearchModel implements Serializable {
    public String url;
    public String width;
    public String height;
    public String size;
    public String frames;
    public String mp4;
    public String mp4_size;
    public String webp;
    public String webp_size;

    public SearchModel() {
    }

    public SearchModel(String url, String width, String height, String size, String frames, String mp4, String mp4_size, String webp, String webp_size) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.size = size;
        this.frames = frames;
        this.mp4 = mp4;
        this.mp4_size = mp4_size;
        this.webp = webp;
        this.webp_size = webp_size;
    }
}
