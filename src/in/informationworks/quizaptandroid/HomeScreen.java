package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.*;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends Activity {

	Button logoutButton;
	SPAccess spa;
	int userId;
	TextView user_idTxt;
	User userModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		spa = new SPAccess(this);
		
		// Get user_id from shared prefrences using SPAccess class.
		userId = spa.getUserId();
		
		//user_idTxt = (TextView) findViewById(R.id.user_id);
		//user_idTxt.setText(userId);
		
		logoutButton = (Button) findViewById(R.id.logout);
		logoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
					Intent signupIntent = new Intent(HomeScreen.this, MainActivity.class);
					startActivity(signupIntent);
			}
		});
	}
	
	@Override
	public void onBackPressed()
	{
	     moveTaskToBack(true);
	}
}
