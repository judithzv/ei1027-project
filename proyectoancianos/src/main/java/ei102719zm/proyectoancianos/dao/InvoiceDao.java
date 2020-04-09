package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Invoice;

@Repository 
public class InvoiceDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addInvoice(Invoice invoice) {		
			jdbcTemplate.update(
			  "INSERT INTO invoice VALUES(?, ?, ?, ?, ?)",
			    invoice.getCode(), invoice.getStartDate(), invoice.getEndDate(),
			    invoice.getAmount(), invoice.getDNI());
		
	   }
	   
	   public void deleteInvoice(String code) {		
			jdbcTemplate.update("DELETE FROM invoice WHERE code = ?;",
					code);
			
	   }

	   public void updateCompany(Invoice invoice) {
		  
			jdbcTemplate.update("UPDATE invoice SET startDate = ?, endDate= ?, amount=?, DNI=?"
					+ " WHERE code = ?",
					invoice.getStartDate(), invoice.getEndDate(),
				    invoice.getAmount(), invoice.getDNI(), invoice.getCode());
			
	   }

	   public Invoice getInvoice(String code) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM invoice WHERE code = ?",
	        	        new InvoiceRowMapper(),
	        	        code);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Invoice> getInvoices() {
	       try {
	    
	    	   List<Invoice> invoices = jdbcTemplate.query(
	        		    "SELECT * FROM invoice",
	        		     new InvoiceRowMapper());
	        		return invoices;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Invoice>();
	       }
	   }

}
