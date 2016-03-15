package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.VisitEventBean;

public class VisitEventDAO {

	private DataSource ds;

	public VisitEventDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Map<String, VisitEventBean> retrieve(String visitDay, String bookID) throws SQLException {
		String query = "select V.DAY, V.BID, B.EVENTTYPE from VISITEVENT V where V.DAY = " + visitDay + " and V.BID = " + bookID;
		Map<String, VisitEventBean> rv = new HashMap<String, VisitEventBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String day = r.getString("DAY");
			String bid = r.getString("BID");
			String eventtype = r.getString("EVENTTYPE");
			VisitEventBean ve = new VisitEventBean(day, bid, eventtype);
			rv.put(day, ve);
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}

}
