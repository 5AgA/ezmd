package controller.Interview;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.InterviewDAO;
import model.dao.InterviewResultDAO;
import model.dao.ProfessorDAO;
import model.dao.StudentDAO;
import model.domain.Interview;
import model.domain.InterviewResult;
import model.domain.Professor;

@WebServlet("/interview/result")
public class InterviewClearListController extends HttpServlet {

    private InterviewDAO interviewDAO = new InterviewDAO();
    private InterviewResultDAO interviewResultDAO = new InterviewResultDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// 세션에서 사용자 정보 가져오기
    	  HttpSession session = request.getSession(false);
          if (session == null || session.getAttribute("user") == null || session.getAttribute("userType") == null) {
              response.sendRedirect("/login/form");
              return;
          }
          String userType = (String) session.getAttribute("userType");
          int userId;
          List<Interview> completedInterviews = null;
          List<Interview> notCompletedInterviews = null;
        // action 파라미터로 요청 구분
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // 첫 번째 화면: 면담 완료 리스트 가져오기
        	   // HttpSession session = request.getSession();
            // int studentId = (int) session.getAttribute("studentId");
         
           // int studentId = 20210670; // 세션에서 studentId 가져오는 로직 대신 임시 값 사용
            //List<Interview> completedInterviews = interviewDAO.getCompletedInterviewsByStudentId(studentId);

        	 if ("Professor".equalsIgnoreCase(userType)) {
                 userId = ((model.domain.Professor) session.getAttribute("user")).getProfessorId();
                 // 교수 계정: 교수 ID로 면담 완료 리스트 조회
                 completedInterviews = interviewDAO.getCompletedInterviewsByProfessorId(userId);
                 notCompletedInterviews = interviewDAO.getNotCompletedInterviewsByProfessorId(userId);
                 request.setAttribute("viewType", "student"); // JSP에서 학생 이름 표시
             } else if ("Student".equalsIgnoreCase(userType)) {
                 userId = ((model.domain.Student) session.getAttribute("user")).getStudentId();
                 // 학생 계정: 학생 ID로 면담 완료 리스트 조회
                 completedInterviews = interviewDAO.getCompletedInterviewsByStudentId(userId);
                 notCompletedInterviews = interviewDAO.getNotCompletedInterviewsByStudentId(userId);
                 request.setAttribute("viewType", "professor"); // JSP에서 교수 이름 표시
             } else {
            	 System.out.println("교수도 아니고 학생도 아님");
             }

             if (notCompletedInterviews.isEmpty()) {
                 System.out.println("미완료된 면담이 없습니다.");
             } else {
                 for (Interview interview : notCompletedInterviews) {
                     try {
                         interview.setProfessorName(new ProfessorDAO().findProfessorById(interview.getProfessorId()).getName());
                     } catch (SQLException e) {
                         throw new RuntimeException(e);
                     }
                     try {
                         interview.setStudentName(new StudentDAO().findStudentById(interview.getStudentId()).getName());
                     } catch (SQLException e) {
                         throw new RuntimeException(e);
                     }
                 }
             }

            System.out.println("완료된 면담 개수: " + completedInterviews.size());
            if (completedInterviews.isEmpty()) {
                System.out.println("완료된 면담이 없습니다.");
            } else {
                for (Interview interview : completedInterviews) {
                    try {
                        interview.setProfessorName(new ProfessorDAO().findProfessorById(interview.getProfessorId()).getName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        interview.setStudentName(new StudentDAO().findStudentById(interview.getStudentId()).getName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // JSP로 데이터 전달
            request.setAttribute("interviewList", completedInterviews);
            request.setAttribute("notCompletedInterviewList", notCompletedInterviews);

            System.out.println("완료된 면담 개수: " + completedInterviews.size());
            System.out.println("미완료된 면담 개수: " + notCompletedInterviews.size());

            // JSP로 포워드
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/interviewManager_clear/interviewClearList.jsp");
            dispatcher.forward(request, response);

        } else if ("getSavedData".equals(action)) {
            // 면담 ID로 면담 카테고리 조회
            try {
                int interviewId = Integer.parseInt(request.getParameter("interviewId"));
                InterviewResult savedData = interviewResultDAO.getSavedData(interviewId);
               
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
            // 클라이언트에서 받은 데이터
            int interviewId = Integer.parseInt(request.getParameter("interviewId"));
            String interviewTopic = request.getParameter("title");
            String summary = request.getParameter("summary");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String feedback = request.getParameter("feedback");

    
            // 데이터베이스에 데이터 존재 여부 확인
            boolean isExisting = interviewResultDAO.isInterviewResultExists(interviewId);
            System.out.println("isExisting: " + isExisting); // 디버깅 로그

            int result;
            if (isExisting) {
                // 데이터가 존재하면 update 수행
                result = interviewResultDAO.updateInterviewResult(interviewId, interviewTopic, summary, feedback, rating);
                if (result > 0) {
                    request.getSession().setAttribute("successMessage", "면담 결과가 성공적으로 수정되었습니다.");
                } else {
                    request.getSession().setAttribute("errorMessage", "면담 결과 수정에 실패했습니다.");
                }
            } else {
                // 데이터가 존재하지 않으면 insert 수행
                result = interviewResultDAO.createInterviewResult(interviewId, interviewTopic, summary, feedback, rating);
                if (result > 0) {
                    request.getSession().setAttribute("successMessage", "면담 결과가 성공적으로 저장되었습니다.");
                } else {
                    request.getSession().setAttribute("errorMessage", "면담 결과 저장에 실패했습니다.");
                }
            }

            response.sendRedirect(request.getContextPath() + "/interview/result");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{ \"error\": \"An error occurred while saving interview result\" }");
        }
    }

            
    private void updateInterviewResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
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

