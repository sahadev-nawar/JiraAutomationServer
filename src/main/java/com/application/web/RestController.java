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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.service.JiraService;
import com.application.util.FileConvertUtil;

//@CrossOrigin("http://localhost:4200")
@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	JiraService jiraService;
	
	@Autowired
	FileConvertUtil util;		
	 
	
	 @RequestMapping(value="/createJira",method=RequestMethod.POST)
	 public String createJira(@RequestBody String inputJson)  {		 
	  return jiraService.createJiraNew(inputJson);	 
	 }
	 
	
	 @RequestMapping(value="/convert",method=RequestMethod.POST,produces = "application/json")
	 public String convert(@RequestBody MultipartFile file) throws IOException
	 {	 
		 return util.convertCSVToJson(file);
	 }
	 
	 @RequestMapping(value="/hello")
	 public String sayHi(@RequestBody String a) 
	 {		
		 return a;
	 }
	
	 @RequestMapping(value="/DBCheck")
	 public int dbCheck() 
	 {		
		 return jiraService.addData();
	 }
	 

	 @RequestMapping(value="/attachment/{key}",method=RequestMethod.POST,produces = "application/json")
	 public String uploadAttachment(@RequestBody MultipartFile file, @PathVariable("key") String key) throws IOException
	 {	 
		 return jiraService.uploadAttachment(key, file);
	 }
	 
	 
 
 
}