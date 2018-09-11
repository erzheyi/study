package servlet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;
import util.AppMD5Util;
import util.VerifyCodeUtil;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao = new UserDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if ("showLogin".equals(type)) {
			showLogin(request, response);
		} else if ("doLogin".equals(type)) {
			doLogin(request, response);
		} else if ("showRegister".equals(type)) {
			showRegister(request, response);
		} else if ("doRegister".equals(type)) {
			doRegister(request, response);
		} else if ("randomImage".equals(type)) {
			randomImage(request, response);
		}
	}

	private void randomImage(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_LOWER, 4, null);
			request.getSession().setAttribute("verifyCode", verifyCode);
			response.setContentType("image/jpeg");
			BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 100, 34, 100, true, Color.WHITE, Color.BLACK, null);
			ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("name");
		String password = AppMD5Util.getMD5(request.getParameter("password")+"用户密码");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		userDao.add(user);
		response.sendRedirect("user?type=showLogin");
	}

	private void showRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("WEB-INF/user/register.jsp").forward(request, response);
	}

	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String yzm = request.getParameter("yzm");
		String code = (String) session.getAttribute("verifyCode");
		if (!yzm.equals(code)) {
			String message = "验证码输入错误！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
		} else {
			session.removeAttribute("verifyCode");
			String username = request.getParameter("name");
			String password = AppMD5Util.getMD5(request.getParameter("password")+"用户密码");
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			boolean flag = userDao.search(user);
			if (flag) {
				session.setAttribute("user", username);
				Cookie cookie = new Cookie("user", username);
				cookie.setMaxAge(60);
				response.addCookie(cookie);
				response.sendRedirect("index");
			} else {
				response.sendRedirect("user?type=showLogin");
			}
		}
	}

	private void showLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String user = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if ("user".equals(cookies[i].getName())) {
					user = cookies[i].getValue();
				}
			}
		}
		request.setAttribute("user", user);
		request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
