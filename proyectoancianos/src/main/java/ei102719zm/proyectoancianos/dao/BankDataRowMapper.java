package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.BankData;

public class BankDataRowMapper implements RowMapper<BankData>{

	@Override
	public BankData mapRow(ResultSet rs, int rowNum) throws SQLException {
		BankData bankData= new BankData();
		bankData.setIBAN(rs.getString("IBAN"));
		bankData.setBank(rs.getString("bank"));
		bankData.setBranchOffice(rs.getString("branch_office"));
		return bankData;
	}

}
