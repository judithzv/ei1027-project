package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model. Request;

public class RequestRowMapper implements RowMapper< Request>{

	@Override
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
		Request request= new Request();
		request.setNumber(rs.getString("number"));
		request.setState(rs.getString("state"));
		request.setService(rs.getString("service"));
		request.setSchedule(rs.getString("schedule"));
		request.setDNI(rs.getString("DNI"));
		request.setIdContract(rs.getString("idContract"));
		return request;
	}
	

}
