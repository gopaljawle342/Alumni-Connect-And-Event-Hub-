package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import config.DBHelper;
import config.PathHelper;
import model.StudentInfoModel;
import services.BranchService;
import services.PassOutYearService;

public class StudentInfoRepository extends DBHelper{
	PassOutYearService year_Service=new PassOutYearService();
	BranchService branch_Service=new BranchService();
	
	static int student_id;
	/* student add as well as set in studentbranchyear table also*/
	public boolean isAddStudent(StudentInfoModel student_Model,int yearId,int branchId) {
		try {
			
			this.getAutomaticStudentId();
		
			cs=conn.prepareCall("call studentinfo(?,?,?,?,?,?)");
			cs.setInt(1, student_id);
			cs.setString(2,student_Model.getName());
			cs.setLong(3, student_Model.getContact());
			cs.setString(4,student_Model.getEmail());
			cs.setInt(5, branchId);
			cs.setInt(6, yearId);
			
			boolean b=cs.execute();
			return !b?true:false;
		}
		catch(Exception ex) {
			System.out.println("add student error "+ex);
			return false;
		}
	}
	
	public int getAutomaticStudentId() {
		try {
			stmt=conn.prepareStatement("select max(student_id) from student");
			rs=stmt.executeQuery();
			if(rs.next()) {
				student_id=rs.getInt(1);
				++student_id;
			}
			return student_id;
		}
		catch(Exception ex) {
			System.out.println("getAutomatic id"+ex);
			return -1;
		}
	}
	
	public ArrayList<StudentInfoModel> getAllStudentInfo(){
		
			ArrayList<StudentInfoModel>al =new ArrayList<StudentInfoModel>();
		try {
			stmt=conn.prepareStatement("select s.student_id,s.student_name,s.contact,s.email,b.branch_name,p.pass_year from student s inner join studentbranchyear sby on s.student_id=sby.student_id inner join alumnibranch b on sby.branch_id=b.branch_id inner join passoutyear p on p.id=sby.year_id");
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				StudentInfoModel studentModel=new StudentInfoModel();
				studentModel.setId(rs.getInt(1));
				studentModel.setName(rs.getString(2));
				studentModel.setContact(rs.getLong(3));
				studentModel.setEmail(rs.getString(4));
				studentModel.setDepartment(rs.getString(5));
				studentModel.setPassoutYear(rs.getInt(6));
				al.add(studentModel);
			}
			return al;
			
		}
		catch(Exception ex) {
			System.out.println("get all student info error "+ex);
			return null;
		}
	}
	
	public boolean deleteStudentInfo(Long contact) {
		try {
			stmt=conn.prepareStatement("delete from student where contact=?");
			stmt.setLong(1, contact);
			int value=stmt.executeUpdate();
			return value>0?true:false;
			
		}catch(Exception ex) {
			System.out.println("delete student info error "+ex);
			return false;
		}
	}
	
	
	public boolean updateStudentContact(Long oldContact,Long newContact) {
		try {
			stmt=conn.prepareStatement("update student set contact=? where contact=?");
			stmt.setLong(1, newContact);
			stmt.setLong(2, oldContact);
			int value=stmt.executeUpdate();
			return value>0?true:false;
			
		}catch(Exception ex) {
			System.out.println("update student info error "+ex);
			return false;
		}
	}
	
	public boolean updateStudentEmail(String oldEmail,String newEmail) {
		try {
			stmt=conn.prepareStatement("update student set email=? where email=?");
			stmt.setString(1, newEmail);
			stmt.setString(2, oldEmail);
			int value=stmt.executeUpdate();
			return value>0?true:false;
			
		}catch(Exception ex) {
			System.out.println("update student info error "+ex);
			return false;
		}
	}
	
	// add multiple student
	public boolean isAddMultipleStudent() {
		try {
			
			
			FileReader f=new FileReader(PathHelper.path+"MultiStudent.csv");
			BufferedReader bf=new BufferedReader(f);
			
			String line;
			int count=0;
			boolean b=false;
			
			System.out.println("Read CSV File...");
			while((line=bf.readLine())!=null) {
				this.getAutomaticStudentId();
				
				String data[]=line.split(",");
				String name=data[0];
				Long contact=Long.parseLong(data[1]);
				String email=data[2];
				String branch=data[3];
				int year=Integer.parseInt(data[4]);
				
				if(year<2024) {
					int year_id=year_Service.getPassOutYearId(year);
					if(year_id!=0) {
						int branch_id=branch_Service.getBranchId(branch);
						if(branch_id>0) {
				
							cs=conn.prepareCall("call studentinfo(?,?,?,?,?,?)");
							cs.setInt(1, student_id);
							cs.setString(2,name);
							cs.setLong(3,contact);
							cs.setString(4,email);
							cs.setInt(5, branch_id);
							cs.setInt(6, year_id);
							
							 b=cs.execute();
							++count;
						}
						
					}
				}
				
				
			
//				System.out.println(student_id+"\t"+name+"\t"+contact+"\t"+branch+"\t"+year);
			}
			bf.close();
			System.out.println(count +" Student Added Successfully");
			
			return b?true:false;
		
			
		}catch(Exception ex) {
			System.out.println(" add multiple student error "+ex);
			return false;
		}
		
	
	}
	
	
	// get student info by id
	public StudentInfoModel getStudentInfoById(int sid){
		StudentInfoModel studentModel=new StudentInfoModel();
		
	try {
		stmt=conn.prepareStatement("select s.student_id,s.student_name,s.contact,s.email,b.branch_name,p.pass_year from student s inner join studentbranchyear sby on s.student_id=sby.student_id inner join alumnibranch b on sby.branch_id=b.branch_id inner join passoutyear p on p.id=sby.year_id where s.student_id=?");
		stmt.setInt(1, sid);
		rs=stmt.executeQuery();
		
		while(rs.next()) {
			
			studentModel.setId(rs.getInt(1));
			studentModel.setName(rs.getString(2));
			studentModel.setContact(rs.getLong(3));
			studentModel.setEmail(rs.getString(4));
			studentModel.setDepartment(rs.getString(5));
			studentModel.setPassoutYear(rs.getInt(6));
			
		}
		return studentModel;
		
	}
	catch(Exception ex) {
		System.out.println("get all student info error "+ex);
		return null;
	}
}
	
	
	// student login check
	public int checkStudentLogin(String user,String pass) {
	
		int studentId=0;
		try {
			stmt=conn.prepareStatement("select *from studentlogin");
			rs=stmt.executeQuery();
			while(rs.next()) {
				if(user.equals(rs.getString(2))&& pass.equals(rs.getString(3))) {
					studentId=rs.getInt(1);
				}
			}
			
			return studentId>0?studentId:0;
		}
		catch(Exception ex) {
			System.out.println("check student login error "+ex);
			return-1;
		}
	}
	
	
	// get all student email address
	public List<String> getAllStudentEmail(){
		
		List<String> list=new ArrayList<String>();
		
		try {
			stmt=conn.prepareStatement("select email from student");
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString(1));
			}
			
			return list;
			
		}
		catch(Exception ex) {
			System.out.println("get all email error "+ex);
			return null;
		}
	}
	
	public int getBranchIdByStudentId(int studentId) {
		try {
			
			stmt=conn.prepareStatement("select branch_id from studentbranchyear where student_id=?");
			stmt.setInt(1, studentId);
			
			rs=stmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			else {
				return 0;
			}
			
			
			
		}catch(Exception ex) {
			System.out.println("get branch id by student id error "+ex);
			return -1;
		}
	}
	
}
