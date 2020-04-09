package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Company;

public class CompanyRowMapper implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company company= new Company();
		company.setName(rs.getString("name"));
		company.setCIF(rs.getString("CIF"));
		company.setName(rs.getString("contactPerson"));
		company.setTelephoneNumber(rs.getInt("telephoneNumber"));
		company.setUserName(rs.getString("userName"));
		company.setPassword(rs.getString("password"));
		return company;
	}

}
