package videosearchapp.com.videosearchapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import videosearchapp.com.videosearchapp.Activity_VideoPlayer;
import videosearchapp.com.videosearchapp.R;
import videosearchapp.com.videosearchapp.model.SearchModel;

/**
 * Created by Ankita on 6/13/2018.
 */

public class AdapterVideoDisplay extends BaseAdapter {
    Context c;
    ArrayList<SearchModel> searchModels;
    public AdapterVideoDisplay(Context context, ArrayList<SearchModel> searchModels) {
        this.c=context;
        this.searchModels=searchModels;
    }

    @Override
    public int getCount() {
        return searchModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.adapter_video_display,null);
        final SearchModel searchModel=searchModels.get(position);
        SimpleDraweeView videoImage= (SimpleDraweeView) convertView.findViewById(R.id.videoImage);
        videoImage.setImageURI(searchModel.url);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c,Activity_VideoPlayer.class);
                i.putExtra("VideoUrl",searchModel.mp4);
                c.startActivity(i);
            }
        });

        return convertView;
    }
}
