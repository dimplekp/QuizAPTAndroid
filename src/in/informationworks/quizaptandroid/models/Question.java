package in.informationworks.quizaptandroid.models;

public class Question {

	private long queId;
	private long quizId;
	private String queTxt;
	
	public Question()
	{
		queId=0;
		quizId=0;
		queTxt="";
		
	}
	
	public Question(String question, long quizid, long queid) {
		queTxt = question;
		quizId = quizid;
		queId = queid;
	}
	
	public long getQueId() {
		return queId;
	}
	public void setQueId(long queid) {
		queId = queid;
	}
	
	public long getQuizId() {
		return quizId;
	}
	public void setQuizId(long quizid) {
		quizId = quizid;
	}
	
	public String getQuestion() {
		return queTxt;
	}
	public void setQuestion(String question) {
		queTxt = question;
	}
}