package videosearchapp.com.videosearchapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * Created by Ankita on 6/14/2018.
 */

public class Activity_VideoPlayer extends AppCompatActivity  {
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    ImageButton img_upvote,img_downvote;
    TextView txt_upvote,txt_downvote;
    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private ImageView hideVideo;
    String video_url;
    List<Integer> listUpVote=new ArrayList<>();
    List<Integer> listDownVote=new ArrayList<>();
    private Box<VideoViews> viewsBox;
    private Query<VideoViews> viewUpvoteQuery;
    private Query<VideoViews> viewDownvoteQuery;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        setToolbar();
        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        window = new Timeline.Window();
        Intent i=getIntent();
        video_url=i.getStringExtra("VideoUrl");
        img_upvote = (ImageButton) findViewById(R.id.img_upvote);
        img_downvote = (ImageButton) findViewById(R.id.img_downvote);
        txt_upvote = (TextView) findViewById(R.id.txt_upvote);
        txt_downvote = (TextView) findViewById(R.id.txt_downvote);

        BoxStore boxStore = ((VideoSearchApplication) getApplication()).getBoxStore();
        viewsBox = boxStore.boxFor(VideoViews.class);

        viewUpvoteQuery = viewsBox.query().equal(VideoViews_.url,video_url).order(VideoViews_.increaseview).build();
        viewDownvoteQuery = viewsBox.query().equal(VideoViews_.url,video_url).order(VideoViews_.decreaseview).build();
        getData();

        img_upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getUpVote();

            }
        });

        img_downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDownVote();
            }
        });

    }

    private void getDownVote() {
        VideoViews videoViews = new VideoViews();
        int decreaseVote[] = getData();
        videoViews.setUrl(video_url);
        videoViews.setDecreaseview(decreaseVote[1] + 1);
        viewsBox.put(videoViews);
        List<VideoViews> downids = viewDownvoteQuery.find();
        txt_downvote.setText(String.valueOf(downids.size()));

    }

    private void initializePlayer() {

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.video_player);

        simpleExoPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(shouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(video_url),
                mediaDataSourceFactory, extractorsFactory, null, null);

        player.prepare(mediaSource);


    }
    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public void getUpVote() {
            VideoViews videoViews = new VideoViews();
            int increaseVote[] = getData();
            videoViews.setUrl(video_url);
            videoViews.setIncreaseview(increaseVote [0]+ 1);
            viewsBox.put(videoViews);

        List<VideoViews> upids = viewUpvoteQuery.find();
        txt_upvote.setText(String.valueOf(upids.size()));
    }
    public int[] getData(){
        int data[] = new int[2];
        List<VideoViews> upids = viewUpvoteQuery.find();
        List<VideoViews> downids = viewDownvoteQuery.find();
        txt_upvote.setText(String.valueOf(upids.size()));
        txt_downvote.setText(String.valueOf(downids.size()));
        if(upids.size() == 0 || downids.size() == 0){
            data[0]=0;
            data[1]=0;
        }else {
            data[0]=upids.size();
            data[1]=downids.size();
        }
        return data;
    }
    public void setToolbar(){
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView) toolbar.findViewById(R.id.title);
        textView.setText("Play Video");
        setSupportActionBar(toolbar);

    }
}
