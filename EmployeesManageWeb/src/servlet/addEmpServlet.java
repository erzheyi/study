package servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import util.Constant;

/**
 * Servlet implementation class addEmpServlet
 */
@WebServlet("/addEmp")
public class addEmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeDao empDao = new EmployeeDao();
	private DeptDao deptDao = new DeptDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addEmpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String path = "e:/avatar/";
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			Employee emp = new Employee();
			String name = null;
			String sex = null;
			String avatar = null;
			int age = 0;
			int deptId = 0;
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				if (item.getFieldName().equals("name")) {
					name = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				} else if (item.getFieldName().equals("sex")) {
					sex = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				} else if (item.getFieldName().equals("age")) {
					age = Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"));
				} else if (item.getFieldName().equals("dept")) {
					deptId = Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"));
				} else if (item.getFieldName().equals("avatar")) {
					UUID uuid = UUID.randomUUID();
					String postfix = item.getName().substring(item.getName().lastIndexOf("."));
					File savedFile = new File(path, uuid.toString() + postfix);
					item.write(savedFile);
					avatar = "pic/" + uuid.toString() + postfix;
				}
			}
			emp.setName(name);
			if ("male".equals(sex)) {
				emp.setSex("ÄÐ");
			} else {
				emp.setSex("Å®");
			}
			emp.setAge(age);
			if (deptId > 0) {
				Dept dept = deptDao.searchById(deptId);
				emp.setDept(dept);
			}
			emp.setAvatar(avatar);
			empDao.addEmployee(emp);
			int page = 0;
			int count = empDao.findCount();
			if (count % Constant.EMP_PAGE_SIZE == 0) {
				page = count / Constant.EMP_PAGE_SIZE;
			} else {
				page = count / Constant.EMP_PAGE_SIZE + 1;
			}
		    response.sendRedirect("employee?type=search&page=" + page);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
