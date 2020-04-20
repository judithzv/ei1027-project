package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Availability;

@Repository 
public class AvailabilityDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addAvailability(Availability availability) {		
			jdbcTemplate.update(
			     "INSERT INTO availability VALUES(?, ?, ?, ?, ?)",
			     availability.getDate(), availability.getStartTime(), availability.getEndTime(),
			     availability.getDNI_elderly(), availability.getDNI_volunteer());
		
	   }

	   
	   public void deleteAvailability(String DNI_elderly, Date date, Date startTime, Date
			   endTime) {		
			jdbcTemplate.update("DELETE FROM availability WHERE DNI_elderly = ?, date=?, "
					+ " startTime=?, endTime=?;",
					DNI_elderly,date, date, startTime, endTime );
			
	   }


	   public Availability getAvailability(String DNI) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM availability WHERE DNI = ?",
	        	        new AvailabilityRowMapper(),
	        	        DNI);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Availability> getAvailabilitis() {
	       try {	    
	    	   List<Availability> availabilitis = jdbcTemplate.query(
	        		    "SELECT * FROM availability",
	        		     new AvailabilityRowMapper());
	        		return availabilitis;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Availability>();
	       }
	   }

}
