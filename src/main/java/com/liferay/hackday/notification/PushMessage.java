package com.liferay.hackday.notification;

import java.util.HashSet;
import java.util.Set;

import com.liferay.hackday.notification.exceptions.NoNotificationIsSpecifiedException;
import com.liferay.hackday.notification.exceptions.NoRecipientsException;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonSerializer;
import jodd.log.Logger;
import jodd.log.LoggerFactory;
import jodd.util.StringBand;

public class PushMessage {

//	public static void main(String [] args) {
//		Notification notification = new Notification();
//		notification.setBody("Hello From JAVA");
//		notification.setClickAction("http://index.hu");
//		notification.setIcon("https://avatars3.githubusercontent.com/u/861105");
//		notification.setTitle("Sample Notification");
//
//		PushMessage pm = new PushMessage("AAAAnYh9W3k:APA91bFt_FZNfloMgi1KkVJxEq6rJxXs2idmWOcjKVOO4Nx0B5GkfkVXC11-PHp9CQaKmAd-zJq7ArTubmtkNsjoFdujtt6FlCxqAx67bZ2aFuCn6ADkrrG2EtJN83PTzlru5ewJYsSK");
//
//		pm.setNotification(notification);
//		pm.addRecipient("dybx-j-Fr7U:APA91bHqdD3vd1D2w0UtvqnXe2PB-YyuVHfaHcfj69GNrqtGmnbL1Ax_UqgGCXQCNDaSdia4Ea5OCi9npd1UzMqzyCmNsudBlB5-jMJMkAVexb1Tt8HT5SnleeTGH5OgdVUYfExtvZ8S");
//		pm.addRecipient("c5ATq8-B714:APA91bGGMLstNec0t6f9fQ75GrKXlAHaOJTp-BK54sxX-NY7TKktEzaJWiLacA-8eEc1IukKymmmyg5UqmDx4PhWg-XWylk7ypIbljuH6R5CdXKvIWGQwEzwCq9Xkp5iUjzAmj0xxb7b");
//
//		try {
//			pm.sendMessage();
//		} catch (NoNotificationIsSpecifiedException | NoRecipientsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public PushMessage() {
		_apiKey = "AAAAnYh9W3k:APA91bFt_FZNfloMgi1KkVJxEq6rJxXs2idmWOcjKVOO4Nx0B5GkfkVXC11-PHp9CQaKmAd-zJq7ArTubmtkNsjoFdujtt6FlCxqAx67bZ2aFuCn6ADkrrG2EtJN83PTzlru5ewJYsSK";
	}
	public PushMessage(String apiKey) {
		_apiKey = apiKey;
	}

	public PushMessage(
		String apiKey, String recipient, Notification notification) {

		_apiKey = apiKey;
		_notification = notification;
		addRecipient(recipient);
	}

	public PushMessage(
		String apiKey, Set<String> recipients, Notification notification) {

		_apiKey = apiKey;
		_notification = notification;
		_recipients = recipients;
	}

	public void addRecipient(String recipient) {
		if (_recipients == null) {
			_recipients = new HashSet<>();
		}

		_recipients.add(recipient);
	}
	public String getApiKey() {
		return _apiKey;
	}

	public Notification getNotification() {
		return _notification;
	}

	public Set<String> getRecipients() {
		return _recipients;
	}

	public void sendMessage() throws
		NoNotificationIsSpecifiedException, NoRecipientsException {

		if ((_recipients == null) || (_recipients.size() == 0)) {
			throw new NoRecipientsException();
		}

		if (_notification == null) {
			throw new NoNotificationIsSpecifiedException();
		}

		JsonSerializer serializer = JsonSerializer.create();

		String message = serializer.serialize(_notification);
		for (String recipient : getRecipients()) {
			StringBand sbMessage = new StringBand(3);
			sbMessage.append("{\"notification\":");
			sbMessage.append(message);
			sbMessage.append(", \"to\":\"");
			sbMessage.append(recipient + "\"}");

			HttpResponse response = HttpRequest.post(ENDPOINT).header(
				"content-type", "application/json").header(
					"Authorization", "key=" + getApiKey()).body(
						sbMessage.toString()).send();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Server Response for: " + sbMessage.toString() + " is: " +
						response.body());
			}
		}
	}

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public void setNotification(Notification notification) {
		_notification = notification;
	}

	public void setRecipients(Set<String> recipients) {
		_recipients = recipients;
	}

	public static final String ENDPOINT = "https://fcm.googleapis.com/fcm/send";

	private static final Logger _log = LoggerFactory.getLogger(
		PushMessage.class);

	private String _apiKey;
	private Notification _notification;
	private Set<String> _recipients;

}
