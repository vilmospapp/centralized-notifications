package com.liferay.hackday.notification;

public class Message {

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public String getMessageId() {
		return _messageId;
	}
	public String getMessage() {
		return _message;
	}
	public String getRecipient() {
		return _recipient;
	}
	public String getSentAt() {
		return _sentAt;
	}
	public boolean isSent() {
		return _sent;
	}
	public void setMessageId(String messageId) {
		_messageId = messageId;
	}
	public void setMessage(String message) {
		_message = message;
	}
	public void setRecipient(String recipient) {
		_recipient = recipient;
	}
	public void setSentAt(String sentAt) {
		_sentAt = sentAt;
	}
	public void setSent(boolean sent) {
		_sent = sent;
	}

	
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		_id = id;
	}

	
	@Override
	public String toString() {
		return "Message [" + (_id != null ? "_id=" + _id + ", " : "")
				+ (_messageId != null ? "_messageId=" + _messageId + ", " : "")
				+ (_message != null ? "_message=" + _message + ", " : "")
				+ (_recipient != null ? "_recipient=" + _recipient + ", " : "")
				+ (_sentAt != null ? "_sentAt=" + _sentAt + ", " : "") + "_sent=" + _sent + "]";
	}


	private String _id;
	private String _messageId;
	private String _message;
	private String _recipient;
	private String _sentAt;
	private boolean _sent;
}
