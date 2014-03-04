package in.informationworks.quizaptandroid.models;

public class Quiz {

	private int id;
	private String name;
	private int no_of_questions;
	private int time_allowerd;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	    return name;
	}
}
