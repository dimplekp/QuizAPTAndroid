package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseLoginSignup extends Activity {

	Button newUserButton;
	Button existingUserButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_login_signup);
		
		newUserButton = (Button) findViewById(R.id.button1);
		existingUserButton = (Button) findViewById(R.id.button2);
		
		newUserButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				Intent signupIntent = new Intent(ChooseLoginSignup.this, Signup.class);
				startActivity(signupIntent);
			}
		});
	
		existingUserButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				Intent signupIntent = new Intent(ChooseLoginSignup.this, Login.class);
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