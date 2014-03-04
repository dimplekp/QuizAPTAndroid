package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends Activity {

	Button logoutButton;
	int userId;
	TextView userIdTxt;
	TextView userNameTxt;

	SPAccess spa;
	DataAccess dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		spa = new SPAccess(this);
		dao = new DataAccess(this);
		
		// Get user_id from shared prefrences using SPAccess class.
		userId = spa.getUserId();
		
		User user = dao.getUser(userId);
		final String name = user.getName();
		
		//Display user id stored in SharedPreferences.
		userIdTxt = (TextView) findViewById(R.id.userId);
		userIdTxt.setText(String.valueOf(userId));
		
		//Display name of a user whose id is stored in SharedPreferences.
		userNameTxt = (TextView) findViewById(R.id.userName);
		userNameTxt.setText(name);
		
		logoutButton = (Button) findViewById(R.id.logout);
		logoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences preferences = getSharedPreferences("QUIZ_APT_PREFS", 0);
				preferences.edit().remove("USER_ID").commit();
				Intent signupIntent = new Intent(HomeScreen.this, ChooseLoginSignup.class);
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
