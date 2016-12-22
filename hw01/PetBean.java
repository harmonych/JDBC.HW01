package hw01;

import java.io.Serializable;
import java.sql.Date;

public class PetBean implements Serializable {
	int id;
	String petName; 	
	String masterName;
	Date birthday;  
	int price; 
	double weight;    
	String filename;  
	byte[] picture; 
	char[] comment;
	
	public PetBean(int id, String petName, String masterName, Date birthday, int price, double weight,
			String filename, byte[] picture, char[] comment) {
		super();
		this.id = id;
		this.petName = petName;
		this.masterName = masterName;
		this.birthday = birthday;
		this.price = price;
		this.weight = weight;
		this.filename = filename;
		this.picture = picture;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public char[] getComment() {
		return comment;
	}

	public void setComment(char[] comment) {
		this.comment = comment;
	}
	
	
}
