package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Quiz;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class SelectedQuiz extends Activity {

	TextView quizNameTextView;
	TextView noOfQuestions;
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
		no_of_questions = dao.getNumberOfQuestionsInQuiz(quizId);
		quiz = dao.getQuiz(quizId);
		
		quizNameTextView = (TextView) findViewById(R.id.quizName);
		quizNameTextView.setText(quiz.getName());
		
		noOfQuestions = (TextView) findViewById(R.id.noOfQuestions);
		noOfQuestions.setText(String.valueOf(no_of_questions));
	}
}