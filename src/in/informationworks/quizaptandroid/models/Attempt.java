package in.informationworks.quizaptandroid.models;

import android.content.Context;
import in.informationworks.quizaptandroid.DataAccess;

public class Attempt {

	Context context;
	DataAccess dao = new DataAccess(context);
	
	private String quizAttemptname;
	private long attemptId;
	private long quizId;
	private long userId;
	private String dateAndTime;
	
	public long getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(long attemptId) {
		this.attemptId = attemptId;
	}
	
	public void setAttemptName(String quizAttemptname){
		this.quizAttemptname = quizAttemptname;
	}
	public String getAttemptName()
	{
		return quizAttemptname;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getQuizId() {
		return quizId;
	}
	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}
	
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
	public String toString() {
		Quiz quiz = dao.getQuiz(quizId);
		return quiz.getName();
	}
	
}
