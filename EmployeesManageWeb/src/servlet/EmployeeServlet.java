package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.DeptDao;
import dao.EmployeeDao;
import entity.Dept;
import entity.Employee;
import net.sf.json.JSONArray;
import util.Constant;
import util.PageModel;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeDao empDao = new EmployeeDao();
	private DeptDao deptDao = new DeptDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
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
			show(request, response);
		} else if ("show".equals(type)) {
			show(request, response);
		} else if ("showAdd".equals(type)) {
			showAdd(request, response);
		} else if ("showAdd2".equals(type)) {
			showAdd2(request, response);
		} else if ("showAdd3".equals(type)) {
			showAdd3(request, response);
		} else if ("addEmp".equals(type)) {
			addEmp(request, response);
		} else if ("addEmp3".equals(type)) {
			addEmp3(request, response);
		} else if ("showUpdate".equals(type)) {
			showUpdate(request, response);
		} else if ("updateEmp".equals(type)) {
			updateEmp(request, response);
		} else if ("delete".equals(type)) {
			deleteEmp(request, response);
		} else if ("delete2".equals(type)) {
			delete2(request, response);
		} else if ("updateBatch".equals(type)) {
			updateBatch(request, response);
		} else if ("search".equals(type)) {
			search(request, response);
		} else if ("upload".equals(type)) {
			upload(request, response);
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
		String sex = request.getParameter("sex");
		int age = -1;
		int deptId = 0;
		if (!"".equals(request.getParameter("age")) && request.getParameter("age") != null) {
			age = Integer.valueOf(request.getParameter("age"));
		}
		if (!"".equals(request.getParameter("dept")) && request.getParameter("dept") != null) {
			deptId = Integer.valueOf(request.getParameter("dept"));
		}
		request.setAttribute("name", name);
		request.setAttribute("sex", sex);
		request.setAttribute("age", age);
		request.setAttribute("deptId", deptId);
		Employee condition = new Employee();
		condition.setName(name);
		condition.setSex(sex);
		condition.setAge(age);
		Dept temp = new Dept();
		temp.setId(deptId);
		condition.setDept(temp);
		int currPage = 1;
		int pages = 0;
		if (request.getParameter("page") != null) {
			currPage = Integer.parseInt(request.getParameter("page"));
		}
		if (currPage < 1) {
			currPage = 1;
		}
		int count = empDao.findCount(condition);
		if (count % Constant.EMP_PAGE_SIZE == 0) {
			pages = count / Constant.EMP_PAGE_SIZE;
		} else {
			pages = count / Constant.EMP_PAGE_SIZE + 1;
		}
		List<Employee> empList = empDao.searchByCondition(condition, currPage);
		PageModel<Employee> pageModel = new PageModel();
		pageModel.setPageNumber(Constant.EMP_PAGE_NUMBER);
		pageModel.setCurrPage(currPage);
		pageModel.setPages(pages);
		pageModel.setCount(count);
		pageModel.setList(empList);
		request.setAttribute("pageModel", pageModel);
		List<Dept> deptList = deptDao.searchAll();
		request.setAttribute("deptList", deptList);
		StringBuilder deptSb = new StringBuilder();
		for (Dept dept : deptList) {
			deptSb.append(dept.getId() + "," + dept.getName() + ";");
		}
		String deptStr = deptSb.toString();
		deptStr = deptStr.substring(0, deptStr.length() - 1);
		request.setAttribute("deptStr", deptStr);
		request.getRequestDispatcher("WEB-INF/employee/employee.jsp").forward(request, response);
	}

	public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currPage = 1;
		int pages = 0;
		if (request.getParameter("page") != null) {
			currPage = Integer.parseInt(request.getParameter("page"));
		}
		if (currPage < 1) {
			currPage = 1;
		}
		int count = empDao.findCount();
		if (count % Constant.EMP_PAGE_SIZE == 0) {
			pages = count / Constant.EMP_PAGE_SIZE;
		} else {
			pages = count / Constant.EMP_PAGE_SIZE + 1;
		}
		if (currPage > pages) {
			currPage = pages;
		}
		List<Employee> empList = empDao.searchByPage(currPage);
		PageModel<Employee> pageModel = new PageModel();
		pageModel.setPageNumber(Constant.EMP_PAGE_NUMBER);
		pageModel.setCurrPage(currPage);
		pageModel.setPages(pages);
		pageModel.setCount(count);
		pageModel.setList(empList);
		request.setAttribute("pageModel", pageModel);
		List<Dept> deptList = deptDao.searchAll();
		request.setAttribute("deptList", deptList);
		StringBuilder deptSb = new StringBuilder();
		for (Dept dept : deptList) {
			deptSb.append(dept.getId() + "," + dept.getName() + ";");
		}
		String deptStr = deptSb.toString();
		deptStr = deptStr.substring(0, deptStr.length() - 1);
		request.setAttribute("deptStr", deptStr);
		request.getRequestDispatcher("WEB-INF/employee/employee.jsp").forward(request, response);
	}

	public void showAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Dept> deptList = deptDao.searchAll();
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("WEB-INF/employee/addEmployee.jsp").forward(request, response);
	}

	public void showAdd2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Dept> deptList = deptDao.searchAll();
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("WEB-INF/employee/addEmployee2.jsp").forward(request, response);
	}

	public void showAdd3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Dept> deptList = deptDao.searchAll();
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("WEB-INF/employee/addEmployee3.jsp").forward(request, response);
	}

	public void addEmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Employee emp = new Employee();
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		int age = 0;
		int deptId = 0;
		try {
			age = Integer.valueOf(request.getParameter("age"));
			deptId = Integer.valueOf(request.getParameter("dept"));
			emp.setName(name);
			if ("male".equals(sex)) {
				emp.setSex("男");
			} else {
				emp.setSex("女");
			}
			emp.setAge(age);
			if (deptId > 0) {
				Dept dept = deptDao.searchById(deptId);
				emp.setDept(dept);
			}
			empDao.addEmployee(emp);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int page = 0;
		int count = empDao.findCount();
		if (count % Constant.EMP_PAGE_SIZE == 0) {
			page = count / Constant.EMP_PAGE_SIZE;
		} else {
			page = count / Constant.EMP_PAGE_SIZE + 1;
		}
		response.sendRedirect("employee?type=search&page=" + page);
	}

	public void addEmp3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Employee emp = new Employee();
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String avatarName = request.getParameter("avatarName");
		int age = 0;
		int deptId = 0;
		try {
			age = Integer.valueOf(request.getParameter("age"));
			deptId = Integer.valueOf(request.getParameter("dept"));
			emp.setName(name);
			if ("male".equals(sex)) {
				emp.setSex("男");
			} else {
				emp.setSex("女");
			}
			emp.setAge(age);
			if (deptId > 0) {
				Dept dept = deptDao.searchById(deptId);
				emp.setDept(dept);
			}
			emp.setAvatar("pic/"+avatarName);
			empDao.addEmployee(emp);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int page = 0;
		int count = empDao.findCount();
		if (count % Constant.EMP_PAGE_SIZE == 0) {
			page = count / Constant.EMP_PAGE_SIZE;
		} else {
			page = count / Constant.EMP_PAGE_SIZE + 1;
		}
		response.sendRedirect("employee?type=search&page=" + page);
	}
	
	public void showUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("ids");
		List<Employee> empList = empDao.searchByIds(ids);
		List<Dept> deptList = deptDao.searchAll();
		request.setAttribute("deptList", deptList);
		request.setAttribute("empList", empList);
		request.getRequestDispatcher("WEB-INF/employee/updateEmployee.jsp").forward(request, response);
	}

	public void updateEmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String se = request.getParameter("sex");
		String ag = request.getParameter("age");
		String de = request.getParameter("dept");
		String emps = request.getParameter("emps");
		String[] str = emps.split(";");
		for (int i = 0; i < str.length; i++) {
			String[] s = str[i].split(",");
			int id = Integer.valueOf(s[0]);
			Employee emp = new Employee();
			String name = s[1];
			String sex = s[2];
			int age = 0;
			int deptId = 0;
			try {
				age = Integer.valueOf(s[3]);
				deptId = Integer.valueOf(s[4]);
				emp.setName(name);
				if ("male".equals(sex)) {
					emp.setSex("男");
				} else {
					emp.setSex("女");
				}
				emp.setAge(age);
				if (deptId > 0) {
					Dept dept = deptDao.searchById(deptId);
					emp.setDept(dept);
				}
				empDao.modifyEmployee(emp, id);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String page = request.getParameter("page");
		na = URLEncoder.encode(na, "utf-8");
		response.sendRedirect("employee?type=search&name=" + na + "&sex=" + se + "&age=" + ag + "&dept=" + de + "&page=" + page);
	}

	public void deleteEmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String se = request.getParameter("sex");
		String ag = request.getParameter("age");
		String de = request.getParameter("dept");
		String ids = request.getParameter("ids");
		empDao.removeEmployee(ids);
		String page = request.getParameter("page");
		na = URLEncoder.encode(na, "utf-8");
		response.sendRedirect("employee?type=search&name=" + na + "&sex=" + se + "&age=" + ag + "&dept=" + de + "&page=" + page);
	}

	public void updateBatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String na = request.getParameter("name");
		String se = request.getParameter("sex");
		String ag = request.getParameter("age");
		String de = request.getParameter("dept");
		String empJson = request.getParameter("empJson");
		JSONArray jsonArray = JSONArray.fromObject(empJson);
		List<Employee> empList = (List<Employee>) JSONArray.toCollection(jsonArray, Employee.class);
		for (Employee emp : empList) {
			int id = emp.getId();
			String sex = emp.getSex();
			if ("male".equals(sex)) {
				emp.setSex("男");
			} else {
				emp.setSex("女");
			}
			empDao.modifyEmployee(emp, id);
		}
		String page = request.getParameter("page");
		na = URLEncoder.encode(na, "utf-8");
		response.sendRedirect("employee?type=search&name=" + na + "&sex=" + se + "&age=" + ag + "&dept=" + de + "&page=" + page);
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = "e:/avatar/";
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String avatarName = null;
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				if (item.getFieldName().equals("avatar")) {
					UUID uuid = UUID.randomUUID();
					String postfix = item.getName().substring(item.getName().lastIndexOf("."));
					avatarName = uuid.toString() + postfix;
					File savedFile = new File(path, avatarName);
					item.write(savedFile);
				}
			}
			PrintWriter out = response.getWriter();
			out.print(avatarName);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void delete2(HttpServletRequest request, HttpServletResponse response) {
		String avatarSrc = request.getParameter("avatarSrc");
		String avatarName = avatarSrc.substring(avatarSrc.lastIndexOf("/")+1);
		String path = "e:/avatar/" + avatarName;
		File file = new File(path);
		file.delete();
	}
}
