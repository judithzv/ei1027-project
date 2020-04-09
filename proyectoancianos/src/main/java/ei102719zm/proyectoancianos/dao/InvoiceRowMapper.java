package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Invoice;

public class InvoiceRowMapper implements RowMapper<Invoice>{

	@Override
	public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
		Invoice invoice= new Invoice();
		invoice.setCode(rs.getString("code"));
		invoice.setStartDate(rs.getDate("startDate"));
		invoice.setEndDate(rs.getDate("EndDate"));
		invoice.setAmount(rs.getInt("amount"));
		invoice.setDNI(rs.getString("DNI"));
		return invoice;
	}

}
