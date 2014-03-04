package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Quiz;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;

public class QuizzesList extends ListActivity {

	DBHelper db;
	DataAccess dao;
	Cursor c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.quizzes_list);
		dao = new DataAccess(this);
		//ListView listview = (ListView) findViewById(R.id.listview);
        List<Quiz> quizzes = dao.getAllQuizzes();

        /*
         * setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends)); 
         * listView = getListView();
         */
        
        ArrayAdapter<Quiz> adapter = new ArrayAdapter<Quiz>(this, android.R.layout.simple_list_item_1, quizzes);
        setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(QuizzesList.this, SelectedQuiz.class);
		startActivity(intent);
	}
}