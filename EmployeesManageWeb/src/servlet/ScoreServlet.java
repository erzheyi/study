package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DeptDao;
import dao.ProjectDao;
import dao.ScoreDao;
import entity.Score;
import entity.Dept;
import entity.Employee;
import entity.Project;
import net.sf.json.JSONArray;
import util.Constant;
import util.PageModel;

/**
 * Servlet implementation class ScoreServlet
 */
@WebServlet("/score")
public class ScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ScoreDao scoDao = new ScoreDao();
	private DeptDao depDao = new DeptDao();
	private ProjectDao proDao = new ProjectDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScoreServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = (String) request.getParameter("type");
		if (type == null) {
			search(request, response);
		} else if ("search".equals(type)) {
			search(request, response);
		} else if ("search2".equals(type)) {
			search2(request, response);
		} else if ("update".equals(type)) {
			update(request, response);
		} else if ("update2".equals(type)) {
			update2(request, response);
		} else if ("depPro".equals(type)) {
			depPro(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int depId = -1;
		int proId = -1;
		double value = -1;
		String grade = request.getParameter("grade");

		Dept dept = null;
		Project project = null;

		if (!"".equals(request.getParameter("dept")) && request.getParameter("dept") != null) {
			depId = Integer.valueOf(request.getParameter("dept"));
			if (depId != -1) {
				dept = depDao.searchById(depId);
			}
		}
		if (!"".equals(request.getParameter("project")) && request.getParameter("project") != null) {
			proId = Integer.valueOf(request.getParameter("project"));
			if (proId != -1) {
				project = proDao.searchById(proId);
			}
		}
		if (!"".equals(request.getParameter("value")) && request.getParameter("value") != null) {
			value = Double.valueOf(request.getParameter("value"));
		}
		request.setAttribute("name", name);
		request.setAttribute("depId", depId);
		request.setAttribute("proId", proId);
		request.setAttribute("value", value);
		request.setAttribute("grade", grade);
		List<Dept> depList = depDao.searchAll();
		request.setAttribute("depList", depList);
		List<Project> proList = proDao.searchAll();	
		request.setAttribute("proList", proList);
		List<String> graList = new ArrayList<>();
		graList.add("优秀");
		graList.add("良好");
		graList.add("一般");
		graList.add("及格");
		graList.add("不及格");
		request.setAttribute("graList", graList);

		Employee employee = new Employee();
		employee.setName(name);
		employee.setDept(dept);

		int currPage = 1;
		int pages = 0;
		if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
			currPage = Integer.parseInt(request.getParameter("page"));
		}
		if (currPage < 1) {
			currPage = 1;
		}
		Score condition = new Score();
		condition.setEmployee(employee);
		condition.setProject(project);
		condition.setValue(value);
		condition.setGrade(grade);
		int count = scoDao.findCount(condition);
		if (count % Constant.DEP_PAGE_SIZE == 0) {
			pages = count / Constant.DEP_PAGE_SIZE;
		} else {
			pages = count / Constant.DEP_PAGE_SIZE + 1;
		}
		List<Score> scoList = scoDao.searchByCondition(condition, currPage);
		PageModel<Score> pageModel = new PageModel();
		pageModel.setPageNumber(Constant.SCO_PAGE_NUMBER);
		pageModel.setCurrPage(currPage);
		pageModel.setPages(pages);
		pageModel.setCount(count);
		pageModel.setList(scoList);
		request.setAttribute("pageModel", pageModel);
		request.getRequestDispatcher("WEB-INF/score/score.jsp").forward(request, response);
	}

	public void search2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int depId = -1;
		int proId = -1;
		double value = -1;
		String grade = request.getParameter("grade");

		Dept dept = null;
		Project project = null;

		if (!"".equals(request.getParameter("dept")) && request.getParameter("dept") != null) {
			depId = Integer.valueOf(request.getParameter("dept"));
			if (depId != -1) {
				dept = depDao.searchById(depId);
			}
		}
		if (!"".equals(request.getParameter("project")) && request.getParameter("project") != null) {
			proId = Integer.valueOf(request.getParameter("project"));
			if (proId != -1) {
				project = proDao.searchById(proId);
			}
		}
		if (!"".equals(request.getParameter("value")) && request.getParameter("value") != null) {
			value = Double.valueOf(request.getParameter("value"));
		}
		request.setAttribute("name", name);
		request.setAttribute("depId", depId);
		request.setAttribute("proId", proId);
		request.setAttribute("value", value);
		request.setAttribute("grade", grade);
		List<Dept> depList = depDao.searchAll();
		request.setAttribute("depList", depList);
		List<Project> proList = null;
		if(depId != -1) {
			proList = depDao.searchProjectById(depId);
		} else {
			proList = proDao.searchAll();
		}
		request.setAttribute("proList", proList);
		List<String> graList = new ArrayList<>();
		graList.add("优秀");
		graList.add("良好");
		graList.add("一般");
		graList.add("及格");
		graList.add("不及格");
		request.setAttribute("graList", graList);

		Employee employee = new Employee();
		employee.setName(name);
		employee.setDept(dept);

		int currPage = 1;
		int pages = 0;
		if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
			currPage = Integer.parseInt(request.getParameter("page"));
		}
		if (currPage < 1) {
			currPage = 1;
		}
		Score condition = new Score();
		condition.setEmployee(employee);
		condition.setProject(project);
		condition.setValue(value);
		condition.setGrade(grade);
		int count = scoDao.findCount(condition);
		if (count % Constant.DEP_PAGE_SIZE == 0) {
			pages = count / Constant.DEP_PAGE_SIZE;
		} else {
			pages = count / Constant.DEP_PAGE_SIZE + 1;
		}
		List<Score> scoList = scoDao.searchByCondition(condition, currPage);
		PageModel<Score> pageModel = new PageModel();
		pageModel.setPageNumber(Constant.SCO_PAGE_NUMBER);
		pageModel.setCurrPage(currPage);
		pageModel.setPages(pages);
		pageModel.setCount(count);
		pageModel.setList(scoList);
		request.setAttribute("pageModel", pageModel);
		request.getRequestDispatcher("WEB-INF/score/score2.jsp").forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String score = request.getParameter("score");
		String[] str = score.split(";");
		for (int i = 0; i < str.length; i++) {
			String[] s = str[i].split(",");
			if ("0".equals(s[0]) && !"".equals(s[1])) {
				double value = Double.parseDouble(s[1]);
				int empId = Integer.valueOf(s[2]);
				int proId = Integer.valueOf(s[3]);
				scoDao.addScore(value, empId, proId);
			} else if (!"0".equals(s[0]) && !"".equals(s[1])) {
				int id = Integer.valueOf(s[0]);
				double value = Double.parseDouble(s[1]);
				scoDao.modifyScore(value, id);
			} else if (!"0".equals(s[0]) && "".equals(s[1])) {
				int id = Integer.valueOf(s[0]);
				scoDao.removeScoreById(id);
			}
		}
		response.sendRedirect("score");
	}

	private void update2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		int id = 0;
		if ("0".equals(request.getParameter("id")) && !"".equals(request.getParameter("value"))) {
			double value = Double.parseDouble(request.getParameter("value"));
			int empId = Integer.parseInt(request.getParameter("empId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			flag = scoDao.addScore(value, empId, proId);
			id = scoDao.searchId(empId, proId);
		} else if (!"0".equals(request.getParameter("id")) && !"".equals(request.getParameter("value"))) {
			id = Integer.parseInt(request.getParameter("id"));
			double value = Double.parseDouble(request.getParameter("value"));
			flag = scoDao.modifyScore(value, id);
		} else if (!"0".equals(request.getParameter("id")) && "".equals(request.getParameter("value"))) {
			id = Integer.parseInt(request.getParameter("id"));
			flag = scoDao.removeScoreById(id);
			id = 0;
		} else {
			flag = true;
		}
		PrintWriter out = response.getWriter();
		out.print(flag + "," + id);
	}

	private void depPro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!"".equals(request.getParameter("depId"))) {
			int depId = Integer.parseInt(request.getParameter("depId"));
			List<Project> proList = depDao.searchProjectById(depId);
			JSONArray proJson = JSONArray.fromObject(proList);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(proJson);
		}else {
			List<Project> proList = proDao.searchAll();
			JSONArray proJson = JSONArray.fromObject(proList);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(proJson);
		}
	}

}
