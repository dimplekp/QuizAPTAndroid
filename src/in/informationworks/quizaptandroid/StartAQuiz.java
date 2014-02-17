package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StartAQuiz extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_a_quiz);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_aquiz, menu);
		return true;
	}

}
