package ei102719zm.proyectoancianos.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Request {
	private String number;
	private String state;
	private String service;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	public LocalDate startDate;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	public LocalDate endDate;
	private String details;
	private String DNI;
	private String idContarct;
	private BankData bankData;
	private Elderly elderly;
	
	
	public Request(){
		
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String DNI) {
		this.DNI=DNI;
	}
	public String getIdContract() {
		return idContarct;
	}
	public void setIdContract(String  idContarct) {
		this. idContarct= idContarct;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number=number;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state=state;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service=service;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate=startDate;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate=endDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details=details;
	}
	public BankData getBankData() {
		return bankData;
	}
	public void setBankData(BankData bankData) {
		this.bankData = bankData;
	}
	
	public Elderly getElderly() {
		return elderly;
	}
	public void setElderly(Elderly elderly) {
		this.elderly = elderly;
	}
	@Override
	   public String toString() {
	       return "Request{" +
	               "number='" + number + "\'" +
	               ", state='" + state + "\'" +
	               ", service='" + service + "\'" +
	               ", startDate=" + startDate +
	               ", endDate =" + endDate +
	               ", details =" + details + 
	               ", DNI=" + DNI +
	               "}";
	   }

}
