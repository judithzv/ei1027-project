package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Address;

public class AddressRowMapper implements RowMapper<Address>{
	
	@Override
	public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
		   Address address = new Address();
	       address.setStreet(rs.getString("street"));
	       address.setPostalCode(rs.getInt("postalCode"));
	       address.setCity(rs.getString("city"));
	       address.setCountry(rs.getString("country"));
	       address.setDNI_elderly(rs.getString("DNI_velderly"));
	       address.setDNI_volunteer(rs.getString("DNI_volunteer"));

	       return address;
	}

}
