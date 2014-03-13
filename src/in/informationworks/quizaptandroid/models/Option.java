package in.informationworks.quizaptandroid.models;

public class Option {
	
	private long optionId;
	private long queId;
	private String optionTxt;
	private String correct;
	
	public long getOptId() {
		return optionId;
	}
	public void setOptId(long optionId) {
		this.optionId = optionId;
	}
	
	public long getQueId() {
		return queId;
	}
	public void setQueId(long queId) {
		this.queId = queId;
	}
	
	public String getOptTxt() {
		return optionTxt;
	}
	public void setOptTxt(String optionTxt) {
		this.optionTxt = optionTxt;
	}
	
	public String getCorrect() {
		return correct;
	}
	public void setCorrect(String correct) {
		this.correct = correct;
	}
}
