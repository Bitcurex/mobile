package com.programmerpeter.android.bitcurex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	public static JSONArray getJSONArray(String u) {
		String json = null;
		URL url = null;
		JSONArray object = null;
		try {
			url = new URL(u);
		} catch (MalformedURLException e4) {
			e4.printStackTrace();
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e3) {
			e3.printStackTrace();
		};
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		json = sb.toString();
		try {
			object = new JSONArray(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static JSONObject getJSONObject(String u) {
		String json = null;
		URL url = null;
		JSONObject object = null;
		try {
			url = new URL(u);
		} catch (MalformedURLException e4) {
			e4.printStackTrace();
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e3) {
			e3.printStackTrace();
		};
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		json = sb.toString();
		try {
			object = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static JSONObject postJSONObject(JSONObject holder, String u) 
	{
	    DefaultHttpClient httpclient = new DefaultHttpClient();
	    HttpPost httpost = new HttpPost(u);
	    StringEntity se = null;
		try {
			se = new StringEntity(holder.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		se.setContentEncoding("UTF-8");
	    se.setContentType("application/json");
	    httpost.setEntity(se);
	    httpost.setHeader("accept", "application/json");
	    httpost.setHeader("content-type", "application/json");
	    String response = null;
	    try {
			response = convertStreamToString(httpclient.execute(httpost).getEntity().getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			return new JSONObject(response);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	static class HttpDelete extends HttpPost{
	    public HttpDelete(String url){
	        super(url);
	    }
	    @Override
	    public String getMethod() {
	        return "DELETE";
	    }
	}
	
	public static JSONObject deleteJSONObject(JSONObject holder, String u) 
	{
	    DefaultHttpClient httpclient = new DefaultHttpClient();
	    HttpDelete httpdelete = new HttpDelete(u);
	    StringEntity se = null;
		try {
			se = new StringEntity(holder.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		se.setContentEncoding("UTF-8");
	    se.setContentType("application/json");
	    httpdelete.setEntity(se);
	    httpdelete.setHeader("accept", "application/json");
	    httpdelete.setHeader("content-type", "application/json");
	    String response = null;
	    try {
			response = convertStreamToString(httpclient.execute(httpdelete).getEntity().getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			return new JSONObject(response);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8192);
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append((line + "\n"));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
}