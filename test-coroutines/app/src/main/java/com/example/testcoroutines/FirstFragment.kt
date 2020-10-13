package com.example.testcoroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutineScope.launch {
            val bitmapDeferred = coroutineScope.async(Dispatchers.IO) { downloadImage() }
            val bitmap = bitmapDeferred.await()
            bitmap?.let {
                image.setImageBitmap(bitmap)
            }
        }
    }

    private fun downloadImage(): Bitmap? {
        val url =
            URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464")
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }
}