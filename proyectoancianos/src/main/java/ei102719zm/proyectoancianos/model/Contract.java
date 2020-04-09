package ei102719zm.proyectoancianos.model;

import java.util.Date;

public class Contract {
	public String id;
	public Date startDate;
	public Date endDate;
	public String serviceType;
	public int price;
	public Date signatureDate;
	public String CIF;
	
	
	public Contract() {
		
	}
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return id;
	}
	public void setStartDate(Date startDate) {
		this.startDate=startDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate=endDate;
	}
	public Date getEndDate() {
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
	public Date getSignatureDate() {
		return signatureDate;
	}
	public void setSignatureDate(Date signatureDate) {
		this.signatureDate=signatureDate;
	}
	public void setCIF(String CIF) {
		this.CIF=CIF;
	}
	public String getCIF() {
		return CIF;
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
