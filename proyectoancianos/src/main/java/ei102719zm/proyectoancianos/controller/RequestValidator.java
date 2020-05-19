package ei102719zm.proyectoancianos.controller;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ei102719zm.proyectoancianos.model.BankData;
import ei102719zm.proyectoancianos.model.Request;

public class RequestValidator implements Validator {
	
	public boolean supports(Class<?> cls) {
		return Request.class.equals(cls);
	}
	
  @Override
  public void validate(Object obj, Errors errors) {
	  Request request = (Request) obj;
	  LocalDate startDate = request.getStartDate();
	  LocalDate endDate = request.getEndDate();
	  if(startDate != null && endDate != null && startDate.isAfter(endDate))
		  errors.rejectValue("startDate", "startDate", "Start date has to be previous than end date");
	  BankData bankData = request.getBankData();
	  if(bankData.getIBAN().isEmpty())
		  errors.rejectValue("bankData.IBAN", "bankData.IBAN", "IBAN cannot be empty");
  }
}
