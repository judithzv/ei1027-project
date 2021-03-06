package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Request;

@Repository 
public class RequestDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addRequest(Request request) {		
			jdbcTemplate.update(
			  "INSERT INTO Request VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
			    request.getNumber(), request.getState(), request.getService(), 
			    request.getStartDate(), request.getEndDate(), request.getDetails(), request.getDNI(), request.getIdContract());
		
	   }
	   
	   public void deleteRequest(String DNI, String number) {		
			jdbcTemplate.update("DELETE FROM Request WHERE DNI = ?, number=? ;",
					DNI, number);
			
	   }

	   public void updateRequest(Request request) {
		  
			jdbcTemplate.update("UPDATE Request SET DNI = ?, state= ?, service=?, "
					+ " startDate=?, endDate=?, details=?, idContract=?"
					+ " WHERE number = ?",
					request.getDNI(), request.getState(), request.getService(), request.getStartDate(),
				    request.getEndDate(), request.getDetails(), request.getIdContract(), request.getNumber());
			
	   }

	   public Request getRequest(String number) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE number = ?",
	        	        new RequestRowMapper(),
	        	        number);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Request> getRequests(String DNI) {
	       try {
	    
	    	   List<Request> requests = jdbcTemplate.query(
	        		    "SELECT * FROM Request WHERE DNI=?",
	        		     new RequestRowMapper(), DNI);
	        		return requests;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Request>();
	       }
	   }
	   
	   public List<Request> getPendingRequests() {
	       try {
	    
	    	   List<Request> requests = jdbcTemplate.query(
	        		    "SELECT * FROM Request WHERE state='in process'",
	        		     new RequestRowMapper());
	        		return requests;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Request>();
	       }
	   }
	   
	   public String getLastNumber() {
		   List<Request> requests = jdbcTemplate.query("SELECT * FROM Request WHERE number=(SELECT MAX(number) FROM Request)" , new RequestRowMapper());
		   if(!requests.isEmpty())
			   return requests.get(0).getNumber();
		   return null;
	   }
	   
	   public List<Contract> getPossibleContracts(String serviceType){
	       try {
	   	    
	    	   List<Contract> contracts = jdbcTemplate.query(
	        		    "SELECT * FROM Contract INNER JOIN Company ON Contract.CIF = Company.CIF WHERE id NOT IN (SELECT idContract FROM Request WHERE idContract IS NOT NULL) AND serviceType=?",
	        		     new ContractRowMapper(), serviceType);
	        		return contracts;

	       }
	       catch(EmptyResultDataAccessException e) {
	    	   return new ArrayList<Contract>();
	       }
	   }

}
