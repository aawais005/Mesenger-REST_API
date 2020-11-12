package org.awais.rest.messenger.database;

import org.awais.rest.messenger.model.Message;
import org.awais.rest.messenger.model.Profile;

import java.util.HashMap;
import java.util.Map;

public class DatabaseClass {
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
	
}
