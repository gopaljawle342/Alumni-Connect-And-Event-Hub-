package repository;

import java.util.ArrayList;

import config.DBHelper;
import model.AdminModel;

public class AdminRepository  extends DBHelper{
	/*to add new admin*/
	public boolean isAddAdmin(AdminModel adminModel) {
		try {
			stmt=conn.prepareStatement("insert into adminlogin values('0',?,?,?,?)");
			stmt.setString(1, adminModel.getName());
			stmt.setLong(2, adminModel.getContact());
			stmt.setString(3, adminModel.getUsername());
			stmt.setString(4, adminModel.getPassword());
			int value=stmt.executeUpdate();
			return value>0? true: false;
		}
		catch(Exception ex) {
			System.out.println("Admin Add Error "+ex);
			return false;
		}	
	}
	
	/*TO Validation of admin login*/
	public ArrayList<AdminModel> getAdminInfo() {
		ArrayList<AdminModel> al=new ArrayList<AdminModel>();
		try {
			
			stmt=conn.prepareStatement("select *from adminlogin");
			rs=stmt.executeQuery();
			while(rs.next()) {
				AdminModel adminModel=new AdminModel();
//				adminModel.setId(rs.getInt(1));
//				adminModel.setName(rs.getString(2));
//				adminModel.setContact(rs.getLong(3));
				adminModel.setUsername(rs.getString(4));
				adminModel.setPassword(rs.getString(5));
				
				al.add(adminModel);
			}
			/* return arraylist list with adminModel class because admin table contain multiple entries  */
			return al;  
		}
		catch(Exception ex) {
			System.out.println("getAdmin Error "+ex);
			return null;
		}
	}
	
	public boolean isAdminCheck(String user,String pass) {
		try {
			
			stmt=conn.prepareStatement("select username,password from adminlogin where username=?");
			stmt.setString(1, user);
			rs=stmt.executeQuery();
			
			if(rs.next())
				return true;
			
			return false;
			
			
		}
		catch(Exception ex) {
			System.out.println("is admin check error "+ex);
			return false;
		}
	}
	
	
	
	public boolean isAdminCheck(Long contact) {
		try {
			
			stmt=conn.prepareStatement("select username,password from adminlogin where admin_contact=?");
			stmt.setLong(1, contact);
			rs=stmt.executeQuery();
			
			if(rs.next())
				return true;
			
			return false;
			
			
		}
		catch(Exception ex) {
			System.out.println("is admin check error "+ex);
			return false;
		}
	}
	
}
