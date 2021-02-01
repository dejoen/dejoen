package com.dejoeent.musicapp;

import android.*;
import android.content.*;
import android.content.pm.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.SearchView;

public class MainActivity extends AppCompatActivity {
  public final int Request_Code=1;
  static boolean shufflebtn=false; static boolean repeatbtn=false;
  static ArrayList<MusicFiles> albums=new ArrayList<>();
  
public static ArrayList<MusicFiles> musicfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		//Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);
        
        ViewPager viewpager=(ViewPager)findViewById(R.id.viewpager);
        TabLayout tablayout=(TabLayout)findViewById(R.id.tabs);
        addtab(viewpager);
        tablayout.setupWithViewPager(viewpager);
       
  checkpermission();
    }

	public    ArrayList getmusicfile(){
		return musicfile;
	}
   public void addtab(ViewPager viewpager){
       ViewPagerAdapter viewpageradapter=new ViewPagerAdapter(getSupportFragmentManager());
       viewpageradapter.addFrag(new Song(),"Song");
       viewpageradapter.addFrag(new Album(),"Album");
       viewpager.setAdapter(viewpageradapter);
       
       
   }
   
   public void checkpermission(){
       if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
         !=PackageManager.PERMISSION_GRANTED){
           
           ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},Request_Code);}
    else
   {
       Toast.makeText(MainActivity.this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show();
       musicfile=getallaudio(this);
   }
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       
       
       if(requestCode==Request_Code){
           
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
               Toast.makeText(MainActivity.this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show();
               musicfile=getallaudio(this);
               
          }
           else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},Request_Code);
            
           }
           }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
	   MenuInflater inflater=getMenuInflater();
	   inflater.inflate(R.menu.searchimage,menu);
	   
	   
	   
	   // TODO: Implement this method
	   //return super.onCreateOptionsMenu(menu);
	   return true;
   }
	   
	   
    
    public static ArrayList<MusicFiles> getallaudio(Context context){
        ArrayList<String> duplicate=new ArrayList<>();
        ArrayList<MusicFiles> tempaudio=new ArrayList<>();
        Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media._ID
        };
        
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);
        if(cursor !=null){
            
            while(cursor.moveToNext()){
                
                String album=cursor.getString(0);
                String title=cursor.getString(1);
                String duration=cursor.getString(3);
                String path=cursor.getString(4);
                String artist=cursor.getString(2);
				String id=cursor.getString(5);
                
                MusicFiles musicfiles=new MusicFiles(path,title,artist,album,duration,id);
                tempaudio.add(musicfiles);
				if(!duplicate.contains(album)){
					albums.add(musicfiles);
					duplicate.add(album);
				}
                
            }
            cursor.close();
        }
        return tempaudio;
    }
    
   



}
