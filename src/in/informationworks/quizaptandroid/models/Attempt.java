package in.informationworks.quizaptandroid.models;

public class Attempt {
	
	private String quizAttemptname;
	private long attemptId;
	private long quizId;
	private long userId;
	private long queId;
	private String dateAndTime;
	boolean isAttempted;
	
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
		return String.valueOf(quizId);
	}
	
	public long getQueId() {
		return queId;
	}
	public void setQueId(long queId) {
		this.queId = queId;
	}
	
	public boolean getIsAttempted() {
		return isAttempted;
	}
	public void setIsAttempted(boolean isAttempted) {
		this.isAttempted = isAttempted;
	}
}