package in.informationworks.quizaptandroid.models;

public class Quiz {

	private long quizId;
	private String quizName;
	private int no_of_questions;
	private int time_allowerd;
	
	public long getId() {
		return quizId;
	}
	public void setId(int quizId) {
		this.quizId = quizId;
	}
	
	public String getName() {
		return quizName;
	}
	public void setName(String quizName) {
		this.quizName = quizName;
	}
	
	public int getNoOfQuestions() {
		return no_of_questions;
	}
	public void setNoOfQuestions(int no_of_questions) {
		this.no_of_questions = no_of_questions;
	}
	
	public int getTimeAllowed() {
		return time_allowerd;
	}
	public void setTimeAllowed(int time_allowerd) {
		this.time_allowerd = time_allowerd;
	}
	
	@Override
	  public String toString() {
	    return quizName;
	  }
}
