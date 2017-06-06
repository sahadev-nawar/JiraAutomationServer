package com.application.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.GlobalProperties;
import com.application.domain.dao.JiraHPSMRepository;

@Service
public class JiraService {
	
	@Autowired
	GlobalProperties gb;
	
	@Autowired
	JiraHPSMRepository jiraRepo;
	
	@SuppressWarnings("deprecation")
	public String createJiraNew(String inputJson)
	{
		StringBuffer sb=new StringBuffer("");
	
		try{

			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			// if dev.env is set to true in properties files, then use ntlm else not 
			if(gb.getDevEnv().equals("true"))
			{
				System.out.println("applying ntlm .");
			httpClient.getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
	        NTCredentials creds = new NTCredentials("m71809", "qwer123$","W7D13390","ACCDOM01");
	        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
	        HttpParams httpParameters = new BasicHttpParams();           
	        HttpHost proxy = new HttpHost("localhost",3128);	        
	        httpParameters.setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
	        httpClient.setParams(httpParameters);
			}
			HttpPost httpPost = new HttpPost("https://daredkar.atlassian.net/rest/api/2/issue");
	
			StringEntity input = new StringEntity(inputJson);
			input.setContentType("application/json");
			httpPost.setEntity(input);			
			httpPost.addHeader("X-Atlassian-Token","no-check");
			httpPost.addHeader("Authorization","Basic YWRtaW46YWRtaW5hZG1pbmFkbWlu");			
	
			HttpResponse response = httpClient.execute(httpPost);
	
			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
	
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
	
				System.out.println(output);
				sb.append(output);
			}
	
			httpClient.getConnectionManager().shutdown();
			
	
		} catch (MalformedURLException e) {
	
	                 e.printStackTrace();
	 } catch (IOException e) {
	
	                 e.printStackTrace();
	
		}

		return sb.toString();
	}

	public int addData() {
		
		return jiraRepo.addData("hpsm1","jira1");
		
	}

	@SuppressWarnings("deprecation")
	public String uploadAttachment(String issueKey, MultipartFile file) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		StringBuffer sb=new StringBuffer("");
		try {
			
			if(gb.getDevEnv().equals("true"))
			{
				System.out.println("applying ntlm .");
			httpClient.getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
	        NTCredentials creds = new NTCredentials("m71809", "qwer123$","W7D13390","ACCDOM01");
	        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
	        HttpParams httpParameters = new BasicHttpParams();           
	        HttpHost proxy = new HttpHost("localhost",3128);	        
	        httpParameters.setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
	        httpClient.setParams(httpParameters);
			}
			HttpPost httpPost = new HttpPost("https://daredkar.atlassian.net/rest/api/2/issue/"+issueKey+"/attachments");
			
			
			httpPost.addHeader("X-Atlassian-Token","no-check");
			httpPost.addHeader("Authorization","Basic YWRtaW46YWRtaW5hZG1pbmFkbWlu");
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			File fileToUpload = multipartToFile(file);
			FileBody fileBody = new FileBody(fileToUpload, "multipart/form-data");
			entity.addPart("file", fileBody);
			
			httpPost.setEntity(entity);
			HttpResponse response = null;
			response = httpClient.execute(httpPost);
			
			if(response.getStatusLine().getStatusCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			//HttpEntity result = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			System.out.println("Output from server : "+response);
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
	
				System.out.println(output);
				sb.append(output);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			httpClient.close();
		}
		return sb.toString();
	}
	private  File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
		File convFile = new File("c:\\" + multipart.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipart.getBytes());
		fos.close();
		return convFile;
	    
	}
	
}
