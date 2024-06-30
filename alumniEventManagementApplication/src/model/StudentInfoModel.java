package model;

public class StudentInfoModel {

	private int id;
	private String name;
	private long contact;
	private String email;
	private String department;
	private int passoutYear;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getPassoutYear() {
		return passoutYear;
	}
	public void setPassoutYear(int passoutYear) {
		this.passoutYear = passoutYear;
	}
	
	
}
