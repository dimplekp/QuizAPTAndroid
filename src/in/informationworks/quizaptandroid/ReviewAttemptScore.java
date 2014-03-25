package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.R;
import in.informationworks.quizaptandroid.models.Attempt;
import in.informationworks.quizaptandroid.models.AttemptDetail;
import in.informationworks.quizaptandroid.models.Question;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReviewAttemptScore extends Activity {

	String dateAndTime;
	long attemptId;
	long quizId;
	long userId;
	int noOfCorrectAnswers;
	int noOfAttemptedQuestions;
	int totalQuestions;
	String quizName;
	
	TextView scoreTextView;
	TextView correctAnswersTextView;
	TextView attemptedQuestionsTextView;
	TextView totalQuestionsTextView;
	TextView QuizNameTextView;
	TextView DateAndTimeTextView;
	
	Button reviewAttemptButton;
	List<Attempt> attempts;
	List<AttemptDetail> attemptDetails;
	List<AttemptDetail> attemptDetailsOptions;
	List<Question> questions;
	DataAccess dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_attempt_score_board);
		
		dao = new DataAccess(this);
	
		attemptId = getIntent().getExtras().getLong(Utility.ATTEMPT_ID);
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		totalQuestions = getIntent().getExtras().getInt(Utility.NO_OF_QUESTIONS);
		quizName =  getIntent().getExtras().getString(Utility.QUIZ_NAME);
		dateAndTime = getIntent().getExtras().getString(Utility.DATE_AND_TIME);
		
		noOfCorrectAnswers = dao.getNoOfCorrectAnswers(attemptId);
		attemptDetailsOptions = dao.getOptionIdFromAttemptDetails(attemptId);
		noOfAttemptedQuestions = attemptDetailsOptions.size();
		
		QuizNameTextView = (TextView) findViewById(R.id.QuizName);
		QuizNameTextView.setText(String.valueOf(quizName));
		
		DateAndTimeTextView = (TextView) findViewById(R.id.DateAndTime);
		DateAndTimeTextView.setText(dateAndTime);
		
		scoreTextView = (TextView) findViewById(R.id.userScore);
		scoreTextView.setText(String.valueOf(noOfCorrectAnswers));
		
		correctAnswersTextView = (TextView) findViewById(R.id.correctQuestions);
		correctAnswersTextView.setText(String.valueOf(noOfCorrectAnswers));
		
		attemptedQuestionsTextView = (TextView) findViewById(R.id.attemptedQuestions);
		attemptedQuestionsTextView.setText(String.valueOf(noOfAttemptedQuestions));
		
		totalQuestionsTextView = (TextView) findViewById(R.id.totalQuestions);
		totalQuestionsTextView.setText(String.valueOf(totalQuestions));
		
		reviewAttemptButton = (Button) findViewById(R.id.reviewAttempt);
		reviewAttemptButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ReviewAttemptScore.this, ReviewAttempt.class);
				intent.putExtra(Utility.QUIZ_ID, quizId);
				intent.putExtra(Utility.ATTEMPT_ID, attemptId);
				startActivity(intent);
				finish();
			}
			
		});
		
	}
	
}