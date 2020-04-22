package com.levirs.example.MusicPlayer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.levirs.example.MusicPlayer.CustomListview;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import hiennguyen.me.circleseekbar.CircleSeekBar;
import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity {
int newpath;
String ctextview;
    int shufflinte=0;
    int i=0;
    ArrayList<String> newsarraylist;
    String newsongpath;
    int j=0;
    int k=0;
    int strin=0;
    int prevstrin=0;
    String artist;
    MediaPlayer mediaPlayer;
    String SongTitle;
    Context context;
    public static final int RUNTIME_PERMISSION_CODE = 7;
    ContentResolver contentResolver;
    Cursor cursor;
    Uri uri;
    int length;
     AdapterView<?> parentseek;
    View view;
     int position;
    long id;
    String songpath;
    TextView lione,liot;
    EditText Searchbtn;
    CustomListview customListview;
    ListView ls;
    GifImageView pulgif;
   CircleImageView sajjadali;
   TextView song_name,artist_name;
   ImageView shufflebtn,previousbtn,playbutton,forwarddbutton,repaetbutton,tickk,tickshuffle;
 SeekBar seekbar;
Runnable runnable;
Handler handler;
TextView starttv,endtv;
     String temppa;
    int seebarpos;


    String[] ListElements = new String[] { };

    ArrayList<String> ListElementsArrayList ;


    String[] ListartistElements = new String[] { };
    ArrayList<String> ListElementsartistArrayList ;
 /*   Integer[] ListimageeElements = new Integer[]{};
    List<Integer> ListElementsimageArrayList ;*/
 String[] ListpathElements = new String[] { };

    ArrayList<String> ListElementspathList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Searchbtn=findViewById(R.id.search);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
artist_name=findViewById(R.id.singername);
tickshuffle=findViewById(R.id.tickshfle);
        pulgif=findViewById(R.id.gifImageView);
        handler=new Handler();
mediaPlayer=new MediaPlayer();
sajjadali=findViewById(R.id.circularimageview);
tickk=findViewById(R.id.tick);
song_name=findViewById(R.id.songname);

shufflebtn=findViewById(R.id.shfle);
previousbtn=findViewById(R.id.previous);
playbutton=findViewById(R.id.play);
forwarddbutton=findViewById(R.id.forward);
repaetbutton=findViewById(R.id.repeat);
starttv=findViewById(R.id.start);
endtv=findViewById(R.id.end);
seekbar=findViewById(R.id.seekbarnew);
        ls=findViewById(R.id.listViewnew);
        context = getApplicationContext();
        ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
        ListElementsartistArrayList = new ArrayList<>(Arrays.asList(ListartistElements));
        ListElementspathList = new ArrayList<>(Arrays.asList(ListpathElements));
        customListview=new CustomListview(MainActivity.this,ListElementspathList,ListElementsartistArrayList,ListElementspathList);
        ls.setAdapter(customListview);
        ls.setTextFilterEnabled(true);
        AndroidRuntimePermission();
        GetAllMediaMp3Files();
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

Log.e("position", String.valueOf(position));
strin=0;
prevstrin=0;
           TextView ass=view.findViewById(R.id.listviewtextView);
                TextView b=view.findViewById(R.id.listviewtextviewtwo);
                final TextView c=view.findViewById(R.id.listviewtextviewthree);
               temppa =ass.getText().toString();
                final int lens=temppa.length();
    song_name.setText(temppa.substring(20,lens));
    ctextview=c.getText().toString();
    artist_name.setText(b.getText().toString());
seebarpos=position;
parentseek=parent;
                forwarddbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       forwarfun(temppa,position,parent);
                    }

                });

                previousbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!temppa.isEmpty()) {
                            if (strin != 0) {
                                prevstrin=strin-1;
                            int pnew =  prevstrin;
                            String newsong = String.valueOf(parent.getItemAtPosition(pnew));
                            int newlen = newsong.length();
                            song_name.setText(newsong.substring(20, newlen));
                            artist_name.setText("");
                            Log.e("newsongpath", newsong);
                            playbutton.setImageResource(R.drawable.pause);
                            pulgif.setVisibility(View.VISIBLE);
                            sajjadali.setVisibility(View.INVISIBLE);
                            playbutton.setClickable(true);
                            mediaPlayer.stop();
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(newsong));
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    seekbar.setMax(mediaPlayer.getDuration());
                                    mediaPlayer.start();
                                    changeseekvar();
                                }
                            });
                            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    if (fromUser) {
                                        mediaPlayer.seekTo(progress);
                                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mp) {
                                                forwarfun(temppa,seebarpos,parentseek);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });

                            --prevstrin;
                        }
                        else{
                           Toast.makeText(MainActivity.this,"You are already on first song",Toast.LENGTH_SHORT).show();
                            }}
                        strin=prevstrin+1;}
                });
                shufflebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(shufflinte==0){
                            tickshuffle.setVisibility(View.VISIBLE);

                            shufflinte=1;
                        }
                        else{
                            tickshuffle.setVisibility(View.INVISIBLE);

                            shufflinte=0;
                        }

                    }
                });

    playbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            length = mediaPlayer.getCurrentPosition();
           Log.e("length", String.valueOf(length));
           //364

            if(i==0){
                mediaPlayer.pause();
                playbutton.setImageResource(R.drawable.play);
                length = mediaPlayer.getCurrentPosition();

                pulgif.setVisibility(View.INVISIBLE);
                sajjadali.setVisibility(View.VISIBLE);
                i=1;
            }
            else{
                playbutton.setImageResource(R.drawable.pause);

                i=0;
                pulgif.setVisibility(View.VISIBLE);
                sajjadali.setVisibility(View.INVISIBLE);
                mediaPlayer.seekTo(length);
                mediaPlayer.start();
                changeseekvar();

            }
        }
    });

Meediaplayer(ctextview);
}




        });

repaetbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(j==0){
            tickk.setVisibility(View.VISIBLE);
            mediaPlayer.setLooping(true);
            changeseekvar();
            j=1;
        }
        else{
            tickk.setVisibility(View.INVISIBLE);
            mediaPlayer.setLooping(false);
            j=0;
        }
    }
});
Searchbtn.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {



    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(!s.toString().isEmpty()) {

            ArrayList<String> templistsong=new ArrayList<>();
            ArrayList<String> templistasong=new ArrayList<>();
            ArrayList<String> templistpsong=new ArrayList<>();//temporary arraylist


                for(String tempp:ListElementspathList ){
                    if(tempp.toLowerCase().contains(s.toString().toLowerCase()))
                    {
                        templistpsong.add(tempp);
                        int a=tempp.length();

                        templistsong.add(tempp.substring(20,a));

                        templistasong.add("");

                    }

            }



            CustomListview arrayadpter = new CustomListview(MainActivity.this, templistsong, templistasong,templistpsong);
            ls.setAdapter(arrayadpter);
        }
        else{
            CustomListview arrayadpter = new CustomListview(MainActivity.this, ListElementsArrayList, ListElementsartistArrayList,ListElementspathList);
            ls.setAdapter(arrayadpter);

        }

    }





    @Override
    public void afterTextChanged(Editable s) {



    }



});

    }

    private void forwarfun(final String temppa, int position, final AdapterView<?> parent) {
        if(!temppa.isEmpty()){
            if(shufflinte==0){
                int pnew=position+1+strin;
                String newsong= String.valueOf(parent.getItemAtPosition(pnew));
                int newlen=newsong.length();
                song_name.setText(newsong.substring(20,newlen));
                artist_name.setText("");
                Log.e("newsongpath",newsong);
                playbutton.setImageResource(R.drawable.pause);
                pulgif.setVisibility(View.VISIBLE);
                sajjadali.setVisibility(View.INVISIBLE);
                playbutton.setClickable(true);
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(newsong));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        seekbar.setMax(mediaPlayer.getDuration());
                        mediaPlayer.start();
                        changeseekvar();
                    }
                });
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mediaPlayer.seekTo(progress);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    forwarfun(temppa,seebarpos,parentseek);
                                }
                            });
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                ++strin;
            }else
            {
                Random rn=new Random();
                //generate random values from 0-24
                int upperbound = ListElementspathList.size()-1;
                int random = rn.nextInt(upperbound);
                Log.e("rn", String.valueOf(random));
                int pnew=position+random;
                String newsong= String.valueOf(parent.getItemAtPosition(pnew));
                int newlen=newsong.length();
                song_name.setText(newsong.substring(20,newlen));
                artist_name.setText("");
                Log.e("newsongpath",newsong);
                playbutton.setImageResource(R.drawable.pause);
                pulgif.setVisibility(View.VISIBLE);
                sajjadali.setVisibility(View.INVISIBLE);
                playbutton.setClickable(true);
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(newsong));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        seekbar.setMax(mediaPlayer.getDuration());
                        mediaPlayer.start();
                        changeseekvar();
                    }
                });
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mediaPlayer.seekTo(progress);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    forwarfun(temppa,seebarpos,parentseek);
                                }
                            });
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });



            }
        }
    }


    @Override
    protected void onRestart() {
        if ((mediaPlayer.isPlaying())) {
            playbutton.setImageResource(R.drawable.pause);
            pulgif.setVisibility(View.VISIBLE);
            sajjadali.setVisibility(View.INVISIBLE);
            playbutton.setClickable(true);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekbar.setMax(mediaPlayer.getDuration());
                    changeseekvar();
                }
            });
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        Log.e("progress", String.valueOf(progress));
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                forwarfun(temppa,seebarpos,parentseek);
                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        super.onRestart();
    }

    private void Meediaplayer(String path) {
        if ((!mediaPlayer.isPlaying())) {
            playbutton.setImageResource(R.drawable.pause);
            pulgif.setVisibility(View.VISIBLE);
            sajjadali.setVisibility(View.INVISIBLE);
            playbutton.setClickable(true);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekbar.setMax(mediaPlayer.getDuration());
                    mediaPlayer.start();
                    changeseekvar();
                }
            });
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                forwarfun(temppa,seebarpos,parentseek);
                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {


                }
            });
        }

        else{

            playbutton.setImageResource(R.drawable.pause);
            pulgif.setVisibility(View.VISIBLE);
            sajjadali.setVisibility(View.INVISIBLE);
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
            mediaPlayer.start();

        }}


    private void changeseekvar() {
        seekbar.setProgress(mediaPlayer.getCurrentPosition());

        int cp=mediaPlayer.getCurrentPosition()/1000;
Log.e("pos", String.valueOf(cp));
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {

                    changeseekvar();

                }
            };
            handler.postDelayed(runnable, 1000);

        }



    }


    public void GetAllMediaMp3Files(){

        contentResolver = context.getContentResolver();

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        cursor = contentResolver.query(
                uri, // Uri
                null,
                null,
                null,
                null
        );

        if (cursor == null) {

            Toast.makeText(MainActivity.this,"Something Went Wrong.", Toast.LENGTH_LONG);

        } else if (!cursor.moveToFirst()) {

            Toast.makeText(MainActivity.this,"No Music Found on SD Card.", Toast.LENGTH_LONG);

        }
        else {
            int Title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int aaartist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
int fullpath=cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
newpath= (int) (fullpath+0.5);


    //Getting Song ID From Cursor.
            //int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            do {
                // You can also get the Song ID using cursor.getLong(id).
                //long SongID = cursor.getLong(id);
                SongTitle = cursor.getString(Title);
               artist=cursor.getString(aaartist);
               songpath=cursor.getString(fullpath);






                Log.e("path", songpath);

                // Adding Media File Names to ListElementsArrayList.
                ListElementsArrayList.add(SongTitle);
                ListElementsartistArrayList.add(artist);
                ListElementspathList.add(songpath);





            } while (cursor.moveToNext());
        }
    }


    // Creating Runtime permission function.
    public void AndroidRuntimePermission(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){

                    AlertDialog.Builder alert_builder = new AlertDialog.Builder(MainActivity.this);
                    alert_builder.setMessage("External Storage Permission is Required.");
                    alert_builder.setTitle("Please Grant Permission.");
                    alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    RUNTIME_PERMISSION_CODE

                            );
                        }
                    });

                    alert_builder.setNeutralButton("Cancel",null);

                    AlertDialog dialog = alert_builder.create();

                    dialog.show();

                }
                else {

                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            RUNTIME_PERMISSION_CODE
                    );
                }
            }else {

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        switch(requestCode){

            case RUNTIME_PERMISSION_CODE:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else {

                }
            }
        }
    }
    private void trying(int p) {




    }

}


