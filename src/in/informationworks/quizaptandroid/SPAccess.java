package in.informationworks.quizaptandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPAccess {
	
	Context c;
	Editor editor;
	SharedPreferences pref;
	
    public SPAccess(Context context)  {
    	c= context;
    	pref = c.getSharedPreferences("QUIZ_APT_PREFS", c.MODE_PRIVATE);
    	editor = pref.edit();
	}
	
	public void saveId(int userId) {
		editor.putInt("USER_ID", userId);
		editor.commit();
	}
	
	public int getUserId() {
		return pref.getInt("USER_ID", 0);
	}
	
	public void saveQuizName(String QuizName) {
		editor.putString("Quiz_Name", QuizName);
		editor.commit();
	}
	
	public String getQuizName() {
		return pref.getString("Quiz_Name", null);
	}
	
	public boolean isUserLoggedin() {
		
		int value = pref.getInt("USER_ID",0);
		pref = c.getSharedPreferences("QUIZ_APT_PREFS", 0);
		if (value == 0) {
			return false;
		}
		else {
			return true; 	
		}
	}
}