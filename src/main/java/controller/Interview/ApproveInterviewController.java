package controller.Interview;

import controller.Controller;
import model.dao.StudentDAO;
import model.dao.ProfessorDAO;
import model.domain.Schedule;
import model.domain.ScheduleCategory;
import model.manager.interview.InterviewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.manager.schedule.ScheduleCategoryService;
import model.manager.schedule.ScheduleService;

import java.time.LocalDateTime;

public class ApproveInterviewController implements Controller {
    private InterviewService interviewService;
    private ScheduleService scheduleService;

    public ApproveInterviewController() {
        interviewService = new InterviewService();
        scheduleService = new ScheduleService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int interviewId = Integer.parseInt(request.getParameter("interviewId"));

        int result =  interviewService.approveInterview(interviewId);
        LocalDateTime requestedDate = interviewService.getInterviewById(interviewId).getRequestedDate();
        String interviewCategory = interviewService.getInterviewById(interviewId).getInterviewCategory();
        String interviewNote = interviewService.getInterviewById(interviewId).getInterviewNote();
        int studentId = interviewService.getInterviewById(interviewId).getStudentId();
        int professorId = interviewService.getInterviewById(interviewId).getProfessorId();

        // 스케줄 추가
        ScheduleCategory category = new ScheduleCategory();
        category.setCategoryName("면담");
        category.setCategoryColor("#FF0000");


        Schedule schedule = new Schedule();
        schedule.setScheduleTitle(new ProfessorDAO().findProfessorById(professorId).getName() + " 교수님과" +
                interviewCategory + " 면담");
        schedule.setScheduleStart(Schedule.parseDateTime(requestedDate.toString()));
        schedule.setScheduleEnd(Schedule.parseDateTime(requestedDate.plusMinutes(30).toString()));
        schedule.setSchedulePlace(new ProfessorDAO().findProfessorById(professorId).getProfessorOffice());
        schedule.setScheduleMemo(interviewNote);
//        schedule.setCategoryId(new ScheduleCategoryService().addScheduleCategory());
        Gson gson = new Gson();

        // 응답 처리: 성공/실패에 대한 메시지
        response.setContentType("application/json; charset=UTF-8");
        String jsonResponse;
        if (result == 1) {
            jsonResponse = gson.toJson(new ResponseMessage("success", "일정이 성공적으로 추가되었습니다."));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage("error", "일정 추가에 실패했습니다."));
        }

        response.getWriter().write(jsonResponse);
        return null;
    }

    // 응답 메시지 객체
    private static class ResponseMessage {
        private String status;
        private String message;

        public ResponseMessage(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
