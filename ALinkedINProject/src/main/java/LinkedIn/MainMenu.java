package main.java.LinkedIn;

import java.io.IOException;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.LinkedIn.LinkedInConnection;
import main.java.LinkedIn.LNGraph;

/**
 * Servlet implementation class MainMenu
 */
@WebServlet("/MainMenu")
public class MainMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String code = " ";
	private final String accessToken1 = " ";
    

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		code = request.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}
		// res.setContentType("application/json");

		System.getProperties().put("http.proxyHost", "proxy.sgp.hp.com");
		System.getProperties().put("http.proxyPort", "8080");

		System.out.println("inside try111111" + code);
		
		LinkedInConnection lnConnection = new LinkedInConnection();
		String accessToken = lnConnection.getAccessToken(code);
		System.out.println("accessToken" + accessToken);
		LNGraph LNGraph = new LNGraph(accessToken);// get the user accessToken
		String graph = LNGraph.getLBGraph();
		// String graph = fbGraph.getUserInfo(accessToken);
		System.out.println("accessTokensss:" + graph);
		Map<String, String> lnProfileData = LNGraph.getGraphData(graph);
		ServletOutputStream out = response.getOutputStream();
		out.println("<h1>LinkedIn Login using Java</h1>");
		out.println("<h2>Application Main Menu</h2>");
		out.println("<div>First Name:" + lnProfileData.get("first_name"));
		out.println("<div> Last Name:" + lnProfileData.get("last_name"));
		out.println("<div> headline:" + lnProfileData.get("headline"));
		
		

	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
