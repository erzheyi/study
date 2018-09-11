package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Project;
import util.ConnectMysql;
import util.Constant;

public class ProjectDao {

	public List<Project> searchAll() {
		List<Project> projects = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select * from project";
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

	public Project searchById(int id) {
		Project project = new Project();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select * from project where id = " + id;
			rs = st.executeQuery(sql);
			while (rs.next()) {
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
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
		return project;
	}

	public boolean addProject(Project project) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "insert into project(name) values(?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, project.getName());
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

	public boolean modifyProject(Project project, int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "update project set name=? where id =" + id;
			ps = conn.prepareStatement(sql);
			ps.setString(1, project.getName());
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

	public boolean removeProject(int id) {
		boolean flag = false;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			String sql = "delete from project where id = " + id;
			st.executeUpdate(sql);
			sql = "delete from r_dep_pro where p_id = " + id;
			st.executeUpdate(sql);
			sql = "delete from score where p_id = " + id;
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

	public int findCount() {
		int count = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select count(*) from project";
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
	
	public int findCount(Project condition) {
		int count = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select count(*) from project";
			if(!"".equals(condition.getName())&&condition.getName()!=null) {
				sql += " where name = '" + condition.getName() + "'";
			}
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

	public List<Project> searchByCondition(Project condition, int currPage) {
		List<Project> proList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "select * from project where 1 = 1";
			if(!"".equals(condition.getName())&&condition.getName()!=null) {
				sql += " and name = '" + condition.getName() + "'";
			}
			sql += " limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (currPage-1)*Constant.PRO_PAGE_SIZE);
			ps.setInt(2, Constant.PRO_PAGE_SIZE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				proList.add(pro);
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
		return proList;
	}

	public List<Project> searchByIds(String ids) {
		List<Project> proList = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select * from project where id in (" + ids + ")";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				proList.add(project);
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
		return proList;
	}

	public void removeProject(String ids) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			String sql = "delete from project where id in (" + ids + ")";
			st.executeUpdate(sql);
			sql = "delete from r_dep_pro where p_id in (" + ids + ")";
			st.executeUpdate(sql);
			sql = "delete from score where p_id in (" + ids + ")";
			st.executeUpdate(sql);
			conn.commit();
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
}
