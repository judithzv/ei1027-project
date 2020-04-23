package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jasypt.util.password.BasicPasswordEncryptor; 
import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.UserDetails;

public class UserRowMapper implements RowMapper<UserDetails> {
	
	@Override
	public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserDetails user = new UserDetails();
		user.setUsername(rs.getString("userName"));
		user.setPassword(rs.getString("password"));
		return user;
	}

}
