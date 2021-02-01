package com.dejoeent.musicapp;
import android.content.*;
import android.media.*;
import android.net.*;
import android.provider.*;
import android.support.design.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.bumptech.glide.*;
import java.io.*;
import java.util.*;

import android.support.v7.widget.PopupMenu;





public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Context context;
    ArrayList<MusicFiles> mfiles;
    
    MusicAdapter(Context context,ArrayList<MusicFiles> mfiles){
        this.context=context;
        this.mfiles=mfiles;
    }
    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(ViewGroup p1, int p2) {
        
        View view=LayoutInflater.from(context).inflate(R.layout.music_list,p1,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.MyViewHolder p1, final int p2) {
        
        p1.musictext.setText(mfiles.get(p2).getTitle());
		//Glide.with(context).load(R.drawable.mypic).into(p1.musicimage);
		
		
	byte[] image=getAlbum(mfiles.get(p2).getPath());
	
		if( image!=null){

			Glide.with(context).asBitmap().load(image).into(p1.musicimage);
			
	
		}
		else{
			Glide.with(context).load(R.drawable.mypic).into(p1.musicimage);
		}
		
		p1.itemView.setOnClickListener(new OnClickListener(){

				

		

				

				

				

				@Override
				public void onClick(View p1)
				{
					Intent intent=new Intent(context,PlayerActivity.class);
					
					intent.putExtra("position", p2);
					context.startActivity(intent);
				}
				
			
		});
		
		p1.deleteimage.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(final View v)
				{
					// TODO: Implement this method
					PopupMenu popupmenu=new PopupMenu(context,v);
					popupmenu.getMenuInflater().inflate(R.menu.moremenu,popupmenu.getMenu());
					popupmenu.show();
					popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

							@Override
							public boolean onMenuItemClick(MenuItem menu)
							{
								// TODO: Implement this method
								switch(menu.getItemId()){
									
									case R.id.delete:
										Toast.makeText(context,"Delete clicked",Toast.LENGTH_SHORT).show();
										deleteFile(p2,v);
										break;
								}
								
								return true;
							}
							
						
						
					});
				}
				
			
		});
    }
	
	public void deleteFile(int position,View v){
		
		Uri contenturi=ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,Long.parseLong(mfiles.get(position).getId()));
		File file=new File(mfiles.get(position).getPath());
		boolean deleted=file.delete();
		if(deleted){
			context.getContentResolver().delete(contenturi,null,null);
		mfiles.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeRemoved(position,mfiles.size());
		Snackbar.make(v,"file deleted",Snackbar.LENGTH_LONG).show();
		}else{
			
			Snackbar.make(v,"file not deleted",Snackbar.LENGTH_LONG).show();
			
		}
	}

    @Override
    public int getItemCount() {
        return mfiles.size();
    }
    
   
    
    public class MyViewHolder extends RecyclerView.ViewHolder{
        
        TextView musictext;
        ImageView musicimage,deleteimage;
        public MyViewHolder(View itemview){
            super(itemview);
            musictext=itemview.findViewById(R.id.textview);
            musicimage=itemview.findViewById(R.id.imageview);
			deleteimage=itemview.findViewById(R.id.moremenu);
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
