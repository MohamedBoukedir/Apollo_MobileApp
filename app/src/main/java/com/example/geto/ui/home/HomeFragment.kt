package com.example.geto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geto.R
import com.example.geto.data.Rest.ApiInterface
import com.example.geto.data.Rest.RetrofitInstance
import com.example.geto.data.model.Project
import com.example.geto.data.requestBody.UserId
import com.example.geto.guser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 1, GridLayoutManager.HORIZONTAL, false)
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        val reqBody= UserId(guser.email, guser.token)
        retIn.get_projects(reqBody).enqueue(object : Callback<List<Project>> {
            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                val appContext = context?.applicationContext ?: return
                Toast.makeText(appContext, "wrong email or password ", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
                if (response.code() == 200) {
                    homeViewModel.projects = MutableLiveData<List<Project>>(response.body())
                    homeViewModel.projects.observe(viewLifecycleOwner, Observer
                    {
                        if (it == null) {
                            val adapter = Home_RecyclerAdapter(requireContext(), listOf())
                            recyclerView.adapter = adapter
                        } else {
                            val adapter = Home_RecyclerAdapter(requireContext(), it)
                            recyclerView.adapter = adapter
                        }
                    })
                    // prevent the softkeyboard to push up the fragment when entering the data
                    getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                } else {
                    val appContext = context?.applicationContext ?: return
                    Toast.makeText(appContext, "connection problem ", Toast.LENGTH_LONG).show()
                }
            }
        })
        view.findViewById<ImageButton>(R.id.add_project).setOnClickListener {
            val bundle = Bundle()
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_addProjet, bundle)
        }
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}