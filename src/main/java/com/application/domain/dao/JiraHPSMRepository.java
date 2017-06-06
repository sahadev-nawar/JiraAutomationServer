package com.application.domain.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JiraHPSMRepository {
	
//	 @Autowired
//	 private JdbcTemplate jdbcTemplate;
	 
	 
	 public int addData(String hpsm, String jira) {

//	       return  jdbcTemplate.update("INSERT INTO jirahpsmdetails(hpsm_ID, jira_ID, created_date) VALUES (?,?,?)",
//	        		hpsm, jira, new Date());
		 return 1;
	    }

}
