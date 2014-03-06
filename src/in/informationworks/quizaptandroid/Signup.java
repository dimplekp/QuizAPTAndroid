package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity implements OnClickListener {

	EditText edtxtName;
	EditText edtxtEmail;
	EditText edtxtPassword;
	EditText edtxtConfirmPassword;
	Button btnSignup;
	final Activity registerActivity = this;
	final Context registerContext = this;
	String name;
	String email;
	String pass;
	DataAccess dao;
	SPAccess spa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		dao = new DataAccess(this);
		spa = new SPAccess(this);
		
		edtxtName = (EditText)findViewById(R.id.fullname_txt);		
		edtxtEmail = (EditText)findViewById(R.id.email_txt);		
		edtxtPassword = (EditText)findViewById(R.id.password_txt);		
		edtxtConfirmPassword = (EditText) findViewById(R.id.edtxtConfirmPassword);
		btnSignup = (Button) findViewById(R.id.signup);
		btnSignup.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		boolean UserStatus = validateSignupInput();

		if(UserStatus == true) {
			Intent loginPage = new Intent(Signup.this, HomeScreen.class);
			startActivity(loginPage);
			finish();
		}
		
		else {
			//Do nothing
		}
	}
	
	/*
	 Checks if all the fields required for sign up are entered or not.
	 */
	private boolean validateSignupInput() {
		
		boolean valid = true;
		
		name = edtxtName.getText().toString();
		pass = edtxtPassword.getText().toString();
		email = edtxtEmail.getText().toString();

		final String password = edtxtPassword.getText().toString().trim();
		
		if(name.trim().equals("") || pass.equals("") || email.equals("") || password.compareTo(edtxtConfirmPassword.getText().toString().trim())!=0) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter all the details properly", Toast.LENGTH_SHORT).show();
		}
		else {
			valid = true;
			addEntry(name, email, pass);					
		}
		
		return valid;
	}
	
	/*
	 If user already exists with the email id entered, error message is displayed. If not, User gets added to the database.
	 */
	private void addEntry(String name, String email, String pass) {	
		long userId = 0;
		boolean check = dao.CheckIfUserAlreadyExist(email);
		if(check == true) {
			Toast.makeText(this.getApplicationContext(), "User with this email id already exist", Toast.LENGTH_SHORT).show();
		}
		else {
			userId = dao.insertUser(name,email,pass);
			int id = (int) (long) userId;
			spa.saveId(id);
		}
	}
}