package com.example.geto.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
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
            image?.let{
                if(position%8==0)it.setImageResource(R.drawable.image6)
                else if(position%8==1) it.setImageResource(R.drawable.image7)
                else if(position%8==2) it.setImageResource(R.drawable.image8)
                else if(position%8==3) it.setImageResource(R.drawable.image9)
                else if(position%8==4) it.setImageResource(R.drawable.image10)
                else if(position%8==5) it.setImageResource(R.drawable.image11)
                else if(position%8==6) it.setImageResource(R.drawable.image12)
                else if(position%8==7) it.setImageResource(R.drawable.imge5)
            }
            nbrTasks?.let{
                it.text=project.tasks.size.toString() + " tasks"
            }
            nbrParticipants?.let{
                it.text=project.users_id.size.toString()+" Participants"
            }
            progress?.let{
                var level =0
                var n=0
                for(task in project.tasks){
                    n++
                    if(task.finished) level++
                }
                progress.progress=((level*100)/n)
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
        val image=itemView.findViewById<ImageView>(R.id.projectImage)
        val nbrTasks=itemView.findViewById<TextView>(R.id.nbr_tasks)
        val nbrParticipants=itemView.findViewById<TextView>(R.id.nbr_participent)
        val progress=itemView.findViewById<ProgressBar>(R.id.progressBar2)
    }
}

