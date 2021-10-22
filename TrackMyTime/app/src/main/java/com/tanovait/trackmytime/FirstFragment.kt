package com.tanovait.trackmytime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.firstfirestore.MyAdapter
import com.example.firstfirestore.MyViewHolder
import com.tanovait.trackmytime.databinding.FragmentFirstBinding
import com.tanovait.trackmytime.ui.ProjectUI

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
/**
 * Steps
 *
 * 1. Create adapter [DONE]
 * 2. Add timer class and start timer at button press
 * 3. Move the button at the list item
 * 3. Use shared prefs to store project data
 * 4. Add button to add new project
 * 5. Store few projects in shared prefs
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    val adapter = object: MyAdapter<ProjectUI>(){

        override fun bind(t: ProjectUI, holder: MyViewHolder?) {
            val projectNameTxt = holder?.itemView?.findViewById<TextView>(R.id.project_name)
            projectNameTxt?.text = t.name

            val projectTimeTxt = holder?.itemView?.findViewById<TextView>(R.id.time_txt)
            projectTimeTxt?.text = t.timeSpend.toString()
        }

        override fun getItemViewType(position: Int): Int {
            return R.layout.project_list_item
        }
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}