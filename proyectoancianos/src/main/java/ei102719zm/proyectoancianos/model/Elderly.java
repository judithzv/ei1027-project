package ei102719zm.proyectoancianos.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Elderly {
	   private String name;
	   private String surNames;
	   private String userName;
	   private String password;
	   private String DNI;
	   private int telephoneNumber;
	   private String mail;
	   @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
		private LocalDate birthDate;
	   private String hobbies;
	   private String illnesses;
	   private String disability;
	   private String socialWorker;
	   private Address address;
	   private BankData bankData;

	   public Elderly() {
	   }

	   public String getName() {
	       return name;
	   }

	   public void setName(String name) {
	       this.name = name;
	   }

	   public String getSurNames() {
	       return surNames;
	   }
	   public void setSurNames(String surNames) {
	       this.surNames = surNames;
	   }

	   public String getUserName() {
	       return userName;
	   }
	   public void setUserName(String userName) {
	       this.userName = userName;
	   }
	   
	   public String getPassword() {
	       return password;
	   }
	   public void setPassword(String password) {
	       this.password = password;
	   }
	   public String getDNI() {
	       return DNI;
	   }
	   public void setDNI(String DNI) {
	       this.DNI = DNI;
	   }
	   public int getTelephoneNumber() {
	       return telephoneNumber;
	   }
	   public void setTelephoneNumber(int telephoneNumber) {
	       this.telephoneNumber = telephoneNumber;
	   }
	   public String getMail() {
	       return mail;
	   }

	   public void setMail(String mail) {
	       this.mail = mail;
	   }
	   public LocalDate getBirthDate() {
	       return birthDate;
	   }

	   public void setBirthDate(LocalDate birthDate) {
	       this.birthDate = birthDate;
	   }
	   
	   public String getHobbies() {
	       return hobbies;
	   }
	   public void setHobbies(String hobbies) {
	       this.hobbies = hobbies;
	   }
	   
	   public String getIllnesses() {
	       return illnesses;
	   }
	   public void setIllnesses(String illeness) {
	       this.illnesses = illeness;
	   }
	   
	   public String getDisability() {
	       return disability;
	   }
	   public void setDisability(String disability) {
	       this.disability = disability;
	   }
	   public String getSocialWorker() {
	       return socialWorker;
	   }
	   public void setSocialWorker(String socialWorker) {
	       this.socialWorker = socialWorker;
	   }
	 
	   public Address getAddress() {
		   return this.address;
	   }
	   public void setAddress(Address address) {
		   this.address = address;
	   }
	   
	   public BankData getBankData() {
		   return this.bankData;
	   }
	   public void setBankData(BankData bankData) {
		   this.bankData=bankData;
	   }
	   

	   @Override
	   public String toString() {
	       return "Elderly{" +
	               "name='" + name + "\'" +
	               ", surNames='" + surNames + "\'" +
	               ", userName='" + userName + "\'" +
	               ", password=' " + password + "\'" +
	               ", DNI=" + DNI +
	               ", telephoneNumber='" + telephoneNumber + "\'" +
	               ", mail='" + mail + "\'" +
	               ", birthDate='" + birthDate + "\'" +
	               ", hobbies='" + hobbies + "\'" +
	               ", illeness='" + illnesses + "\'" +
	               ", disability='" + disability + "\'" +
	               ", socialWorker='" + socialWorker + "\'" +
	               ", address='" + address.toString() + "\'" +
	               ", bankData='" + bankData.toString() + "\'" +
	               "}";
	   }

}
