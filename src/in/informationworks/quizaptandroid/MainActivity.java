package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	DBHelper dbHelper;
	Button newUserButton;
	Button existingUserButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
            synchronized(this){
            	dbHelper = new DBHelper(MainActivity.this);
        		dbHelper.createDataBase();
        		dbHelper.close();
            }
        }
        catch(Exception ex){
           	return;
        }
		
		newUserButton = (Button) findViewById(R.id.button1);
		existingUserButton = (Button) findViewById(R.id.button2);
		
		newUserButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
					Intent signupIntent = new Intent(MainActivity.this, Signup.class);
					startActivity(signupIntent);
			}
		});
		
		existingUserButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
					Intent signupIntent = new Intent(MainActivity.this, Login.class);
					startActivity(signupIntent);
			}
		});
	}
}
