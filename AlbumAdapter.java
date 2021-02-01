package com.dejoeent.musicapp;
import android.content.*;
import android.media.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.*;
import java.util.*;
import android.view.View.*;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.Myholder>
{
	
	 Context context;
	static ArrayList<MusicFiles> albumfiles;
	 View view;
	 public AlbumAdapter(Context context,ArrayList<MusicFiles> albumfiles){
		 
		 this.context=context;
		 this.albumfiles=albumfiles;
	 }

	@Override
	public AlbumAdapter.Myholder onCreateViewHolder(ViewGroup p1, int p2)
	{
		// TODO: Implement this method
		view=LayoutInflater.from(context).inflate(R.layout.album_list,p1,false);
		return new Myholder(view);
	}

	@Override
	public void onBindViewHolder(AlbumAdapter.Myholder p1, final int p2)
	{
		p1.text.setText(albumfiles.get(p2).getAlbum());
		

		byte[] image=getAlbum( albumfiles.get(p2).getPath());
		//Glide.with(context).load(image).into(p1.image);
		
		if( image!=null){

			Glide.with(context).asBitmap().load(image).into(p1.image);


		}
		else{
			Glide.with(context).load(R.drawable.mypic).into(p1.image);
		}
		
		
		p1.itemView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					
					Toast.makeText(context,"youclicked me",Toast.LENGTH_LONG).show();
					Intent intent=new Intent(context,AlbumActivity.class);
					intent.putExtra("albumname",albumfiles.get(p2).getAlbum());
					context.startActivity(intent);
					// TODO: Implement this method
				}
				
			
		});
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return albumfiles.size();
	}
	



public class Myholder extends RecyclerView.ViewHolder{
	
	ImageView image;
	TextView text;
	
	
	public Myholder(View itemview){
		super(itemview);
		
		image=itemview.findViewById(R.id.album_listImageView);
		text=itemview.findViewById(R.id.album_listTextView);
		
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
