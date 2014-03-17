package in.informationworks.quizaptandroid.models;

public class Attempt {

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
	
}
