package ei102719zm.proyectoancianos.model;

public class Request {
	private String number;
	private String state;
	private String service;
	private String schedule;
	private String DNI;
	
	
	public Request(){
		
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String DNI) {
		this.DNI=DNI;
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
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule=schedule;
	}
	@Override
	   public String toString() {
	       return "Request{" +
	               "number='" + number + "\'" +
	               ", state='" + state + "\'" +
	               ", service='" + service + "\'" +
	               ", schedule=" + schedule +
	               ", DNI=" + DNI +
	               "}";
	   }

}
