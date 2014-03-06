package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	DBHelper dbHelper;
	DataAccess dao;
	SPAccess sao;
	
	Boolean isUserLoggedin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		dao = new DataAccess(this);
		sao = new SPAccess(this);
		try{
			new Thread() {
				@Override
				public void run(){
					try {
						synchronized(this){
							dbHelper = new DBHelper(MainActivity.this);
							dbHelper.createDataBase();
							dbHelper.close();
						}
						sleep(2000);
					}
					catch(Exception ex){
						return;
					}
		
					isUserLoggedin = sao.isUserLoggedin();
					//If no user is logged in, open first activity
					if(isUserLoggedin == false) {
			
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, ChooseLoginSignup.class);
						startActivity(intent);
						finish();
						
					}
					//If user is already logged in, open Home screen directly
					else {
						
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, HomeScreen.class);
						startActivity(intent);
						finish();
	
					}
				}
			}.start();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
