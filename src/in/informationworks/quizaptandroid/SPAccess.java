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
	
	public void saveId(long userId) {
		editor.putLong("USER_ID", userId);
		editor.commit();
	}
	
	public long getUserId() {
		return pref.getLong("USER_ID", 0);
	}
	
	public void saveAttemptId(long attemptId) {
		editor.putLong("ATTEMPT_ID", attemptId);
		editor.commit();
	}
	
	public long getAttemptId() {
		return pref.getLong("ATTEMPT_ID", 0);
	}
	
	public void saveQuizName(String QuizName) {
		editor.putString("Quiz_Name", QuizName);
		editor.commit();
	}
	
	public String getQuizName() {
		return pref.getString("Quiz_Name", null);
	}
	
	public boolean isUserLoggedin() {
		
		long value = pref.getLong("USER_ID",0);
		pref = c.getSharedPreferences("QUIZ_APT_PREFS", 0);
		if (value == 0) {
			return false;
		}
		else {
			return true; 	
		}
	}
	
	public boolean isQuizPending() {
		long value = pref.getLong("ATTEMPT_ID", 0);
		pref = c.getSharedPreferences("QUIZ_APT_PREFS", 0);
		if (value == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
}