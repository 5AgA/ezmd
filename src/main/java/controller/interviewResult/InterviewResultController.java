package controller.interviewResult;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller; // Controller 인터페이스 import
import javax.servlet.http.HttpServlet;
import model.dao.InterviewResultDAO;

@WebServlet("/interviewResult/create")
public class InterviewResultController extends HttpServlet implements Controller {
    private InterviewResultDAO interviewResultDAO = new InterviewResultDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("form");
    	
        if (action != null) {
            saveInterviewResult(request, response);
        } else {
            request.setAttribute("error", "Invalid action");
            //request.getRequestDispatcher("/error.jsp").forward(request, response);
            System.out.println("아 개같다.");
        }
        
    }

    private void saveInterviewResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 입력 데이터 처리
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            String interviewTopic = request.getParameter("title");
            String summary = request.getParameter("summary");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String feedback = request.getParameter("feedback");

            // DB 작업 수행
            int result = interviewResultDAO.createInterviewResult(interviewId, interviewTopic, summary, feedback, rating);

            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{ \"message\": \"Interview result saved successfully\" }");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{ \"error\": \"Failed to save interview result\" }");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{ \"error\": \"An error occurred while saving interview result\" }");
        }
    }

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
