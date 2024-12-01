package model.manager.user;

import javax.servlet.http.HttpSession;

public class LogoutManager {

	//세션 무효화
	public void logout(HttpSession session) {
		if(session != null) {
			session.invalidate();
		}
	}
}
