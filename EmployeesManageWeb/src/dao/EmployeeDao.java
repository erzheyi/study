package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import entity.Dept;
import entity.Employee;
import util.ConnectMysql;
import util.Constant;

public class EmployeeDao {

	public int findCount() {
		int count = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select count(*) from employee as e left join dept as d on e.d_id = d.id";
			rs = st.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public int findCount(Employee condition) {
		int count = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "select count(*) from employee as e left join dept as d on e.d_id = d.id where 1 = 1";
			if (!"".equals(condition.getName()) && condition.getName() != null) {
				sql += " and e.name = '" + condition.getName() + "'";
			}
			if (!"".equals(condition.getSex()) && condition.getSex() != null) {
				if ("male".equals(condition.getSex())) {
					sql += " and e.sex = 'ÄÐ'";
				} else {
					sql += " and e.sex = 'Å®'";
				}
			}
			if (condition.getAge() != -1) {
				sql += " and e.age = " + condition.getAge();
			}
			if (condition.getDept().getId() != 0) {
				sql += " and e.d_id = " + condition.getDept().getId();
			}
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public List<Employee> searchAll() {
		List<Employee> employees = new ArrayList<Employee>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select e.*,d.id as dId,d.name as dName,emp_count from employee as e left join dept as d on e.d_id = d.id";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Dept dept = new Dept();
				dept.setId(rs.getInt("dId"));
				dept.setName(rs.getString("dName"));
				dept.setEmpNum(rs.getInt("emp_count"));
				emp.setDept(dept);
				emp.setAvatar(rs.getString("avatar"));
				employees.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employees;
	}

	public List<Employee> searchByPage(int page) {
		List<Employee> employees = new ArrayList<Employee>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "select e.*,d.id as dId,d.name as dName,emp_count from employee as e left join dept as d on e.d_id = d.id limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (page - 1) * Constant.EMP_PAGE_SIZE);
			ps.setInt(2, Constant.EMP_PAGE_SIZE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Dept dept = new Dept();
				dept.setId(rs.getInt("dId"));
				dept.setName(rs.getString("dName"));
				dept.setEmpNum(rs.getInt("emp_count"));
				emp.setDept(dept);
				emp.setAvatar(rs.getString("avatar"));
				employees.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employees;
	}

	public Employee searchById(int id) {
		Employee emp = new Employee();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select e.*,d.id as dId,d.name as dName,emp_count from employee as e left join dept as d on e.d_id = d.id having id = " + id;
			rs = st.executeQuery(sql);
			while (rs.next()) {
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Dept dept = new Dept();
				dept.setId(rs.getInt("dId"));
				dept.setName(rs.getString("dName"));
				dept.setEmpNum(rs.getInt("emp_count"));
				emp.setDept(dept);
				emp.setAvatar(rs.getString("avatar"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return emp;
	}

	public List<Employee> searchByIds(String ids) {
		List<Employee> employees = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select e.*,d.id as dId,d.name as dName,emp_count from employee as e left join dept as d on e.d_id = d.id where e.id in (" + ids + ")";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Dept dept = new Dept();
				dept.setId(rs.getInt("dId"));
				dept.setName(rs.getString("dName"));
				dept.setEmpNum(rs.getInt("emp_count"));
				emp.setDept(dept);
				emp.setAvatar(rs.getString("avatar"));
				employees.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employees;
	}

	public List<Employee> searchByCondition(Employee condition, int page) {
		List<Employee> empList = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = null;
			sql = "select e.*,d.id as dId,d.name as dName,emp_count from employee as e left join dept as d on e.d_id = d.id having 1 = 1";
			if (!"".equals(condition.getName()) && condition.getName() != null) {
				sql += " and name = '" + condition.getName() + "'";
			}
			if (!"".equals(condition.getSex()) && condition.getSex() != null) {
				if ("male".equals(condition.getSex())) {
					sql += " and sex = 'ÄÐ'";
				} else {
					sql += " and sex = 'Å®'";
				}
			}
			if (condition.getAge() != -1) {
				sql += " and age = " + condition.getAge();
			}
			if (condition.getDept().getId() != 0) {
				sql += " and d_id = " + condition.getDept().getId();
			}
			int temp = (page - 1) * Constant.EMP_PAGE_SIZE;
			sql += " limit " + temp + " , " + Constant.EMP_PAGE_SIZE;
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Dept dept = new Dept();
				dept.setId(rs.getInt("dId"));
				dept.setName(rs.getString("dName"));
				dept.setEmpNum(rs.getInt("emp_count"));
				emp.setDept(dept);
				emp.setAvatar(rs.getString("avatar"));
				empList.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empList;
	}

	public boolean addEmployee(Employee emp) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "insert into employee(name,sex,age,d_id,avatar) values(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getName());
			ps.setString(2, emp.getSex());
			ps.setInt(3, emp.getAge());
			if (emp.getDept() != null) {
				ps.setInt(4, emp.getDept().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.setString(5, emp.getAvatar());
			int temp = ps.executeUpdate();
			if (temp > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean modifyEmployee(Employee emp, int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id =" + id;
			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getName());
			ps.setString(2, emp.getSex());
			ps.setInt(3, emp.getAge());
			if (emp.getDept() != null && emp.getDept().getId() != 0) {
				ps.setInt(4, emp.getDept().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			int temp = ps.executeUpdate();
			if (temp > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean removeEmployee(int id) {
		boolean flag = false;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			conn.setAutoCommit(false);
			String sql = "delete from employee where id = " + id;
			st = conn.createStatement();
			st.executeUpdate(sql);
			sql = "delete from score where e_id = " + id;
			st.executeUpdate(sql);
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean removeEmployee(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			conn.setAutoCommit(false);
			String sql = "delete from employee where id in (" + ids + ")";
			st = conn.createStatement();
			st.executeUpdate(sql);
			sql = "delete from score where e_id in (" + ids + ")";
			st.executeUpdate(sql);
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

}
