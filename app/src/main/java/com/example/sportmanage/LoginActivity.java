/**
 * 
 */
package com.example.sportmanage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends Activity {
	private EditText mUserName = null;
	private EditText mPassword = null;
	private TextView register = null;
	public static boolean bIsGuestLogin = false;// 是否游客登录，这个变量用于其他界面数据的处理

	ProgressDialog m_Dialog;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUserName = (EditText) findViewById(R.id.user_name);
		mPassword = (EditText) findViewById(R.id.password);
		register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener() {
			//跳转注册页面
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Uri uri = Uri.parse("tel:18764563501");

				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
*/
				Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);

			}
		});
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	public void guest_btn(View view) {
		bIsGuestLogin = true;
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, TourActivity.class);
		startActivity(intent);
		Toast.makeText(this, "您现在是以游客身份登录，登录成功", Toast.LENGTH_SHORT).show();
		//LoginActivity.this.finish();
	}

	@SuppressLint("NewApi")
	public void login_btn(View view) {
		bIsGuestLogin = false;

		if (("sl".equals(mUserName.getText().toString()) && "123456"
				.equals(mPassword.getText().toString())))// 判断输入的值是否正确
		{
			m_Dialog = ProgressDialog.show(this, "请等待...", "正在登录...", true);
			new Thread() {
				public void run() {
					try {
						SystemClock.sleep(2000);
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, TourActivity.class);
						startActivity(intent);
						LoginActivity.this.finish();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						m_Dialog.dismiss();
					}
				}
			}.start();
		} else if (mUserName.getText().toString().isEmpty()
				|| mPassword.getText().toString().isEmpty())// 判断是否输入相关值
		{
			AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle("登录")
					.setMessage("请输入相关值")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
													int which) {
									dialog.dismiss();
								}
							}).create();
			dialog.show();
		} else {
			new AlertDialog.Builder(LoginActivity.this).setTitle("登录失败")
					.setMessage("帐号或者密码不正确，\n请检查后重新输入！").create().show();
		}
	}

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("Login Page") // TODO: Define a title for the content shown.
				// TODO: Make sure this auto-generated URL is correct.
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}
}
