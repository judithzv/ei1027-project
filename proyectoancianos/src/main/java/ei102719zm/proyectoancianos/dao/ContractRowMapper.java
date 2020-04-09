package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Contract;

public class ContractRowMapper implements RowMapper<Contract>{

	@Override
	public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contract contract= new Contract();
		contract.setId(rs.getString("id"));
		contract.setStartDate(rs.getDate("startDate"));
		contract.setEndDate(rs.getDate("endDate"));
		contract.setServiceType(rs.getString("serviceType"));
		contract.setPrice(rs.getInt("price"));
		contract.setSignatureDate(rs.getDate("signatureDate"));
		contract.setCIF(rs.getString("CIF"));

		return contract;
	}

}
