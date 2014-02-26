package in.informationworks.quizaptandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPAccess {
	
	Context c;
	Editor editor;
	SharedPreferences pref;
	DataAccess dao = new DataAccess(c);
	
    public SPAccess(Context context)  {
    	c= context;
    	pref = c.getSharedPreferences("User id", 0);
    	editor = pref.edit();
	}
	
	public void saveId(int userId) {
		editor.putInt("USER_ID", userId);
		editor.commit();
	}
	
	public int getUserId() {
		return pref.getInt("USER_ID", 0);
	}
	
	/*public static final String MyPREFERENCES = "MyPrefs" ;
	public int user_id;
	Context context;
	public void SaveId(String key, int userId) {
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, userId);
		editor.commit(); 
	}
	*/
	
	/*
	public int LoadId(){
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
	    user_id = sharedPreferences.getInt("user id", 0);
	    return user_id;
	    */
}