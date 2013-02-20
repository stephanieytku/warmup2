package com.cs169.warmupapp;
//Stephanie Ku
//CS169-BC
import com.cs169.warmupapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends Activity {
	String TAG = "SuccessActivity";
	TextView successMessage;
	Button logoutB;

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.success);
		successMessage= (TextView)findViewById(R.id.successResponse);
		logoutB = (Button)findViewById(R.id.logoutButton);
		Intent intent = getIntent();
		if (intent!=null){
			Log.d(TAG,"Intent not null");
			String ourUser=intent.getStringExtra("user");
			String count=intent.getStringExtra("count");

			Log.d("asdf","About to print success message");
			Log.d(TAG,"Welcome "+ourUser+"!\n You have logged in "+count+" times.");
			successMessage.setText("Welcome "+ourUser+"!\nYou have logged in "+count+" times.");
		}
		
		logoutB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				//Logout -> return to main activity
				Intent returnHomeIntent = new Intent(getApplicationContext(), com.cs169.warmupapp.MainActivity.class);
				startActivity(returnHomeIntent);

			}
		});
	}
}
