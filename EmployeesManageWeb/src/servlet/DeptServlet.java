package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DeptDao;
import entity.Dept;
import entity.Project;
import net.sf.json.JSONArray;
import util.Constant;
import util.PageModel;

/**
 * Servlet implementation class DeptServlet
 */
@WebServlet("/dept")
public class DeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DeptDao depDao = new DeptDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeptServlet() {
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
		} else if ("addDep".equals(type)) {
			addDep(request, response);
		} else if ("showUpdate".equals(type)) {
			showUpdate(request, response);
		} else if ("updateDep".equals(type)) {
			updateDep(request, response);
		} else if ("delete".equals(type)) {
			deleteDep(request, response);
		} else if ("updateBatch".equals(type)) {
			updateBatch(request, response);
		} else if ("search".equals(type)) {
			search(request, response);
		} else if ("showProject".equals(type)) {
			showProject(request, response);
		} else if ("showProject2".equals(type)) {
			showProject2(request, response);
		} else if ("showProject3".equals(type)) {
			showProject3(request, response);
		} else if ("showProject4".equals(type)) {
			showProject4(request, response);
		} else if ("addPro".equals(type)) {
			addPro(request, response);
		} else if ("addPro2".equals(type)) {
			addPro2(request, response);
		} else if ("addPro3".equals(type)) {
			addPro3(request, response);
		} else if ("deletePro".equals(type)) {
			deletePro(request, response);
		} else if ("deletePro3".equals(type)) {
			deletePro3(request, response);
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
		int num = -1;
		if (!"".equals(request.getParameter("num")) && request.getParameter("num") != null) {
			num = Integer.valueOf(request.getParameter("num"));
		}
		request.setAttribute("name", name);
		request.setAttribute("num", num);
		Dept condition = new Dept();
		condition.setName(name);
		condition.setEmpNum(num);
		int currPage = 1;
		int pages = 0;
		if (!"".equals(request.getParameter("page")) && request.getParameter("page") != null) {
			currPage = Integer.parseInt(request.getParameter("page"));
		}
		if (currPage < 1) {
			currPage = 1;
		}
		int count = depDao.findCount(condition);
		if (count % Constant.DEP_PAGE_SIZE == 0) {
			pages = count / Constant.DEP_PAGE_SIZE;
		} else {
			pages = count / Constant.DEP_PAGE_SIZE + 1;
		}
		List<Dept> depList = depDao.searchByCondition(condition, currPage);
		PageModel<Dept> pageModel = new PageModel();
		pageModel.setPageNumber(Constant.DEP_PAGE_NUMBER);
		pageModel.setCurrPage(currPage);
		pageModel.setPages(pages);
		pageModel.setCount(count);
		pageModel.setList(depList);
		request.setAttribute("pageModel", pageModel);
		request.getRequestDispatcher("WEB-INF/dept/dept.jsp").forward(request, response);
	}

	public void showAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/dept/addDept.jsp").forward(request, response);
	}

	public void addDep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dept dep = new Dept();
		String name = request.getParameter("name");
		dep.setName(name);
		depDao.addDept(dep);
		int page = 0;
		int count = depDao.findCount();
		if (count % Constant.DEP_PAGE_SIZE == 0) {
			page = count / Constant.DEP_PAGE_SIZE;
		} else {
			page = count / Constant.DEP_PAGE_SIZE + 1;
		}
		response.sendRedirect("dept?type=search&page=" + page);
	}
	
	public void addPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String depName = request.getParameter("depName");
		String depNum = request.getParameter("depNum");
		String depPage = request.getParameter("depPage");
		int depId = Integer.valueOf(request.getParameter("depId"));
		int proId = Integer.valueOf(request.getParameter("proId"));
		depDao.addProject(depId, proId);
		int page = 0;
		int count = depDao.findProjectCount(depId);
		if (count % Constant.PRO_PAGE_SIZE == 0) {
			page = count / Constant.PRO_PAGE_SIZE;
		} else {
			page = count / Constant.PRO_PAGE_SIZE + 1;
		}
		depName = URLEncoder.encode(depName, "utf-8");
		response.sendRedirect("dept?type=showProject&id="+depId+"&depName="+depName+"&depNum="+depNum+"&depPage="+depPage+"&page="+page);
	}
	
	public void addPro2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String depName = request.getParameter("depName");
//		String depNum = request.getParameter("depNum");
//		String depPage = request.getParameter("depPage");
		int depId = Integer.valueOf(request.getParameter("depId"));
		int proId = Integer.valueOf(request.getParameter("proId"));
//		depDao.addProject(depId, proId);
		int page = 0;
		int count = depDao.findProjectCount(depId);
		if (count % Constant.PRO_PAGE_SIZE == 0) {
			page = count / Constant.PRO_PAGE_SIZE;
		} else {
			page = count / Constant.PRO_PAGE_SIZE + 1;
		}
		List<Project> otherProList = depDao.searchProjectNotId(depId);
		List<Project> proList = depDao.searchProjectById(depId, 1);
		JSONArray otherJson = JSONArray.fromObject(otherProList);
		JSONArray proJson = JSONArray.fromObject(proList);
//		depName = URLEncoder.encode(depName, "utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(proJson);
		out.print(otherJson.toString());
//		response.sendRedirect("dept?type=showProject&id="+depId+"&depName="+depName+"&depNum="+depNum+"&depPage="+depPage+"&page="+page);
	}
	
	public void addPro3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int depId = Integer.valueOf(request.getParameter("depId"));
		String proIds = request.getParameter("proIds");
		String[] proId = proIds.split(",");
		for(int i=0;i<proId.length;i++) {
			int id = Integer.valueOf(proId[i]);
			depDao.addProject(depId, id);	
		}	
		PrintWriter out = response.getWriter();
		out.print("true");
	//	response.sendRedirect("dept?type=showProject2&id="+depId);
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("ids");
		List<Dept> depList = depDao.searchByIds(ids);
		request.setAttribute("depList", depList);
		request.getRequestDispatcher("WEB-INF/dept/updateDept.jsp").forward(request, response);
	}

	public void updateDep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String nu = request.getParameter("num");
		String deps = request.getParameter("deps");
		String[] str = deps.split(";");
		for (int i = 0; i < str.length; i++) {
			String[] s = str[i].split(",");
			int id = Integer.valueOf(s[0]);
			String name = s[1];
			Dept dep = new Dept();
			dep.setName(name);
			depDao.modifyDept(dep, id);
		}
		String page = request.getParameter("page");
		na = URLEncoder.encode(na, "utf-8");
		response.sendRedirect("dept?type=search&name=" + na + "&num=" + nu + "&page=" + page);
	}

	public void deleteDep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String nu = request.getParameter("num");
		String ids = request.getParameter("ids");
		depDao.removeDept(ids);
		String page = request.getParameter("page");
		na = URLEncoder.encode(na, "utf-8");
		response.sendRedirect("dept?type=search&name=" + na + "&num=" + nu + "&page=" + page);
	}
	
	public void deletePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String depName = request.getParameter("depName");
		String depNum = request.getParameter("depNum");
		String depPage = request.getParameter("depPage");
		String page = request.getParameter("page");
		int depId = Integer.valueOf(request.getParameter("depId"));
		String ids = request.getParameter("ids");
		depDao.removeProject(depId, ids);
		depName = URLEncoder.encode(depName, "utf-8");
		response.sendRedirect("dept?type=showProject&id="+depId+"&depName="+depName+"&depNum="+depNum+"&depPage="+depPage+"&page="+page);
	}
	
	public void deletePro3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int depId = Integer.valueOf(request.getParameter("depId"));
		String ids = request.getParameter("proIds");
		boolean flag = depDao.removeProject(depId, ids);
		PrintWriter out = response.getWriter();
		out.print(flag);
		//response.sendRedirect("dept?type=showProject2&id="+depId);
	}

	public void updateBatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String  na= request.getParameter("name");
		String nu = request.getParameter("num");
		String depJson = request.getParameter("depJson");
		JSONArray jsonArray = JSONArray.fromObject(depJson);
		List<Dept> depList = (List<Dept>) JSONArray.toCollection(jsonArray, Dept.class);
		for (Dept dep : depList) {
			int id = dep.getId();
			depDao.modifyDept(dep, id);
		}
		String page = request.getParameter("page");
		na = URLEncoder.encode(na, "utf-8");
		response.sendRedirect("dept?type=search&name=" + na + "&num=" + nu + "&page=" + page);
	}

	private void showProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int deptId = Integer.valueOf(request.getParameter("id"));
		Dept dept = depDao.searchById(deptId);
		request.setAttribute("dept", dept);
		List<Project> otherProList = depDao.searchProjectNotId(deptId);
		request.setAttribute("otherProList", otherProList);
		int currPage = 1;
		int pages = 0;
		if (request.getParameter("page") != null) {
			currPage = Integer.parseInt(request.getParameter("page"));
		}
		if (currPage < 1) {
			currPage = 1;
		}
		List<Project> proList = depDao.searchProjectById(deptId, currPage);
		int count = depDao.findProjectCount(deptId);
		if (count % Constant.PRO_PAGE_SIZE == 0) {
			pages = count / Constant.PRO_PAGE_SIZE;
		} else {
			pages = count / Constant.PRO_PAGE_SIZE + 1;
		}
		PageModel<Project> pageModel = new PageModel();
		pageModel.setPageNumber(Constant.PRO_PAGE_NUMBER);
		pageModel.setCurrPage(currPage);
		pageModel.setPages(pages);
		pageModel.setCount(count);
		pageModel.setList(proList);
		request.setAttribute("pageModel", pageModel);
		request.getRequestDispatcher("WEB-INF/dept/showProject.jsp").forward(request, response);
	}
	
	private void showProject2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int deptId = Integer.valueOf(request.getParameter("id"));
		Dept dept = depDao.searchById(deptId);
		request.setAttribute("dept", dept);
		List<Project> otherProList = depDao.searchProjectNotId(deptId);
		request.setAttribute("otherProList", otherProList);
		List<Project> proList = depDao.searchProjectById(deptId);
		request.setAttribute("proList", proList);
		request.getRequestDispatcher("WEB-INF/dept/showProject2.jsp").forward(request, response);
	}
	
	private void showProject3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int deptId = Integer.valueOf(request.getParameter("id"));
		Dept dept = depDao.searchById(deptId);
		request.setAttribute("dept", dept);
		List<Project> otherProList = depDao.searchProjectNotId(deptId);
		request.setAttribute("otherProList", otherProList);
		List<Project> proList = depDao.searchProjectById(deptId);
		request.setAttribute("proList", proList);
		request.getRequestDispatcher("WEB-INF/dept/showProject3.jsp").forward(request, response);
	}
	
	private void showProject4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int deptId = Integer.valueOf(request.getParameter("id"));
		Dept dept = depDao.searchById(deptId);
		request.setAttribute("dept", dept);
		List<Project> otherProList = depDao.searchProjectNotId(deptId);
		request.setAttribute("otherProList", otherProList);
		List<Project> proList = depDao.searchProjectById(deptId);
		request.setAttribute("proList", proList);
		request.getRequestDispatcher("WEB-INF/dept/showProject4.jsp").forward(request, response);
	}

}
