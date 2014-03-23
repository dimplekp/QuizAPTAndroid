package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import in.informationworks.quizaptandroid.R;

public class Login extends Activity implements OnClickListener {

	EditText edtxtEmail;
	EditText edtxtPassword;
	Button btnLogin;
	long check;
	String email;
	String password;
	DBHelper DB = null;
	DataAccess dao;
	SPAccess spa;
	TextView signUpTextView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        btnLogin = (Button)findViewById(R.id.login);
        edtxtEmail = (EditText)findViewById(R.id.email_txt);
		edtxtPassword = (EditText)findViewById(R.id.password_txt);
		signUpTextView = (TextView) findViewById(R.id.signup);
		
        btnLogin.setOnClickListener(this);
        dao = new DataAccess(this);
        spa = new SPAccess(this);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
        	
			
			@Override
			public void onClick(View v) {
				Intent signupIntent = new Intent(Login.this, Signup.class);
				startActivity(signupIntent);
				
			}
		});
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.login:
			
			if (validateLoginInput()) {
				validateLogin(email, password);
			}
			break;
		}
	}
	
	/*
	 Checks if Email id and password are entered or not.
	 */
	private boolean validateLoginInput() {
		
		boolean valid = true;
		
		email = edtxtEmail.getText().toString();
		password = edtxtPassword.getText().toString();
		if(email.equals("") || email == null) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter your Email address", Toast.LENGTH_SHORT).show();
		}
		else if(password.equals("") || password == null) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
		}
		return valid;	
	}	
	/*
	 If login credentials matches, user gets logged in and if not, error message is displayed.
	 */
	 private void validateLogin(String email, String password) {
		 long user_id = dao.ValidateCredentialAndGetId(email, password);
		 if(user_id == -1)
		 {
			 Toast.makeText(getApplicationContext(), "Login failed. Try again.", Toast.LENGTH_SHORT).show();
		 }
		 else
		 {
			 // Store user_id in stored preferences using SPAccess class.
			 //spa.DisplayUserId(String.valueOf(user_id));
			 spa.saveId(user_id );
			 Intent intent = new Intent();
			 intent.setClass(Login.this, HomeScreen.class);
			 startActivity(intent);
			 finish();
		 }
	 }   
}