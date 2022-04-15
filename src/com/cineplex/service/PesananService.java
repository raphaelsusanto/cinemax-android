package com.cineplex.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cineplex.util.AutoSyncTheThread;

public class PesananService {

	public String pesan(int idJadwal, String username, String no, String jam,Date d) {
		AutoSyncTheThread sync = new AutoSyncTheThread();
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
		String msg = (String) sync.fetch("?do=pesan&idJadwal=" + idJadwal
				+ "&username=" + username + "&jam=" + jam + "&no=" + no+ "&date=" + dateFormat.format(d));
		return msg;
	}

	public Object[] detailPesan(int idJadwal, Date d) {
		AutoSyncTheThread sync = new AutoSyncTheThread();
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
		Object[] msg = (Object[]) sync.fetch("?do=detailPesan&idJadwal="
				+ idJadwal+ "&date=" + dateFormat.format(d));
		return msg;
	}

	public List<Object[]> getHistoryOrder(String username) {
		AutoSyncTheThread sync = new AutoSyncTheThread();

		List<Object[]> msg = (List<Object[]>) sync
				.fetch("?do=getOrderHistory&id=" + username);
		return msg;
	}
	
	public String[] getDetailHistoryOrder(String idOrder){
		AutoSyncTheThread sync = new AutoSyncTheThread();

		String[] msg = (String[]) sync
				.fetch("?do=getDetailHistoryOrder&id=" + idOrder);
		return msg;
	}

}
