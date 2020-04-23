package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.UserDetails;

@Repository
public class UserDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   
	public UserDetails loadUserByUsername(String username, String password) {
		if(username == null || password == null)
			return null;
		Collection<UserDetails> users = listAllUsers();
		Iterator iter = users.iterator();
		while(iter.hasNext()) {
			UserDetails user = (UserDetails) iter.next();
			if(user.getUsername().equals(username) && user.getPassword().equals(password))
				return user;
		}
		return null;
	}
	
	public Elderly getElderly(String username) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM Elderly INNER JOIN address ON address.DNI=elderly.DNI INNER JOIN bankdata ON bankdata.DNI=elderly.DNI WHERE username=?", new ElderlyRowMapper(), username);
		} catch (EmptyResultDataAccessException e) {
	           return null;
		       
	       }
	}
	
 	public Collection<UserDetails> listAllUsers(){
	       try {
	    	   Collection<UserDetails> users = jdbcTemplate.query("SELECT userName, password FROM Elderly", new UserRowMapper());
	    	   BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); 
	    	   UserDetails admin = new UserDetails();
	    	   admin.setUsername("admin");
	    	   admin.setPassword("admin");
	    	   users.add(admin);
	    	   return users;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<UserDetails>();
	       
	       }
 	}
}