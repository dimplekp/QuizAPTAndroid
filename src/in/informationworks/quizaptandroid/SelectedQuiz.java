package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Quiz;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class SelectedQuiz extends Activity {

	TextView quizNameTextView;
	TextView noOfQuestionsTextView;
	TextView timeAllowedTextView;
	String name;
	int no_of_questions;
	int time_allowed;
	long quizId;
	Quiz quiz;
	
	DataAccess dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selected_quiz);
		
		dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(QuizzesList.QUIZ_ID);
		quiz = dao.getQuiz(quizId);
		no_of_questions = dao.getNumberOfQuestionsInQuiz(quizId);
		time_allowed = dao.getTimeAllowed(quizId);
		
		quizNameTextView = (TextView) findViewById(R.id.quizName);
		quizNameTextView.setText(quiz.getName());
		
		noOfQuestionsTextView = (TextView) findViewById(R.id.noOfQuestions);
		noOfQuestionsTextView.setText(String.valueOf(no_of_questions));
		
		timeAllowedTextView = (TextView) findViewById(R.id.timeAllowed);
		timeAllowedTextView.setText(String.valueOf(time_allowed));
	}
}