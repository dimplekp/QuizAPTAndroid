package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreen extends Activity {

	Button logoutButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
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
