package com.cineplex.service;

import com.cineplex.util.AutoSyncTheThread;

public class LoginService {
	
	public static String email;
	public Boolean check(String email, String password){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		this.email=email;
		return (Boolean)sync.fetch("?do=login&u="+email+"&p="+password);
	}
}
