package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Option;
import in.informationworks.quizaptandroid.models.Question;
import in.informationworks.quizaptandroid.models.Quiz;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
	Option curentOptions;
	DataAccess dao;
	Quiz quiz;
	Option option;
	Question question;
	List<Question> quesList;
	List<Option> optList;
	final RadioButton[] optionRB = new RadioButton[10];
	int optionId = 0;
	long currentQueId = 0;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_question);
		
		dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(SelectedQuiz.QUIZ_ID);
		totalQuestions = getIntent().getExtras().getInt(SelectedQuiz.NO_OF_QUESTIONS);
		
		quiz = dao.getQuiz(quizId);
		quesList = dao.getAllQuestions(quizId);
		currentQue = quesList.get((int) queId);
		
		questionTextView = (TextView)findViewById(R.id.question);
		quizNameTextView = (TextView) findViewById(R.id.QuizName);
		nextButton = (Button) findViewById(R.id.next); 
		prevButton = (Button) findViewById(R.id.previous);
		optionsRadioGroup = (RadioGroup) findViewById (R.id.optionsRadioGroup);
		
		setQuestionView();
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(queId<totalQuestions) {
					optionId = 0;
					currentQue = quesList.get((int) queId);
					setQuestionView();
				} else {
					Intent intent = new Intent(LoadQuestion.this, ScoreBoard.class);
					startActivity(intent);
					finish();
				}
			}
		});
		
		prevButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				queId = queId - 2;
				
				if((queId>0 && queId<currentQueId) || (queId == 0)) 
				{
					optionId = 0;
					currentQue = quesList.get((int) queId);
					setQuestionView();
				}
				
				else 
				{
					Toast.makeText(getApplicationContext(), "This is first question. No more previous questions!",
							   Toast.LENGTH_LONG).show();
					queId = queId + 2;
				}
			}
		});
	}
	
	private void setQuestionView() {
		questionTextView.setText(currentQue.getQuestion());
		quizNameTextView.setText(quiz.getName());
		
		optList = dao.getAllOptions(currentQue.getQueId());		
		noOfOptions = dao.getNumberOfOptionsInQuestion(currentQue.getQueId());
		optionsRadioGroup.removeAllViews();
		
		for (int i=0; i<noOfOptions; i++) {
			curentOptions = optList.get(optionId);
			optionRB[i] = new RadioButton(this);
			optionsRadioGroup.addView(optionRB[i]);
			optionRB[i].setText(curentOptions.getOptTxt());
			optionId++;
		}
		queId++;
		currentQueId = queId;
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
						LoadQuestion.this.finish();
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