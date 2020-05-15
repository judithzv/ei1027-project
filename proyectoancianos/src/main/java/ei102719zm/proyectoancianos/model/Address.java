package ei102719zm.proyectoancianos.model;

public class Address {
	private String street;
	private int postalCode;
	private String city;
	private String country;
	private String DNI;

	public Address() {
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String DNI) {
		this.DNI = DNI;
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
