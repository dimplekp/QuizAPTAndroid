package in.informationworks.quizaptandroid.models;

public class AttemptDetail {

	private long attemptId;
	private long attemptDetailsId;
	private long optionId;
	
	public long getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(int attemptId) {
		this.attemptId = attemptId;
	}
	
	public long getAttemptDetailsId() {
		return attemptDetailsId;
	}
	public void setAttemptDetailsId(long attemptDetailsId) {
		this.attemptDetailsId = attemptDetailsId;
	}
	
	public long getOptionId() {
		return optionId;
	}
	public void setOptionId(long optionId) {
		this.optionId = optionId;
	}
}
