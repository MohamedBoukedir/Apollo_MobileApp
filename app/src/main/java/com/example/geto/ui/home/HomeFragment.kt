package com.example.geto.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import com.example.geto.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        homeViewModel.projects.observe(viewLifecycleOwner, Observer
        {
            val adapter = Home_RecyclerAdapter(requireContext(), it)
            recyclerView.adapter = adapter
        })
        view.findViewById<Button>(R.id.add_project).setOnClickListener {
            val bundle = Bundle()
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_addProjet, bundle)
        }
        // prevent the softkeyboard to push up the fragment when entering the data
        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}