package ei102719zm.proyectoancianos.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ei102719zm.proyectoancianos.model.Contract;


public class ContractValidator implements Validator {
	
	public boolean supports(Class<?> cls) {
		return Contract.class.equals(cls);
	}
  @Override
  public void validate(Object obj, Errors errors) {
	  Contract contract = (Contract) obj;
	  if(contract.getCIF().isEmpty())
	       errors.rejectValue("CIF", "CIF",
                   "A value has to be introduced");
	  if(contract.getStartDate() == null)
		  errors.rejectValue("startDate", "startDate",
                  "A start date has to be introduced");
	  if(contract.getEndDate() == null)
		  errors.rejectValue("endDate", "endDate",
                  "An end date has to be introduced");
	  if(contract.getServiceType().isEmpty())
		  errors.rejectValue("serviceType", "serviceType",
                  "A value has to be introduced");
	  else if(!contract.getServiceType().equals("catering") && !contract.getServiceType().equals("cleaning")
			  && !contract.getServiceType().equals("health"))
		  errors.rejectValue("serviceType", "serviceType",
                  "Service has to be catering, cleaning or health");
	  if(contract.getPrice() <= 0)
		  errors.rejectValue("price", "price",
				  "Price has to be bigger than 0");
	  }
  }