package com.dejoeent.musicapp;
import android.os.*;
import android.support.v4.app.*;
import android.text.*;
import android.view.*;
import android.support.v7.widget.*;

public class Album extends Fragment{

    MainActivity main;
    AlbumAdapter albumadapter;
	
    public Album(){
        
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       
		
		View view=inflater.inflate(R.layout.album_fragment,container,false);
		RecyclerView recyclerview=view.findViewById(R.id.recyclerView);
		recyclerview.setHasFixedSize(true);

		if(!(main.albums.size()<1)){

			albumadapter=new AlbumAdapter(getContext(),main.albums);
			recyclerview.setAdapter(albumadapter);
			recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
		}
		return view;
		
		
		
        
    }
    

    
    
}
