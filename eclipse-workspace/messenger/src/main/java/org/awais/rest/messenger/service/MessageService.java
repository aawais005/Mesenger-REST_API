package org.awais.rest.messenger.service;

import org.awais.rest.messenger.database.DatabaseClass;
import org.awais.rest.messenger.exception.DataNotFoundException;
import org.awais.rest.messenger.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

public class MessageService {
	
	private static Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1, "Hello World", "Awais"));
		messages.put(2L, new Message(2, "Hello Jersey", "Awais"));
	}
	
	public List<Message> getAllMessages() {
		return  new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> listForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message: messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				listForYear.add(message);
			}
		}
		return listForYear;
	}
	
	public List<Message> getAllMessagesPag(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start+size >= list.size()) return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null) {
			throw new DataNotFoundException("Message with id "+id +" not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);	
	}
}
