package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ScoreBoard extends Activity {

	int score = 0;
	String dateAndTime;
	
	TextView scoreTextView;
	TextView dateTimeTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_board);
		
		score = getIntent().getExtras().getInt(Utility.USER_SCORE);
		dateAndTime= getIntent().getExtras().getString(Utility.DATE_AND_TIME);
		
		scoreTextView = (TextView) findViewById(R.id.userScore);
		scoreTextView.setText(String.valueOf(score));
		
		dateTimeTextView = (TextView) findViewById(R.id.currentDateandTime);
		dateTimeTextView.setText(String.valueOf(dateAndTime));
		
	}
	
	/*@Override
	public void onBackPressed() {
		Intent intent = new Intent(ScoreBoard.this, QuizzesList.class);
		intent.putExtra(Utility.USER_SCORE, score);
		startActivity(intent);
		finish();
	}*/
}