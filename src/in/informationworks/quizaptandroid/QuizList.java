package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Quiz;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuizList extends ListActivity{

	DataAccess dao;
	List<Quiz> quizzes;
	long userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new DataAccess(this);
		userId = getIntent().getExtras().getLong(Utility.USER_ID);
        quizzes = dao.getAllQuizzes();
        ArrayAdapter<Quiz> adapter = new ArrayAdapter<Quiz>(this, android.R.layout.simple_list_item_1, quizzes);
        setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(QuizList.this, SelectedQuiz.class);
		intent.putExtra(Utility.QUIZ_ID, quizzes.get(position).getId());
		intent.putExtra(Utility.USER_ID, userId);
		startActivity(intent);
	}
}
