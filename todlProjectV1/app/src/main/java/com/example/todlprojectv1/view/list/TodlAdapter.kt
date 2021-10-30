package com.example.todlprojectv1.view.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todlprojectv1.R
import com.example.todlprojectv1.database.MainTaskWithSubTask
import com.example.todlprojectv1.view.TodlViewModel


class TodlAdapter(val listTask: List<MainTaskWithSubTask>, val todlViewModel: TodlViewModel): RecyclerView.Adapter<TodlAdapter.TodelViewHolder>() {
    class TodelViewHolder(view:View):RecyclerView.ViewHolder(view){
        val taskTitlea:TextView = view.findViewById(R.id.List_TextView)
        val taskPrioritya:TextView= view.findViewById(R.id.List__prio_TextView)
        val delete: Button = view.findViewById(R.id.delete_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodelViewHolder {
        return TodelViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout2,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodelViewHolder, position: Int) {
        val list = listTask[position].task
        holder.taskTitlea.text = list.taskTitle.toUpperCase()
        holder.taskPrioritya.text = list.priority
        when(list.priority){
            "High"-> holder.taskPrioritya.setBackgroundColor(Color.parseColor("#9FFD2E2E"))
            "Med"-> holder.taskPrioritya.setBackgroundColor(Color.parseColor("#A1FFB74D"))
            "Low" -> holder.taskPrioritya.setBackgroundColor(Color.parseColor("#9F6DFF4D"))
        }

        holder.itemView.setOnClickListener{it ->
            todlViewModel.selectedListMutableLiveData.postValue(list)
            it.findNavController().navigate(R.id.action_todlListFragment2_to_subListFragment)

        }
        holder.delete.setOnClickListener {
        todlViewModel.deleteList(list)
        }
    }

    override fun getItemCount(): Int {
        return listTask.size
    }
}