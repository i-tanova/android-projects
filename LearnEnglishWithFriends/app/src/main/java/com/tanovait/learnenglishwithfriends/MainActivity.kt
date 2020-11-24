package com.tanovait.learnenglishwithfriends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.firstfirestore.MyAdapter
import com.example.firstfirestore.MyViewHolder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.tanovait.learnenglishwithfriends.data.DataManager
import com.tanovait.learnenglishwithfriends.data.VideoUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val adapter = object: MyAdapter<VideoUI>(){

        override fun bind(t: VideoUI, holder: MyViewHolder?) {
            val playerView = holder?.itemView?.findViewById<YouTubePlayerView>(R.id.youtube_player_view)
            playerView?.getYouTubePlayerWhenReady(object : YouTubePlayerCallback{
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(t.url, 1f)
                }
            })
        }

        override fun getItemViewType(position: Int): Int {
            return R.layout.youtube_list_item
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = adapter
        val db = DataManager(this)
        coroutineScope.launch {
            db.getDataFromFirestore {
                adapter.setData(it)
            }
        }
    }
}