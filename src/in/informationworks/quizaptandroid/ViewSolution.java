package in.informationworks.quizaptandroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import in.informationworks.quizaptandroid.R;
import in.informationworks.quizaptandroid.models.AttemptDetail;
import in.informationworks.quizaptandroid.models.Option;
import in.informationworks.quizaptandroid.models.Question;
import in.informationworks.quizaptandroid.models.Quiz;

public class ViewSolution extends Activity {

	Button nextButton;
	Button prevButton;
	int currentQuestionIndex = 0;
	long quizId;
	int totalQuestions;
	int noOfOptions;
	TextView questionTextView;
	TextView quizNameTextView;
	RadioGroup optionsRadioGroup;
	Question currentQue;
	Option currentOption;
	DataAccess dao;
	Quiz quiz;
	List<Question> quesList;
	List<Option> optList;
	ArrayList<Option> optionList = new ArrayList<Option>();
	ArrayList<Option> currentOptions = new ArrayList<Option>();
	ArrayList<ArrayList<Option>> allOptions = new  ArrayList<ArrayList<Option>>();
	final RadioButton[] optionRB = new RadioButton[10];
	long currentQueId = 0;
	final Context context = this;
	SPAccess spa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_solution);
		
		dao = new DataAccess(this);
		
		quizId = getIntent().getExtras().getLong(Utility.QUIZ_ID);
		
		totalQuestions = dao.getNumberOfQuestionsInQuiz(quizId);
		
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
			optionRB[i].setTextColor(getResources().getColor(R.color.white));
			optionRB[i].setTextSize(20.0f);
			
			if(currentOption.getCorrect() == true) 
			{	
				optionRB[i].setTextColor(getResources().getColor(R.color.green));
			}
			optionsRadioGroup.getChildAt(i).setEnabled(false);
		}
		
		currentQueId = currentQuestionIndex;
	}
}
