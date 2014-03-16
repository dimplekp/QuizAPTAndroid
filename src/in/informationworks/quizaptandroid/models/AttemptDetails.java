package in.informationworks.quizaptandroid.models;

public class AttemptDetails {

	private long attemptId;
	private long userId;
	private long quizId;
	private String attemptDate;
	private String attemptTime;
	
	public long getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(int attemptId) {
		this.attemptId = attemptId;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public long getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
		
	public String getAttemptTime() {
		return attemptTime;
	}
	public void setAttemptTime(String attemptTime) {
		this.attemptTime = attemptTime;
	}
	
	public String getAttemptDate() {
		return attemptDate;
	}
	public void setAttemptDate(String attemptDate) {
		this.attemptDate = attemptDate;
	}
}
