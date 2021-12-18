package com.example.geto.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.geto.R
import com.example.geto.data.model.Project


class Home_RecyclerAdapter(
    val context: Context,
    val Projects: List<Project>
):
    RecyclerView.Adapter<Home_RecyclerAdapter.ViewHolder>()
{

// 1. Méthode redéfinie qui a besoin du nombre d’éléments de données à afficher
    override fun getItemCount() = Projects.size
// 2. Méthode redéfinie qui retourne un ViewHolder et fait une association avec le fichier layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_projet, parent, false)
        return ViewHolder(view)
    }
// 3. Méthode redéfinie qui fait le lien entre les composants et les données à afficher dans le recyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project= Projects[position]
        with(holder) {
            title?.let {
                it.text = project.title
            }
            description?.let {
                it.text = project.description
            }
        }
         holder.itemView.setOnClickListener { view ->
             val action =HomeFragmentDirections.actionNavigationHomeToProjetDetaills(Projects[position])
             Navigation.findNavController(view).navigate(action)
    }

   }
    // 0. Le ViewHolder
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.projet_title)
        val description = itemView.findViewById<TextView>(R.id.description)
    }
}

