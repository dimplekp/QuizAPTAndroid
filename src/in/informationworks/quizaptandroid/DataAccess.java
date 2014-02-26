package in.informationworks.quizaptandroid;

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
	
	public DataAccess(Context context) {
		dbHelper = new DBHelper(context);
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
	
	public int ValidateCredentialAndGetId(String email, String password) {
		int user_id = -1;
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
	
	public User getUserById(int userId) { 
		
		User user = null;
		
		// open db
		
		// user retrive 
		
		// return
		
		return user;
		
	}
	
	public int getUserid() {
		int userId = -1;
		try {
			db = dbHelper.getReadableDatabase();
			if (db != null) {
				Cursor cursor = db.rawQuery("select _id from "
						+ DBHelper.USERS_TABLE_NAME, null);
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
	
	public User getUser(int userId) {
		User user = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select name from "
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

	public boolean isRegistered(int userId) {
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
	
	
	
	public User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		user.setName(cursor.getString(cursor.getColumnIndex("name")));
		user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
		return user;
	}	
}