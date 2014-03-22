package in.informationworks.quizaptandroid.models;

public class Option {
	
	private long optionId;
	private long queId;
	private String optionTxt;
	private boolean correct;
	private boolean checked;
	
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
	
	public boolean getCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
