package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	EditText edtxtEmail;
	EditText edtxtPassword;
	Button btnLogin;
	long check;
	String email;
	String password;
	Context baseContext;
	DBHelper DB = null;
	DataAccess dao;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        btnLogin = (Button)findViewById(R.id.login);
        edtxtEmail = (EditText)findViewById(R.id.email_txt);
		edtxtPassword = (EditText)findViewById(R.id.password_txt);
		
        btnLogin.setOnClickListener(this);
        dao = new DataAccess(this);
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
		 boolean check = dao.ValidateLoginCredentials(email, password);
		 if(check == true)
		 {
			 Intent in = new Intent(getBaseContext(), HomeScreen.class);
			 startActivity(in);
			 finish();
		 }
		 else
		 {
			 Toast.makeText(getApplicationContext(), "Login failed. Try again.", Toast.LENGTH_SHORT).show();
		 }
	 }   
}
