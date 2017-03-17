package henrychuang.tw.chatheadmsg;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class BaseApp extends Application {

    private static BaseApp instance;

    public static BaseApp getApp() {
        return instance;
    }

    private static RequestQueue queue ;

    public BaseApp() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }

    public static void addQueue(JsonArrayRequest request){
        queue.add(request);
    }

    public static void addQueue(JsonObjectRequest request){
        queue.add(request);
    }

}