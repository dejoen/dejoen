package com.dejoeent.musicapp;
import android.support.v7.app.*;
import android.os.*;
import android.widget.*;
import android.support.design.widget.*;
import java.util.*;
import android.net.*;


import android.media.*;
import java.io.*;
import android.view.View;
import android.view.View.*;
import android.graphics.drawable.*;
import android.widget.SeekBar.*;
import com.bumptech.glide.*;
import android.graphics.*;
import android.support.v7.graphics.*;
import android.content.*;
import android.view.animation.*;
import android.app.*;
import android.support.v4.app.NotificationCompat.*;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;
import android.media.session.*;


import android.widget.Magnifier.*;
import android.support.v4.app.*;
import android.support.v4.media.session.*;

public class PlayerActivity extends AppCompatActivity implements Actionplay, ServiceConnection
{
   TextView songname,artistname,playduration,totalduration;
	 ImageView backbtn,nextbutton,previousbutton,repeat,shuffle,coverart;
	 FloatingActionButton playbutton;
	 SeekBar seekbar;
	 int position=-1;
	// static MediaPlayer mediaplay;
	 static Uri uri;
	 Handler handler=new Handler();
	 static ArrayList<MusicFiles> songlist=new ArrayList<>();
	Thread playthread,pausethread,nextyhread,prevthread;
    MainActivity main;
	public MusicService musicservice;
	MediaSession mediasession;
	MediaSessionCompat mediasessioncompat;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
	setContentView(R.layout.player);
	
		//mediaplay=MediaPlayer.create(this,uri);
		
		//mediasession=new MediaSession(getBaseContext(),"myAudio");
		mediasessioncompat=new MediaSessionCompat(getBaseContext(),"myAudio");

		
		initView();
		getitemmethod();
		
		      
		
		
		
		
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					// TODO: Implement this method
					
					if(musicservice != null && p3){
						
						musicservice.seekTo(p2*1000);
					}
				}
				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}
				
			
			
		});
		
		shuffle.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(MainActivity.shufflebtn){
						
						MainActivity.shufflebtn=false;
						shuffle.setImageResource(R.drawable.shuffleon);
					}else{
						

						MainActivity.shufflebtn=true;
						shuffle.setImageResource(R.drawable.shuffleoff);
						
					}
					// TODO: Implement this method
				}
				
			
			
		});
		repeat.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					if(main.repeatbtn){
					main.repeatbtn=false;
					repeat.setImageResource(R.drawable.repeaton);
					}else{
						main.repeatbtn=true;
						repeat.setImageResource(R.drawable.repeatoff);
					}
				}
			
			
		});
			
		PlayerActivity.this.runOnUiThread(new Runnable(){

				@Override
				public void run()
				{  
					if(musicservice!=null){
					int currentposition=musicservice.getCurrentPosition()/1000;
					seekbar.setProgress(currentposition);
					playduration.setText(formattedTime(currentposition));
					// TODO: Implement this method
				
				}
				handler.postDelayed(this,1000);
				
				}
				
			
		});
		
		
		
	



		
	/*playbutton.setOnClickListener(new OnClickListener(){
           boolean isplaying=false;
				@Override
				public void onClick(View p1)
				{   
				if(isplaying){
					playbutton.setImageResource(R.drawable.play);
					mediaplay.stop();
					//mediaplay.release();
					mediaplay.getCurrentPosition();
				}else{
					getitemmethod();
					// TODO: Implement this method
					}
					isplaying=!isplaying;
				} 
				
				
			
		});*/
		
	}
	
	@Override
	public void onServiceConnected(ComponentName p1, IBinder p2)
	{
		// TODO: Implement this method
		MusicService.MyBinder mybinder= (MusicService.MyBinder)  p2;
		musicservice=mybinder.getService();
		Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show();
		seekbar.setMax(musicservice.getduration()/1000);
		metadata(uri);
		artistname.setText(songlist.get(position).getArtist());
		songname.setText((songlist.get(position).getTitle()));
		musicservice.oncompleted();
	}

	@Override
	public void onServiceDisconnected(ComponentName p1)
	{
		 musicservice=null;
		// TODO: Implement this method
	}
	
	

	@Override
	protected void onResume()
	{  Intent intent=new Intent(this,MusicService.class);
	  bindService(intent,this,BIND_AUTO_CREATE);
		
		playthreadbtn();
		nextthreadbtn();
		prevthreadbtn();
		pausethreadbtn();
		// TODO: Implement this method
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		unbindService(this);
	}
	

	private void pausethreadbtn()
	{
		// TODO: Implement this method
	}

	private void prevthreadbtn()
	{
		// TODO: Implement this method
		prevthread=new Thread(){

			@Override
			public void run(){

				super.run();

				previousbutton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							// TODO: Implement this method
							prevbtnclick();
						}





					});
			}


		};
		prevthread.start();
	}

	private void nextthreadbtn()
	{
		// TODO: Implement this method
		
		nextyhread=new Thread(){

			@Override
			public void run(){

				super.run();

				nextbutton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							// TODO: Implement this method
							nextbtnclick();
						}





					});
			}


		};
		nextyhread.start();
	}
	
	public void playthreadbtn(){
		
		playthread=new Thread(){
			 
			@Override
			public void run(){
				
				super.run();
				
				playbutton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							// TODO: Implement this method
							playbtnClick();
						}

		
						
					
					
				});
			}
			
			
		};
		playthread.start();
		
	}
	
	public void prevbtnclick(){
		
		
		boolean isplaying=musicservice.isplaying();
		if(isplaying){

			musicservice.stop();
			musicservice.release();
			if(main.shufflebtn &&  !main.repeatbtn){

				position=getRandom(songlist.size()-1);
			}
			else if(!main.shufflebtn && !main.repeatbtn){
		

			
			position=((position-1)<0 ? (songlist.size()-1): position-1);
			
			}
			uri=Uri.parse(songlist.get(position).getPath());
			//mediaplay=MediaPlayer.create(this,uri);
			musicservice.createMusicplayer(position);
			metadata(uri);
			songname.setText(songlist.get(position).getTitle());
			artistname.setText(songlist.get(position).getArtist());

			seekbar.setMax(musicservice.getduration()/1000);

			PlayerActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run()
					{
						if(musicservice!=null){
							int currentposition=musicservice.getCurrentPosition()/1000;
							seekbar.setProgress(currentposition);

							// TODO: Implement this method

						}
						handler.postDelayed(this,1000);

					}


				});
				musicservice.oncompleted();
			playbutton.setBackgroundResource(R.drawable.pause);
			showNotification(R.drawable.pause);
			musicservice.start();
		}else{


			musicservice.stop();
			musicservice.release();
			if(main.shufflebtn &&  !main.repeatbtn){

				position=getRandom(songlist.size()-1);
			}
			else if(!main.shufflebtn && !main.repeatbtn){
		

			
			
			position=((position-1)<0 ?(songlist.size()-1) :position-1);
			
			}
			uri=Uri.parse(songlist.get(position).getPath());
			//mediaplay=MediaPlayer.create(this,uri);
			musicservice.createMusicplayer(position);
			metadata(uri);
			songname.setText(songlist.get(position).getTitle());
			artistname.setText(songlist.get(position).getArtist());

			seekbar.setMax(musicservice.getduration()/1000);

			PlayerActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run()
					{
						if(musicservice !=null){
							int currentposition=musicservice.getCurrentPosition()/1000;
							seekbar.setProgress(currentposition);

							// TODO: Implement this method

						}
						handler.postDelayed(this,1000);

					}});
					musicservice.oncompleted();
			playbutton.setBackgroundResource(R.drawable.play);
			showNotification(R.drawable.play);
		
		
		
		
		
	}}
	
	public void nextbtnclick(){
		
		
		boolean isplaying=musicservice.isplaying();
		if(isplaying){
			
			musicservice.stop();
			musicservice.release();
			if(main.shufflebtn &&  !main.repeatbtn){
				
				position=getRandom(songlist.size()-1);
			}
			else if(!main.shufflebtn && !main.repeatbtn){
			position=((position+1)% songlist.size());
			
			}
			uri=Uri.parse(songlist.get(position).getPath());
			//mediaplay=MediaPlayer.create(this,uri);
			musicservice.createMusicplayer(position);
			metadata(uri);
			songname.setText(songlist.get(position).getTitle());
			artistname.setText(songlist.get(position).getArtist());
			
			seekbar.setMax(musicservice.getduration()/1000);

			PlayerActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run()
					{
						if(musicservice !=null){
							int currentposition=musicservice.getCurrentPosition()/1000;
							seekbar.setProgress(currentposition);

							// TODO: Implement this method

						}
						handler.postDelayed(this,1000);

					}


				});
				musicservice.oncompleted();
			
				playbutton.setBackgroundResource(R.drawable.pause);
				showNotification(R.drawable.pause);
				musicservice.start();
		}else{
			
			
			musicservice.stop();
			musicservice.release();
			if(main.shufflebtn &&  !main.repeatbtn){

				position=getRandom(songlist.size()-1);
			}
			else if(!main.shufflebtn && !main.repeatbtn){
				position=((position+1)% songlist.size());

			}
			uri=Uri.parse(songlist.get(position).getPath());
			//mediaplay=MediaPlayer.create(this,uri);
			musicservice.createMusicplayer(position);
			metadata(uri);
			songname.setText(songlist.get(position).getTitle());
			artistname.setText(songlist.get(position).getArtist());

			seekbar.setMax(musicservice.getduration()/1000);

			PlayerActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run()
					{
						if(musicservice !=null){
							int currentposition=musicservice.getCurrentPosition()/1000;
							seekbar.setProgress(currentposition);

							// TODO: Implement this method

						}
						handler.postDelayed(this,1000);

					}});
					playbutton.setBackgroundResource(R.drawable.play);
					showNotification(R.drawable.play);
					musicservice.oncompleted();
		
	}
	
	}
	
	public int getRandom(int i){
		Random random=new Random();
		return random.nextInt(i-1);
		
	}
	
	
	public void playbtnClick(){
		
		boolean isplaying=musicservice.isplaying();
		if(isplaying){
		playbutton.setImageResource(R.drawable.play);
		showNotification(R.drawable.play);
		musicservice.pause();
		seekbar.setMax(musicservice.getduration()/1000);
		
			PlayerActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run()
					{
						if(musicservice !=null){
							int currentposition=musicservice.getCurrentPosition()/1000;
							seekbar.setProgress(currentposition);
						
							// TODO: Implement this method

						}
						handler.postDelayed(this,1000);

					}


				});
		
	}else{
		playbutton.setImageResource(R.drawable.pause);
		showNotification(R.drawable.pause);
		musicservice.start();
		seekbar.setMax(musicservice.getduration()/1000);
		
		PlayerActivity.this.runOnUiThread(new Runnable(){

				@Override
				public void run()
				{
					if(musicservice!=null){
						int currentposition=musicservice.getCurrentPosition()/1000;
						seekbar.setProgress(currentposition);

						// TODO: Implement this method

					}
					handler.postDelayed(this,1000);

				}


			});
		
	}
	
	
	}
		
		


	private String formattedTime(int currentposition){

		String totalNew="";
		String totalOut="";
		String minutes=String.valueOf(currentposition / 60);
		String seconds=String.valueOf(currentposition  % 60);
		totalOut=minutes +":"+seconds;
		totalNew=minutes+":"+"0"+seconds;
		if(seconds.length()==1){
			
			return totalNew;
		}else{
			
			return totalOut;
		}
	}
	
		
	public void metadata(Uri uri){
		
	MediaMetadataRetriever retriver=new MediaMetadataRetriever();
	retriver.setDataSource(uri.toString());
	int totaldurationtime=Integer.parseInt(songlist.get(position).getDuration())/1000;
	totalduration.setText(formattedTime(totaldurationtime));
	
	byte[] art=retriver.getEmbeddedPicture();
	Bitmap bitmap;
	if(art !=null){
		Glide.with(this).asBitmap().load(art).into(coverart);
		
		bitmap=BitmapFactory.decodeByteArray(art,0,art.length);
		imageAnimation(this,coverart);
		Palette.from(bitmap).generate(new Palette.PaletteAsyncListener(){

				@Override
				public void onGenerated(Palette p1)
				{
					Palette.Swatch swatch=p1.getDominantSwatch();
					
					if(swatch !=null){
					ImageView image=findViewById(R.id.gradientbg);
					RelativeLayout contain=findViewById(R.id.relativeplayer);
					image.setBackgroundResource(R.drawable.gradient_bg);
					contain.setBackgroundResource(R.drawable.bg);
					GradientDrawable graddraw=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{
						swatch.getRgb(),0x00000000
					});
					image.setBackground(graddraw);
						GradientDrawable graddrawbg=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{
																		   swatch.getRgb(),swatch.getRgb()
																	   });
						contain.setBackground(graddrawbg);
						
						songname.setTextColor(swatch.getTitleTextColor());
						artistname.setTextColor(swatch.getBodyTextColor());
					
					// TODO: Implement this method
				}else{
					
					
				

					
						ImageView image=findViewById(R.id.gradientbg);
						RelativeLayout contain=findViewById(R.id.relativeplayer);
						image.setBackgroundResource(R.drawable.gradient_bg);
						contain.setBackgroundResource(R.drawable.bg);
						GradientDrawable graddraw=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{
																		   0xff000000,0x00000000
																	   });
						image.setBackground(graddraw);
						GradientDrawable graddrawbg=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{
																			 0xff000000,0xff000000
																		 });
						contain.setBackground(graddrawbg);
						songname.setTextColor(Color.WHITE);
						artistname.setTextColor(Color.DKGRAY);
						
					
				
					}}});
	}   else{
		Glide.with(this).asBitmap().load(R.drawable.mypic).into(coverart);
	
		ImageView image=findViewById(R.id.gradientbg);
		RelativeLayout contain=findViewById(R.id.relativeplayer);
		image.setBackgroundResource(R.drawable.gradient_bg);
		contain.setBackgroundResource(R.drawable.bg);
		songname.setTextColor(Color.WHITE);
		artistname.setTextColor(Color.DKGRAY);
		
	}
	}
	
		
	
	
	public void getitemmethod(){
		
		position=getIntent().getIntExtra("position",-1);
		songlist=main.musicfile;
	    String sender=getIntent().getStringExtra("sender");
		if(sender!=null &&sender.equals("albumDetails")){
			 songlist=AlbumdetailsAdapter.albumfiles;
		}else{
			
			songlist=MainActivity.musicfile;
		}
		if(!(songlist==null)){
			
			playbutton.setImageResource(R.drawable.pause);
			uri=Uri.parse(songlist.get(position).getPath());
			
			
			//mediaplay=MediaPlayer.create(this,uri);
			//mediaplay.start();
		 //musicservice.createMusicplayer(position);
		    }
		   showNotification(R.drawable.pause);
			Intent intent=new Intent(this,MusicService.class);
			intent.putExtra("musicserviceposition",position);
			startService(intent);
		//musicservice.createMusicplayer(position);
		//musicservice.start();
		//musicservice.getCurrentPosition();
		

		
	}
	
	public void initView(){
		songname=(TextView)findViewById(R.id.songname);
		artistname=(TextView)findViewById(R.id.songartist);
		playduration=(TextView)findViewById(R.id.playerTextViewnum);
		totalduration=(TextView)findViewById(R.id.playerTextViewnum2);
		backbtn=(ImageView)findViewById(R.id.playerImageView);
		coverart=(ImageView)findViewById(R.id.playerpic);
		nextbutton=(ImageView)findViewById(R.id.next);
		previousbutton=(ImageView)findViewById(R.id.previous);
		repeat=(ImageView)findViewById(R.id.repeatob);
		shuffle=(ImageView)findViewById(R.id.shuffleon);
		playbutton=(FloatingActionButton)findViewById(R.id.playpause);
		seekbar=(SeekBar)findViewById(R.id.seekbar);
		
		
	}
	
	public void imageAnimation(final Context context,final ImageView imageview){
		
		Animation animateout=AnimationUtils.loadAnimation(context,android.R.anim.fade_out);
		final Animation animatein=AnimationUtils.loadAnimation(context,android.R.anim.fade_in);
		animateout.setAnimationListener(new Animation.AnimationListener(){

				@Override
				public void onAnimationStart(Animation p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationEnd(Animation p1)
				{
					// TODO: Implement this method
				
					
					animatein.setAnimationListener(new Animation.AnimationListener(){

							@Override
							public void onAnimationStart(Animation p1)
							{
								// TODO: Implement this method
							}

							@Override
							public void onAnimationEnd(Animation p1)
							{
								// TODO: Implement this method
							}

							@Override
							public void onAnimationRepeat(Animation p1)
							{
								// TODO: Implement this method
							}
							
						
						
					});
					
					imageview.startAnimation(animatein);
				}

				@Override
				public void onAnimationRepeat(Animation p1)
				{
					// TODO: Implement this method
				}
				
			
			
			
		});
		
		imageview.startAnimation(animateout);
		
	}
	
	
	
		// TODO: Implement this method
	
		void showNotification(int playpause){
			
			Intent intent=new Intent(this,PlayerActivity.class);
			PendingIntent contentintent=PendingIntent.getActivity(this,0,intent,0);
			
			Intent previntent=new Intent(this,NotificationReciever.class).setAction(Applicationpart.Action_prev);
			PendingIntent prevpending=PendingIntent.getBroadcast(this,0,previntent,PendingIntent.FLAG_UPDATE_CURRENT);
			
			Intent pauseintent=new Intent(this,NotificationReciever.class).setAction(Applicationpart.Action_play);
			PendingIntent pausepending=PendingIntent.getBroadcast(this,0,pauseintent,PendingIntent.FLAG_UPDATE_CURRENT);
			
			Intent nextintent=new Intent(this,NotificationReciever.class).setAction(Applicationpart.Action_Next);
			PendingIntent nextpending=PendingIntent.getBroadcast(this,0,nextintent,PendingIntent.FLAG_UPDATE_CURRENT);
			
			byte[] picture=null;
			picture=getAlbum(main.musicfile.get(position).getPath());
			Bitmap thumb=null;
			if(picture!=null){
				
				thumb=BitmapFactory.decodeByteArray(picture,0,picture.length);
				
			}else
			{
				thumb=BitmapFactory.decodeResource(getResources(),R.drawable.myimage);
			}
			
			/*Notification.Builder notification= new Notification.Builder(this,Applicationpart.Channel_ID_2).setSmallIcon(playpause).setLargeIcon(thumb)
			.setContentTitle(main.musicfile.get(position).getTitle()).setContentText(main.musicfile.get(position).getArtist()).
			addAction(R.drawable.previous,"previous",prevpending).
				addAction(playpause,"pause",pausepending).
				addAction(R.drawable.next,"next",nextpending).setPriority(Notification.PRIORITY_HIGH).
				setStyle(
				new android.app.Notification.MediaStyle().  
				setMediaSession(mediasession.getSessionToken())).
			   setOnlyAlertOnce(true);
				
				NotificationManager notificationmanager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationmanager.notify(0,notification.build());
				*/
			
			NotificationCompat.Builder notificatin=new NotificationCompat.Builder(this,Applicationpart.Channel_ID_2).
			setSmallIcon(playpause).
			setLargeIcon(thumb)
				.setContentTitle(main.musicfile.get(position).getTitle()).
				setContentText(main.musicfile.get(position).getArtist()).
				addAction(R.drawable.shuffleoff,"",prevpending).
				addAction(playpause,"",pausepending).
			  addAction(R.drawable.pause,"",nextpending).setPriority(Notification.PRIORITY_HIGH).
				setStyle(
			new android.support.v4.media.app.NotificationCompat.
			MediaStyle().setShowActionsInCompactView(0)
			.setMediaSession(mediasessioncompat.getSessionToken()
			)).
				setOnlyAlertOnce(true);

			NotificationManager notificationmanager2=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationmanager2.notify(0,notificatin.build());

			
		
		
		}
		
		
		
	private byte[] getAlbum(String uri){

		MediaMetadataRetriever retriver=new MediaMetadataRetriever();
		retriver.setDataSource(uri);
		byte[] act=retriver.getEmbeddedPicture();
		retriver.release();
		return act;
	}

}
