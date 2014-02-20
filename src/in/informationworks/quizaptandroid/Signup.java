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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		dao = new DataAccess(this);
		
		edtxtName = (EditText)findViewById(R.id.fullname_txt);		
		edtxtEmail = (EditText)findViewById(R.id.email_txt);		
		edtxtPassword = (EditText)findViewById(R.id.password_txt);		
		edtxtConfirmPassword = (EditText) findViewById(R.id.edtxtConfirmPassword);
		btnSignup = (Button) findViewById(R.id.signup);
		btnSignup.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		validateLoginInput();	
		addEntry(name, email, pass);
		Intent loginPage = new Intent(Signup.this, HomeScreen.class);
		startActivity(loginPage);
		finish();
	}
	
	/*
	 Checks if all the fields required for sign up are entered or not.
	 */
	private boolean validateLoginInput() {
		
		boolean valid = false;
		
		name = edtxtName.getText().toString();
		pass = edtxtPassword.getText().toString();
		email = edtxtEmail.getText().toString();

		final String password = edtxtPassword.getText().toString().trim();
		
		if(name.equals(""))	{
			valid = false;
			Toast.makeText(getApplicationContext(), "Enter your Name", Toast.LENGTH_SHORT).show();
		}
		else
		
		if(pass.equals("")) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
		}
		else
			
		if(email.equals("")) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter your Email ID", Toast.LENGTH_SHORT).show();
		}
		else
			
		if(password.compareTo(edtxtConfirmPassword.getText().toString().trim())!=0) {
			Toast.makeText(this.getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
		}
		return valid;
	}
	
	/*
	 If user already exists with the email id entered, error message is displayed. If not, User gets added to the database.
	 */
	private void addEntry(String name, String email, String pass) {	
		boolean check = dao.CheckIfUserAlreadyExist(email);
		if(check == true) {
			Toast.makeText(this.getApplicationContext(), "User with this email id already exist", Toast.LENGTH_SHORT).show();
		}
		else {
			dao.insertUser(name, email, pass);
		}
	}
}