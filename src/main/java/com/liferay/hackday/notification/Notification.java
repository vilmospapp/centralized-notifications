package com.liferay.hackday.notification;

import jodd.json.meta.JSON;

public class Notification {

	public Notification() {
	}

	public String getBody() {
		return _body;
	}

	@JSON(name="click_action")
	public String getClickAction() {
		return _clickAction;
	}
	public String getIcon() {
		return _icon;
	}
	public String getTitle() {
		return _title;
	}
	public void setBody(String _body) {
		this._body = _body;
	}
	public void setClickAction(String _clickAction) {
		this._clickAction = _clickAction;
	}
	public void setIcon(String _icon) {
		this._icon = _icon;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}

	private String _body;
	
	private String _clickAction;
	private String _icon;
	private String _title;

}
