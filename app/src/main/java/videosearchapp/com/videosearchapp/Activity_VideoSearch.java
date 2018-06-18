package videosearchapp.com.videosearchapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.Response;
import videosearchapp.com.videosearchapp.adapter.AdapterVideoDisplay;
import videosearchapp.com.videosearchapp.common.Global;
import videosearchapp.com.videosearchapp.model.SearchModel;

public class Activity_VideoSearch extends AppCompatActivity implements View.OnClickListener {
    EditText etSearch;
    RelativeLayout btnSearch;
    GridView videoList;
    ArrayList<SearchModel> searchModels=new ArrayList<>();
    AdapterVideoDisplay adapterVideoDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__video_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etSearch= (EditText) findViewById(R.id.etSearch);
        btnSearch= (RelativeLayout) findViewById(R.id.btnSearch);
        videoList= (GridView) findViewById(R.id.videoList);

        btnSearch.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__video_search, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        new GetVideos().execute();
    }
    public class GetVideos extends AsyncTask<Void,String ,Void>{
        String Responce;
        @Override
        protected Void doInBackground(Void... params) {
            searchModels.clear();
            Request.Builder builder = new Request.Builder();
            builder.url("http://api.giphy.com/v1/gifs/search?q="+etSearch.getText().toString()+"&api_key="+ Global.API);
            Request request = builder.build();

            try {
                Response response = Global.okHttpClient.newCall(request).execute();
                Responce= response.body().string();
                JSONObject jsonObject=new JSONObject(Responce);

                JSONArray data=jsonObject.getJSONArray("data");


                for(int i=0;i<data.length();i++){
                    JSONObject obj=data.getJSONObject(i).getJSONObject("images");
                    JSONObject object=obj.getJSONObject("original");
                    SearchModel searchModel=new SearchModel(object.getString("url"),object.getString("width"),object.getString("height"),object.getString("size"),
                            object.getString("frames"), object.getString("mp4"),object.getString("mp4_size"),object.getString("webp"),object.getString("webp_size"));
                    searchModels.add(searchModel);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void rsult) {
            Global.dismissProgressDialog();
            // execution of result of Long time consuming operation
            Log.d("Response",searchModels.toString());

            adapterVideoDisplay=new AdapterVideoDisplay(Activity_VideoSearch.this,searchModels);
            videoList.setAdapter(adapterVideoDisplay);
        }


        @Override
        protected void onPreExecute() {
            Global.showProgressDialog(Activity_VideoSearch.this);
        }

    }
}
