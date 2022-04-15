package com.cineplex.service;

import com.cineplex.entity.Member;
import com.cineplex.util.AutoSyncTheThread;

public class MemberService {
	public Member getMemberById(String email){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		return (Member)sync.fetch("?do=getMember&id="+email);
	}
	
	public boolean changePassword(String username,String old,String newPass){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		return (Boolean)sync.fetch("?do=changePassword&id="+username+"&old="+old+"&new="+newPass);
	}
	
	public Long getSaldo(String username){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		return (Long)sync.fetch("?do=getSaldo&id="+username);
	}
}
