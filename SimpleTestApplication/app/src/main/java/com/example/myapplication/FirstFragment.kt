package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val TAG = "IVANA"

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonOne.setOnClickListener {
           onButtonOneClick()
        }

        binding.buttonTwo.setOnClickListener {
            onButtonTwoClick()
        }
    }

    private fun onButtonTwoClick() {
        job?.cancel()
    }

    var job: Job? = null
    private fun onButtonOneClick() {
        job = coroutineScope.launch {
            try {
                work()
            }catch (e: Exception){
                log("Exception")
            }

            //try {
//                work()
//                delay(5000)
//            } catch (e: CancellationException){
//              log("Work is cancelled")
//            } finally {
//               log("Clean up!")
//            }
        }
    }

    fun log(text: String){
        Log.d(TAG, text)
    }

    private fun work() {
        //binding.textviewFirst.text = String.format("%s %s", binding.textviewFirst.text,  "\n New Text")
        throw IllegalStateException()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}