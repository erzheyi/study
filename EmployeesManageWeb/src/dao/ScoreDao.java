package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entity.Score;
import entity.Dept;
import entity.Employee;
import entity.Project;
import util.ConnectMysql;
import util.Constant;

public class ScoreDao {

	public List<Score> searchAll() {
		List<Score> scores = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select s.id,e.id as empId,p.id as proId,e.name as empName,d.name as depName,p.name as proName,s.value,s.grade from employee as e"
					+ " left join dept as d on d.id = e.d_id left join r_dep_pro as r on d.id = r.d_id left join project as p on p.id = r.p_id"
					+ " left join score as s on e.id = s.e_id and p.id = s.p_id order by e.id , p.id";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Score score = new Score();
				score.setId(rs.getInt("id"));
				Employee employee = new Employee();
				employee.setId(rs.getInt("empId"));
				employee.setName(rs.getString("empName"));
				Dept dept = new Dept();
				dept.setName(rs.getString("depName"));
				employee.setDept(dept);
				score.setEmployee(employee);
				Project project = new Project();
				project.setId(rs.getInt("proId"));
				project.setName(rs.getString("proName"));
				score.setProject(project);
				if (rs.getObject("value") != null) {
					score.setValue(rs.getDouble("value"));
				}
				score.setGrade(rs.getString("grade"));
				scores.add(score);
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
		return scores;
	}

	public List<Score> searchByCondition(Score condition,int page){
		List<Score> scoList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "select s.id,e.id as empId,p.id as proId,e.name as empName,d.name as depName,p.name as proName,s.value,s.grade from employee as e"
					+ " left join dept as d on d.id = e.d_id left join r_dep_pro as r on d.id = r.d_id left join project as p on p.id = r.p_id"
					+ " left join score as s on e.id = s.e_id and p.id = s.p_id where 1 = 1";
			if(!"".equals(condition.getEmployee().getName())&&condition.getEmployee().getName()!=null) {
				sql += " and e.name = '" + condition.getEmployee().getName() + "'";
			}
			if(condition.getEmployee().getDept()!=null) {
				sql += " and d.id = " + condition.getEmployee().getDept().getId();
			}
			if(condition.getProject()!=null) {
				sql += " and p.id = " + condition.getProject().getId();
			}
			if(condition.getValue() != -1) {
				sql += " and s.value = " + condition.getValue();
			}
			if(!"".equals(condition.getGrade())&&condition.getGrade()!=null) {
				sql += " and s.grade = '" + condition.getGrade() + "'";
			}
			sql += " order by e.id,p.id limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (page-1)*Constant.SCO_PAGE_SIZE);
			ps.setInt(2, Constant.SCO_PAGE_SIZE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Score score = new Score();
				score.setId(rs.getInt("id"));
				Employee employee = new Employee();
				employee.setId(rs.getInt("empId"));
				employee.setName(rs.getString("empName"));
				Dept dept = new Dept();
				dept.setName(rs.getString("depName"));
				employee.setDept(dept);
				score.setEmployee(employee);
				Project project = new Project();
				project.setId(rs.getInt("proId"));
				project.setName(rs.getString("proName"));
				score.setProject(project);
				if (rs.getString("grade") != null) {
					score.setValue(rs.getDouble("value"));
				}
				score.setGrade(rs.getString("grade"));
				scoList.add(score);
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
		return scoList;
	}

	public int searchId(int empId,int proId) {
		int id = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			st = conn.createStatement();
			String sql = "select id from score where e_id = "+empId+" and p_id = "+proId;
			rs = st.executeQuery(sql);
			if (rs.next()) {
				id = rs.getInt(1);
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
		return id;
	}
	
	public boolean modifyScore(Double value, int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "update score set value = ? where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, value);
			ps.setInt(2, id);
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

	public boolean addScore(Double value, int empId, int proId) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "insert into score(e_id,p_id,value) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			ps.setInt(2, proId);
			ps.setDouble(3, value);
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

	public boolean removeScoreById(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "delete from score where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
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
	
	public boolean removeScoreByEmpId(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "delete from score where e_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
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

	public int findCount(Score condition) {
		int count = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectMysql.Connect();
			String sql = "select count(*) from employee as e"
					+ " left join dept as d on d.id = e.d_id left join r_dep_pro as r on d.id = r.d_id left join project as p on p.id = r.p_id"
					+ " left join score as s on e.id = s.e_id and p.id = s.p_id where 1 = 1";
			if(!"".equals(condition.getEmployee().getName())&&condition.getEmployee().getName()!=null) {
				sql += " and e.name = '" + condition.getEmployee().getName() + "'";
			}
			if(condition.getEmployee().getDept()!=null) {
				sql += " and d.id = " + condition.getEmployee().getDept().getId();
			}
			if(condition.getProject()!=null) {
				sql += " and p.id = " + condition.getProject().getId();
			}
			if(condition.getValue() != -1) {
				sql += " and s.value = " + condition.getValue();
			}
			if(!"".equals(condition.getGrade())&&condition.getGrade()!=null) {
				sql += " and s.grade = '" + condition.getGrade() + "'";
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

}
