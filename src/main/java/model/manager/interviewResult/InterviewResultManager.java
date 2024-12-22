package model.manager.interviewResult;

import model.dao.InterviewResultDAO;

public class InterviewResultManager {
	private InterviewResultDAO interviewResultDAO;
	
	public InterviewResultManager() {
		interviewResultDAO = new InterviewResultDAO();
	}
}
