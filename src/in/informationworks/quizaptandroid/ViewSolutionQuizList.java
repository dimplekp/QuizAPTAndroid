package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.QuizList.QuizListAdapter;
import in.informationworks.quizaptandroid.QuizList.QuizViewHolder;
import in.informationworks.quizaptandroid.models.Quiz;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewSolutionQuizList extends ListActivity {

	DataAccess dao;
	List<Quiz> quizzes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.quiz_list);
	    dao = new DataAccess(this);
        SetList(); 
	}
	
	public void SetList(){
		try {
			quizzes = dao.getAllQuizzes();
			QuizListAdapter adapter = new QuizListAdapter(this, R.layout.quiz_list_details, quizzes);
			setListAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item;
		item = (String) String.valueOf(quizzes.get(position).getId());
		Intent intent = new Intent(this, ViewSolution.class);
		intent.putExtra(Utility.QUIZ_ID, quizzes.get(position).getId());
		startActivity(intent);
	}
	
	static class QuizViewHolder {
		public int position;
		public TextView txtQuizName;
	}
	
	public class QuizListAdapter extends ArrayAdapter<Quiz> {

		public QuizListAdapter(Context context, int textViewResourceId, List<Quiz> objects) {
			super(context, textViewResourceId, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			QuizViewHolder holder;
			
			if(row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.quiz_list_details, null);
				holder = new QuizViewHolder();
				holder.txtQuizName = (TextView) row.findViewById(R.id.quizName);
				holder.txtQuizName.setHeight(100);
				row.setTag(holder);
			}
			else {
				holder = (QuizViewHolder) row.getTag();
			}
			String attemptQuizName = dao.getQuiz(quizzes.get(position).getId()).getName();
			if(holder.txtQuizName != null) {
				holder.txtQuizName.setText(attemptQuizName);
				holder.txtQuizName.setTextSize(25.0f);
				holder.txtQuizName.setTextColor(getResources().getColor(R.color.white));
			}
			return row;
		}
	}

}
