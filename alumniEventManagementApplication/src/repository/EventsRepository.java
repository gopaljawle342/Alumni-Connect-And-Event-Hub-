package repository;

import java.util.ArrayList;
import java.util.List;

import config.DBHelper;
import model.EventModel;
import model.StudentInfoModel;

public class EventsRepository extends DBHelper {

	public boolean isAddNewEvent(EventModel eModel) {
		
		try {
			stmt=conn.prepareStatement("insert into alumnievent values('0',?,?,?)");
//			stmt.setInt(1, 2);
			stmt.setString(1, eModel.getEventName());
			stmt.setString(2, eModel.getDate());
			stmt.setInt(3, eModel.getOrg_BranchId());
			
			int value=stmt.executeUpdate();
	
			return value>0?true:false;
		}
		catch(Exception ex) {
			System.out.println("ia add event error "+ex);
			return false;
		}
		
		
	}
	// show all events
	public ArrayList<EventModel> showAllEventDetails(){
		ArrayList<EventModel> al=new ArrayList<EventModel>();
		try {
			
			stmt=conn.prepareStatement("select e.event_Id,e.event_Name,e.event_date,b.branch_name from alumnievent e inner join alumnibranch b on e.org_branch_id=b.branch_id;");
			rs=stmt.executeQuery();
			
			
			while(rs.next()) {
				EventModel eModel=new EventModel();
				eModel.setEventId(rs.getInt(1));
				eModel.setEventName(rs.getString(2));
				eModel.setDate(rs.getString(3));
				eModel.setOrg_Branch(rs.getString(4));
				al.add(eModel);
			}
			return al;
			
		}
		catch(Exception ex) {
			System.out.println("show all event error "+ex);
			return null;
		}
	}
	
	// student register for event
	public boolean eventRegistration(int studentId,int eventId) {
		try {
			
			stmt=conn.prepareStatement("insert into eventregistration values('0',?,?)");
//			stmt.setInt(1, studentId+1);
			stmt.setInt(1, studentId);
			stmt.setInt(2, eventId);
			
			int value=stmt.executeUpdate();
			
			return value>0?true:false;
			
		}catch(Exception ex) {
			System.out.println("event Registration error "+ex);
			return false;
		}
		
	}
	

	public ArrayList<StudentInfoModel> getEventWiseStudent(int eventId){
		
		ArrayList<StudentInfoModel> al=new ArrayList<StudentInfoModel>();
		
		try {
			stmt=conn.prepareStatement(" select s.student_id,s.student_name from student s inner join eventregistration er on s.student_id=er.student_id inner join eventattendance ea on s.student_id=ea.student_id where ea.event_Id=? and ea.status=0");
			stmt.setInt(1, eventId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				StudentInfoModel studentModel=new StudentInfoModel();
				studentModel.setId(rs.getInt(1));
				studentModel.setName(rs.getString(2));
				al.add(studentModel);
			}
			return al;
			
		}
		catch(Exception ex) {
			System.out.println("get event wise student Error "+ex);
			return null;
		}
		
	}
	
	//event attendance
	public boolean eventAttendance(int studentId ,int eventId,int status,String date) {
		
		try {
			stmt=conn.prepareStatement("update  eventattendance set status=?,adate=? where student_id=? and event_Id=?");
			stmt.setInt(3, studentId);
			stmt.setInt(4, eventId);
			stmt.setInt(1, status);
			stmt.setString(2, date);
			int value=stmt.executeUpdate();
			
			return value>0?true:false;
			
		}
		catch(Exception ex) {
			System.out.println("error event attendance "+ex);
			return false;
		}
		
	}
	
	
	
	public boolean checkPresentStudent(int studentId,int eventId) {
		
		try {
			stmt=conn.prepareStatement("select * from eventattendance where student_id=? and event_Id=? and status=1");
			stmt.setInt(1, studentId);
			stmt.setInt(2, eventId);
			
			rs=stmt.executeQuery();
			if(rs.next())return true;
			else return false;
			
			
			
			
		}
		catch(Exception ex) {
			System.out.println("check present student error "+ex);
			return false;
		}
		
		
	}
	
	// add event review
	public boolean addEventReview(int studentId,int eventId,int rating,String feedback) {
		
		try {
			stmt=conn.prepareStatement("insert into eventreview values('0',?,?,?,?)");
			stmt.setInt(1, studentId);
			stmt.setInt(2, eventId);
			stmt.setInt(3, rating);
			stmt.setString(4, feedback);
			
			int value=stmt.executeUpdate();
			
			return value>0?true:false;
			
		}
		catch(Exception ex) {
			System.out.println("add event review error "+ex);
			return false;
		}
		
	}
	
	// event wise review   
	public List<Object[]> eventWiseReview(int eventId) {
		
		 List<Object[]> list=new  ArrayList<Object[]>();
		
		
		try {
			stmt=conn.prepareStatement("select *from eventreview where event_Id=?");
			stmt.setInt(1,eventId );
			
			rs=stmt.executeQuery();
			while(rs.next()) {
				Object obj[] = new Object[] {
					
						rs.getInt(2), //student id
						rs.getInt(4), // rating 
						rs.getString(5) //feedback
					
				};
				list.add(obj);
				
			}
		 return list;
			
		}
		catch(Exception ex) {
			System.out.println("event wise review error "+ex);
			return null;
		}
	
	}
	
	
	// check student register for specific event or not
	public boolean checkEventRegistration(int studentId, int eventId) {
		try {
			
			stmt=conn.prepareStatement("select *from eventregistration where student_id=? and event_Id=?");
			stmt.setInt(1, studentId);
			stmt.setInt(2, eventId);
			rs=stmt.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
			
		}
		catch(Exception ex) {
			System.out.println("check event registration error "+ex);
			return false;
		}
	}
	
	
	public ArrayList<Integer> getAllEventIds(){
		ArrayList<Integer> al=new ArrayList<Integer>();
		try {
			stmt=conn.prepareStatement("select event_Id from alumnievent");
			rs=stmt.executeQuery();
			while(rs.next()) {
				al.add(rs.getInt(1));
			}
			return al;
		}
		catch(Exception ex) {
			System.out.println("get all event id error "+ex);
			return null;
		}
		
	}
	
	
	public List<StudentInfoModel> getAbsentStudentByEvent(int eventId){
		
		List<StudentInfoModel> list=new ArrayList<StudentInfoModel>();
		
		try {
			stmt=conn.prepareStatement(" select s.student_id,s.student_name,s.email from student s inner join eventattendance et on s.student_id=et.student_id where et.status=0 and et.event_id=?");
			stmt.setInt(1, eventId);
			
			rs=stmt.executeQuery();
			while(rs.next()) {
				StudentInfoModel sModel=new StudentInfoModel();
				sModel.setId(rs.getInt(1));
				sModel.setName(rs.getString(2));
				sModel.setEmail(rs.getString(3));
				
				list.add(sModel);
			}
			return list;
		
		
		}
		catch(Exception ex) {
			System.out.println("get absent student error "+ex);
			return null;
		}
		
	}
	
	public int getOrginizerBranchIdByEventId(int eventId) {
		
		try {
			
			stmt=conn.prepareStatement("select org_branch_id from alumnievent where event_id=?");
			stmt.setInt(1, eventId);
			
			rs=stmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			else {
				return 0;
			}
			
		}
		catch(Exception ex) {
			System.out.println("get orginizer branch id error "+ex);
			return -1;
		}
		
	}
	
}










