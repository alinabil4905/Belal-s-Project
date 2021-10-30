package com.example.todlprojectv1.view.SubList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todlprojectv1.R
import com.example.todlprojectv1.database.MainTaskWithSubTask
import com.example.todlprojectv1.database.TodlModelSubList
import com.example.todlprojectv1.view.TodlViewModel

class TodlSubAdapter(val listsubTask: List<MainTaskWithSubTask>, val todlViewModel: TodlViewModel): RecyclerView.Adapter<TodlSubAdapter.TodelSubViewHolder>() {
    class TodelSubViewHolder(view: View):RecyclerView.ViewHolder(view){
        val sub_task:TextView = view.findViewById(R.id.subList_TextView)
        val subtaskprio:TextView = view.findViewById(R.id.subList__prio_TextView)
        val subdelete:Button = view.findViewById(R.id.subdelete_button)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodelSubViewHolder {
        return TodelSubViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodelSubViewHolder, position: Int) {
        val lists = listsubTask[position].subTask
        if (lists != null) {
            holder.sub_task.text = lists.subTask
        }
        if (lists != null) {
            holder.subtaskprio.text =lists.prioritysub
        }
        if (lists != null) {
            when(lists.prioritysub){
                "High"-> holder.subtaskprio.setBackgroundColor(Color.parseColor("#9FFD2E2E"))
                "Med"-> holder.subtaskprio.setBackgroundColor(Color.parseColor("#A1FFB74D"))
                "Low" -> holder.subtaskprio.setBackgroundColor(Color.parseColor("#9F6DFF4D"))
            }
            holder.subdelete.setOnClickListener {
                todlViewModel.deletesubList(lists)
            }
        }

    }


    override fun getItemCount(): Int {
    return listsubTask.size
    }

}


