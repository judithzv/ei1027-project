package ei102719zm.proyectoancianos.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Contract {
	public String id;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	public LocalDate startDate;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	public LocalDate endDate;
	public String serviceType;
	public int price;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	public LocalDate signatureDate;
	public String CIF;
	public Company company;
	
	
	public Contract() {
		
	}
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return id;
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType=serviceType;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price=price;
	}
	public LocalDate getSignatureDate() {
		return signatureDate;
	}
	public void setSignatureDate(LocalDate signatureDate) {
		this.signatureDate=signatureDate;
	}
	public void setCIF(String CIF) {
		this.CIF=CIF;
	}
	public String getCIF() {
		return CIF;
	}
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	   public String toString() {
	       return "Contract{" +
	               "id='" + id + "\'" +
	               ", startDate='" + startDate + "\'" +
	               ", endDate='" + endDate + "\'" +
	               ", serviceType=" + serviceType +
	               ", price=" + price +
	               ", signatureDate=" + signatureDate +
	               ", CIF=" + CIF +
	               "}";
	   }
	

}
