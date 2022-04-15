package com.cineplex.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cineplex.entity.Studio;
import com.cineplex.util.AutoSyncTheThread;

public class StudioService {

	public Studio getStudioByJadwal(int id){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		Studio s=(Studio)sync.fetch("?do=getStudioByJadwal&id="+id);
		return s;
	}
	
	public List<String> getNotAvaibleSeat(int idJadwal, String jam,Date d){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
		List<String> data=(List<String>)sync.fetch("?do=getNotAvaibleSeat&id="+idJadwal+"&jam="+jam+ "&date=" + dateFormat.format(d));
		return data;
	}
}
