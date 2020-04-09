package ei102719zm.proyectoancianos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ei102719zm.proyectoancianos.model.Detail;

@Repository
public class DetailDao {
	private JdbcTemplate jdbcTemplate;

	   @Autowired
	   public void setDataSource(DataSource dataSource) {
	       jdbcTemplate = new JdbcTemplate(dataSource);
	   }
	   

	   public void addDetail(Detail detail) {		
			jdbcTemplate.update(
			     "INSERT INTO detail VALUES(?, ?, ?)",
			     detail.getConcept(), detail.getAmount(), detail.getCode()) ;
		
	   }

	   
	   public void deleteDetail(String code, float amount, String concept  ) {		
			jdbcTemplate.update("DELETE FROM Detail WHERE concept = ?, amount=? AND"
					+ "concept=? ;",
					concept, amount, code);
			
	   }



	   public Detail getDetail(String code) {
	       try {
	           return jdbcTemplate.queryForObject("SELECT * FROM Detail WHERE code = ?",
	        	        new DetailRowMapper(),
	        	        code);

	       }
	       catch(EmptyResultDataAccessException e) {
	           return null;
	       }
	   }

	   public List<Detail> getDetails() {
	       try {	    
	    	   List<Detail> details = jdbcTemplate.query(
	        		    "SELECT * FROM Elderly",
	        		     new DetailRowMapper());
	        		return details;

	       }
	       catch(EmptyResultDataAccessException e) {
	           return new ArrayList<Detail>();
	       }
	   }

}
