package com.cobatte.taxi;

public class RoomInfo {
	String admin;
	String roomname;
	String place;
	String hour;
	String min;
	String person;
	
	public RoomInfo() {
		admin = null;
		roomname = null;
		place = null;
		hour = null;
		min = null;
		person = null;
	}	// default constructor
	
	public RoomInfo(String _admin, String _roomname, String _place,
						String _hour, String _min, String _person) {
		admin = _admin;
		roomname = _roomname;
		place = _place;
		hour = _hour;
		min = _min;
		person = _person;
	}
	
	public String getAdmin() {
		return admin;
	}
	
	public String getRoomname() {
		return roomname;
	}
	
	public String getPlace() {
		return place;
	}
	
	public String getHour() {
		return hour;
	}
	
	public String getMin() {
		return min;
	}
	
	public String getPerson() {
		return person;
	}
	
	public void setAdmin(String _admin) {
		admin = _admin;
	}
	
	public void setRoomname(String _roomname) {
		roomname = _roomname;
	}
	
	public void setPlace(String _place) {
		place = _place;
	}
	
	public void setHour(String _hour) {
		hour = _hour;
	}
	
	public void setMin(String _min) {
		min = _min;
	}
	
	public void setPerson(String _person) {
		person = _person;
	}
}
