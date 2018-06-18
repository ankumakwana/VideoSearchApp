package videosearchapp.com.videosearchapp.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import okhttp3.OkHttpClient;
import videosearchapp.com.videosearchapp.R;

/**
 * Created by Ankita on 6/13/2018.
 */

public class Global {
    public static final String API = "ydxhsy1yVwretzZY96F9VFfX2pCeSyXk";
    public  static OkHttpClient okHttpClient=new OkHttpClient();
    private static boolean isShowing = false;
    private static Dialog mDialog;
   // public static OkHttpClient okHttpClient;

    public static void showProgressDialog(Context context) {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.custom_progress_dialog);
        ProgressBar progressBar = (ProgressBar)mDialog.findViewById(R.id.spin_kit);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }
    public static void dismissProgressDialog() {
        isShowing = false;
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }




}
