package com.cineplex.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.thoughtworks.xstream.XStream;

//public class AutoSyncTheThread extends Thread implements Runnable {
public class AutoSyncTheThread extends Thread  {
	//public static final String IP="180.253.117.68";
	public static final String IP="192.168.43.120";
	//public static final String IP="192.168.81.104";
	//public static final String IP="192.168.1.7";
	//public static final String IP="10.0.2.2";
	//public static final String IP="192.168.177.104";
    public static final String URL ="http://"+IP+":8084/21WebApp/faces/action.jsp";
    public static final String URLimg="http://"+IP+":8084/21WebApp/faces/img/";
//	public static final String URL ="http://"+IP+":8080/21WebApp/faces/action.jsp";
//  public static final String URLimg="http://"+IP+":8080/21WebApp/faces/img/";
    @Override
    public void run(){
    	
    }
    public Object fetch(String url)
    {        
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpRequestBase httpRequest = null;
    	HttpResponse httpResponse = null;
    	InputStream inputStream = null;
    	String response = "";
    	StringBuffer buffer = new StringBuffer();
    	httpRequest = new HttpGet(URL+url);
    	
    	try
    	{
    		httpResponse = httpclient.execute(httpRequest);
    	}
    	catch (ClientProtocolException el)
    	{
    		el.printStackTrace();
    	}
    	catch (IOException el)
    	{
    		el.printStackTrace();
    	}
    	
    	try
    	{
    		inputStream = httpResponse.getEntity().getContent();
    	}
    	catch (IllegalStateException el)
    	{
    		el.printStackTrace();
    	}
    	catch (IOException el)
    	{
    		el.printStackTrace();
    	}
    	
    	byte[] data = new byte[512];
    	int len = 0;
    	
    	try
    	{
    		while(-1 != (len = inputStream.read(data)))
    		{
    			buffer.append(new String(data,0,len));
    		}
    	}
    	catch (IOException el)
    	{
    		el.printStackTrace();
    	}
    	
    	try
    	{
    		inputStream.close();
    	}
    	catch (IOException el)
    	{
    		el.printStackTrace();
    	}
    	
    	response = buffer.toString();
    	XStream xstream= new XStream();
    	return xstream.fromXML(response);
    	
    }
    
   
    public Bitmap downloadFile(String img){
          URL myFileUrl =null;          
          Bitmap bmImg=null;
          try {
               myFileUrl= new URL(URLimg+img);
          } catch (MalformedURLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
          }
          try {
        	  
               HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
               conn.setDoInput(true);
               conn.connect();
               InputStream is = conn.getInputStream();
               
               bmImg = BitmapFactory.decodeStream(is);
          } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
          }
          return bmImg;
     }
}