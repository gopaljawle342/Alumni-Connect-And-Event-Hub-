package model;



public class EventModel {

	private int eventId;
	private String eventName;
	private String date;
	private String orgBranch;
	private int orgBranchId;
	
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOrg_Branch() {
		return orgBranch;
	}
	public void setOrg_Branch(String orgBranch) {
		this.orgBranch = orgBranch;
	}
	
	public int getOrg_BranchId() {
		return orgBranchId;
	}
	public void setOrg_BranchId(int orgBranchId) {
		this.orgBranchId = orgBranchId;
	}
	
	
}
