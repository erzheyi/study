package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import entity.Dept;
import entity.Project;
import util.ConnectMysql;
import util.Constant;

public class DeptDao {

	public List<Dept> searchAll() {
		List<Dept> depts = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select * from dept";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Dept dept = new Dept();
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setEmpNum(rs.getInt("emp_count"));
				depts.add(dept);
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
		return depts;
	}

	public Dept searchById(int id) {
		Dept dept = new Dept();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select * from dept where id = " + id;
			rs = st.executeQuery(sql);
			while (rs.next()) {
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setEmpNum(rs.getInt("emp_count"));
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
		return dept;
	}

	public List<Dept> searchByIds(String ids) {
		List<Dept> depList = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select * from dept where id in (" + ids + ")";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Dept dept = new Dept();
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setEmpNum(rs.getInt("emp_count"));
				depList.add(dept);
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
		return depList;
	}
	
	public List<Dept> searchByCondition(Dept condition,int page) {
		List<Dept> depList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "select * from dept where 1 = 1";
			if(!"".equals(condition.getName())&&condition.getName()!=null) {
				sql += " and name = '" + condition.getName() + "'";
			}
			if(condition.getEmpNum() != -1) {
				sql += " and emp_count = " + condition.getEmpNum();
			}
			sql += " limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (page-1)*Constant.DEP_PAGE_SIZE);
			ps.setInt(2, Constant.DEP_PAGE_SIZE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Dept dept = new Dept();
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setEmpNum(rs.getInt("emp_count"));
				depList.add(dept);
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
		return depList;
	}

	public List<Project> searchProjectById(int id) {
		List<Project> projects = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select d.id,p.id as pId,p.name from dept as d join r_dep_pro as r on d.id = r.d_id join " + 
					"project as p on r.p_id = p.id where d.id = "+id;
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("pId"));
				project.setName(rs.getString("name"));
				projects.add(project);
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
		return projects;
	}
	
	public List<Project> searchProjectById(int id,int page) {
		List<Project> projects = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "select d.id,p.id as pId,p.name from dept as d join r_dep_pro as r on d.id = r.d_id join " + 
					"project as p on r.p_id = p.id where d.id = "+id;
			sql += " limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (page-1)*Constant.PRO_PAGE_SIZE);
			ps.setInt(2, Constant.PRO_PAGE_SIZE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("pId"));
				project.setName(rs.getString("name"));
				projects.add(project);
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
		return projects;
	}
	
	public List<Project> searchProjectNotId(int id) {
		List<Project> projects = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select * from project as p where p.id not in (select p.id from dept as d join r_dep_pro as r on d.id = r.d_id join project as p on p.id = r.p_id where d.id = "+id+" )";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				projects.add(project);
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
		return projects;
	}
	
	public boolean addDept(Dept dept) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "insert into dept(name,emp_count) values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getName());
			ps.setInt(2, 0);
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

	public boolean addProject(int depId,int proId) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "insert into r_dep_pro(d_id,p_id) values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, depId);
			ps.setInt(2, proId);
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
	
	public boolean modifyDept(Dept dept, int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "update dept set name=? where id =" + id;
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getName());
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

	public boolean removeDept(int id) {
		boolean flag = false;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
//		ScoreDao scoreDao = new ScoreDao();
		try {
			conn = ConnectMysql.Connect();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			String sql = "select id from employee where d_id = "+id;
			rs = st.executeQuery(sql);
			while (rs.next()){
				int empId = rs.getInt("id");
//				scoreDao.removeScoreByEmpId(empId);
			}
			sql = "update employee set d_id = null where d_id = " + id;
			st.executeUpdate(sql);
			sql = "delete from dept where id = " + id;
			st.executeUpdate(sql);
			sql = "delete from r_dep_pro where d_id = " + id;
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
	
	public void removeDept(String ids) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "delete from dept where id in(" + ids + ")";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void removeProject(int dId,int pId) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "delete from r_dep_pro where d_id = " + dId + " and p_id = "+pId;
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean removeProject(int depId,String ids) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int temp = 0;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "delete from r_dep_pro where d_id = " + depId + " and p_id in ("+ids+")";
			temp = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectMysql.closeAll(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return temp>0;
	}

	public int findCount() {
		int count = 0; 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select count(*) from dept";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count=rs.getInt(1);
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
	
	public int findCount(Dept condition) {
		int count = 0; 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select count(*) from dept where 1 = 1";
			if(!"".equals(condition.getName())&&condition.getName()!=null) {
				sql += " and name = '" + condition.getName() +"'";
			}
			if(condition.getEmpNum() != -1) {
				sql += " and emp_count = " + condition.getEmpNum();
			}
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count=rs.getInt(1);
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

	public int findProjectCount(int deptId) {
		int count = 0; 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select count(*) from dept as d left join r_dep_pro as r on d.id = r.d_id left join project as p on p.id = r.p_id where d.id = " + deptId;
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count=rs.getInt(1);
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

}
