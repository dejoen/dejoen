package com.dejoeent.musicapp;

public class MusicFiles {
    String path;
    String title;
    String artist;
    String album;
    String duration;
	String id;
    
    
    public MusicFiles(String path,String title,String artist,String album,String duration,String id){
       
        this.path=path;
        this.title=title;
        this.artist=artist;
        this.album=album;
        this.duration=duration;
		this.id=id;
    }
    
    public MusicFiles(){
        
    }
    
    public void setPath(String path){
        this.path=path;
    }
    public String getPath(){
        return path;
    }
    
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }
    
    public void setArtist(String artist){
        this.artist=artist;
    }
    public String getArtist(){
        return artist;
    }
    
    public void setAlbum(String album){
        this.album=album;
    }
    public String getAlbum(){
        return album;
    }
    
    public void setDuration(String duration){
        this.duration=duration;
    }
    public String getDuration(){
        return duration;
    }
	
	public void setilId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
	
}
