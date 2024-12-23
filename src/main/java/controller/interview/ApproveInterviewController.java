package controller.Interview;

import controller.Controller;
import model.manager.interview.InterviewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

public class ApproveInterviewController implements Controller {
    private InterviewService interviewService;

    public ApproveInterviewController() {
        interviewService = new InterviewService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int interviewId = Integer.parseInt(request.getParameter("interviewId"));

        int result =  interviewService.approveInterview(interviewId);
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
