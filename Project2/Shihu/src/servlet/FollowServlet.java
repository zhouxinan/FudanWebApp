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

import bean.User;
import dao.Dao;

import org.json.*;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FollowServlet() {
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
		User user = (User) request.getSession().getAttribute("user");
		if (action.equals("getPopularUserList")) {
			int popularUserNumber = Integer.parseInt(request.getParameter("popularUserNumber"));
			try {
				PrintWriter out = response.getWriter();
				out.println(dao.getPopularUserList(popularUserNumber));
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		} else if (action.equals("getFollowInfo")) {
			try {
				int userID = Integer.parseInt(request.getParameter("userID"));
				PrintWriter out = response.getWriter();
				out.println(dao.getFollowInfo(user, userID));
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		if (user == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		if (action.equals("follow")) {
			try {
				int toUserID = Integer.parseInt(request
						.getParameter("toUserID"));
				PrintWriter out = response.getWriter();
				out.println(dao.follow(user, toUserID));
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("defollow")) {
			try {
				int toUserID = Integer.parseInt(request
						.getParameter("toUserID"));
				PrintWriter out = response.getWriter();
				out.println(dao.defollow(user, toUserID));
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("checkFollow")) {
			try {
				int toUserID = Integer.parseInt(request
						.getParameter("toUserID"));
				PrintWriter out = response.getWriter();
				out.print(dao.checkFollow(user, toUserID));
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("getFollowers")) {
			try {
				int toUserID = Integer.parseInt(request
						.getParameter("toUserID"));
				List<JSONObject> followerList = dao.getFollowers(toUserID);
				PrintWriter out = response.getWriter();
				out.println(followerList);
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("getFollowing")) {
			try {
				int fromUserID = Integer.parseInt(request
						.getParameter("fromUserID"));
				List<JSONObject> followingList = dao.getFollowing(fromUserID);
				PrintWriter out = response.getWriter();
				out.println(followingList);
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
