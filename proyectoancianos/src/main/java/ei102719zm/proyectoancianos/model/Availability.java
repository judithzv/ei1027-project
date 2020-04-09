package ei102719zm.proyectoancianos.model;

import java.util.Date;

public class Availability {
	private Date date;
	private Date startTime;
	private Date endTime;
	private  String DNI_elderly;

	public Availability(){
			
	}
	
	public String getDNI() {
		return DNI_elderly;
	}
	
	public void setDNI(String DNI) {
		this.DNI_elderly = DNI;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	   public String toString() {
	       return "Availability{" +
	               "date='" + date + "\'" +
	               ", startTime='" + startTime + "\'" +
	               ", endTime='" + endTime + "\'" +
	               ", DNI=" + DNI_elderly +
	               "}";
	   }

}
