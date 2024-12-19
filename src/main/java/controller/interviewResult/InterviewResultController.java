package controller.interviewResult;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.domain.InterviewResult;
import model.manager.interviewResult.InterviewResultManager;

@WebServlet("/interviewResult")
public class InterviewResultController extends HttpServlet{
	
	private final InterviewResultManager interviewResultManager = new InterviewResultManager();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			//'execute' 메서드 호출 및 반환된 뷰 처리
			String view = execute(request, response);
			request.getRequestDispatcher(view).forward(request, response);\
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "면담 결과 처리 중 오류 발생");
		}
	}
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                return createInterviewResult(request, response);
            case "read":
                return readInterviewResult(request, response);
            case "update":
                return updateInterviewResult(request, response);
            case "delete":
                return deleteInterviewResult(request, response);
            default:
                request.setAttribute("errorMessage", "Invalid action");
                return "error.jsp";
        }
    }
	
	private String createInterviewResult(HttpServletRequest request, HttpServletResponse response) throws IOException{
		InterviewResult interviewResult = new InterviewResult();
		interviewResult.setInterviewTopic(request.getParameter("interviewTopic"));
		interviewResult.setInterviewSummary(request.getParameter("interviewSummary"));
		
		boolean success = interviewResultManager.createInterviewResult(interviewResult);
		if (createdInterviewResult != null) {
			request.setAttribute("message", "InterviewResult created successfully: " + createdInterviewResult.toString());
			return "interviewResultSuccess.jsp"; 
		} else {
			request.setAttribute("errorMessage", "Failed to create interviewResult");
			return "interviewResultError.jsp";
		}
	}
}
