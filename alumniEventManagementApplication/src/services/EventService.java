package services;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import model.EventModel;
import model.StudentInfoModel;
import repository.EventsRepository;

public class EventService {

	EventsRepository eventRepo=new EventsRepository();
	
	
	public boolean isAddNewEvent(EventModel eModel) {
		return eventRepo.isAddNewEvent(eModel);
	}
	
	// date varification
	public boolean isEventDateCheck(String edate) {
		
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        
		
		try {
            LocalDate inputDate = LocalDate.parse(edate, formatter);
            LocalDate today = LocalDate.now();
            
            if (inputDate.isAfter(today)) {
                return true;
            } else {
                System.out.println("The input date is not greater than today's date.");
                return false;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }
		
	}
	
	public void showAllEvent() {
		ArrayList<EventModel> al=eventRepo.showAllEventDetails();
		if(al!=null) {
			
			System.out.println("\nEvent_ID\tEvent_Name\tEvent_Date\tOrganizer");
		
			for(EventModel emodel:al) {
				System.out.println(emodel.getEventId()+"\t\t"+emodel.getEventName()+"\t"+emodel.getDate()+"\t"+emodel.getOrg_Branch());
			}
		}
		else {
			System.out.println("Event Data not present");
		}
	}
	
	public void showUpcomingEvent() {
		
	}
	
	
	public boolean eventRegistration(int studentId,int eventId) {
		return eventRepo.eventRegistration(studentId, eventId);
	}
	
	public ArrayList<StudentInfoModel> getEventWiseStudent(int eventId){
		return eventRepo.getEventWiseStudent(eventId);
	}
	
	public boolean eventAttendance(int studentId ,int eventId,int status,String date) {
		return eventRepo.eventAttendance(studentId, eventId, status, date);
	}
	
	public boolean checkPresentStudent(int studentId,int eventId) {
		return eventRepo.checkPresentStudent(studentId, eventId);
	}
	
	public boolean addEventReview(int studentId,int eventId,int rating,String feedback) {
		return eventRepo.addEventReview(studentId, eventId, rating, feedback);
	}
	
	public List<Object[]> eventWiseReview(int eventId) {
		return eventRepo.eventWiseReview(eventId);
	}
	
	public boolean checkEventRegistration(int studentId, int eventId) {
		return eventRepo.checkEventRegistration(studentId, eventId);
	}
	
	
	public ArrayList<Integer> getAllEventIds(){
		return eventRepo.getAllEventIds();
	}
	
	
	public Long verifyContact(String contact1) {
		
		if(contact1.length()==10) {
			try {
				Long contact=Long.parseLong(contact1);
				return contact;
			}
			catch(Exception ex) {
				return (long) -1;
			}
		}
		else
			return (long)0;
		
		
		
	}
	
	// get absent student data
	public List<StudentInfoModel> getAbsentStudentByEvent(int eventId){
		return eventRepo.getAbsentStudentByEvent(eventId);
	}
	
	// get orginizer branch id by event id
	public int getOrginizerBranchIdByEventId(int eventId) {
		return eventRepo.getOrginizerBranchIdByEventId(eventId);
	}
	
}
