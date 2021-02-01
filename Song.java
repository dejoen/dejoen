package com.dejoeent.musicapp;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.*;



public class Song extends Fragment{
    
    MainActivity main;
    MusicAdapter musicadapter;
    
    public Song(){
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.song_fragment,container,false);
       RecyclerView recyclerview=view.findViewById(R.id.recyclerView);
	   recyclerview.setHasFixedSize(true);
	   
		if(!(main.musicfile.size()<1)){
			
			musicadapter=new MusicAdapter(getContext(),main.musicfile);
			recyclerview.setAdapter(musicadapter);
			recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),recyclerview.VERTICAL,false));
		}
	   return view;
     
        
    }
    
    
}
