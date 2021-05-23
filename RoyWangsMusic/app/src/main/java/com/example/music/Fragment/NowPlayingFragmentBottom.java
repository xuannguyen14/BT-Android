package com.example.music.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.music.MusicService;
import com.example.music.PlayerActivity;
import com.example.music.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.music.MainActivity.ARTIST_TO_FRAG;
import static com.example.music.MainActivity.PATH_TO_FRAG;
import static com.example.music.MainActivity.SHOW_MINI_PLAYER;
import static com.example.music.MainActivity.SONG_NAME_TO_FRAG;
import static com.example.music.MainActivity.repeatBoolean;
import static com.example.music.MainActivity.shuffleBoolean;
import static com.example.music.PlayerActivity.ImageAnimation;
import static com.example.music.PlayerActivity.getRandom;
import static com.example.music.PlayerActivity.listSongs;
import static com.example.music.PlayerActivity.musicService;

public class NowPlayingFragmentBottom extends Fragment {

    ImageView nextBtn, albumArt;
    TextView artist, songname;
    public static FloatingActionButton playPauseFrBtn;
    View view;
    int position = -1;
    public static Uri uri;

    public NowPlayingFragmentBottom() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_now_playing_bottom, container, false);

        artist = view.findViewById(R.id.song_artist_miniPlayer);
        songname = view.findViewById(R.id.song_name_miniPlayer);
        albumArt = view.findViewById(R.id.bottom_album_art);
        nextBtn = view.findViewById(R.id.skip_next_bottom);
        playPauseFrBtn = view.findViewById(R.id.play_pause_miniPlayer);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicService.isPlaying()) {
                    musicService.stop();
                    musicService.release();

                    if (shuffleBoolean && !repeatBoolean) {
                        position = getRandom(listSongs.size() - 1);
                    } else if (!shuffleBoolean && !repeatBoolean) {
                        position = ((position + 1) % listSongs.size());
                    }
                    // else position will be position

                    uri = Uri.parse(listSongs.get(position).getPath());
                    musicService.createMediaPlayer(position);

                    // get the image of the song
                    byte[] art = getAlbumArt(String.valueOf(uri));
                    if (art != null) {
                        Glide.with(getContext()).load(art).into(albumArt);
                    } else {
                        Glide.with(getContext()).load(R.drawable.music_default).into(albumArt);
                    }

                    songname.setText(listSongs.get(position).getTitle());
                    artist.setText(listSongs.get(position).getArtist());

                    musicService.OnCompleted();

                    musicService.showNotification(R.drawable.ic_baseline_pause);

                    playPauseFrBtn.setBackgroundResource(R.drawable.ic_baseline_pause);
                    musicService.start();
                } else {
                    musicService.stop();
                    musicService.release();

                    if (shuffleBoolean && !repeatBoolean) {
                        position = getRandom(listSongs.size() - 1);
                    } else if (!shuffleBoolean && !repeatBoolean) {
                        position = ((position + 1) % listSongs.size());
                    }

                    uri = Uri.parse(listSongs.get(position).getPath());
                    musicService.createMediaPlayer(position);

                    // get the image of the song
                    byte[] art = getAlbumArt(String.valueOf(uri));
                    if (art != null) {
                        Glide.with(getContext()).load(art).into(albumArt);
                    } else {
                        Glide.with(getContext()).load(R.drawable.music_default).into(albumArt);
                    }

                    songname.setText(listSongs.get(position).getTitle());
                    artist.setText(listSongs.get(position).getArtist());

                    musicService.OnCompleted();

                    musicService.showNotification(R.drawable.ic_baseline_play);

                    playPauseFrBtn.setBackgroundResource(R.drawable.ic_baseline_play);
                }
            }
        });

        playPauseFrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicService != null) {
                    //Toast.makeText(getContext(), "PlayPause", Toast.LENGTH_SHORT).show();
                    if (musicService.isPlaying()) {
                        playPauseFrBtn.setImageResource(R.drawable.ic_baseline_play);
                        musicService.pause();

                    } else {
                        playPauseFrBtn.setImageResource(R.drawable.ic_baseline_pause);
                        musicService.start();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SHOW_MINI_PLAYER) {
            if (PATH_TO_FRAG != null) {
                byte[] art = getAlbumArt(PATH_TO_FRAG);

                if (art != null) {
                    Glide.with(getContext()).load(art).into(albumArt);
                } else {
                    Glide.with(getContext()).load(R.drawable.music_default).into(albumArt);
                }

                songname.setText(SONG_NAME_TO_FRAG);
                artist.setText(ARTIST_TO_FRAG);
            }
        }
    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}