package ei102719zm.proyectoancianos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import ei102719zm.proyectoancianos.model.Address;
import ei102719zm.proyectoancianos.model.BankData;
import ei102719zm.proyectoancianos.model.Elderly;

public class ElderlyRowMapper implements RowMapper<Elderly>{

	@Override
	public Elderly mapRow(ResultSet rs, int rowNum) throws SQLException {
		   Elderly elderly = new Elderly();
		   Address address = new Address();
		   BankData bankData = new BankData();
	       elderly.setName(rs.getString("name"));
	       elderly.setSurNames(rs.getString("surNames"));
	       elderly.setUserName(rs.getString("userName"));
	       elderly.setPassword(rs.getString("password"));
		   elderly.setTelephoneNumber(rs.getInt("telephoneNumber"));
		   elderly.setMail(rs.getString("mail"));
		   elderly.setBirthDate(rs.getObject("birthDate", LocalDate.class)); 
		   elderly.setHobbies(rs.getString("hobbies"));
		   elderly.setDNI(rs.getString("DNI"));
		   elderly.setIllnesses(rs.getString("illnesses"));
		   elderly.setDisability(rs.getString("disability"));
		   elderly.setSocialWorker(rs.getString("socialWorker"));
		   address.setCity(rs.getString("city"));
		   address.setCountry(rs.getString("country"));
		   address.setPostalCode(rs.getInt("postalCode"));
		   address.setStreet(rs.getString("street"));
		   elderly.setAddress(address);
		   bankData.setBank(rs.getString("bank"));
		   bankData.setBranchOffice(rs.getString("branch_office"));
		   bankData.setIBAN(rs.getString("IBAN"));
		   elderly.setBankData(bankData);
	       return elderly;
	}

}
