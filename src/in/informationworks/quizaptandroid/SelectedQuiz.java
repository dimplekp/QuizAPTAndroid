package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Quiz;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SelectedQuiz extends Activity {

	TextView quizNameTextView;
	TextView noOfQuestionsTextView;
	TextView timeAllowedTextView;
	String name;
	static int no_of_questions;
	int time_allowed;
	long quizId;
	Quiz quiz;
	Button startAQuizButton;
	int position;
	
	DataAccess dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selected_quiz);
		
		dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		quiz = dao.getQuiz(quizId);
		no_of_questions = dao.getNumberOfQuestionsInQuiz(quizId);
		time_allowed = dao.getTimeAllowed(quizId);
		
		quizNameTextView = (TextView) findViewById(R.id.quizName);
		quizNameTextView.setText(quiz.getName());
		
		noOfQuestionsTextView = (TextView) findViewById(R.id.noOfQuestions);
		noOfQuestionsTextView.setText(String.valueOf(no_of_questions));
		
		timeAllowedTextView = (TextView) findViewById(R.id.timeAllowed);
		timeAllowedTextView.setText(String.valueOf(time_allowed));
		
		startAQuizButton = (Button) findViewById(R.id.startAQuiz);
		startAQuizButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent quizQuestion = new Intent(SelectedQuiz.this, TakeQuiz.class);
				quizQuestion.putExtra(Utility.NO_OF_QUESTIONS, no_of_questions);
				quizQuestion.putExtra(Utility.QUIZ_ID, quiz.getId());
				startActivity(quizQuestion);
				finish();
			}
		});
	}
}