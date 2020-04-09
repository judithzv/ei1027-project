package ei102719zm.proyectoancianos.model;

import java.util.Date;

public class Invoice {
	private String code;
	private Date startDate;
	private Date endDate;
	private float amount;
	private String DNI;
	
	
	public Invoice() {
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	@Override
	   public String toString() {
	       return "Invoice{" +
	               "code='" + code + "\'" +
	               ", startDate='" + startDate + "\'" +
	               ", endDate='" + endDate + "\'" +
	               ", amount=" + amount +
	               ", DNI=" + DNI +
	               "}";
	   }

}
