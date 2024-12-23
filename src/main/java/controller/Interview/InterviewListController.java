package controller.Interview;

import controller.Controller;
import model.domain.Interview;
import model.manager.interview.InterviewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class InterviewListController implements Controller {
    private InterviewService interviewService;

    public InterviewListController() {
        this.interviewService = new InterviewService();
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
            if (interview.getInterviewStatus() == "approved") {
                approvedList.add(interview);
            } else if (interview.getInterviewStatus() == "pending") {
                pendingList.add(interview);
            } else if (interview.getInterviewStatus() == "rejected") {
                rejectedList.add(interview);
            }
        }

        System.out.println(approvedList);
        System.out.println(pendingList);
        System.out.println(rejectedList);

        request.setAttribute("approvedList", approvedList);
        request.setAttribute("pendingList", pendingList);
        request.setAttribute("rejectedList", rejectedList);
        return "/interview/interview-check.jsp";
    }
}
