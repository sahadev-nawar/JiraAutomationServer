package com.application.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.service.JiraService;
import com.application.util.FileConvertUtil;




@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	JiraService jiraService;
	
	@Autowired
	FileConvertUtil util;
	
	 @RequestMapping(value="/createJira")
	 public String createJira(@RequestBody String inputJson)  {		 
	  return jiraService.createJiraNew(inputJson);
	 }
	 
	 @RequestMapping("/convert")
	 public String convert() throws IOException
	 {
		 util.convertCSVToJson();
		 return "done";
	 }
	 
	 @RequestMapping("/hello")
	 public String sayHi(@RequestBody String a) 
	 {		
		 return a;
	 }
	 
 
 
}