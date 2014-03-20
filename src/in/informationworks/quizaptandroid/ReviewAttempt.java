package in.informationworks.quizaptandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import in.informationworks.quizapt.R;

public class ReviewAttempt extends Activity {

	Button nextButton;
	Button prevButton;
	TextView questionTextView;
	RadioGroup optionsRadioGroup;
	DataAccess dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_attempt);
		
		dao = new DataAccess(this);
		
		
	}

}
