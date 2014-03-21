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
	
	//Insert new user in database.
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
	
	//Compare user's email and password for the validation of login and if they're valid, gets that user's id.
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
	
	//Checks if answer is correct or not
	public boolean checkCorrectnessOfAnswer(long optId) {
		boolean isCorrect = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {"correct"};
		String selection = "_id = ?";
		String[] selectionArg = {String.valueOf(optId)};
		
		Cursor cursor = null;
		cursor = db.query(DBHelper.OPTION_TABLE_NAME, columns, selection, selectionArg, null, null, null);
		if(cursor.getCount()  > 0) {
			cursor.moveToFirst();
			//trueOrFalse = cursor.getString(cursor.getColumnIndex("correct"));
			isCorrect = cursor.getString(cursor.getColumnIndex("correct")).equalsIgnoreCase("true");
		}
		cursor.close();
		return isCorrect;
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
	
	public boolean isOptionChecked(long optionId, long attemptId) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {"option_id"};
		String selection = "option_id=? AND attempt_id=?";
		String[] selectionArg = {String.valueOf(optionId), String.valueOf(attemptId)};
		Cursor cursor = null;
		cursor = db.query(DBHelper.ATTEMPT_DETAILS_TABLE_NAME, columns, selection, selectionArg, null, null, null);
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
	
	//Gets user's id
	
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
	
	public String getQuizName(long quizId) {
		String quizName = "";
		db = dbHelper.getReadableDatabase();
		String[] columns = {"name"};
		String selection = "_id=?";
		String[] selectionArgs = {String.valueOf(quizId)};
		Cursor cursor = null;
		try {
			if (db != null) {
				cursor = db.query(DBHelper.QUIZZES_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					quizName = cursor.getString(cursor.getColumnIndex("name"));
				}
					cursor.close();
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return quizName;
	}
	
	//Gets a list of Users
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

	//Gets Quiz name
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
	
	//Gets a list of Quizzes	
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
    
    //Gets no. of questions in the Quiz
    public int getNumberOfQuestionsInQuiz(long quizId) {
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
    
    public int getNumberOfOptionsInQuestion(long queId) {
    	int cnt = -1;
    	try {
    		db = dbHelper.getReadableDatabase();
    		if (db != null){
    			Cursor cursor = db.rawQuery("select count(_id) as optcnt from " 
    					+ DBHelper.OPTION_TABLE_NAME
    					+ " where que_id = ?", new String[] { String.valueOf(queId)});
    			if (cursor.getCount() > 0) {
    				cursor.moveToFirst();
    				cnt = cursor.getInt(cursor.getColumnIndex("optcnt"));
    			}
    			cursor.close();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return cnt;
    }
    
    public int getNoOfCorrectAnswers(long attemptId) {
    	int noOfCorrectAnswers = 0;
    	db = dbHelper.getReadableDatabase();
		
    	Cursor getCorrectAnswers = db.rawQuery("select count(correct) as noOfCorrectAnswers from "
    			+ DBHelper.OPTION_TABLE_NAME
    			+ " where _id in (select option_id from "
    			+ DBHelper.ATTEMPT_DETAILS_TABLE_NAME
    			+ " where attempt_id = " + String.valueOf(attemptId) + " ) AND correct = ?", new String[] { String.valueOf(true)});
    	if (getCorrectAnswers.getCount() > 0) {
    		getCorrectAnswers.moveToFirst();
    		noOfCorrectAnswers = getCorrectAnswers.getInt(getCorrectAnswers.getColumnIndex("noOfCorrectAnswers"));
		}
		return noOfCorrectAnswers;
    }
    
    public List<Option> getOptions(long queId) {
		List<Option> options = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db != null) {
				Cursor cursorGetOptions = db.query(DBHelper.OPTION_TABLE_NAME,
						null, "q_id = " + "'" + queId + "'", null, null, null,
						null);
				if (cursorGetOptions.getCount() > 0) {
					options = new ArrayList<Option>();
					cursorGetOptions.moveToFirst();
					while (!cursorGetOptions.isAfterLast()) {
						Option option = cursorToOption(cursorGetOptions);
						options.add(option);
						cursorGetOptions.moveToNext();
					}
				}
				cursorGetOptions.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}
    
    //Gets all the questions of selected quiz.
    public List<Question> getAllQuestions(long quizId) {
		List<Question> quesList = new ArrayList<Question>();
		db = dbHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select * from "
				+ DBHelper.QUESTION_TABLE_NAME
				+ " where quiz_id = ?", new String[] { String.valueOf(quizId) });
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
		do {
		Question quest = new Question();
		quest.setQueId(cursor.getLong(0));
		quest.setQuizId(cursor.getLong(1));
		quest.setQuestion(cursor.getString(2));
		
		quesList.add(quest);
		} while (cursor.moveToNext());
		}
		// return quest list
		return quesList;
	}
	
    public List<AttemptDetail> getOptionIdFromAttemptDetails(long attemptId) {
    	List<AttemptDetail> attemptDetailsOptionList = new ArrayList<AttemptDetail>();
    	db = dbHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select option_id from " 
    			+ DBHelper.ATTEMPT_DETAILS_TABLE_NAME
    			+ " where attempt_id = "+ String.valueOf(attemptId), null);
    	if(cursor.moveToFirst()) {
    		do {
    			AttemptDetail attemptDetailOptions = new AttemptDetail();
    			attemptDetailOptions.setOptionId(cursor.getLong(0));
    			attemptDetailsOptionList.add(attemptDetailOptions);
    		} while (cursor.moveToNext());
    	}
    	cursor.close();
    	return attemptDetailsOptionList;
    }
    
    public long deleteAttemptDetail(long attemptId, long optionId) {
		long id = -1;
		try {
			db = dbHelper.getReadableDatabase();
		
				String whereClause = "option_id=? AND attempt_id=?";
				String[] whereArgs = {String.valueOf(optionId), String.valueOf(attemptId)};
				if(db != null) {
					id = db.delete(DBHelper.ATTEMPT_DETAILS_TABLE_NAME, whereClause, whereArgs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
    
    public ArrayList<Option> getAllOptionsForQuestion(long queId) {
    	ArrayList<Option> optList = new ArrayList<Option>();
    	db = dbHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select * from "
    			+ DBHelper.OPTION_TABLE_NAME
    			+ " where que_id = ?", new String[] {String.valueOf(queId) });
    	if (cursor.moveToFirst()) {
    		do {
    			Option option = new Option();
    			option.setOptId(cursor.getLong(0));
    			option.setQueId(cursor.getLong(1));
    			option.setOptTxt(cursor.getString(2));
    			option.setCorrect(cursor.getString(3));
    			optList.add(option) ;
    		} while (cursor.moveToNext());
    	}
    	return optList;
    }
    
    public List<Option> getAllOptions() {
    	List<Option> optList = new ArrayList<Option>();
    	db = dbHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select * from "
    			+ DBHelper.OPTION_TABLE_NAME, null);
    	if (cursor.moveToFirst()) {
    		do {
    			Option option = new Option();
    			option.setOptId(cursor.getLong(0));
    			optList.add(option);
    		} while (cursor.moveToNext());
    	}
    	return optList;
    }
    
    public Question getQuestion(long quizId) {
    	Question question = null;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from "
					+ DBHelper.QUESTION_TABLE_NAME + " where _id = '"
					+ String.valueOf(quizId) + "'", null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				question = cursorToQuestion(cursor);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return question;
	}
    
    public long insertQuizAttempt(long quizId, long userId, String currentDateandTime) {
		long id = -1;
		try {
			ContentValues values = new ContentValues();
			values.put("quiz_id", quizId);
			values.put("user_id", userId);
			values.put("date_and_time", currentDateandTime);
			
			db = dbHelper.getWritableDatabase();
			id = db.insert(DBHelper.ATTEMPTS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
    
	public void insertAttemptDetail(long attemptId, long optionId) {
		try {
			ContentValues values = new ContentValues();
			values.put("option_id", optionId);
			values.put("attempt_id", attemptId);
			
			db = dbHelper.getWritableDatabase();
			db.insert(DBHelper.ATTEMPT_DETAILS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertTempQuestionOptionValues(long optionId, long queId) {
		try{
			ContentValues values = new ContentValues();
			values.put("opt_id", optionId);
			values.put("que_id", queId);
			db = dbHelper.getWritableDatabase();
			db.insert(DBHelper.TEMP_QUESTION_OPTION_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getOptionsFromSameQuestionInSameAttempt(long optionId) {
		db = dbHelper.getReadableDatabase();
		String[] columns = {"_id"};
		String selection = "_id=?";
		String[] selectionArgs = {String.valueOf(optionId)};
		Cursor cursor = null;
		try {
			if(db!=null) {
				cursor = db.query(DBHelper.OPTION_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
				if(cursor.getCount() > 0) {
					cursor.moveToFirst();
					optionId = cursor.getInt(cursor.getColumnIndex(String.valueOf(optionId)));
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return optionId;
	}
	
	public long getQueIdFromOptionId(long optionId) {
		long  queId = -1;
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select que_id from " 
				+ DBHelper.OPTION_TABLE_NAME 
				+ " where _id = '"+ String.valueOf(optionId) + "'", null);
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			queId = cursor.getLong(cursor.getColumnIndex(String.valueOf("que_id")));
		}
		cursor.close();
		
		return queId;
	}
	
	public void deleteAllValuesOfTempTable() {
		try {
			db = dbHelper.getReadableDatabase();
			if(db != null) {
				db.delete(DBHelper.TEMP_QUESTION_OPTION_TABLE_NAME, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Attempt> getAllAttempts(long userId) {
    	List<Attempt> attemptList = new ArrayList<Attempt>();
    	try {
    		db = dbHelper.getReadableDatabase();
    		Cursor cursor = db.rawQuery("select * from "
    				+ DBHelper.ATTEMPTS_TABLE_NAME
    				+ " where user_id = " + String.valueOf(userId), null);
    		cursor.moveToFirst();
    		while(!cursor.isAfterLast()) {
    			Attempt attempt = cursorToAttempt(cursor);
    			attemptList.add(attempt);
    			cursor.moveToNext();
    		}
    		cursor.close();
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return attemptList;
    }
	
	public List<Attempt> getAllQuizAttempts(long quizId, long userId) {
    	List<Attempt> attemptList = new ArrayList<Attempt>();
    	try {
    		db = dbHelper.getReadableDatabase();
    		Cursor cursor = db.rawQuery("select * from " 
    				+ DBHelper.ATTEMPTS_TABLE_NAME
    				+" where quiz_id = " + String.valueOf(quizId) 
    				+" AND user_id = " + String.valueOf(userId), null);
    		cursor.moveToFirst();
    		while(!cursor.isAfterLast()) {
    			Attempt attempt = cursorToAttempt(cursor);
    			attemptList.add(attempt);
    			cursor.moveToNext();
    		}
    		cursor.close();
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return attemptList;
    }
	
	public List<AttemptDetail> getAllQuizAttemptDetails(long attemptId) {
    	List<AttemptDetail> attemptDetailList = new ArrayList<AttemptDetail>();
    	try {
    		db = dbHelper.getReadableDatabase();
    		Cursor cursor = db.rawQuery("select * from " 
    				+ DBHelper.ATTEMPT_DETAILS_TABLE_NAME
    				+" where attempt_id = " + String.valueOf(attemptId), null);
    		cursor.moveToFirst();
    		while(!cursor.isAfterLast()) {
    			AttemptDetail attemptDetail = cursorToAttemptDetail(cursor);
    			attemptDetailList.add(attemptDetail);
    			cursor.moveToNext();
    		}
    		cursor.close();
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return attemptDetailList;
    }
	
	public List<Attempt> getSelectedQuizAttempts(long quizId) {
    	List<Attempt> attemptList = new ArrayList<Attempt>();
    	
    	try {
    		db = dbHelper.getReadableDatabase();
    		String selection = "quiz_id=?";
    		String[] selectionArgs = {String.valueOf(quizId)};
    		Cursor cursor = db.query(DBHelper.ATTEMPTS_TABLE_NAME, null, selection, selectionArgs, null, null, null);
    		cursor.moveToFirst();
    		while(!cursor.isAfterLast()) {
    			Attempt attempt = cursorToAttempt(cursor);
    			attemptList.add(attempt);
    			cursor.moveToNext();
    		}
    		cursor.close();
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return attemptList;
    }
	
	
	public User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ROWID)));
		user.setName(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)));
		user.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_EMAIL)));
		return user;
	}
	
	public Quiz cursorToQuiz(Cursor cursor) {
		Quiz quiz = new Quiz();
		quiz.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUIZ_ID)));
		quiz.setName(cursor.getString(cursor.getColumnIndex(DBHelper.QUIZ_NAME)));
		return quiz;
	}
	
	public Question cursorToQuestion(Cursor cursor) {
		Question question = new Question();
		question.setQueId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUES_ID)));
		question.setQuizId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUIZ_ID)));
		question.setQuestion(cursor.getString(cursor.getColumnIndex(DBHelper.QUES_TXT)));
		return question;
	}
	
	public Option cursorToOption(Cursor cursor) {
		Option option = new Option();
		option.setOptId(cursor.getInt(cursor.getColumnIndex(DBHelper.OPT_ID)));
		option.setQueId(cursor.getInt(cursor.getColumnIndex(DBHelper.QUES_ID)));
		option.setOptTxt(cursor.getString(cursor.getColumnIndex(DBHelper.OPT_TXT)));
		option.setCorrect(cursor.getString(cursor.getColumnIndex(DBHelper.OPT_CORRECT)));
		return option;
	}
	
	public Attempt cursorToAttempt(Cursor cursor) {
		Attempt attempt = new Attempt();
		attempt.setAttemptId(cursor.getInt(cursor.getColumnIndex(DBHelper.ATTEMPT_ID)));
		attempt.setQuizId(cursor.getLong(cursor.getColumnIndex(DBHelper.ATTEMPT_QUIZ_ID)));
		attempt.setDateAndTime(cursor.getString(cursor.getColumnIndex(DBHelper.ATTEMPT_DATE_AND_TIME)));
		return attempt;
	}
	
	public AttemptDetail cursorToAttemptDetail(Cursor cursor) {
		AttemptDetail attemptdetail = new AttemptDetail();
		attemptdetail.setAttemptDetailsId(cursor.getLong(cursor.getColumnIndex(DBHelper.ATTEMPT_DETAIL_ID)));
		attemptdetail.setOptionId(cursor.getLong(cursor.getColumnIndex(DBHelper.ATTEMPT_DETAIL_OPTION_ID)));
		attemptdetail.setAttemptId(cursor.getLong(cursor.getColumnIndex(DBHelper.ATTEMPT_DETAIL_ATTEMPT_ID)));
		return attemptdetail;
	}
	
}