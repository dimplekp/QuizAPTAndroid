package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.R;
import in.informationworks.quizaptandroid.models.AttemptDetail;
import in.informationworks.quizaptandroid.models.Option;
import in.informationworks.quizaptandroid.models.Question;
import in.informationworks.quizaptandroid.models.Quiz;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.os.Bundle;
import android.annotation.SuppressLint;
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
	int currentOptionIndex = 0;
	long quizId;
	int totalQuestions;
	int position;
	int noOfOptions;
	long optionId;
	long attemptDetailId;
	TextView questionTextView;
	TextView quizNameTextView;
	RadioGroup optionsRadioGroup;
	Question currentQue;
	Option currentOption;
	DataAccess dao;
	Quiz quiz;
	Option option;
	Question question;
	List<Question> quesList;
	List<Option> optList;
	ArrayList<Option> optionList = new ArrayList<Option>();
	List<AttemptDetail> attemptOptionList = new ArrayList<AttemptDetail>();
	ArrayList<Option> optionGroupList = new ArrayList<Option>();
	ArrayList<Option> currentOptions = new ArrayList<Option>();
	ArrayList<ArrayList<Option>> allOptions = new  ArrayList<ArrayList<Option>>();
	final RadioButton[] optionRB = new RadioButton[10];
	long currentQueId = 0;
	final Context context = this;
	boolean checkAnswer;
	String currentDateandTime;
	SPAccess spa;
	Calendar c;
	long attemptId;
	
	long queId = 0;
	
	@SuppressWarnings("unused")
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_quiz);
	
		dao = new DataAccess(this);
		spa = new SPAccess(this);
		
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		totalQuestions = getIntent().getExtras().getInt(Utility.NO_OF_QUESTIONS);
		
		currentQuestionIndex = 0;
		quiz = dao.getQuiz(quizId);
		quesList = dao.getAllQuestions(quizId);
		
		for(int i = 0;i < quesList.size(); i++)
		{
		  optionList = dao.getAllOptionsForQuestion(quesList.get(i).getQueId());
		  allOptions.add(optionList);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		currentDateandTime = dateFormat.format(new Date());
		attemptId = dao.insertQuizAttempt(quizId, spa.getUserId(), currentDateandTime);
		
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
					dao.deleteAllValuesOfTempTable();
					
					Intent intent = new Intent(TakeQuiz.this, ScoreBoard.class);
					intent.putExtra(Utility.ATTEMPT_ID, attemptId);
					intent.putExtra(Utility.QUIZ_ID, quizId);
					intent.putExtra(Utility.NO_OF_QUESTIONS, totalQuestions);
					
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
				//Hide previous button if on first question
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
		
		
		optionsRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			
				int selectedOptionIndex = optionsRadioGroup.indexOfChild(findViewById(optionsRadioGroup.getCheckedRadioButtonId()));
				currentOption = optList.get(selectedOptionIndex);
				
				// If current option is set true, values of selected options will not be inserted in Attempt_details table 
				if (currentOption.getChecked()) {
					return;
				} else {
					for(int i = 0; i < optList.size(); i++){
						Option opt = optList.get(i);
						if (opt.getChecked() == true) {
							dao.deleteAttemptDetail(attemptId, opt.getOptId());
							opt.setChecked(false);
						}
					}
					dao.insertAttemptDetail(attemptId, currentOption.getOptId());
					currentOption.setChecked(true);
				}
			}
		});
	}
	
	private void setQuestionView() {
		currentQue = quesList.get(currentQuestionIndex);
		currentOptions = allOptions.get(currentQuestionIndex);
	
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
		
		optList = allOptions.get(currentQuestionIndex);	
		noOfOptions = optList.size();
		if(noOfOptions < 2)
		{
			this.finish();
			//Process.killProcess( android.os.Process.myPid() ); 
		}
		optionsRadioGroup.removeAllViews();
		
		for (int i=0; i<noOfOptions; i++) {
			currentOption = optList.get(i);
			optionRB[i] = new RadioButton(this);
			optionsRadioGroup.addView(optionRB[i]);
			optionRB[i].setText(currentOption.getOptTxt());
			optionRB[i].setChecked(currentOption.getChecked());
		}
		
		currentQueId = currentQuestionIndex;
		
	}
	
	@Override
	public void onBackPressed()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			alertDialogBuilder.setTitle("Do you want to quit the Quiz?");
 
			alertDialogBuilder
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