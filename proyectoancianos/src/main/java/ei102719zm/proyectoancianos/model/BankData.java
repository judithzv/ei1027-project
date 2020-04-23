package ei102719zm.proyectoancianos.model;

public class BankData {
	private String IBAN;
	private String bank;
	private String branch_office;
	private String DNI;
	
	
	public BankData(){
		
	}
	public String getIBAN() {
		return IBAN;
	}
	
	public void setIBAN(String IBAN) {
		this.IBAN= IBAN;
	}
	public String getDNI() {
		return DNI;
	}
	
	public void setDNI(String DNI) {
		this.DNI= DNI;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank= bank;
	}
	public String getBranch_office() {
		return branch_office;
	}
	
	public void setBranchOffice(String branch_office) {
		this.branch_office = branch_office;
	}
	@Override
	   public String toString() {
	       return "BankData{" +
	               "IBAN='" + IBAN + "\'" +
	               ", bank='" + bank + "\'" +
	               ", branch office='" + branch_office + "\'" + ", DNI='" + DNI + "\'" +
	               
	               "}";
	   }
	
	

}
