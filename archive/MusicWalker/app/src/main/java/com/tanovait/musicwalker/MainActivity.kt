package com.tanovait.musicwalker

//import com.spotify.android.appremote.api.ConnectionParams
//import com.spotify.android.appremote.api.Connector
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tanovait.musicwalker.ui.theme.MusicWalkerTheme


class MainActivity : ComponentActivity() {

//    private val CLIENT_ID = "your_client_id" // Replace with your Spotify Client ID
//    private val REDIRECT_URI = "your_redirect_uri" // Replace with your Redirect URI
//    private val REQUEST_CODE = 1337
//    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicWalkerTheme {
                SpotifyApp()
            }
        }
    }

    @Composable
    fun SpotifyApp() {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .clickable {
                    val spotifyContent = "https://open.spotify.com/playlist/3osEYCqtoApEUXbYrlvzhn?si=gKvzfujqQb--sUQKXcOZcQ&pi=bInBX95sQUyOt"
                    val branchLink =
                        (buildString {
                            append("https://spotify.link/content_linking?~campaign=")
                            append("${context.packageName}")
                        }) + "&\$deeplink_path=" + spotifyContent + "&\$fallb. ack_url=" + spotifyContent
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse(branchLink))
                    startActivity(intent)
                }
        ) {
//            Scaffold(
//                modifier = Modifier.fillMaxSize(),
//                content = {
            Text("Welcome to Music Walker!")
            // }
            //)
        }
        //    SpotifyAuthenticate()
    }

//    @Composable
//    private fun SpotifyAuthenticate() {
//        var isAuthenticated by remember { mutableStateOf(false) }
//
//        LaunchedEffect(Unit) {
//            // Authenticate the user
//            val request = AuthorizationRequest.Builder(
//                CLIENT_ID,
//                AuthorizationResponse.Type.TOKEN,
//                REDIRECT_URI
//            ).setScopes(arrayOf("streaming")).build()
//
//            AuthorizationClient.openLoginActivity(this@MainActivity, REQUEST_CODE, request)
//        }
//
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            content = {
//                if (isAuthenticated) {
//                    Text("Connected to Spotify!")
//                } else {
//                    Text("Authenticating with Spotify...")
//                }
//            }
//        )
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intent)
//        if (requestCode == REQUEST_CODE) {
//            val response = AuthorizationClient.getResponse(resultCode, intent)
//            if (response.type == AuthorizationResponse.Type.TOKEN) {
//                // Connect to Spotify App Remote
//                val connectionParams = ConnectionParams.Builder(CLIENT_ID)
//                    .setRedirectUri(REDIRECT_URI)
//                    .showAuthView(true)
//                    .build()
//
//                SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
//                    override fun onConnected(appRemote: SpotifyAppRemote) {
//                        spotifyAppRemote = appRemote
//                        spotifyAppRemote?.playerApi?.play("spotify:playlist:your_playlist_id") // Replace with your playlist URI
//                    }
//
//                    override fun onFailure(throwable: Throwable) {
//                        throwable.printStackTrace()
//                    }
//                })
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        SpotifyAppRemote.disconnect(spotifyAppRemote)
//    }
}