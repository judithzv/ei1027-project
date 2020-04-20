package ei102719zm.proyectoancianos.model;

public class Address {
	private String street;
	private int postalCode;
	private String city;
	private String country;
	private String DNI_elderly;
	private String DNI_volunteer;

	public Address() {
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	   public String toString() {
	       return "Address{" +
	               "street='" + street + "\'" +
	               ", postalCode='" + postalCode + "\'" +
	               ", city='" + city + "\'" +
	               ", Country=" + country +
	               "}";
	   }

}
