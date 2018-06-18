package videosearchapp.com.videosearchapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import io.objectbox.BoxStore;

public class VideoSearchApplication extends Application {
    private BoxStore boxStore;


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        boxStore = MyObjectBox.builder().androidContext(VideoSearchApplication.this).build();

    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
