package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
	
	protected DBHelper DB = new DBHelper(Signup.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		edtxtName = (EditText)findViewById(R.id.fullname_txt);		
		edtxtEmail = (EditText)findViewById(R.id.email_txt);		
		edtxtPassword = (EditText)findViewById(R.id.password_txt);		
		edtxtConfirmPassword = (EditText) findViewById(R.id.edtxtConfirmPassword);
		btnSignup = (Button) findViewById(R.id.signup);
		btnSignup.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause(){
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){

		case R.id.signup:
			String name = edtxtName.getText().toString();
			String pass = edtxtPassword.getText().toString();
			String email = edtxtEmail.getText().toString();


			boolean invalid = false;
			final String password = edtxtPassword.getText().toString().trim();
			
			if(name.equals(""))	{
				invalid = true;
				Toast.makeText(getApplicationContext(), "Enter your Name", Toast.LENGTH_SHORT).show();
			}
			else
			
			if(pass.equals("")) {
				invalid = true;
				Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
			}
			else
				
			if(email.equals("")) {
				invalid = true;
				Toast.makeText(getApplicationContext(), "Please enter your Email ID", Toast.LENGTH_SHORT).show();
			}
			else
				
			if(password.compareTo(edtxtConfirmPassword.getText().toString().trim())!=0) {
				Toast.makeText(this.getApplicationContext(), "Password does not match.", Toast.LENGTH_SHORT).show();
			}
			else
				
			if(invalid == false) {
				addEntry(name, email, pass);
				Intent loginPage = new Intent(Signup.this, Login.class);
				startActivity(loginPage);
				finish();
			}
			break;
			}
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		DB.close();
	}

	private void addEntry(String name, String email, String pass) 
	{
		SQLiteDatabase db = DB.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("password", pass);
		values.put("email_id", email);

		try
		{
			db.insert(DBHelper.USERS_TABLE_NAME, null, values);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}