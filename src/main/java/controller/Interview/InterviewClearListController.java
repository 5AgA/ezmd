package controller.Interview;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.InterviewDAO;
import model.dao.InterviewResultDAO;
import model.domain.Interview;
import model.domain.InterviewResult;

@WebServlet("/interview/result")
public class InterviewClearListController extends HttpServlet {

    private InterviewDAO interviewDAO = new InterviewDAO();
    private InterviewResultDAO interviewResultDAO = new InterviewResultDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // action 파라미터로 요청 구분
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // 첫 번째 화면: 면담 완료 리스트 가져오기
            int studentId = 20210670; // 세션에서 studentId 가져오는 로직 대신 임시 값 사용
            List<Interview> completedInterviews = interviewDAO.getCompletedInterviewsByStudentId(studentId);

            // 디버깅 로그 출력
            System.out.println("완료된 면담 개수: " + completedInterviews.size());
            if (completedInterviews.isEmpty()) {
                System.out.println("완료된 면담이 없습니다.");
            } else {
                for (Interview interview : completedInterviews) {
                    System.out.println("교수 ID: " + interview.getProfessorId());
                    System.out.println("날짜: " + interview.getRequestedDate().toLocalDate());
                    System.out.println("시간: " + interview.getRequestedDate().toLocalTime());
                    System.out.println("-----------------------------");
                }
            }

            // JSP로 데이터 전달
            request.setAttribute("interviewList", completedInterviews);

            // JSP로 포워드
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/interviewManager_clear/interviewClearList.jsp");
            dispatcher.forward(request, response);

        } else if ("getSavedData".equals(action)) {
            // 면담 ID로 면담 카테고리 조회
            try {
                int interviewId = Integer.parseInt(request.getParameter("interviewId"));
                InterviewResult savedData = interviewResultDAO.getSavedData(interviewId);
                // DB에서 interview_category 조회
                //InterviewResult interviewSavedData = interviewResultDAO.getSavedData(interviewId);

                if (savedData != null) {
                    // 조회된 데이터를 JSON 형식으로 응답
                	String jsonResponse = String.format(
                            "{ \"interviewId\": %d, \"interviewTopic\": \"%s\", \"interviewSummary\": \"%s\", \"reviewOfInterview\": \"%s\", \"reviewRating\": %d }",
                            savedData.getInterviewId(),
                            savedData.getInterviewTopic(),
                            savedData.getInterviewSummary(),
                            savedData.getReviewOfInterview(),
                            savedData.getReviewRating()
                        );
                	response.setContentType("application/json");
                    response.getWriter().write(jsonResponse);
                } else {
                    // 데이터가 없으면 기본값 반환
                    response.setContentType("application/json");
                    response.getWriter().write("{ \"interviewId\": \"" + interviewId + "\", \"interviewTopic\": \"\", \"interviewSummary\": \"\", \"reviewOfInterview\": \"\", \"reviewRating\": 0 }");
                }
            }  catch (Exception e) {
                e.printStackTrace();
                // 실제 에러가 발생한 경우만 500 응답 전송
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{ \"error\": \"An unexpected error occurred.\" }");
            }
        }
            }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateInterviewResult(request, response);
        } else {
            saveInterviewResult(request, response);
        }
    }

    private void saveInterviewResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        	 System.out.println("Received Data: ");
             System.out.println("interviewId: " + request.getParameter("interviewId"));
             System.out.println("title: " + request.getParameter("title"));
             System.out.println("summary: " + request.getParameter("summary"));
             System.out.println("rating: " + request.getParameter("rating"));
             System.out.println("feedback: " + request.getParameter("feedback"));
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            String interviewTopic = request.getParameter("title");
            String summary = request.getParameter("summary");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String feedback = request.getParameter("feedback");

            int result = interviewResultDAO.createInterviewResult(interviewId, interviewTopic, summary, feedback, rating);

            if (result > 0) {
                // 성공 메시지 세션에 저장
                request.getSession().setAttribute("successMessage", "면담 결과가 성공적으로 저장되었습니다.");
                response.sendRedirect(request.getContextPath() + "/interview/result");
            } else {
                // 실패 메시지 세션에 저장
                request.getSession().setAttribute("errorMessage", "면담 결과 저장에 실패했습니다.");
                response.sendRedirect(request.getContextPath() + "/interview/result");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{ \"error\": \"An error occurred while saving interview result\" }");
        }
    }
            
    private void updateInterviewResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        	 System.out.println("Received Data: ");
             System.out.println("interviewId: " + request.getParameter("interviewId"));
             System.out.println("title: " + request.getParameter("title"));
             System.out.println("summary: " + request.getParameter("summary"));
             System.out.println("rating: " + request.getParameter("rating"));
             System.out.println("feedback: " + request.getParameter("feedback"));
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            String interviewTopic = request.getParameter("title");
            String summary = request.getParameter("summary");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String feedback = request.getParameter("feedback");

            int result = interviewResultDAO.updateInterviewResult(interviewId, interviewTopic, summary, feedback, rating);

            if (result > 0) {
                // 성공 메시지 세션에 저장
                request.getSession().setAttribute("successMessage", "면담 결과가 성공적으로 수정되었습니다.");
                response.sendRedirect(request.getContextPath() + "/interview/result");
            } else {
                // 실패 메시지 세션에 저장
                request.getSession().setAttribute("errorMessage", "면담 결과 수정에 실패했습니다.");
                response.sendRedirect(request.getContextPath() + "/interview/result");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{ \"error\": \"An error occurred while saving interview result\" }");
        }
    }
        }

