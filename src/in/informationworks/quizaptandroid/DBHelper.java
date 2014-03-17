package in.informationworks.quizaptandroid;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email_id";
    
    public static final String QUIZ_ID = "_id";
	public static final String QUIZ_NAME = "name";
	public static final String QUIZ_TIME_ALLOWED = "time_allowed_per_question_in_minutes";
	
	public static final String QUES_ID = "_id";
	public static final String QUES_TXT = "question_txt";
	
	public static final String OPT_ID = "_id";
	public static final String OPT_TXT = "option_txt";
	public static final String OPT_QUE_ID = "que_id";
	public static final String OPT_CORRECT = "correct";
	
	public static final String ATTEMPT_ID = "_id";
	public static final String ATTEMPT_USER_ID = "user_id";
	public static final String ATTEMPT_QUIZ_ID = "quiz_id";
	public static final String ATTEMPT_DATE_AND_TIME = "date_and_time";
	
	public static final String ATTEMPT_DETAIL_ID = "_id";
	public static final String ATTEMPT_DETAIL_OPTION_ID = "option_id";
	public static final String ATTEMPT_DETAIL_ATTEMPT_ID = "attempt_id";
	
	public static final String TEMP_TABLE_QUE_ID = "que_id";
	public static final String TEMP_TABLE_OPTION_ID = "opt_id";
	
    public static final String USERS_TABLE_NAME = "user";
    public static final String QUIZZES_TABLE_NAME = "Quizzes";
    public static final String QUESTION_TABLE_NAME = "Questions";
    public static final String OPTION_TABLE_NAME = "Options";
    public static final String ATTEMPTS_TABLE_NAME = "Attempts";
    public static final String ATTEMPT_DETAILS_TABLE_NAME = "Attempt_Details";
    public static final String TEMP_QUESTION_OPTION_TABLE_NAME = "Temp_question_option";
    
    private final Context myContext;
    private static DBHelper mInstance;
    private SQLiteDatabase myDataBase;
        
    DBHelper DB = null;
    private static final String DATABASE_NAME = "quizapt.sqlite";
    private static final int DATABASE_VERSION = 2;
    
    public static String PackageName = "in.informationworks.quizaptandroid";
    private static String DB_PATH = Environment.getDataDirectory()+"/data/" + PackageName + "/databases/";
    public static final String DB_FULL_PATH = DB_PATH + DATABASE_NAME;    
       
        public static DBHelper getInstance(Context ctx) {
    		
            if (mInstance == null) {
               mInstance = new DBHelper(ctx);
            }
            return mInstance;
        }
        
        @Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}
        
        /*
         Created database by copying it from Assets folder. If database alredy exists, it does nothing.
         */
        public void createDataBase() throws Exception{

        	boolean dbExist = checkDataBase();
     
        	if(dbExist){
        		//do nothing - database already exist
        	}
        	else {
        		//By calling this method and empty database will be created into the default system path
                //of your application so we are gonna be able to overwrite that database with our database.
            	this.getReadableDatabase();
     
            	try {           		
        			copyDataBase();        			
        		} 
            	catch (IOException e) {
            		throw new Exception("Error copying database");
            	}	
        	}
        }
        
        /*
         * Check if the database already exist to avoid re-copying the file each time you open the application.
         * @return true if it exists, false if it doesn't.
         */
        private boolean checkDataBase(){
     
        	SQLiteDatabase checkDB = null;
     
        	try{
        		checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
        		checkDB.close();
        	}catch(Exception e){
        		//database does't exist yet.
        		Log.d("DB", "Db does not exist-" + e.getMessage());
        		return false;
        	}
        	return checkDB != null ? true : false;
        }
        /*
         * Copies your database from your local assets-folder to the just created empty database in the
         * system folder, from where it can be accessed and handled.
         * This is done by transferring bytestream.
         */
        private void copyDataBase() throws IOException{
        	try {
     
        	// Path to the just created empty db
        	String outFileName = DB_PATH + DATABASE_NAME;
     
        	//Open the empty db as the output stream
        	OutputStream myOutput = new FileOutputStream(outFileName);
     
        	//transfer bytes from the inputfile to the outputfile
        	byte[] buffer = new byte[1024];
        	int length;
        	
        	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        	while ((length = myInput.read(buffer))>0){
        		myOutput.write(buffer);
        	}
        	
        	//Close the streams
        	myOutput.flush();
        	myOutput.close();
        	myInput.close();
        	}
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        public SQLiteDatabase openDataBase() throws SQLException{
        	 
        	//Open the database
            String myPath = DB_PATH + DATABASE_NAME;
        	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
        	return myDataBase;
        }
        
      
        
        /*
         * Closes database connection
         */
        @Override
    	public synchronized void close() {
     
        	    if(myDataBase != null && myDataBase.isOpen())
        		    myDataBase.close();
        	    super.close();
    	}

		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}	
}