package ei102719zm.proyectoancianos.model;

public class Company {

	private String name;
	private String CIF;
	private String contactPerson;
	private int telephoneNumber;
	private String userName;
	private String password;
	
	public Company() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getCIF() {
		return CIF;
	}
	public void setCIF(String CIF) {
		this.CIF=CIF;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson=contactPerson;
	}
	public int getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(int telephoneNumber) {
		this.telephoneNumber=telephoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName=userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	@Override
	   public String toString() {
	       return "Company{" +
	               "name='" + name + "\'" +
	               ", CIF='" + CIF + "\'" +
	               ", contactPerson='" + contactPerson + "\'" +
	               ", telephoneNumber=" + telephoneNumber +
	               ", userName=" + userName +
	               ", password=" + password +
	               "}";
	   }
	
	
}
