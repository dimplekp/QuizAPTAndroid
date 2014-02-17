package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	EditText edtxtEmail;
	EditText edtxtPassword;
	Button btnLogin;
	
	DBHelper DB = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        btnLogin = (Button)findViewById(R.id.login);
        btnLogin.setOnClickListener(this); 
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.login:
			edtxtEmail = (EditText)findViewById(R.id.email_txt);
			edtxtPassword = (EditText)findViewById(R.id.password_txt);
			String email = edtxtEmail.getText().toString();
			String password = edtxtPassword.getText().toString();
			if(email.equals("") || email == null) {
				Toast.makeText(getApplicationContext(), "Please enter your Email address", Toast.LENGTH_SHORT).show();
			}
			else if(password.equals("") || password == null) {
				Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
			}
			else {
				boolean validLogin = validateLogin(email, password, getBaseContext());
				if(validLogin) {
					Intent in = new Intent(getBaseContext(), StartAQuiz.class);
					startActivity(in);
					finish();
				}
			}
			break;
		}
	}
		
	 private boolean validateLogin(String email, String password, Context baseContext) 
	 {
		 try{
			 DB = new DBHelper(getBaseContext());
			 SQLiteDatabase db = DB.getReadableDatabase();
			 String[] columns = {"_id"};
			 String selection = "email_id=? AND password=?";
			 String[] selectionArgs = {email,password};
			 Cursor cursor = null;
		 	 cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
				 
		 	 int numberOfRows = cursor.getCount();
		 
		 	 if(numberOfRows <= 0)
		 	 {
		 		 Toast.makeText(getApplicationContext(), "Email and Password miss match..\nPlease Try Again", Toast.LENGTH_LONG).show();
		 		 return false;
		 	 }
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	 }
	 
	 public void onDestroy()
	 {
		 super.onDestroy();
		 DB.close();
	 }    
}
