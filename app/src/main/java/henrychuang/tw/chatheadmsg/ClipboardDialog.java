package henrychuang.tw.chatheadmsg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;


/**
 * Created by Intersect on 3/1/2017.
 */
public class ClipboardDialog extends Activity {
    public static boolean active = false;
    public static Activity myClipboard;
    public static String clipBoardText;
    EditText edt;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        onNewIntent(getIntent());
        setContentView(R.layout.clipboard_dialog);

        edt = (EditText) findViewById(R.id.edit_tag);
        btn = (Button) findViewById(R.id.save_info);

        myClipboard = ClipboardDialog.this;

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String str = edt.getText().toString();
                if(clipBoardText.contains("http")){
                    Queries.saveClipboardData(clipBoardText, str);
                    finish();
                } else {
                    if (clipBoardText.length() > 0) {
                        BackgroundMail bm = new BackgroundMail(ClipboardDialog.this);
                        bm.setGmailUserName("alx.dobrin@gmail.com");
                        bm.setGmailPassword("alexandru333");
                        bm.setMailTo("do@any.do");
                        bm.setFormSubject(str);
                        bm.setFormBody(clipBoardText);
                        bm.send();
                    }
                }
            }
        });


    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            clipBoardText = extras.getString("text");
            Log.v("clipboad", clipBoardText);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        active = true;

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        active = false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        active = false;
    }
}