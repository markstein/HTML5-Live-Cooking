package de.mosst.skeletons.gae.resteasy.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SecureServlet extends HttpServlet {
	
	private static Logger LOG = Logger.getLogger(SecureServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    String userId = user.getUserId();
	    String nickname = user.getNickname();
	    String email = user.getEmail();
	    
	    LOG.fine("User: " + userId);
	    LOG.fine("Nickname: " + nickname);
	    LOG.fine("Email: " + email);
	    
		resp.setContentType("text/html");
		resp.getWriter().println("Hello, <h1>" + nickname + "</h1><br>");
		resp.getWriter().println("UserId: " + userId + "<br>");
		resp.getWriter().println("Email: " + email);
	}
}
