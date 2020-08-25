package com.custom.spring.db.model;


public class ActionObjForWrapper {
	private String action;
	private boolean hideRdy;
	
	public ActionObjForWrapper() {
	}
	
	public ActionObjForWrapper(String act, boolean hide) {
		this.action = act;
		this.hideRdy = hide;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String setAction) {
		this.action = setAction;
	}
	public boolean isHideRdy() {
		return hideRdy;
	}
	public void setHideRdy(boolean hideRdy) {
		this.hideRdy = hideRdy;
	}
}
