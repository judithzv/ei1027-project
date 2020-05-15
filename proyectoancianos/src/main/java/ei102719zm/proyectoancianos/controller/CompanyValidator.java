package ei102719zm.proyectoancianos.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ei102719zm.proyectoancianos.model.Company;

public class CompanyValidator implements Validator {
	public boolean supports(Class<?> cls) {
		return Company.class.equals(cls);
	}
	
  @Override
  public void validate(Object obj, Errors errors) {
	  Company company = (Company) obj;
		 
	  if(company.getCIF().isEmpty()) 
		  errors.rejectValue("CIF", "CIF", "A CIF has to be introduced"); 
	  
	  if(company.getName().isEmpty())
		  errors.rejectValue("name", "name", "A name has to be introduced");
	  
	  if(company.getTelephoneNumber() <= 0)
		  errors.rejectValue("telephoneNumber", "telephoneNumber", "Invalid telephone number");
	  
	  if(company.getUserName().isEmpty())
		  errors.rejectValue("userName","userName", "A username has to be introduced"); 
	  
	  if(company.getPassword().isEmpty())
		  errors.rejectValue("password","password", "A password has to be introduced"); 
	  
	  if(company.getContactPerson().isEmpty())
		  errors.rejectValue("contactPerson", "contactPerson", "A contact person has to be introduced");
  }
}
