package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectDao;
import entity.Project;
import net.sf.json.JSONArray;
import util.Constant;
import util.PageModel;

/**
 * Servlet implementation class ProjectServlet
 */
@WebServlet("/project")
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProjectDao proDao = new ProjectDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectServlet() {
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
		} else if ("showAdd".equals(type)) {
			showAdd(request, response);
		} else if ("addPro".equals(type)) {
			addPro(request, response);
		} else if ("showUpdate".equals(type)) {
			showUpdate(request, response);
		} else if ("updatePro".equals(type)) {
			updatePro(request, response);
		} else if ("delete".equals(type)) {
			deletePro(request, response);
		} else if ("updateBatch".equals(type)) {
			updateBatch(request, response);
		} else if ("search".equals(type)) {
			search(request, response);
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
		request.setAttribute("name", name);
		Project condition = new Project();
		condition.setName(name);
		int currPage = 1;
		int pages = 0;
		if (request.getParameter("page") != null) {
			currPage = Integer.parseInt(request.getParameter("page"));
		}
		if (currPage < 1) {
			currPage = 1;
		}
		int count = proDao.findCount(condition);
		if (count % Constant.DEP_PAGE_SIZE == 0) {
			pages = count / Constant.DEP_PAGE_SIZE;
		} else {
			pages = count / Constant.DEP_PAGE_SIZE + 1;
		}
		List<Project> proList = proDao.searchByCondition(condition, currPage);
		PageModel<Project> pageModel = new PageModel();
		pageModel.setPageNumber(Constant.DEP_PAGE_NUMBER);
		pageModel.setCurrPage(currPage);
		pageModel.setPages(pages);
		pageModel.setCount(count);
		pageModel.setList(proList);
		request.setAttribute("pageModel", pageModel);
		request.getRequestDispatcher("WEB-INF/project/project.jsp").forward(request, response);
	}

	public void showAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/project/addProject.jsp").forward(request, response);
	}

	public void addPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Project pro = new Project();
		String name = request.getParameter("name");
		pro.setName(name);
		proDao.addProject(pro);
		int page = 0;
		int count = proDao.findCount();
		if (count % Constant.DEP_PAGE_SIZE == 0) {
			page = count / Constant.DEP_PAGE_SIZE;
		} else {
			page = count / Constant.DEP_PAGE_SIZE + 1;
		}
		response.sendRedirect("project?type=search&page=" + page);
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("ids");
		List<Project> proList = proDao.searchByIds(ids);
		request.setAttribute("proList", proList);
		request.getRequestDispatcher("WEB-INF/project/updateProject.jsp").forward(request, response);
	}

	public void updatePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String pros = request.getParameter("pros");
		String[] str = pros.split(";");
		for (int i = 0; i < str.length; i++) {
			String[] s = str[i].split(",");
			int id = Integer.valueOf(s[0]);
			String name = s[1];
			Project pro = new Project();
			pro.setName(name);
			proDao.modifyProject(pro, id);
		}
		String page = request.getParameter("page");
		response.sendRedirect("project?type=search&name="+na+"&page=" + page);
	}

	public void deletePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String ids = request.getParameter("ids");
		proDao.removeProject(ids);
		String page = request.getParameter("page");
		response.sendRedirect("project?type=search&name="+na+"&page=" + page);
	}

	public void updateBatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String proJson = request.getParameter("proJson");
		JSONArray jsonArray = JSONArray.fromObject(proJson);
		List<Project> proList = (List<Project>) JSONArray.toCollection(jsonArray, Project.class);
		for (Project pro : proList) {
			int id = pro.getId();
			proDao.modifyProject(pro, id);
		}
		String page = request.getParameter("page");
		response.sendRedirect("project?type=search&name="+na+"&page=" + page);
	}

}
