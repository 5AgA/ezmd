package controller.interview;

import model.domain.Interview;
import model.dto.InterviewDTO;
import model.manager.interview.InterviewManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/api/interview")
public class InterviewController extends HttpServlet implements Controller{
	
    private final InterviewManager interviewManager = new InterviewManager();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	System.out.println("디버깅");
        	
            String view = execute(request, response);
            request.getRequestDispatcher(view).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "면담 처리 중 오류 발생");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String view = execute(request, response);
            request.getRequestDispatcher(view).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "면담 조회 중 오류 발생");
        }
    }
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                return createInterview(request, response);
            case "getByStudentId":
                return getInterviewListByStudentId(request, response);
            case "getByProfessorId":
                return getInterviewListByProfessorId(request, response);
            case "getByProfessorIdAndStatus":
            	return getInterviewListByProfessorIdAndStatus(request, response);
            case "getById":
                return getInterviewById(request, response);
            case "update":
                return updateInterview(request, response);
            case "delete":
                return deleteInterview(request, response);
            case "approve":
                return approveInterview(request, response);
            case "reject":
                return rejectInterview(request, response);
            default:
                request.setAttribute("errorMessage", "Invalid action");
                return "/home";
        }
    }
    //면담생성 컨트롤러
    private String createInterview(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestedDateStr = request.getParameter("requestedDate");
            int professorId = Integer.parseInt(request.getParameter("professorId"));
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String interviewCategory = request.getParameter("interviewCategory");
            String interviewNote = request.getParameter("interviewNote");
            
            System.out.println("Requested date: " + requestedDateStr);
            System.out.println("Professor ID: " + professorId);
            System.out.println("Student ID: " + studentId);
            System.out.println("Interview Category: " + interviewCategory);
            System.out.println("Interview Note: " + interviewNote);
           
            LocalDateTime requestedDate = LocalDateTime.parse(requestedDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
            // Interview 객체 생성 후 필요한 필드 set
            Interview interview = new Interview();
            interview.setRequestedDate(requestedDate);
            interview.setInterviewCategory(interviewCategory);
            interview.setInterviewNote(interviewNote);
            interview.setStudentId(studentId);
            interview.setProfessorId(professorId);

            int success = interviewManager.createInterview(interview);

            if (success != 0) {
                request.setAttribute("message", "면담이 성공적으로 생성되었습니다.");
                return "/home";
            } else {
                request.setAttribute("errorMessage", "면담 생성 실패");
                return "/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 생성 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    
    private String getInterviewListByStudentId(HttpServletRequest request, HttpServletResponse response) {
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            List<Interview> interviews = interviewManager.getInterviewListByStudentId(studentId);
            request.setAttribute("interviews", interviews);
            return "/home";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 목록 조회 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    
    private String getInterviewListByProfessorId(HttpServletRequest request, HttpServletResponse response) {
        try {
            int professorId = Integer.parseInt(request.getParameter("professorId"));
            List<Interview> interviews = interviewManager.getInterviewListByStudentId(professorId);
            request.setAttribute("interviews", interviews);
            return "/home";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 목록 조회 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    
    private String getInterviewListByProfessorIdAndStatus(HttpServletRequest request, HttpServletResponse response) {
        try {            
        	String professorIdParam = request.getParameter("professorId");
            System.out.println(professorIdParam);
            int professorId = Integer.parseInt(request.getParameter("professorId"));

            List<InterviewDTO> interviews = interviewManager.getInterviewListByProfessorIdAndStatus(professorId);
            request.setAttribute("interviews", interviews);
            return "/interview-check";
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "교수의 비승인 면담 목록 조회 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    
    private String getInterviewById(HttpServletRequest request, HttpServletResponse response) {
        try {
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            Interview interview = interviewManager.getInterviewById(interviewId);
            if (interview != null) {
                request.setAttribute("interview", interview);
                return "/home";
            } else {
                request.setAttribute("errorMessage", "해당 인터뷰를 찾을 수 없습니다.");
                return "/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 조회 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    private String updateInterview(HttpServletRequest request, HttpServletResponse response) {
        try {
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            String requestedDateStr = request.getParameter("requestedDate");
            String interviewCategory = request.getParameter("interviewCategory");
            String interviewNote = request.getParameter("interviewNote");

            LocalDateTime requestedDate = LocalDateTime.parse(requestedDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            Interview interview = new Interview();
            interview.setInterviewId(interviewId);
            interview.setRequestedDate(requestedDate);
            interview.setInterviewCategory(interviewCategory);
            interview.setInterviewNote(interviewNote);

            boolean success = interviewManager.updateInterview(interview);
            if (success) {
                request.setAttribute("message", "면담이 성공적으로 업데이트되었습니다.");
                return "/home";
            } else {
                request.setAttribute("errorMessage", "면담 업데이트 실패");
                return "/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 업데이트 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    private String deleteInterview(HttpServletRequest request, HttpServletResponse response) {
        try {
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            boolean success = interviewManager.deleteInterview(interviewId);
            if (success) {
                request.setAttribute("message", "면담이 성공적으로 삭제되었습니다.");
                return "/home";
            } else {
                request.setAttribute("errorMessage", "면담 삭제 실패");
                return "/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 삭제 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    
    private String approveInterview(HttpServletRequest request, HttpServletResponse response) {
        try {
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            boolean success = interviewManager.approveInterview(interviewId);
            if (success) {
                request.setAttribute("message", "면담이 승인되었습니다.");
                return "/home";
            } else {
                request.setAttribute("errorMessage", "면담 승인 실패");
                return "/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 승인 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
    
    private String rejectInterview(HttpServletRequest request, HttpServletResponse response) {
        try {
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            boolean success = interviewManager.rejectInterview(interviewId);
            if (success) {
                request.setAttribute("message", "면담이 반려되었습니다.");
                return "/home";
            } else {
                request.setAttribute("errorMessage", "면담 반려 실패");
                return "/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "면담 반려 중 오류 발생: " + e.getMessage());
            return "/home";
        }
    }
}