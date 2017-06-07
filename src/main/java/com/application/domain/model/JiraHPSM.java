package com.application.domain.model;

import java.util.Date;

public class JiraHPSM {
	
	
	private int id;
	private String hpsm_ID;
	private String jira_ID;
	private Date created_date;
	
	public JiraHPSM(int id, String hpsm_ID, String jira_ID, Date created_date) {
		super();
		this.id = id;
		this.hpsm_ID = hpsm_ID;
		this.jira_ID = jira_ID;
		this.created_date = created_date;
	}

	public String getHpsm_ID() {
		return hpsm_ID;
	}

	public void setHpsm_ID(String hpsm_ID) {
		this.hpsm_ID = hpsm_ID;
	}

	public String getJira_ID() {
		return jira_ID;
	}

	public void setJira_ID(String jira_ID) {
		this.jira_ID = jira_ID;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	
	
	
	
	
	
	

}
