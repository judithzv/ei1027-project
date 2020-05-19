package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Company;
import ei102719zm.proyectoancianos.model.Contract;

public class ContractRowMapper implements RowMapper<Contract>{

	@Override
	public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contract contract= new Contract();
		Company company = new Company();
		contract.setId(rs.getString("id"));
		contract.setStartDate(rs.getObject("startDate", LocalDate.class));
		contract.setEndDate(rs.getObject("endDate", LocalDate.class));
		contract.setServiceType(rs.getString("serviceType"));
		contract.setPrice(rs.getInt("price"));
		contract.setSignatureDate(rs.getObject("signatureDate", LocalDate.class));
		contract.setCIF(rs.getString("CIF"));
		company.setName(rs.getString("name"));
		company.setCIF(rs.getString("CIF"));
		company.setContactPerson(rs.getString("contactPerson"));
		company.setTelephoneNumber(rs.getInt("telephoneNumber"));
		company.setUserName(rs.getString("userName"));
		company.setPassword(rs.getString("password"));
		contract.setCompany(company);
		return contract;
	}

}
