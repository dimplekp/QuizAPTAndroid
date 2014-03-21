package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Quiz;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewSolutionQuizList extends ListActivity {

	DataAccess dao;
	List<Quiz> quizzes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new DataAccess(this);
        quizzes = dao.getAllQuizzes();
        ArrayAdapter<Quiz> adapter = new ArrayAdapter<Quiz>(this, android.R.layout.simple_list_item_1, quizzes);
        setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(ViewSolutionQuizList.this, ViewSolution.class);
		intent.putExtra(Utility.QUIZ_ID, quizzes.get(position).getId());
		startActivity(intent);
	}

}
