package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import dao.Dao;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		Dao dao = Dao.getInstance();
		String action = request.getParameter("action");
		if (action.equals("get")) {
			try {
				List<JSONObject> messageList = dao
						.getMessages(Integer.parseInt(request
								.getParameter("lastReceivedMessageID")));
				PrintWriter out = response.getWriter();
				out.println(messageList);
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("send")) {
			String username = request.getParameter("username");
			String messageText = request.getParameter("messageText");
			int lastReceivedMessageID = Integer.parseInt(request
					.getParameter("lastReceivedMessageID"));
			try {
				dao.saveMessage(username, messageText);
				List<JSONObject> messageList = dao
						.getMessages(lastReceivedMessageID);
				PrintWriter out = response.getWriter();
				out.println(messageList);
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("getLastMessage")) {
			try {
				PrintWriter out = response.getWriter();
				JSONObject returnObj = dao.getLastMessage();
				out.println(returnObj);
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
