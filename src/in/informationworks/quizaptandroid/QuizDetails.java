package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.R;
import in.informationworks.quizaptandroid.models.Quiz;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizDetails extends Activity {

	TextView quizNameTextView;
	TextView noOfQuestionsTextView;
	String name;
	static int no_of_questions;
	long quizId;
	Quiz quiz;
	Button startAQuizButton;
	Button reviewAttemptsButton;
	Button resumeQuizButton;
	int position;
	long userId;
	
	DataAccess dao;
	SPAccess spa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selected_quiz);
		
		dao = new DataAccess(this);
		spa = new SPAccess(this);
		
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		userId = getIntent().getExtras().getLong(Utility.USER_ID);
		quiz = dao.getQuiz(quizId);
		no_of_questions = dao.getNumberOfQuestionsInQuiz(quizId);
		
		quizNameTextView = (TextView) findViewById(R.id.quizName);
		quizNameTextView.setText(quiz.getName());
		
		noOfQuestionsTextView = (TextView) findViewById(R.id.noOfQuestions);
		noOfQuestionsTextView.setText(String.valueOf(no_of_questions));
		
		startAQuizButton = (Button) findViewById(R.id.startAQuiz);
		startAQuizButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent quizQuestionIntent = new Intent(QuizDetails.this, AttemptQuiz.class);
				quizQuestionIntent.putExtra(Utility.NO_OF_QUESTIONS, no_of_questions);
				quizQuestionIntent.putExtra(Utility.QUIZ_ID, quiz.getId());
				startActivity(quizQuestionIntent);
				finish();
			}
		});
		
		reviewAttemptsButton = (Button) findViewById(R.id.reviewAttempt);
		reviewAttemptsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent attemptListIntent = new Intent(QuizDetails.this, QuizAttempts.class);
				attemptListIntent.putExtra(Utility.QUIZ_ID, quiz.getId());
				attemptListIntent.putExtra(Utility.USER_ID, userId);
				startActivity(attemptListIntent);
			}
			
		});
		
		resumeQuizButton = (Button) findViewById(R.id.resumeAttempt);
		resumeQuizButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(spa.getAttemptId() == 0) {
					Toast.makeText(getApplicationContext(), "No pending Quiz", Toast.LENGTH_SHORT).show();
				}
				else {
					Intent attemptListIntent = new Intent(QuizDetails.this, AttemptQuiz.class);
					attemptListIntent.putExtra(Utility.QUIZ_ID, quiz.getId());
					attemptListIntent.putExtra(Utility.USER_ID, userId);
					attemptListIntent.putExtra(Utility.NO_OF_QUESTIONS, no_of_questions);
					startActivity(attemptListIntent);
				}
			}
			
		});
	}
}