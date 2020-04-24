package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Address;
import ei102719zm.proyectoancianos.model.BankData;
import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.UserDetails;

@Repository 
public class ElderlyDao {
	
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addElderly(Elderly elderly) {
		
			jdbcTemplate.update(
			     "INSERT INTO elderly VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			     elderly.getName(),elderly.getSurNames(),elderly.getUserName(), elderly.getPassword()
			    , elderly.getTelephoneNumber(), elderly.getMail(), elderly.getBirthDate(), elderly.getHobbies(), elderly.getDNI()
			    , elderly.getIllnesses(), elderly.getDisability(), elderly.getSocialWorker() );
		
	   }

	   
	   public void deleteElderly(String DNI) {	
			jdbcTemplate.update("DELETE FROM elderly WHERE DNI =?;",
					DNI);
			
	   }

	   public void updateElderly(Elderly elderly) {
			jdbcTemplate.update("UPDATE Elderly SET name =?, surNames=?, userName=?, password=?"
					+ ", telephoneNumber=?, mail=?, birthDate=?, illnesses=?, hobbies=?, disability=?, socialWorker=?"
					+ " WHERE DNI =?",
					 elderly.getName(),elderly.getSurNames(),elderly.getUserName(), elderly.getPassword(),
					    elderly.getTelephoneNumber(), elderly.getMail(), elderly.getBirthDate(), elderly.getIllnesses(), 
					    elderly.getHobbies(), elderly.getDisability(), elderly.getSocialWorker(), elderly.getDNI() );
			
	   }

	   public Elderly getElderly(String DNI) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM elderly INNER JOIN address ON address.DNI=elderly.DNI INNER JOIN bankdata ON bankdata.DNI=elderly.DNI WHERE elderly.DNI = ?",
	        	        new ElderlyRowMapper(),
	        	        DNI);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Elderly> getElderlies() {
	       try {
	    
	    	   List<Elderly> elderlies = jdbcTemplate.query(
	        		    "SELECT * FROM Elderly INNER JOIN address ON address.DNI=elderly.DNI INNER JOIN bankdata ON bankdata.DNI=elderly.DNI",
	        		     new ElderlyRowMapper());
	        		return elderlies;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Elderly>();
	       }
	   }
	   
	   public void addAddress(Address address, String DNI) {
			
			jdbcTemplate.update(
			     "INSERT INTO address VALUES(?, ?, ?, ?, ?)",
			    address.getStreet(), address.getPostalCode(), address.getCity(), address.getCountry(), DNI);
		
	   }
	   
	   public void updateAddress(Address address, String DNI) {
		   jdbcTemplate.update("UPDATE address SET street=?, postalCode=?, city=?, country=? WHERE dni=?",
		   address.getStreet(), address.getPostalCode(), address.getCity(), address.getCountry(), DNI);
	   }
	   
	   public void deleteAddress(String DNI) {
			
			jdbcTemplate.update("DELETE FROM address WHERE DNI=?",
					DNI);
			
	   }

	   public Address getAddress(String DNI) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM address WHERE DNI=?",
	        	        new AddressRowMapper(),
	        	        DNI);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Address> getAddresses() {
	       try {
	    
	    	   List<Address> addresses = jdbcTemplate.query(
	        		    "SELECT * FROM address",
	        		     new AddressRowMapper());
	        		return addresses;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Address>();
	       }
	   }
	   
	   public void addBankData(BankData bankData, String DNI) {		
			jdbcTemplate.update(
			     "INSERT INTO BankData VALUES(?, ?, ?, ?)",
			     bankData.getIBAN(), bankData.getBank(), bankData.getBranch_office(),
			     DNI);
		
	   }

	   
	   public void deleteBankData(String DNI) {		
			jdbcTemplate.update("DELETE FROM BankData WHERE DNI = ?",
					DNI);
			
	   }

	   public void updateBankData(BankData bankData, String DNI) {  
			jdbcTemplate.update("UPDATE BankData SET DNI = ?, bank= ?, branch_office=?"
					+ " WHERE IBAN = ?",
					DNI, bankData.getBank(), bankData.getBranch_office(),
				    bankData.getIBAN());
			
	   }

	   public BankData getBankData(String DNI) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM BankData WHERE DNI =?",
	        	        new BankDataRowMapper(),
	        	        DNI);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<BankData> getBankDatas() {
	       try {	    
	    	   List<BankData> bankDatas = jdbcTemplate.query(
	        		    "SELECT * FROM BankData",
	        		     new BankDataRowMapper());
	        		return bankDatas;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<BankData>();
	       }
	   }
	   
		public Elderly loadUserByUsername(String username, String password) {
			if(username == null || password == null)
				return null;
			Collection<Elderly> elderlies = getElderlies();
			Iterator iter = elderlies.iterator();
			while(iter.hasNext()) {
				Elderly elderly = (Elderly) iter.next();
				if(elderly.getUserName().equals(username) && elderly.getPassword().equals(password))
					return elderly;
			}
			return null;
		}
}
