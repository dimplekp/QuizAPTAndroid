package in.informationworks.quizaptandroid;

import in.informationworks.quizaptandroid.models.Attempt;
import in.informationworks.quizaptandroid.models.AttemptDetail;
import in.informationworks.quizaptandroid.models.Option;
import in.informationworks.quizaptandroid.models.Question;
import in.informationworks.quizaptandroid.models.Quiz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ViewAttemptedQuiz extends Activity {

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
	List<Attempt> attempts;
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
	long userId;
	
	long queId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_attempted_quiz);
		
		dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		userId = getIntent().getExtras().getLong(Utility.USER_ID);
		
		totalQuestions = dao.getNumberOfQuestionsInQuiz(quizId);
		attempts = dao.getAllQuizAttempts(quizId, userId);
		
		
		currentQuestionIndex = 0;
		quiz = dao.getQuiz(quizId);
		quesList = dao.getAllQuestions(quizId);
		
		for(int i = 0;i < quesList.size(); i++)
		{
		  optionList = dao.getAllOptionsForQuestion(quesList.get(i).getQueId());
		  allOptions.add(optionList);
		}
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
				  if(currentQuestionIndex == (totalQuestions - 1)) 
				  {
					  nextButton.setEnabled(false);
				  }
				  else 
				  {
					  nextButton.setEnabled(true);
				  }
				  if(currentQuestionIndex == (totalQuestions))
				  {
					  // do nothing
				  }
				  else
				  {
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
					  nextButton.setEnabled(true);
				  }
				  setQuestionView();
			  }
		  });
	}
	
	@SuppressLint("NewApi")
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
		
		if(currentQuestionIndex == (totalQuestions - 1)) 
		{
			  nextButton.setEnabled(false);
		}
		else 
		{
			  nextButton.setEnabled(true);
		}
		optList = allOptions.get(currentQuestionIndex);	
		noOfOptions = optList.size();
		
		optionsRadioGroup.removeAllViews();
		for (int i=0; i<noOfOptions; i++) {
			currentOption = optList.get(i);
			optionRB[i] = new RadioButton(this);
			optionsRadioGroup.addView(optionRB[i]);
			optionRB[i].setText(currentOption.getOptTxt());
			
			if(Boolean.parseBoolean(currentOption.getCorrect()) == true) 
			{	
				optionRB[i].setTextColor(getResources().getColor(R.color.green));
			}
			optionsRadioGroup.getChildAt(i).setEnabled(false);
		}
		
		currentQueId = currentQuestionIndex;
	}
}