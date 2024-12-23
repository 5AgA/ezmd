package controller.Interview;

import controller.Controller;
import model.dao.StudentDAO;
import model.domain.Interview;
import model.manager.interview.InterviewService;
import model.manager.user.ProfessorService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class InterviewListController implements Controller {
    private InterviewService interviewService;
    private StudentDAO studentDAO;

    public InterviewListController() {
        this.interviewService = new InterviewService();
        this.studentDAO = new StudentDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        String userType = request.getSession().getAttribute("userType").toString();
        List<Interview> interviewList = new ArrayList<Interview>();
        List<Interview> approvedList = new ArrayList<Interview>();
        List<Interview> pendingList = new ArrayList<Interview>();
        List<Interview> rejectedList = new ArrayList<Interview>();

        if (userType == "student") {
            interviewList = interviewService.getInterviewsByStudentId(userId);
        } else if (userType == "professor") {
            interviewList = interviewService.getInterviewsByProfId(userId);
        }

        for (Interview interview : interviewList) {
            interview.setStudentName(studentDAO.findStudentById(interview.getStudentId()).getName());
            interview.setStudentMajor(studentDAO.findStudentById(interview.getStudentId()).getDept());
            interview.setProfessorName(new ProfessorService().getProfessorById(interview.getProfessorId()).getName());

            if ("approved".equals(interview.getInterviewStatus())) {
                approvedList.add(interview);
            } else if ("pending".equals(interview.getInterviewStatus())) {
                pendingList.add(interview);
            } else if ("rejected".equals(interview.getInterviewStatus())) {
                rejectedList.add(interview);
            }
        }

        if (approvedList.size() == 0) {
            request.setAttribute("approvedList", null);
        } else {
            request.setAttribute("approvedList", approvedList);
        }
        if (pendingList.size() == 0) {
            request.setAttribute("pendingList", null);
        } else {
            request.setAttribute("pendingList", pendingList);
        }
        if (rejectedList.size() == 0) {
            request.setAttribute("rejectedList", null);
        } else {
            request.setAttribute("rejectedList", rejectedList);
        }

        // RequestDispatcher를 이용해 포워딩
        String requestUri = request.getRequestURI();
        RequestDispatcher dispatcher;
        if (requestUri.contains("/home")) {
            dispatcher = request.getRequestDispatcher("/WEB-INF/user/home.jsp");  // /home으로 포워딩
        } else {
            dispatcher = request.getRequestDispatcher("/WEB-INF/interview/interview-check.jsp");
        }
        dispatcher.forward(request, response);  // 포워딩

        return null;  // 포워딩이 되므로 return null로 종료
    }
}
