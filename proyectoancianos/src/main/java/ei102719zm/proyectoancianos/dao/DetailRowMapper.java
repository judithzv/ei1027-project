package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Detail;

public class DetailRowMapper implements RowMapper<Detail>{

	@Override
	public Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
		Detail detail= new Detail();
		detail.setConcept(rs.getString("concept"));
		detail.setAmount(rs.getFloat("amount"));
		detail.setCode(rs.getString("code"));
		return detail;
	}

}
