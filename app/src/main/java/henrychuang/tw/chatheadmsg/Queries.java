package henrychuang.tw.chatheadmsg;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Intersect on 3/1/2017.
 */
public class Queries {
    public static Task<Void> saveClipboardData(String url, String tag) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        Map<String, Object> params = new HashMap<>();
        params.put("consumer_key",  "64156-e7f7d9ea4853739fb308f98c");
        params.put("access_token", "4f372bb8-a5b0-0ddd-921b-315fb7");
        params.put("url", url);
        params.put("time", System.currentTimeMillis());
        params.put("title", "panDO link");

        if(tag != null && tag.length() > 0){
            params.put("tags", tag);
        }

        String URL = "https://getpocket.com/v3/add";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tcs.setResult(null);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tcs.setException(error);
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("X-Accept", "application/json");
                return headers;
            }
        };

        req.setRetryPolicy(new DefaultRetryPolicy(2000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        BaseApp.addQueue(req);

        return tcs.getTask();
    }
}
