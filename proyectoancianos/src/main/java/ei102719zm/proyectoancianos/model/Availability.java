package ei102719zm.proyectoancianos.model;

import java.util.Date;

public class Availability {
	private Date date;
	private Date startTime;
	private Date endTime;
	private  String DNI_elderly;
	private  String DNI_volunteer;

	public Availability(){
			
	}
	
	public String getDNI_elderly() {
		return DNI_elderly;
	}
	
	public void setDNI_elderly(String DNI_elderly) {
		this.DNI_elderly = DNI_elderly;
	}
	public String getDNI_volunteer() {
		return DNI_volunteer;
	}
	
	public void setDNI_volunteer(String DNI_volunteer) {
		this.DNI_volunteer = DNI_volunteer;
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
