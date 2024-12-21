package controller.user;

import com.google.gson.Gson;
import controller.Controller;
import model.domain.Professor;
import model.manager.user.ProfessorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SearchProfController implements Controller {
    private ProfessorService professorService;

    public SearchProfController() {
        this.professorService = new ProfessorService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Professor> professors = professorService.searchProfessorsByKeyword(request.getParameter("keyword"));

        // JSON 응답
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new Gson().toJson(professors));
        return null; // JSON만 반환
    }
}
