package services;

import java.util.ArrayList;

import model.AdminModel;
import repository.AdminRepository;

public class AdminService {
	AdminRepository adminRepo=new AdminRepository();
	/*ADD new Admin it pass admin model reference to repository class*/
	public boolean isAddAdmin(AdminModel adminModel) {
		return adminRepo.isAddAdmin(adminModel);
	}
	
	/*it get admin information from repositary and check login credintial and if correct return true otherwise false*/
	
	public boolean getAdminInfo(String username,String password) {
		ArrayList<AdminModel> al=adminRepo.getAdminInfo();
		
		boolean flag=false;
		
		for(AdminModel admin:al) {
			if(username.equals(admin.getUsername())&& password.equals(admin.getPassword())) {
				flag=true;
				break;
			}
		}
		
		return flag;
	}
	
	// check admin crediantial
	public boolean isAdminCheck(String user,String pass) {
		return adminRepo.isAdminCheck(user, pass);
	}
	// check admin contact
	public boolean isAdminCheck(Long contact) {
		return adminRepo.isAdminCheck(contact);
	}
	
	
}
