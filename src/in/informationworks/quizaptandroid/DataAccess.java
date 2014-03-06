package in.informationworks.quizaptandroid;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.quizaptandroid.DBHelper;
import in.informationworks.quizaptandroid.models.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DataAccess {

	private SQLiteDatabase db;
	private DBHelper dbHelper = null;
	Context context;
	SPAccess spa;
	
	public DataAccess(Context context) {
		dbHelper = new DBHelper(context);
		spa = new SPAccess(context);
	}
	
	public long insertUser(String name, String email, String pass) {
		long id = -1;
		try {
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("password", pass);
			values.put("email_id", email);
			
			db = dbHelper.getWritableDatabase();
			id = db.insert(DBHelper.USERS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	/*
	 Compare entered email id and password with the ones in database to check if they are correct or not.
	 */
	public boolean ValidateLoginCredentials(String email, String password) {
		 db = dbHelper.getReadableDatabase();
		 String[] columns = {"_id"};
		 String selection = "email_id=? AND password=?";
		 String[] selectionArgs = {email,password};
		 Cursor cursor = null;
	 	 cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
			 
	 	 int numberOfRows = cursor.getCount();
	 
	 	 if(numberOfRows <= 0)
	 	 {
	 		 return false;
	 	 }
	 	 else
	 		 return true;
	}
	
	public long ValidateCredentialAndGetId(String email, String password) {
		long user_id = -1;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {"_id"};
		String selection = "email_id=? AND password=?";
		String[] selectionArgs = {email,password};
		Cursor cursor = null;
		cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		
		int numberOfRows = cursor.getCount();
		
		if(numberOfRows <= 0)
	 	 {
	 		 return user_id;
	 	 }
	 	 else
	 	 {
	 		cursor.moveToFirst();
	 		user_id = cursor.getInt(cursor.getColumnIndex("_id"));
	    	return user_id;
	 	 }
	}
	/*
	 Checks before creating a new user if user with the same email id exists or not.
	 */
	public boolean CheckIfUserAlreadyExist(String email) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		 String[] columns = {"_id"};
		 String selection = "email_id=?";
		 String[] selectionArg = {email};
		 Cursor cursor = null;
	 	 cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArg, null, null, null);
	 	 
	 	 int numberOfRows = cursor.getCount();
	 	 
	 	 if(numberOfRows <= 0) {
	 		 return false;
	 	 }
	 	 else
	 		 return true;
	 }

	
	public long updateUser(ContentValues values, int userId) {
		long id = -1;
		try {
			db = dbHelper.getWritableDatabase();
			id = db.update(DBHelper.USERS_TABLE_NAME, values,
					BaseColumns._ID + "=" + userId, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public long getUserid(String email) {
		long userId = -1;
		db = dbHelper.getReadableDatabase();
		 String[] columns = {"_id"};
		 String selection = "email_id=?";
		 String[] selectionArgs = {email};
		 Cursor cursor = null;
		try {
			if (db != null) {
				cursor = db.query(DBHelper.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					userId = cursor.getInt(cursor.getColumnIndex("_id"));
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	public User getUser(long userId) {
		User user = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.USERS_TABLE_NAME + " where _id = '"
					+ String.valueOf(userId) + "'", null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				user = cursorToUser(cursor);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean isRegistered(long userId) {
		boolean isRegistered = false;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.USERS_TABLE_NAME + " where _id = '"
					+ String.valueOf(userId) + "'", null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				isRegistered = cursor.getString(
						cursor.getColumnIndex("registered")).equalsIgnoreCase(
						"1") ? true : false;
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRegistered;
	}
	    
	public Quiz getQuiz(long quizId) {
		Quiz quiz = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.QUIZZES_TABLE_NAME + " where _id = '"
					+ String.valueOf(quizId) + "'", null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				quiz = cursorToQuiz(cursor);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quiz;
	}
	
    public List<Quiz> getAllQuizzes() {
    	List<Quiz> quizList = new ArrayList<Quiz>();
    	try {
    		db = dbHelper.getReadableDatabase();
    		Cursor cursor = db.query(DBHelper.QUIZZES_TABLE_NAME, null, null, null, null, null, null);
    		cursor.moveToFirst();
    		while(!cursor.isAfterLast()) {
    			Quiz quiz = cursorToQuiz(cursor);
    			quizList.add(quiz);
    			cursor.moveToNext();
    		}
    		cursor.close();
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return quizList;
    }
    
    public int getTimeAllowed(long quizId) {
    	int cnt = -1;
		int timeAllowed = -1;
		db = dbHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			if (db != null) {
				
				cursor = db.rawQuery("select count(_id) as quecnt from "
						+ DBHelper.QUESTION_TABLE_NAME
						+ " where quiz_id = ?", new String[] { String.valueOf(quizId) });
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					cnt = cursor.getInt(cursor.getColumnIndex("quecnt"));
				}
				
				cursor = db.rawQuery("select time_allowed_per_question_in_minutes from " 
						+ DBHelper.QUIZZES_TABLE_NAME 
						+ " where _id = ?",
						new String[] { String.valueOf(quizId) });
				
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					timeAllowed = cursor.getInt(cursor.getColumnIndex("time_allowed_per_question_in_minutes"));
				}
				
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt*timeAllowed;
	}
    
    public int getNumberOfQuestionsInQuiz(Long quizId) {
		int cnt = -1;
		try {
			db = dbHelper.getReadableDatabase();
			if (db != null) {
				Cursor cursor = db.rawQuery("select count(_id) as quecnt from "
						+ DBHelper.QUESTION_TABLE_NAME
						+ " where quiz_id = ?", new String[] { String.valueOf(quizId) });
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					cnt = cursor.getInt(cursor.getColumnIndex("quecnt"));
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
    
	public User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		user.setName(cursor.getString(cursor.getColumnIndex("name")));
		user.setEmail(cursor.getString(cursor.getColumnIndex("email_id")));
		return user;
	}
	
	public Quiz cursorToQuiz(Cursor cursor) {
		Quiz quiz = new Quiz();
		quiz.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		quiz.setName(cursor.getString(cursor.getColumnIndex("name")));
		return quiz;
	}
}