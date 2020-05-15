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

@Repository 
public class CompanyDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addCompany(Company company) {		
			jdbcTemplate.update( "INSERT INTO company VALUES(?, ?, ?, ?, ?, ?)",
			    company.getName(), company.getCIF(), company.getContactPerson(), company.getTelephoneNumber(), 
			    company.getUserName(), company.getPassword());
		
	   }
	   
	   public void deleteCompany(String CIF) {		
			jdbcTemplate.update("DELETE FROM company WHERE CIF = ?;",
					CIF);
			
	   }

	   public void updateCompany(Company company) {
		  
			jdbcTemplate.update("UPDATE company SET name=?, contactPerson=?, telephoneNumber=?, userName=?"
					+ ", password=?" + " WHERE CIF =?;",
					company.getName(), company.getContactPerson(), company.getTelephoneNumber(), 
				    company.getUserName(), company.getPassword(), company.getCIF());
			
	   }

	   public Company getCompany(String CIF) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM company WHERE CIF = ?;",
	        	        new CompanyRowMapper(),
	        	        CIF);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Company> getCompanies() {
	       try {
	    
	    	   List<Company> companies = jdbcTemplate.query(
	        		    "SELECT * FROM Company",
	        		     new CompanyRowMapper());
	        		return companies;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Company>();
	       }
	   }
	   
	   public List<Contract> getContracts(String CIF) {
	       try {
	    
	    	   List<Contract> contracts= jdbcTemplate.query(
	        		    "SELECT * FROM Contract WHERE CIF=?",
	        		     new ContractRowMapper(), CIF);
	        		return contracts;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Contract>();
	       }
	   }

}
