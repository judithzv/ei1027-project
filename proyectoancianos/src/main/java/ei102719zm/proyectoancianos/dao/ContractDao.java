package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Company;
import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Request;

@Repository 
public class ContractDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addContract(Contract contract) {		
			jdbcTemplate.update(
			     "INSERT INTO contract VALUES(?, ?, ?, ?, ?, ?, ?)",
			      contract.getId(),contract.getStartDate(), contract.getEndDate(),
			      contract.getServiceType(), contract.getPrice(), contract.getSignatureDate(),
			      contract.getCIF());
		
	   }

	   
	   public void deleteContract(String id) {		
			jdbcTemplate.update("DELETE FROM contract WHERE id=?;",
					id);
			
	   }

	   public void updateContract(Contract contract) {
		  
			jdbcTemplate.update("UPDATE Contract SET CIF=?, startDate=?, endDate=?, serviceType=?"
					+ ", price=?, signatureDate=?"
					+ " WHERE id=?",
					contract.getCIF(),contract.getStartDate(), contract.getEndDate(),
				    contract.getServiceType(), contract.getPrice(), contract.getSignatureDate(),
				    contract.getId());
			
	   }

	   public Contract getContract(String id) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM Contract INNER JOIN Company ON Contract.CIF = Company.CIF WHERE id=?",
	        	        new ContractRowMapper(),
	        	        id);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }
	   public List<Contract> getContractsCIF(String CIF) {
	       try {	    
	    	   List<Contract> contracts = jdbcTemplate.query(
	        		    "SELECT * FROM contract INNER JOIN Company ON Contract.CIF = Company.CIF WHERE Contract.CIF=?",
	        		     new ContractRowMapper(), CIF);
	        		return contracts;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Contract>();
	       }
	   }

	   public List<Contract> getContracts() {
	       try {	    
	    	   List<Contract> contracts = jdbcTemplate.query(
	        		    "SELECT * FROM Contract INNER JOIN Company ON Contract.CIF = Company.CIF",
	        		     new ContractRowMapper());
	        		return contracts;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Contract>();
	       }
	   }
	   
	   public String getLastId() {
		   try {
			   Contract contract = jdbcTemplate.queryForObject("SELECT * FROM Contract INNER JOIN Company ON Contract.CIF = Company.CIF WHERE id=(SELECT MAX(id) FROM Contract)" , new ContractRowMapper());
			   return contract.getId();
		   } catch(EmptyResultDataAccessException e) {
			   return null;
		   }
	   }
	   
	   public Company getCompany(String CIF) {
		   try {
			   return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE CIF=?", new CompanyRowMapper(), CIF);
		   } catch(EmptyResultDataAccessException e) {
			   return null;
		   }
		   
	   }
	   
	   public List<Request> getRequests(String id){
		   try {
			   return jdbcTemplate.query("SELECT * FROM Request WHERE idContract=?", new RequestRowMapper(), id);
		   } catch(EmptyResultDataAccessException e) {
			   return new ArrayList<Request>();
		   }
		   
	   }

}
