package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.StudentInfoModel;
import repository.StudentInfoRepository;

public class StudentInfoService {
	
	BranchService branch_Service=new BranchService();
	StudentInfoRepository studentRepo=new StudentInfoRepository();
	PassOutYearService pass_Service =new PassOutYearService();
	Scanner sc=new Scanner(System.in);
	public boolean isAddStudent(StudentInfoModel student_Model) {
		boolean b=false;
		if(student_Model.getPassoutYear()<2024) {
			int year_id=pass_Service.getPassOutYearId(student_Model.getPassoutYear());
			if(year_id!=0) {
	
				int branchId=branch_Service.getBranchId(student_Model.getDepartment());
				if(branchId>0) {
				 b=studentRepo.isAddStudent(student_Model,year_id,branchId);
				}
				else {
					System.out.println("Branch not in our college");
				}
			}
			else {
				System.out.println("Pass Out year not in table");
				System.out.println("Do you want to add Pass Out Year");
				String temp=sc.nextLine();
				if(temp.equals("yes")) {
					boolean c=pass_Service.isAddPassOutYear(student_Model.getPassoutYear());
					if(c)System.out.println("Pass Out Year Added Successufully....");
					else System.out.println("Pass Out Year Not Added");
				}
			}
			
		}
		else {
			System.out.println("Student Not allow");
		}
		
		return b?true:false;
	
		
	}
	
	public ArrayList<StudentInfoModel> getAllStudentInfo(){
		return studentRepo.getAllStudentInfo();
	}
	
	public boolean deleteStudentInfo(Long contact) {
		return studentRepo.deleteStudentInfo(contact);
	}
	
	public boolean updateStudentContact(Long oldcontact,Long newContact) {
		return studentRepo.updateStudentContact(oldcontact, newContact);
	}
	
	public boolean updateStudentEmail(String oldEmail,String newEmail) {
		return studentRepo.updateStudentEmail(oldEmail, newEmail);
	}
	
	public boolean isAddMultipleStudent() {
		return studentRepo.isAddMultipleStudent();
	}
	
	public StudentInfoModel getStudentInfoById(int sid){
		return studentRepo.getStudentInfoById(sid);
	}
	
	public int checkStudentLogin(String user,String pass) {
		return studentRepo.checkStudentLogin(user, pass);
	}
		
	public List<String> getAllStudentEmail(){
		return studentRepo.getAllStudentEmail();
	}
	public int getBranchIdByStudentId(int studentId) {
		return studentRepo.getBranchIdByStudentId(studentId);
	}
	
}
