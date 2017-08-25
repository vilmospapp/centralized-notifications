package com.liferay.hackday.notification.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.liferay.hackday.notification.Message;
import com.liferay.hackday.notification.Notification;
import com.liferay.hackday.notification.PushMessage;
import com.liferay.hackday.notification.exceptions.NoNotificationIsSpecifiedException;
import com.liferay.hackday.notification.exceptions.NoRecipientsException;
import org.springframework.scheduling.annotation.Scheduled;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonParser;

@Controller
@EnableAutoConfiguration
@EnableScheduling
public class NotificationController {

    public NotificationController() {
    	try {
			sendAllNotifications();
		} catch (NoNotificationIsSpecifiedException | NoRecipientsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationController.class, args);
    }

    @RequestMapping("/")
    public ModelAndView hello() {
        return new ModelAndView("layout");
    }

    @Scheduled(fixedRate = 5000)
    protected void sendAllNotifications()
    		throws NoNotificationIsSpecifiedException, NoRecipientsException {

    		System.out.println("Send all");
    		HttpResponse response = HttpRequest.get("https://notification-b5ed.restdb.io/rest/notificationmessage").header(
    			"content-type", "application/json").header(
    				"x-apikey", "8dfeb0254fcfc5cc21cd11b92950f89f85022").send();

    		JsonParser parser = JsonParser.create();
    		parser.looseMode(true);

    		List<HashMap> messages = parser.parse(response.body(), List.class);

    		for (HashMap<String, String> m : messages) {
    			Message msg = getMessage(m);

    			System.out.println(msg);
    			Notification notification = new Notification();
    			notification.setBody(msg.getMessage());
    			notification.setIcon("");
    			notification.setTitle("Hackday");
    			notification.setClickAction("https://web.liferay.com");

    			PushMessage push = new PushMessage();
    			push.addRecipient(msg.getRecipient());
    			push.setNotification(notification);

    			push.sendMessage();
    		}
    	}

    	protected Message getMessage(Map<String, String> map) {
    		Message message = new Message();
    		message.setMessage(map.get("message"));
    		message.setId(map.get("_id"));
    		message.setMessageId(String.valueOf(map.get("messageId")));
    		message.setRecipient(map.get("recipient"));
    		message.setSentAt(map.get("sentAt"));

    		return message;
    	}
}