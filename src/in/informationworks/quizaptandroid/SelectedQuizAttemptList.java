package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Attempt;

import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectedQuizAttemptList extends ListActivity {

	DBHelper db;
	DataAccess dao;
	Cursor c;
	List<Attempt> attempts;
	long quizId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dao = new DataAccess(this);
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		
		attempts = dao.getSelectedQuizAttempts(quizId);
		ArrayAdapter<Attempt> adapter = new ArrayAdapter<Attempt>(this, android.R.layout.simple_list_item_1, attempts);
		setListAdapter(adapter);
	}
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(SelectedQuizAttemptList.this, ReviewAttempt.class);
		intent.putExtra(Utility.QUIZ_ID, attempts.get(position).getAttemptId());
		startActivity(intent);
	}
	
}
