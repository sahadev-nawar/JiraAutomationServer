package com.application.service;

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
import org.springframework.stereotype.Service;

@Service
public class JiraService {
	
	
	@SuppressWarnings("deprecation")
	public String createJiraNew(String inputJson)
	{
		StringBuffer sb=new StringBuffer("");
	
		try{

			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			httpClient.getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
	        NTCredentials creds = new NTCredentials("m71809", "qwer123$","W7D13390","ACCDOM01");
	        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
	        HttpParams httpParameters = new BasicHttpParams();           
	        HttpHost proxy = new HttpHost("localhost",3128);	        
	        httpParameters.setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
	        httpClient.setParams(httpParameters);
	        
			HttpPost httpPost = new HttpPost("https://daredkar.atlassian.net/rest/api/2/issue");
	
			StringEntity input = new StringEntity(inputJson);
			input.setContentType("application/json");
			httpPost.setEntity(input);			
			httpPost.addHeader("X-Atlassian-Token","no-check");
			httpPost.addHeader("Authorization","Basic YWRtaW46YWRtaW4=");			
	
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
