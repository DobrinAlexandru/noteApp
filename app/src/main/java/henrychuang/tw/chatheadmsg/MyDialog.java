package henrychuang.tw.chatheadmsg;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;


public class MyDialog extends Activity {
	public static boolean active = false;
	public static Activity myDialog;
	
	EditText edt;
	Button btn;
	View top;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		onNewIntent(getIntent());
		setContentView(R.layout.dialog);
		
		edt = (EditText) findViewById(R.id.dialog_edt);
		btn = (Button) findViewById(R.id.dialog_btn);
		top = (View)findViewById(R.id.dialog_top);
				
		myDialog = MyDialog.this;
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = edt.getText().toString();
				if(str.length() > 0){
					/*BackgroundMail.newBuilder(MyDialog.this)
							.withUsername("alx.dobrin@gmail.com")
							.withPassword("alexandru333")
							.withMailto("do@any.do")
							.withType(BackgroundMail.TYPE_PLAIN)
							.withSubject(str)
							.withBody(str)
							.send();*/
					BackgroundMail bm = new BackgroundMail(MyDialog.this);
					bm.setGmailUserName("alx.dobrin@gmail.com");
					bm.setGmailPassword("alexandru333");
					bm.setMailTo("do@any.do");
					bm.setFormSubject(str);
					bm.setFormBody(str);
					bm.send();
//					ChatHeadService.showMsg(MyDialog.this, str);
				}
			}
		});
		
		
		top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	public void onNewIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		if(extras != null){
			String str = extras.getString("text");
			BackgroundMail bm = new BackgroundMail(MyDialog.this);
			bm.setGmailUserName("alx.dobrin@gmail.com");
			bm.setGmailPassword("alexandru333");
			bm.setMailTo("do@any.do");
			bm.setFormSubject(str);
			bm.setFormBody(str);
			bm.send();
			finish();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		edt.requestFocus();
		super.onResume();
		active = true;
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

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