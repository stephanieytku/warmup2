package com.cs169.warmupapp;
//Stephanie Ku
//CS169-BC
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cs169.warmupapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity
{
	String TAG = "MainActivity";
	String LoginURL = "http://pacific-spire-3209.herokuapp.com/users/login";
	String AddURL = "http://pacific-spire-3209.herokuapp.com/users/add";
	EditText userField, pwField;
	Button loginB, addB;
	TextView toShowUserResponse;
	final int SUCCESS               =   1;  //a success
	final int ERR_BAD_CREDENTIALS   =  -1;  //(for login only) cannot find the user/password pair in the database
	final int ERR_USER_EXISTS       =  -2;  //(for add only) trying to add a user that already exists
	final int ERR_BAD_USERNAME      =  -3;  //(for add, or login) invalid user name (only empty string is invalid for now)
	final int ERR_BAD_PASSWORD      =  -4;	//bad password

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d(TAG,"In onCreate()");

		userField = (EditText)findViewById(R.id.usernameBlank);
		pwField = (EditText)findViewById(R.id.passwordBlank);
		loginB = (Button)findViewById(R.id.loginButton);
		addB = (Button)findViewById(R.id.addButton);
		toShowUserResponse = (TextView)findViewById(R.id.responseText);
	

		//Login Button
		loginB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					Log.d(TAG,"Login Button Pressed");
					JSONObject postData = new JSONObject();
					postData.put("user", userField.getText().toString());
					postData.put("password", pwField.getText().toString());
					Log.d(TAG,"postData created");
					JSONObject obj = HTTPClient.sendHttpPost(LoginURL, postData);
					Log.d(TAG,"Retrieved obj");
					if(obj.get("errCode").toString().equals("1")){
						//Launch Intent with count and username
						Intent launchSuccessIntent = new Intent(getBaseContext(), SuccessActivity.class);
						launchSuccessIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						launchSuccessIntent.putExtra("user", userField.getText().toString());
						launchSuccessIntent.putExtra("count", obj.get("count").toString());
						getBaseContext().startActivity(launchSuccessIntent);
					}
					else{
						//show user response of error code
						toShowUserResponse.setText(lookupErrCode(obj.get("errCode").toString()));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.d(TAG,e.getMessage());
				}
				 catch (Exception e) {
						// TODO Auto-generated catch block
					 Log.d(TAG,e.getMessage());
					}

			}

		});

		//Add Button
		addB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					Log.d(TAG,"Add Button Pressed");
					JSONObject postData = new JSONObject();
					postData.put("user", userField.getText().toString());
					postData.put("password", pwField.getText().toString());
					Log.d(TAG,"postData created");
					JSONObject obj = HTTPClient.sendHttpPost(AddURL, postData);
					Log.d(TAG,"Retrieved obj");
					if(obj.get("errCode").toString().equals("1")){
						//Launch Intent with count and username
						Intent launchSuccessIntent = new Intent(getBaseContext(), SuccessActivity.class);
						launchSuccessIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						launchSuccessIntent.putExtra("user", userField.getText().toString());
						launchSuccessIntent.putExtra("count", obj.get("count").toString());
						getBaseContext().startActivity(launchSuccessIntent);
					}
					else{
						toShowUserResponse.setText(lookupErrCode(obj.get("errCode").toString()));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.d(TAG,e.getMessage());
				}
				 catch (Exception e) {
						// TODO Auto-generated catch block
					 Log.d(TAG,e.getMessage());
					}
			}
		});

	}

	protected String lookupErrCode(String code){
		try{
			int codeNum = Integer.parseInt(code);
			switch(codeNum){
			case ERR_BAD_CREDENTIALS:
				return "Sorry! Bad Credentials";
			case ERR_USER_EXISTS:
				return "Sorry! User already exists";
			case ERR_BAD_USERNAME:
				return "Sorry! Bad username";
			case ERR_BAD_PASSWORD:
				return "Sorry! Bad password";

			}
		}
		catch(Exception e){
			System.out.println(e.getMessage().toString());
		}
		return "";
	}

	@Override
	protected void onResume()
	{
		super.onResume();

	}

	@Override
	public void onPause()
	{
		super.onPause();

	}

}
