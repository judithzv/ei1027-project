package ei102719zm.proyectoancianos.model;

public class Detail {
	private String concept;
	private float amount;
	private String code;
	private String number;
	
	public Detail() {
		
	}
	public void setConcept(String concept) {
		this.concept=concept;
	}
	public String getConcept() {
		return concept;
	}
	public void setNumber(String number) {
		this.number=number;
	}
	public String getNumber() {
		return number;
	}
	public void setAmount(float amount) {
		this.amount=amount;
	}
	public float getAmount() {
		return amount;
	}
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return code;
	}
	@Override
	   public String toString() {
	       return "Detail{" +
	               "concept='" + concept + "\'" +
	               ", amount='" + amount + "\'" +
	               ", code='" + code + "\'" +
	               ", number='" + number + "\'" +
	               "}";
	   }
	
	

}
