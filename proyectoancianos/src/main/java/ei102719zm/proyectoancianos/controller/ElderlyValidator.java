package ei102719zm.proyectoancianos.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Elderly;

public class ElderlyValidator implements Validator {
	
	public boolean supports(Class<?> cls) {
		return Contract.class.equals(cls);
	}
	
	  @Override
	  public void validate(Object obj, Errors errors) {
		  Elderly elderly = (Elderly) obj;
		  if(elderly.getDNI().isEmpty())
			  errors.rejectValue("DNI", "DNI", "A name has to be introduced");
		  
		  if(elderly.getName().isEmpty())
			  errors.rejectValue("name", "name", "A name has to be introduced");
		  
		  if(elderly.getSurNames().isEmpty())
			  errors.rejectValue("surNames", "surNames", "Surnames has to be introduced");
		  
		  if(elderly.getTelephoneNumber() <= 0)
			  errors.rejectValue("telephoneNumber", "telephoneNumber", "Invalid telephone number");
		  
		  if(elderly.getUserName().isEmpty())
			  errors.rejectValue("userName","userName", "A username has to be introduced"); 
		  
		  if(elderly.getPassword().isEmpty())
			  errors.rejectValue("password","password", "A password has to be introduced");
		  
		  if(elderly.getMail().isEmpty())
			  errors.rejectValue("mail","mail", "A mail has to be introduced");
		  
		  if(elderly.getAddress().getCity().isEmpty())
			  errors.rejectValue("address.city","address.city", "A city has to be introduced");
		  
		  if(elderly.getAddress().getCountry().isEmpty())
			  errors.rejectValue("address.city","address.country", "A country has to be introduced");
		  
		  if(elderly.getAddress().getStreet().isEmpty())
			  errors.rejectValue("address.street","address.street", "A city has to be introduced");
		  
		  
		  if(elderly.getBankData().getIBAN().isEmpty())
			  errors.rejectValue("bankData.IBAN","bankData.IBAN", "A IBAN has to be introduced");
		  
		  
		  
		  
		  
	  }

}
