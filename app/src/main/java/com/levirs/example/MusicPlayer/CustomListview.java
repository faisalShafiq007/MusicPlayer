package com.levirs.example.MusicPlayer;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//Custom class generated for viewholder and all that crap
public class CustomListview extends ArrayAdapter<String> {

    private List<String> song;
    private List<String> artist;
    private List<String> path;


    private Activity context;
    public CustomListview(Activity context, List<String> songname, List<String>artistname,List<String>pathname) {
        super(context, R.layout.main_recycle,songname);
        this.context=context;

        this.song= songname;
        this.artist=artistname;
        this.path=pathname;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.main_recycle,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{

            viewHolder= (ViewHolder) r.getTag();
        }
        viewHolder.tvtw1.setText(song.get(position));
        viewHolder.tvtw2.setText(artist.get(position));
        viewHolder.tvtw3.setText(path.get(position));
        return r;

    }
    class ViewHolder{

        TextView tvtw1,tvtw2,tvtw3;
        ImageView imgw;
        ViewHolder(View v){


            tvtw1=(TextView) v.findViewById(R.id.listviewtextView);
            tvtw2=(TextView) v.findViewById(R.id.listviewtextviewtwo);
            tvtw3=(TextView) v.findViewById(R.id.listviewtextviewthree);
        }
    }

}
