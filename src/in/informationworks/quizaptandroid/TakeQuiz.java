package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Option;
import in.informationworks.quizaptandroid.models.Question;
import in.informationworks.quizaptandroid.models.Quiz;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TakeQuiz extends Activity {

	Button nextButton;
	Button prevButton;
	int currentQuestionIndex = 0;
	long quizId;
	int totalQuestions;
	int position;
	int noOfOptions;
	TextView questionTextView;
	TextView quizNameTextView;
	RadioGroup optionsRadioGroup;
	Question currentQue;
	Option curentOption;
	DataAccess dao;
	Quiz quiz;
	Option option;
	Question question;
	List<Question> quesList;
	List<Option> optList;
	final RadioButton[] optionRB = new RadioButton[10];
	long currentQueId = 0;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_quiz);
	
dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		totalQuestions = getIntent().getExtras().getInt(Utility.NO_OF_QUESTIONS);
		
		currentQuestionIndex = 0;
		quiz = dao.getQuiz(quizId);
		quesList = dao.getAllQuestions(quizId);
		
		questionTextView = (TextView)findViewById(R.id.question);
		quizNameTextView = (TextView) findViewById(R.id.QuizName);
		nextButton = (Button) findViewById(R.id.next); 
		prevButton = (Button) findViewById(R.id.previous);
		optionsRadioGroup = (RadioGroup) findViewById (R.id.optionsRadioGroup);
		
		setQuestionView();
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				currentQuestionIndex++;
				if(currentQuestionIndex == (totalQuestions)) {
					Intent intent = new Intent(TakeQuiz.this, ScoreBoard.class);
					startActivity(intent);
					finish();
				} else {
					setQuestionView();
				}
			}
		});
		
		prevButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				currentQuestionIndex--;
				
				if(currentQuestionIndex == 0) 
				{
					prevButton.setEnabled(false);
				}
				else
				{
					prevButton.setEnabled(true);
				}
				setQuestionView();
			}
		});
	}
	
	private void setQuestionView() {
		currentQue = quesList.get(currentQuestionIndex);
		
		questionTextView.setText(currentQue.getQuestion());
		quizNameTextView.setText(quiz.getName());
		
		if(currentQuestionIndex == 0) 
		{
			prevButton.setEnabled(false);
		}
		else
		{
			prevButton.setEnabled(true);
		}
		
		optList = dao.getAllOptions(currentQue.getQueId());		
		noOfOptions = optList.size();
		optionsRadioGroup.removeAllViews();
		
		for (int i=0; i<noOfOptions; i++) {
			curentOption = optList.get(i);
			optionRB[i] = new RadioButton(this);
			optionsRadioGroup.addView(optionRB[i]);
			optionRB[i].setText(curentOption.getOptTxt());
		}
		
		currentQueId = currentQuestionIndex;
		
		optionsRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 //int pos = optionsRadioGroup.indexOfChild(findViewById(checkedId));	 
			}
			
		});
	}
	
	@Override
	public void onBackPressed()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
 
			alertDialogBuilder.setTitle("Do you want to quit the Quiz?");
 
			alertDialogBuilder
				.setMessage("Click yes to quit!")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						TakeQuiz.this.finish();
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
 
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				alertDialog.show();
	}

}
