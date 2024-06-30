package client;

import java.time.LocalDate;
import java.util.*;

import model.AdminModel;
import model.BranchModel;
import model.EventModel;
import model.StudentInfoModel;
import services.AdminService;
import services.BranchService;
import services.CheckFailedMail;
import services.StudentInfoService;
import services.EventService;
import services.EmailSender;
import services.PassOutYearService;

public class AlumniEventClient {

	public static void main(String[] args) {
		
		AdminModel adminModel=null;
		AdminService adminService=new AdminService ();
		BranchModel branchModel=null;
		BranchService branchService=new BranchService();  
		PassOutYearService passService=new PassOutYearService();
//		Pass_Out_Year_Model pass_Model=null;
		StudentInfoModel studentModel=new StudentInfoModel();
		StudentInfoService studentService=new StudentInfoService();
		EventService eventService=new EventService();
		EmailSender  send=new EmailSender ();
		CheckFailedMail checkMail=new CheckFailedMail();
		
		Scanner sc=new Scanner(System.in);
		boolean b;
		int choice=0;
		System.out.println("------------------------------------------------");
		System.out.println("-----   Alumni Event Management System     -----");
		System.out.println("------------------------------------------------\n\n");
		
		
		do {
			
			System.out.println("\n\n1.Admin Login\n2.Student Login\nSelect Option:");
			try {
				choice=sc.nextInt();
			}catch(Exception ex) {
				System.out.println("Enter digit Only");
				sc.next();
			}
			
			switch(choice) {
			
			case 1: 
				// admin login
				//System.out.println("1.New Admin\n2.Exiting Admin\nSelect Option");
			
				switch(choice) {
				
				case 1:
					
					System.out.println("Enter Admin UserName ");
					sc.nextLine();
					String username=sc.nextLine();
					System.out.println("Enter Admin Password");
					String password=sc.nextLine();
					
					b=adminService.getAdminInfo(username,password);
					if(b) {
						System.out.println("Admin Login Successully.....");
						
						
						
						do {
							
							System.out.println("\n-----              ADMIN MENU              -----");
							System.out.println("------------------------------------------------");
							
							System.out.println("1.Add Admin");
							System.out.println("2.Add Branch");
							System.out.println("3.Add Pass Out Year");
							System.out.println("4.Add / View / Delete /Update Student");
							System.out.println("5.Add New / View All Event");
							System.out.println("6.Take Attendance of event");
							System.out.println("7.View Event Wise Review");
							System.out.println("8.View Absent Student By Event Wise");
							System.out.println("11.Exit Admin Login");
							System.out.println("Select Option:");
							try {
								choice=sc.nextInt();
							}catch(Exception ex) {
								System.out.println("Enter digit Only");
								sc.next();
								choice=0;
							}
							
							switch(choice) {
							
							case 1:
								adminModel =new AdminModel();
								System.out.println("enter Name, contact, username , password:");
								sc.nextLine();
								adminModel.setName(sc.nextLine());
								String contact1=sc.nextLine();
								// verify contact number
								Long contact=eventService.verifyContact(contact1);
								if(contact==0) {
									System.out.println("Enter 10 Digit");
									break;
								}else if(contact==-1){
									System.out.println("Enter only Digit");
									break;
								}
								adminModel.setContact(contact);
								
								adminModel.setUsername(sc.nextLine());
								adminModel.setPassword(sc.nextLine());
								
								if(adminService.isAdminCheck(adminModel.getUsername(), adminModel.getPassword())) {
									System.out.println("Admin Already Present");
									break;
								}
								else if(adminService.isAdminCheck(adminModel.getContact())) {
									System.out.println("Contact Already Present");
									break;
								}
								
									
								b=adminService.isAddAdmin(adminModel);
									if(b) 
										System.out.println("Admin added Successfully....");
									else
										System.out.println("Admin Not Added....");
									
								
							
								
								break;
							
							
							case 2:
								// branch add
								branchModel=new BranchModel();
								
								System.out.println("enter Branch Name and Branch HOD Name");
								sc.nextLine();
								branchModel.setBranchName(sc.nextLine());
								branchModel.setHodName(sc.nextLine());
								
								 b=branchService.isAddNewBranch(branchModel);
								if(b) {
									System.out.println("Branch Added Successfully...");
								}
								else {
									System.out.println("Branch Not Added...");
								}
								
								
								/*Branch information*/
							/*	ArrayList<Alumni_Branch_Model> al=branch_Service.getBranchInfo();
								for(Alumni_Branch_Model am:al) {
									System.out.println(am.getId()+"\t"+am.getBranch_name()+"\t"+am.getHod_name());
								}
							*/
								
								/*Branch id and hod info*/
//								Alumni_Branch_Model abm=branch_Service.getIdHODInfo("IT");
//								System.out.println(abm.getId()+"\t"+abm.getHod_name());
//								
								break;
								
							case 3:
								// passout year add
								int year=0;
								System.out.println("Enter Pass Out Year");
								try {
									year=sc.nextInt();
								}catch(Exception ex) {
									System.out.println("Enter digit Only");
									sc.next();
									break;
								}
							
								if(year<2024) {
									if(passService.getPassOutYearId(year)==0) {
										b=passService.isAddPassOutYear(year);
										if(b) System.out.println("Pass Out Year add sucessfully...");
										else System.out.println("Not add Pass Out Year...");
									}
									else {System.out.println("Pass out year Already in table");}	 
								}
								else {System.out.println("Year not allow");}
								
								
								/*ALL Pass out year Info*/
								
							/*
							 
								pass_Model=new Pass_Out_Year_Model();
							 	ArrayList<Pass_Out_Year_Model>al=pass_Service.getPassYearInfo();
								
								if(al!=null) {
									System.out.println("PassYear_Id\tPassYear");
									for(Pass_Out_Year_Model passModel:al) {
										System.out.println(passModel.getId()+"\t"+passModel.getYear());
									}
								}
								else {
									System.out.println("Pass_year_Data not found..");
								}
							*/
		
								
								break;
							case 4:
								// alumni add case
								
								do {
									
								
								System.out.println("\n-----           STUDENT DETAILS            -----");
								System.out.println("------------------------------------------------");
								
								
								System.out.println("1.Add Student\n2.View Student\n3.Delete Student\n4.Update Student\n5.Update Email\n6.Add Multiple Student\n7.To Exit\n");
								System.out.println("Select Option :");
								try {
									choice=sc.nextInt();
								}catch(Exception ex) {
									System.out.println("Enter digit Only");
									sc.next();
									choice=0;
								}
								
								switch(choice) {
								case 1:
								
									System.out.println("\nenter Student Name");
									sc.nextLine();
									String name=sc.nextLine();
									System.out.println("Enter Contact :");
									contact1=sc.nextLine();
									// verify contact number
									 contact=eventService.verifyContact(contact1);
									if(contact==0) {
										System.out.println("Enter 10 Digit");
										break;
									}else if(contact==-1){
										System.out.println("Enter only Digit");
										break;
									}
									
									System.out.println("Enter Email :");
									String email=sc.nextLine();
									System.out.println("> Computer\n"
											+ "> Electronics \n"
											+ "> Mechanical\n"
											+ "> Civil\n"
											+ "> Electrical"+
											"\nSelect Branch Name :");
									String branch=sc.nextLine();
									System.out.println("Enter Pass Out Year: ");
									int student_year=sc.nextInt();
									
									studentModel.setName(name);
									studentModel.setContact(contact);
									studentModel.setEmail(email);
									studentModel.setDepartment(branch);
									studentModel.setPassoutYear(student_year);
									
									
									List<StudentInfoModel> list=studentService.getAllStudentInfo();
									int flag=1;
									if(list.size()>0) {
										for(StudentInfoModel student:list) {
											if(student.getContact()==contact) {
												flag=0;
												System.out.println("Student Contact Already Registerd");
												break;
											}
											else if(student.getEmail().equals(email)){
												flag=0;
												System.out.println("Student Email Already Registerd");
												break;
											}
											
										}
									}
									if(flag==1) {
										 b=studentService.isAddStudent(studentModel);
										 if(b) System.out.println("Student Added Successfully...");
											else System.out.println("Student Not Added...");
									}
									
								
									
									break;
								case 2:
									// view all student
									
									System.out.println("All Student Details : ");
									ArrayList<StudentInfoModel>al=studentService.getAllStudentInfo();
									
									if(al.size()>0) {
										System.out.println("id\tStudent_Name\tContact\t\tEmail\t\tBranch\t\tPassOutYear");
										
										for(StudentInfoModel sModel:al) {
											System.out.println(sModel.getId()+"\t"+sModel.getName()+"\t"+sModel.getContact()+"\t"+sModel.getEmail()+"\t"+sModel.getDepartment()+"\t"+sModel.getPassoutYear());
										}
										
									}
									else {
										System.out.println("No Student Data Available");
									}
									
									
									break;
								case 3:
									// delete student
									System.out.println("\nenter Student contact details to delete details");
									contact=sc.nextLong();
									b=studentService.deleteStudentInfo(contact);
									if(b)System.out.println("Student Details Deleted Successfully...");
									else System.out.println("Student Details Not Deleted...");
									break;
								case 4:
									//update student contact
									System.out.println("Enter Old Contact Number: ");
									sc.nextLine();
									contact1=sc.nextLine();
									Long oldContact=eventService.verifyContact(contact1);
									if(oldContact==0) {
										System.out.println("Enter 10 Digit");
										break;
									}else if(oldContact==-1){
										System.out.println("Enter only Digit");
										break;
									}
									
									System.out.println("Enter New Contact Number:");
//									Long newContact=sc.nextLong();
									
								    contact1=sc.nextLine();
									Long newContact=eventService.verifyContact(contact1);
									if(newContact==0) {
										System.out.println("Enter 10 Digit");
										break;
									}else if(newContact==-1){
										System.out.println("Enter only Digit");
										break;
									}
									b=studentService.updateStudentContact(oldContact, newContact);
									if(b)System.out.println("Contact Update Successfully....");
									else System.out.println("No Record Founds....");
									
									break;
								case 5:
									System.out.println("Enter Old Email: ");
									sc.nextLine();
									String oldEmail=sc.nextLine();
									System.out.println("Enter New Email:");
									String newEmail=sc.nextLine();
									b=studentService.updateStudentEmail(oldEmail, newEmail);
									if(b)System.out.println("Email Update Successfully....");
									else System.out.println("No Record Founds....");
									break;
									
								case 6:
									// add multiple student
									
									b=studentService.isAddMultipleStudent();
									if(b)System.out.println("Multiple Student Added Successfully.......");
									else System.out.println("Multiple Student Not Added...");
									break;
								
								}
								
								System.out.println("================================================");
								}while(choice<6);
								
								break;
							case 5:
								//add / show events
								do {
									System.out.println("\n1.Add New Event\n2.Show All Event\n3.To Exit\nSelect Option:");
									try {
										choice=sc.nextInt();
									}catch(Exception ex) {
										System.out.println("Enter digit Only");
										sc.next();
										choice=0;
									}
									
									switch(choice){
									case 1:
										// add new event send mail
										
										
										
										
										String branch=null;
										System.out.println("Enter Event Name");
										sc.nextLine();
										String ename=sc.nextLine();
										System.out.println("Enter Event Date(YYYY-MM-DD)");
										String edate=sc.nextLine();
										
										b=eventService.isEventDateCheck(edate);
										if(b) {
											
											ArrayList<BranchModel> al=branchService.getBranchInfo();
											for(BranchModel am:al) {
												System.out.println(am.getId()+"\t"+am.getBranchName());
											}
											System.out.println("Select Organizer Branch Id:");
											int orgId=sc.nextInt();
										
											EventModel event_Model=new EventModel();
											event_Model.setEventName(ename);
											event_Model.setDate(edate);
											event_Model.setOrg_BranchId(orgId);
											

											
											b=eventService.isAddNewEvent(event_Model);
											if(b) {
												System.out.println("Event Added Successfully....");
												
												// get branch name
												for(BranchModel am:al) {
													if(orgId==am.getId()) {
														branch=am.getBranchName();
														break;
													}
													
												}
												
												List<String>list=studentService.getAllStudentEmail();
												
//												String [] arg= {"jawlegopal123@gmail.com","ganeshturate@gmail.com","tj880545@gmail.com"};
												
												System.out.println("Mail Sending In Progress.................");
												send.sendEmail(list,ename,edate,branch);
												
												
												// Check Failed Mail 
												System.out.println("\nEmail Failed Student:");
												
												b=checkMail.checkFailedMail();
												if(b) {
													System.out.println("No Failed Mail .....");
												}
												
												
												
												
											}
											else System.out.println("Event Not Added..");
											
										}
										
										else {
											System.out.println("Event Date Not Allow");
										}
										
										
										break;
									case 2:
										// show all event
										eventService.showAllEvent();
										System.out.println("\n-----------------------------------------------");
										break;
									}
								}
								while(choice<3);
								
								
								System.out.println("================================================");
								
								
								
								break;
								
							case 6:
									// event attendance
									
									eventService.showAllEvent(); // show all event
									System.out.println("Select Event Id for Attendance:");
									int eventId=sc.nextInt();
									boolean flag=false;
									
									// check enter event id is correct or not
									ArrayList<Integer> eId=eventService.getAllEventIds();  
									for(int id:eId) {
										if(id==eventId) {
											flag=true;
										}
									}
									
									if(!flag) {
										System.out.println("Enter Correct Event Id");
										break;
									}
									
									LocalDate today=LocalDate.now();
//									System.out.println("Enter Date(YYYY-mm-dd)");
//									sc.nextLine();
//									String date=sc.nextLine();
									String date=String.valueOf(today);
									System.out.println(date);
									ArrayList<StudentInfoModel> al=eventService.getEventWiseStudent(eventId);
									
									
									if(al.size()>0) {
										System.out.println("Registered Student ");
										for(StudentInfoModel sModel:al) {
											
											System.out.println("\nStudent Name : "+sModel.getId()+"  "+sModel.getName());
											System.out.println("\nSelect Option : \n0.absent    1.present");
											int status=sc.nextInt();
											b=eventService.eventAttendance(sModel.getId(), eventId,status,date);
											if(b)System.out.println("Attedance save sucess");
											else System.out.println("Attedance not save");
										}
										
									}
									else {
										System.out.println("NO Student For Event");
									}
									
								
								break;
								
							case 7:
								
								// event wise review
								
								eventService.showAllEvent();
								
								System.out.println("Select Event Id for view Review:");
								eventId=sc.nextInt();
								
								flag=false;
								// check enter event id is correct or not
								ArrayList<Integer> eID=eventService.getAllEventIds();  
								for(int id:eID) {
									if(id==eventId) {
										flag=true;
									}
								}
								
								if(!flag) {
									System.out.println("Enter Correct Event Id");
									break;
								}
								
								List<Object []> list=eventService.eventWiseReview(eventId);
								
								System.out.println("Rating\tFeedback");
								for(Object[] obj:list) {
//									obj[0]  -->it contain student id who review that event
									System.out.println(obj[1]+"\t"+obj[2]);
								}
								
								
								break;
						
								
							case 8:
								// it show all event 
								eventService.showAllEvent();
								
								System.out.println("Select Event Id for view Absent Student:");
								eventId=sc.nextInt();
								
								flag=false;
								// check enter event id is correct or not
								ArrayList<Integer> eID1=eventService.getAllEventIds();  
								for(int id:eID1) {
									if(id==eventId) {
										flag=true;
									}
								}
								
								if(!flag) {
									System.out.println("Enter Correct Event Id");
									break;
								}
								
								List<StudentInfoModel> list1=eventService.getAbsentStudentByEvent(eventId);
								if(list1.size()>0) {
									System.out.println("Student_Id\tStudent_Name\tStudent_Email");
									for(StudentInfoModel sModel:list1) {
										System.out.println(sModel.getId()+"\t"+sModel.getName()+"\t"+sModel.getEmail());
									}
								}
								else {
									System.out.println("No Absent Student...");
								}
								
								
								break;
							}
						}while(choice<=10); // end of admin login
						
					
					}
					else {
						System.out.println("Admin not found");
					}
				
					break;
					
				}
				
				System.out.println("================================================");
				System.out.println("################################################");
				
				break;
			
				
				
			case 2:
				
				// student login
				
				
				
				do {
					
					System.out.println("\n-----             STUDENT LOGIN              -----");
					System.out.println("------------------------------------------------");
					
					
					System.out.println("\n1.Login Student\n2.New Student Registration\n3.To Exit Student Menu\nSelect Option:");
					try {
						choice=sc.nextInt();
					}catch(Exception ex) {
						System.out.println("Enter digit Only");
						sc.next();
						choice=0;
					}
					
					
					switch(choice) {
					case 1:
						System.out.println("Enter Username as Email:");
						sc.nextLine();
						String user=sc.nextLine();
						System.out.println("enter Password as Contact:");
						String pass=sc.nextLine();
						
						int studentId=studentService.checkStudentLogin(user, pass);
						if(studentId!=0) {
							System.out.println("Student Login Successfully............");
							
							do {
								
								System.out.println("\n1.Register For Event\n2.Give Event Review\n3.View Details\n4.Update Contact\n5.Update Email\n6.To Exit Student Login\nSelect Option:");
								try {
									choice=sc.nextInt();
								}catch(Exception ex) {
									System.out.println("Enter digit Only");
									sc.next();
									choice=0;
								}
								
								switch(choice) {
								case 1:
									//view all events
									System.out.println("Upcoming Events:");
									eventService.showAllEvent();
									System.out.println("Enter Event Id For Registration:");
									int eventId=sc.nextInt();
									
									
									boolean flag=true;
									// check event id is correct or not
									ArrayList<Integer> eid=eventService.getAllEventIds();
									for(int id:eid) {
										if(id==eventId) {
											flag=false;
										}
									}
									if(flag) {
										System.out.println("Enter Correct Event Id");
										break;
									}
									
									int branchId=studentService.getBranchIdByStudentId(studentId);
									int orginizerId=eventService.getOrginizerBranchIdByEventId(eventId);
									
									if(branchId==orginizerId || orginizerId==7) {
										
										
										b=eventService.checkEventRegistration(studentId, eventId);
										if(!b) { // student already register so it return true so it not b
											b=eventService.eventRegistration(studentId, eventId);
											if(b)System.out.println("Successfully Register For Event.....");
											else System.out.println("Not Register For Event..");
										
										}
										
										else {
											System.out.println("You Already Register For this Event...");
										}
										
									}
								
									else {
										System.out.println("Event Is Not For Your Branch...");
									}
									
									
									break;
								
								case 2:
									//event review
									
									eventService.showAllEvent();
									System.out.println("Select Event Id for Review:");
									eventId=sc.nextInt();
									flag=false;
									ArrayList<Integer> eId=eventService.getAllEventIds();
									for(int id:eId) {
										if(id==eventId) {
											flag=true;
										}
									}
									// check enter event id is correct or not
									if(!flag) {
										System.out.println("Enter Correct Event Id");
										break;
									}
									
									if(eventService.checkPresentStudent(studentId, eventId)) {
										System.out.println("Enter Rating in Between 1 and 5 :");
										int rating=sc.nextInt();
										System.out.println("Give Feed back to this event:");
										sc.nextLine();
										String feedback=sc.nextLine();
										
										b=eventService.addEventReview(studentId, eventId, rating, feedback);
										if(b)System.out.println("Thank You For Your Review....");
										else System.out.println("Revies Not Save..");
									}
									else {
										System.out.println("You Not give review for this Event Becasuse You not Attended this Event");
									}
									
									System.out.println("\n==============================================");
									break;
									
								case 3:
									//view student details
									
									studentModel=studentService.getStudentInfoById(studentId);
									System.out.println("Student_Id\tStudent_Name\tContact\t\tEmail\t\tBranch");
								
									System.out.println(studentModel.getId()+"\t\t"+studentModel.getName()+"\t"+studentModel.getContact()+"\t"+studentModel.getEmail()+"\t"+studentModel.getDepartment());
								
									
									break;
								case 4:
									//update contact details
									
									System.out.println("Enter Old Contact Number: ");
									sc.nextLine();
									String contact=sc.nextLine();
									Long oldContact=eventService.verifyContact(contact);
									if(oldContact==0) {
										System.out.println("Enter 10 Digit");
										break;
									}else if(oldContact==-1){
										System.out.println("Enter only Digit");
										break;
									}
									
									System.out.println("Enter New Contact Number:");
//									Long newContact=sc.nextLong();
									
								    contact=sc.nextLine();
									Long newContact=eventService.verifyContact(contact);
									if(newContact==0) {
										System.out.println("Enter 10 Digit");
										break;
									}else if(newContact==-1){
										System.out.println("Enter only Digit");
										break;
									}
									
									b=studentService.updateStudentContact(oldContact, newContact);
									if(b)System.out.println("Contact Update Successfully....");
									else System.out.println("Contact Not Updated ....");
									
									
									break;
									
								case 5:
									
									System.out.println("Enter Old Email: ");
									sc.nextLine();
									String oldEmail=sc.nextLine();
									System.out.println("Enter New Email:");
									String newEmail=sc.nextLine();
									b=studentService.updateStudentEmail(oldEmail, newEmail);
									if(b)System.out.println("Email Update Successfully....");
									else System.out.println("No Record Founds....");
									
									break;
								}
								
							}while(choice<6);
							
							
							
						}
						else {
							System.out.println("Invalid Student Login...");
							System.out.println("Please Register Student First...");
						}
						break;
					case 2:
						
						System.out.println("\nenter Name");
						sc.nextLine();
						String name=sc.nextLine();
						System.out.println("Enter Contact:");
						String contact1=sc.nextLine();
						Long contact=eventService.verifyContact(contact1);
						if(contact==0) {
							System.out.println("Enter 10 Digit");
							break;
						}else if(contact==-1){
							System.out.println("Enter only Digit");
							break;
						}
						
						System.out.println("Enter Email :");
						String email=sc.nextLine();
						System.out.println("> Computer\n"
								+ "> Electronics \n"
								+ "> Mechanical\n"
								+ "> Civil\n"
								+ "> Electrical"+
								"\nSelect Branch Name :");
						String branch=sc.nextLine();
						System.out.println("Enter Pass Out Year: ");
						int student_year=sc.nextInt();
						
						studentModel.setName(name);
						studentModel.setContact(contact);
						studentModel.setDepartment(branch);
						studentModel.setEmail(email);
						studentModel.setPassoutYear(student_year);
						
						b=studentService.isAddStudent(studentModel);
					
						if(b) System.out.println("Student Added Successfully...");
						else System.out.println("Student Not Added...");
						
						
						break;
					}
					
				}while(choice<3);
				
				
				
				
				System.out.println("================================================");
				System.out.println("################################################");
				
				
					
				break;
			
				
			}
			
		
			
		}while(true);
		
		
	}
	
	
}
