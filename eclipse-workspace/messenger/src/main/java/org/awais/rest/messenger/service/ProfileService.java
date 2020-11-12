package org.awais.rest.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.awais.rest.messenger.database.DatabaseClass;
import org.awais.rest.messenger.model.Profile;

public class ProfileService {
	private static Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("awais", new Profile(1L, "Awais", "Ahmed", "awais"));
		profiles.put("waleed", new Profile(2L, "Waleed", "Malik", "waleed"));
	}
	
	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
	
}
