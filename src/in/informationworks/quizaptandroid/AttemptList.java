package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.R;
import in.informationworks.quizaptandroid.models.Attempt;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AttemptList extends ListActivity {
	
	DataAccess dao;
	List<Attempt> attempts;
	long userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.quiz_list);
	    userId = getIntent().getExtras().getLong(Utility.USER_ID);
	     
	    dao = new DataAccess(this);
        SetList();   
	}
	
	public void SetList(){
		try {
			attempts = dao.getAllAttempts(userId);
			AttemptListAdapter adapter = new AttemptListAdapter(this, R.layout.quiz_list_details, attempts);
			setListAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item;
		item = (String) String.valueOf(attempts.get(position).getQuizId()) + " | " + attempts.get(position).getDateAndTime();
		Intent intent = new Intent(this, ReviewAttemptScoreBoard.class);
		intent.putExtra(Utility.DATE_AND_TIME, attempts.get(position).getDateAndTime());
		intent.putExtra(Utility.NO_OF_QUESTIONS, dao.getNumberOfQuestionsInQuiz(attempts.get(position).getQuizId()));
		intent.putExtra(Utility.QUIZ_ID, attempts.get(position).getQuizId());
		intent.putExtra(Utility.ATTEMPT_ID, attempts.get(position).getAttemptId());
		intent.putExtra(Utility.QUIZ_NAME, dao.getQuiz(attempts.get(position).getQuizId()).getName());
		startActivity(intent);
	}
	
	static class QuizViewHolder {
		public int position;
		public TextView txtQuizName;
		public TextView txtDateAndTime;
	}
	
	public class AttemptListAdapter extends ArrayAdapter<Attempt> {

		public AttemptListAdapter(Context context, int textViewResourceId, List<Attempt> objects) {
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
				holder.txtDateAndTime= (TextView) row.findViewById(R.id.dateAndTime);
				holder.txtQuizName.setHeight(80);
				row.setTag(holder);
			}
			else {
				holder = (QuizViewHolder) row.getTag();
			}
			String attemptQuizName = dao.getQuiz(attempts.get(position).getQuizId()).getName();
			if(holder.txtQuizName != null) {
				holder.txtQuizName.setText(attemptQuizName);
				holder.txtQuizName.setTextSize(25.0f);
				holder.txtQuizName.setTextColor(getResources().getColor(R.color.white));
			}
			
			holder.txtDateAndTime.setText(attempts.get(position).getDateAndTime());
			holder.txtDateAndTime.setTextSize(15.0f);
			holder.txtDateAndTime.setTextColor(getResources().getColor(R.color.white));
			
			return row;
		}
	}
}