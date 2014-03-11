package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Option;
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
	Button prevButton;
	long queId = 0;
	long quizId;
	int totalQuestions;
	int position;
	int noOfOptions;
	TextView questionTextView;
	TextView quizNameTextView;
	RadioGroup optionsRadioGroup;
	Question currentQue;
	DataAccess dao;
	Quiz quiz;
	Option option;
	Question question;
	List<Question> quesList;
	List<Option> optList;
	final RadioButton[] optionRB = new RadioButton[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_question);
		
		dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(SelectedQuiz.QUIZ_ID);
		totalQuestions = getIntent().getExtras().getInt(SelectedQuiz.NO_OF_QUESTIONS);
		
		quesList = dao.getAllQuestions(quizId);
		quiz = dao.getQuiz(quizId);
		currentQue = quesList.get((int) queId);
		
		//optList = dao.getOptions(queId);
		//option = dao.getOptions(queId);
		
		questionTextView = (TextView)findViewById(R.id.question);
		quizNameTextView = (TextView) findViewById(R.id.QuizName);
		nextButton = (Button) findViewById(R.id.next); 
		prevButton = (Button) findViewById(R.id.previous);
		optionsRadioGroup = (RadioGroup) findViewById (R.id.optionsRadioGroup);
		
		setQuestionView();
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//optionsRadioGroup = (RadioGroup)findViewById(R.id.optionsRadioGroup);
				//RadioButton answer = (RadioButton)findViewById(optionsRadioGroup.getCheckedRadioButtonId());
				if(queId<totalQuestions) {
					currentQue = quesList.get((int) queId);
					setQuestionView();
				} else {
					Intent intent = new Intent(LoadQuestion.this, ScoreBoard.class);
					startActivity(intent);
					finish();
				}
			}
		});
		
		/*prevButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(queId<totalQuestions) {
					currentQue = quesList.get((int) queId-1);
					setQuestionView();
				}
				else {
					Intent intent = new Intent(LoadQuestion.this, SelectedQuiz.class);
					startActivity(intent);
					finish();
				}
			}
		}); */
	}

	
	private void setQuestionView() {
		questionTextView.setText(currentQue.getQuestion());
		quizNameTextView.setText(quiz.getName());
		noOfOptions = dao.getNumberOfOptionsInQuestion(currentQue.getQueId());
		optionsRadioGroup.removeAllViews();
		for (int i=0; i<noOfOptions; i++) {
			optionRB[i] = new RadioButton(this);
			optionsRadioGroup.addView(optionRB[i]);
			optionRB[i].setText("option");
		}
		queId++;
	}
}