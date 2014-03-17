package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ScoreBoard extends Activity {

	String dateAndTime;
	long attemptId = 0;
	int noOfCorrectAnswers = 0;
	
	TextView scoreTextView;
	TextView noOfCorrectAnswersTextView;
	DataAccess dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_board);
		dao = new DataAccess(this);
	
		
		attemptId = getIntent().getExtras().getLong(Utility.ATTEMPT_ID);
		
		noOfCorrectAnswers = dao.getNoOfCorrectAnswers(attemptId);
		
		scoreTextView = (TextView) findViewById(R.id.userScore);
		scoreTextView.setText(String.valueOf(noOfCorrectAnswers));
		
		noOfCorrectAnswersTextView = (TextView) findViewById(R.id.correctQuestions);
		noOfCorrectAnswersTextView.setText(String.valueOf(noOfCorrectAnswers));
		
		
	}
	
}