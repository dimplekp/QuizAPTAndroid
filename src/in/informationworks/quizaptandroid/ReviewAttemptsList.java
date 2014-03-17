package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Attempt;
import in.informationworks.quizaptandroid.models.Quiz;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class ReviewAttemptsList extends ListActivity {

	DBHelper db;
	DataAccess dao;
	Cursor c;
	List<Attempt> attempts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new DataAccess(this);
		attempts = dao.getAllAttempts();
		ArrayAdapter<Attempt> adapter = new ArrayAdapter<Attempt>(this, android.R.layout.simple_list_item_1, attempts);
		setListAdapter(adapter);
	}
	
	

}
