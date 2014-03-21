package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.R;
import in.informationworks.quizaptandroid.models.AttemptDetail;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoreBoard extends Activity {

	String dateAndTime;
	long attemptId;
	long quizId;
	int noOfCorrectAnswers;
	int noOfAttemptedQuestions;
	int totalQuestions;
	List<AttemptDetail> attemptDetailsOptions;
	
	TextView scoreTextView;
	TextView correctAnswersTextView;
	TextView attemptedQuestionsTextView;
	TextView totalQuestionsTextView;
	
	Button reviewAttemptButton;
	
	DataAccess dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_board);
		dao = new DataAccess(this);
	
		attemptId = getIntent().getExtras().getLong(Utility.ATTEMPT_ID);
		totalQuestions = getIntent().getExtras().getInt(Utility.NO_OF_QUESTIONS);
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		
		noOfCorrectAnswers = dao.getNoOfCorrectAnswers(attemptId);
		attemptDetailsOptions = dao.getOptionIdFromAttemptDetails(attemptId);
		noOfAttemptedQuestions = attemptDetailsOptions.size();
		
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
				Intent attemptListIntent = new Intent(ScoreBoard.this, SelectedQuizAttemptList.class);
				startActivity(attemptListIntent);
				attemptListIntent.putExtra(Utility.QUIZ_ID, quizId);
				finish();
			}
			
		});
		
	}
	
}