package com.dejoeent.musicapp;
import android.app.*;
import android.os.*;

public class Applicationpart extends Application
{
	 public static final String Channel_ID_1="channel1";
	public static final String Channel_ID_2="channel2";
	public static final String Action_Next="next";
	public static final String Action_prev="prev";
	public static final String Action_play="play";
	

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		
		createNotificationchanel();
	}

	private void createNotificationchanel()
	{
		// TODO: Implement this method
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
			
			NotificationChannel channel1=new NotificationChannel(Channel_ID_1,"channel(1)",
			NotificationManager.IMPORTANCE_HIGH);
			channel1.setDescription("channel 1 Desc..");
			
			NotificationChannel channel2=new NotificationChannel(Channel_ID_2,"channel(2)",
																 NotificationManager.IMPORTANCE_HIGH);
			channel2.setDescription("channel 2 Desc..");
			
			NotificationManager notoficationnmanager=getSystemService(NotificationManager.class);
			notoficationnmanager.createNotificationChannel(channel1);
			notoficationnmanager.createNotificationChannel(channel2);
		
			
		}
		
	}
	
	
}
