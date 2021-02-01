package com.dejoeent.musicapp;
import android.media.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.widget.*;
import java.util.*;
import com.bumptech.glide.*;



public class AlbumActivity extends AppCompatActivity
{
	 RecyclerView recyclerview;
	 MainActivity main;
	 ImageView imageview;
	 String name;
	 TextView textnane;
	 ArrayList<MusicFiles> albumsong=new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.albumact);
		
		recyclerview=(RecyclerView)findViewById(R.id.recyclerViewbig);
		imageview=(ImageView)findViewById(R.id.albumactImageView);
		
		name = getIntent().getStringExtra("albumname");
		int j = 0;
		for(int i=0;i<main.musicfile.size();i++){
			if(name.equals(main.musicfile.get(i).getAlbum())){

				albumsong.add(j,MainActivity.musicfile.get(i));
				j++;
			
			}
		
		}
		
		byte[] image=getAlbum(albumsong.get(0).getPath());
		

		if(image!=null){
			Glide.with(this).asBitmap().load(image).into(imageview);
		}
		else{
			Glide.with(this).load(R.drawable.mypic).into(imageview);
		}
	}

		

	@Override
	 protected void onResume()
	 {
	 // TODO: Implement this method
	 super.onResume();

	 if(!(albumsong.size()-1  <1)){
	 AlbumdetailsAdapter albumadapter=new AlbumdetailsAdapter(this,albumsong);
	 recyclerview.setAdapter(albumadapter);
	 recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
     
	 }
	}
	
	
  
	private byte[] getAlbum(String uri){

		MediaMetadataRetriever retriver=new MediaMetadataRetriever();
		retriver.setDataSource(uri);
		byte[] act=retriver.getEmbeddedPicture();
		retriver.release();
		return act;
	}
}
