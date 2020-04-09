package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Availability;

public class AvailabilityRowMapper implements RowMapper<Availability>{

	@Override
	public Availability mapRow(ResultSet rs, int rowNum) throws SQLException {
		Availability availability= new Availability();
		availability.setDate(rs.getDate("date"));
		availability.setStartTime(rs.getDate("startTime"));
		availability.setEndTime(rs.getDate("endTime"));
		availability.setDNI(rs.getString("DNI_elderly"));
		
		return availability;
	}
	

}
