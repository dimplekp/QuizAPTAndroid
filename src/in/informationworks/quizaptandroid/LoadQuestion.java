package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Question;
import in.informationworks.quizaptandroid.models.Quiz;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LoadQuestion extends Activity {
	
	Button nextButton;
	long queId = 0;
	long quizId;
	int totalQuestions;
	int position;
	TextView questionTextView;
	TextView quizNameTextView;
	RadioButton rda, rdb, rdc, rdd;
	Question currentQue;
	DataAccess dao;
	Quiz quiz;
	List<Question> quesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_question);
		
		dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(SelectedQuiz.QUIZ_ID);
		totalQuestions = getIntent().getExtras().getInt(SelectedQuiz.NO_OF_QUESTIONS);
		
		quesList = dao.getAllQuestions(quizId);
		currentQue = quesList.get((int) queId);
		quiz = dao.getQuiz(quizId);
		
		questionTextView = (TextView)findViewById(R.id.question);
		quizNameTextView = (TextView) findViewById(R.id.QuizName);
		nextButton = (Button) findViewById(R.id.next);
		rda=(RadioButton)findViewById(R.id.radio0);
		rdb=(RadioButton)findViewById(R.id.radio1);
		rdc=(RadioButton)findViewById(R.id.radio2);
		
		setQuestionView();
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup options = (RadioGroup)findViewById(R.id.radioGroup1);
				RadioButton answer = (RadioButton)findViewById(options.getCheckedRadioButtonId());
				if(queId<totalQuestions){
					currentQue=quesList.get((int) queId);
					setQuestionView();
				} else {
					Intent intent = new Intent(LoadQuestion.this, ScoreBoard.class);
					startActivity(intent);
					finish();
				}
			}
		});
	}

	private void setQuestionView() {
		questionTextView.setText(currentQue.getQuestion());
		quizNameTextView.setText(quiz.getName());
		queId++;
	}
}