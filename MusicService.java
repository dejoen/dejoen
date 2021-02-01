package com.dejoeent.musicapp;
import android.app.*;
import android.content.*;
import android.media.*;
import android.net.*;
import android.os.*;
import java.util.*;
import android.support.v7.widget.*;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener
{

	IBinder mbinder=new MyBinder();
	MediaPlayer mediaplayer;
	ArrayList<MusicFiles> musicfiles=new ArrayList<>();
	Uri uri;
	int position=-1;
  Actionplay actionplay;
	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		
	}
	

	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return mbinder;
	}
	
	public class MyBinder extends Binder{
		MusicService getService(){
		return MusicService.this;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		int myposition=intent.getIntExtra("musicserviceposition",-1);
		if(myposition!=-1){
			getMedia(myposition);
		}
		
		return START_STICKY;
	}

	public void getMedia(int startposition)
	{
		// TODO: Implement this method
		musicfiles=PlayerActivity.songlist;
	    position=startposition;
		if(mediaplayer!=null){
			mediaplayer.stop();
			mediaplayer.release();
			if(musicfiles!=null){
				createMusicplayer(position);
				mediaplayer.start();
			}
		}else{
			createMusicplayer(position);
			mediaplayer.start();
		}
		
	}

	void start(){
		mediaplayer.start();
	}
	void stop(){
		mediaplayer.stop();
	}
	boolean isplaying(){
		return mediaplayer.isPlaying();
	}
	int getduration(){
		return mediaplayer.getDuration();
	}
	void seekTo(int seekto){
		mediaplayer.seekTo(seekto);
	}
	
	void createMusicplayer(int position ){
		uri=Uri.parse(musicfiles.get(position).getPath());
		mediaplayer=MediaPlayer.create(getBaseContext(),uri);
	}
	int getCurrentPosition(){
		return mediaplayer.getCurrentPosition();
	}
	void release(){
		mediaplayer.release();
	}
	void pause(){
		mediaplayer.pause();
	}
	
	void oncompleted(){
		mediaplayer.setOnCompletionListener(this);
	}


	@Override
	public void onCompletion(MediaPlayer p1)
	{
		// TODO: Implement this method
		if(actionplay!=null){
			
		
		   actionplay.nextbtnclick();
		}


		

			createMusicplayer(position);
			mediaplayer.start();
			oncompleted();

		
		
		
	}
}
