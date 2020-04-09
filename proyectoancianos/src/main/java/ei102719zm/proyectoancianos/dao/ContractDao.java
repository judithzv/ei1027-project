package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Contract;

@Repository 
public class ContractDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addContract(Contract contract) {		
			jdbcTemplate.update(
			     "INSERT INTO contract VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			      contract.getId(),contract.getStartDate(), contract.getEndDate(),
			      contract.getServiceType(), contract.getPrice(), contract.getSignatureDate(),
			      contract.getCIF());
		
	   }

	   
	   public void deleteContract(String id) {		
			jdbcTemplate.update("DELETE FROM contract WHERE id = ?;",
					id);
			
	   }

	   public void updateContract(Contract contract) {
		  
			jdbcTemplate.update("UPDATE Contract SET CIF = ?, startDate= ?, endDate=?, serviceType=?"
					+ ", price=?, signatureDate=?"
					+ " WHERE id = ?",
					contract.getCIF(),contract.getStartDate(), contract.getEndDate(),
				    contract.getServiceType(), contract.getPrice(), contract.getSignatureDate(),
				    contract.getId());
			
	   }

	   public Contract getContract(String id) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM contract WHERE id = ?",
	        	        new ContractRowMapper(),
	        	        id);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Contract> getContracts() {
	       try {	    
	    	   List<Contract> contracts = jdbcTemplate.query(
	        		    "SELECT * FROM Contract",
	        		     new ContractRowMapper());
	        		return contracts;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Contract>();
	       }
	   }
	
	

}
