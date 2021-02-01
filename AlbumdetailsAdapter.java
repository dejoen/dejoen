package com.dejoeent.musicapp;
import android.support.v7.widget.*;
import android.content.*;
import java.util.*;
import android.view.*;
import android.widget.*;
import android.media.*;
import com.bumptech.glide.*;
import android.view.View.*;

public class AlbumdetailsAdapter extends RecyclerView.Adapter<AlbumdetailsAdapter.Myholder>
{

	Context context;
	static ArrayList<MusicFiles> albumfiles;
	View view;
	public AlbumdetailsAdapter(Context context,ArrayList<MusicFiles> albumfiles){

		this.context=context;
		this.albumfiles=albumfiles;
	}

	@Override
	public AlbumdetailsAdapter.Myholder onCreateViewHolder(ViewGroup p1, int p2)
	{
		// TODO: Implement this method
		view=LayoutInflater.from(context).inflate(R.layout.music_list,p1,false);
		return new Myholder(view);
	}

	@Override
	public void onBindViewHolder(AlbumdetailsAdapter.Myholder p1, final int p2)
	{
		p1.text.setText(albumfiles.get(p2).getTitle());


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

				
					Intent intent=new Intent(context,PlayerActivity.class);
					intent.putExtra("sender","albumDetails");
					intent.putExtra("position",p2);
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

			image=itemview.findViewById(R.id.imageview);
			text=itemview.findViewById(R.id.textview);

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
