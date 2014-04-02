package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.AttemptDetail;
import in.informationworks.quizaptandroid.models.Option;
import in.informationworks.quizaptandroid.models.Question;
import in.informationworks.quizaptandroid.models.Quiz;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ResumeQuiz extends Activity {

	Button nextButton;
	Button prevButton;
	Button submitButton;
	int resumeQuestionIndex = 0;
	long quizId;
	long queId;
	int totalQuestions;
	int noOfOptions;
	TextView questionTextView;
	TextView quizNameTextView;
	RadioGroup optionsRadioGroup;
	Question currentQue;
	Question resumeQue;
	Option currentOption;
	DataAccess dao;
	Quiz quiz;
	List<Question> quesList;
	List<Option> optList;
	ArrayList<Option> optionList = new ArrayList<Option>();
	List<Quiz> quizzes;
	List<AttemptDetail> attemptDetails;
	ArrayList<Option> currentOptions = new ArrayList<Option>();
	ArrayList<ArrayList<Option>> allOptions = new  ArrayList<ArrayList<Option>>();
	final RadioButton[] optionRB = new RadioButton[10];
	long currentQueId = 0;
	final Context context = this;
	String currentDateandTime;
	SPAccess spa;
	long attemptId;
	long userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resume_quiz);
		
		dao = new DataAccess(this);
		
		userId = getIntent().getExtras().getLong(Utility.USER_ID);
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		queId = getIntent().getExtras().getLong(Utility.QUE_ID);
		totalQuestions = getIntent().getExtras().getInt(Utility.NO_OF_QUESTIONS);
		
		quiz = dao.getQuiz(quizId);
		quesList = dao.getAllQuestions(quizId);
		attemptDetails = dao.getAllQuizAttemptDetails(attemptId);
		resumeQuestionIndex = quesList.indexOf(dao.getPendingAttempt(quizId, userId).getQueId());
		
		for(int i = 0;i < quesList.size(); i++)
		{
		  optionList = dao.getAllOptionsForQuestion(quesList.get(i).getQueId());
		  allOptions.add(optionList);
		}
		
		questionTextView = (TextView)findViewById(R.id.question);
		quizNameTextView = (TextView) findViewById(R.id.QuizName);
		nextButton = (Button) findViewById(R.id.next); 
		prevButton = (Button) findViewById(R.id.previous);
		submitButton = (Button) findViewById(R.id.submit);
		optionsRadioGroup = (RadioGroup) findViewById (R.id.optionsRadioGroup);
		
		setQuestionView();
	
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resumeQuestionIndex++;
				if(resumeQuestionIndex == (totalQuestions)) {
					
					Intent intent = new Intent(ResumeQuiz.this, ReceiveScore.class);
					intent.putExtra(Utility.ATTEMPT_ID, attemptId);
					intent.putExtra(Utility.QUIZ_ID, quizId);
					intent.putExtra(Utility.NO_OF_QUESTIONS, totalQuestions);
					startActivity(intent);
					finish();
				} else {
					
					if(resumeQuestionIndex == totalQuestions-1)
					{
						nextButton.setEnabled(false);
						//nextButton.setBackgroundColor(getResources().getColor(R.color.babyblue));
					}
					else
					{
						nextButton.setEnabled(true);
					}
					setQuestionView();
				}
			}
		});
	
		prevButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				resumeQuestionIndex--;
				//Hide previous button if on first question
				if(resumeQuestionIndex == 0) 
				{
					prevButton.setEnabled(false);
					//prevButton.setBackgroundColor(getResources().getColor(R.color.babyblue));
				}
				else
				{
					prevButton.setEnabled(true);
				}
				setQuestionView();
			}
		});
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Make is_attempted true in Attempt
				dao.updateAttemptCompleteTrue(attemptId);
				
				Intent intent = new Intent(ResumeQuiz.this, ReceiveScore.class);
				intent.putExtra(Utility.ATTEMPT_ID, attemptId);
				intent.putExtra(Utility.QUIZ_ID, quizId);
				intent.putExtra(Utility.NO_OF_QUESTIONS, totalQuestions);
				startActivity(intent);
				finish();
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
		
		currentQue = quesList.get(resumeQuestionIndex);
		currentOptions = allOptions.get(resumeQuestionIndex);

		questionTextView.setText(currentQue.getQuestion());
		quizNameTextView.setText(quiz.getName());
	
		if(resumeQuestionIndex == 0) 
		{
			prevButton.setEnabled(false);
			//prevButton.setBackgroundColor(getResources().getColor(R.color.babyblue));
		}
		else
		{
			prevButton.setEnabled(true);
		}
	
		if(resumeQuestionIndex == totalQuestions-1)
		{
			nextButton.setEnabled(false);
			//nextButton.setBackgroundColor(getResources().getColor(R.color.babyblue));
		}
		else
		{
			nextButton.setEnabled(true);
		}
	
		optList = allOptions.get(resumeQuestionIndex);	
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
			optionRB[i].setTextColor(getResources().getColor(R.color.white));
			optionRB[i].setTextSize(20.0f);
			for(int j = 0; j < attemptDetails.size(); j++) {
				if(currentOption.getOptId() == attemptDetails.get(j).getOptionId())
				{
					optionRB[i].setChecked(currentOption.getChecked());
				}
			}
		
			queId = currentOption.getQueId();
		}
	
		currentQueId = resumeQuestionIndex;
	}

}