package models;

import java.util.HashMap;

public class services {
	private HashMap<String, String> users = new HashMap<String, String>();
	
	public services() {
		users.put("ramya", "12345");
		
	}
	
	public boolean login(String username, String password) {
		
		if(username == null || password == null) {
			return false;
		}
		else {
			return true;
		}
		
	}

}
