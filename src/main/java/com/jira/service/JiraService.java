package com.jira.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

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
import org.springframework.stereotype.Component;

@Component
public class JiraService {
	
	public String createJira()
	{
		StringBuffer sb=new StringBuffer("");
	
		try{

			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			httpClient.getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
	        NTCredentials creds = new NTCredentials("m71809", "qwer123$","W7D13390","ACCDOM01");
	        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
	        
			HttpPost httpPost = new HttpPost("https://daredkar.atlassian.net/rest/api/2/issue");
	
			StringEntity input = new StringEntity("{    \"fields\": {       \"project\":       {           \"key\": \"HPSMTOJ\"       },       \"summary\": \"SUPPORT-5168 CC-93 - 6\",       \"description\": \"six testing on dev by issuetypeid\",       \"issuetype\": {          \"name\": \"Bug\",          \"id\": \"10000\"       },       \"customfield_10002\": \"admin\",       \"components\": [            {                \"name\": \"Kasia2\"            }        ],        \"assignee\": {            \"name\": \"admin\"        },        \"priority\": {            \"id\": \"3\"        }   }}");
			input.setContentType("application/json");
			httpPost.setEntity(input);
			
			httpPost.addHeader("X-Atlassian-Token","no-check");
			httpPost.addHeader("Authorization","Basic YWRtaW46U2hhbWlrYUAxMTEy");		
		
			 HttpParams httpParameters = new BasicHttpParams();           
	        HttpHost proxy = new HttpHost("localhost",5865);	        
	         httpParameters.setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
	            
	         httpClient.setParams(httpParameters);
	
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
	
}
