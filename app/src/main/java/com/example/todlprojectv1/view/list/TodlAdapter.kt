package com.example.todlprojectv1.view.list

import android.graphics.Color
import android.graphics.Paint
import android.inputmethodservice.ExtractEditText
import android.media.MediaPlayer
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todlprojectv1.R
import com.example.todlprojectv1.database.MainTaskWithSubTask
import com.example.todlprojectv1.database.TodlModelList
import com.example.todlprojectv1.view.TodlViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext
/***
after layout finished.
These two classes work together to define how your data is displayed.
The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
The Adapter creates ViewHolder objects as needed, and also sets the data for those views.
The process of associating views to their data is called binding.
When you define your adapter, you need to override three key methods:
onCreateViewHolder()
onBindViewHolder()
getItemCount()
 **/

class TodlAdapter(val listTask: List<MainTaskWithSubTask>, val todlViewModel: TodlViewModel, val manger:FragmentManager): RecyclerView.Adapter<TodlAdapter.TodelViewHolder>() {

    class TodelViewHolder(view:View):RecyclerView.ViewHolder(view){
        val taskTitlea:TextView = view.findViewById(R.id.List_TextView)
        val taskPrioritya:TextView= view.findViewById(R.id.List__prio_TextView)
        val delete: Button = view.findViewById(R.id.delete_button)
        val creationdate:TextView = view.findViewById(R.id.creationdate)
        val duedateshow:TextView =view.findViewById(R.id.textView4)
        val Completed:CheckBox = view.findViewById(R.id.checkBox)
        val edit:Button = view.findViewById(R.id.edit_button)

    }
    /**
     * onCreateViewHolder(): RecyclerView calls this method whenever it needs to create a new ViewHolder.
    The method creates and initializes the ViewHolder and its associated View,
    but does not fill in the view's contents—the ViewHolder has not yet been bound to specific data.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodelViewHolder {
        return TodelViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout2,
                parent,
                false
            )
        )
    }
    /**
     * onBindViewHolder(): RecyclerView calls this method to associate a ViewHolder with data.
    The method fetches the appropriate data and uses the data to fill in the view holder's layout.
    For example, if the RecyclerView displays a list of names,
    the method might find the appropriate name in the list and fill in the view holder's TextView widget.
     */
    override fun onBindViewHolder(holder: TodelViewHolder, position: Int) {


        val list = listTask[position].task

        val creationDate = list.creationDate
        holder.duedateshow.text = list.dueDate
        holder.creationdate.text = SimpleDateFormat("yyyy/MM/d").format(creationDate)
        holder.taskTitlea.text = list.taskTitle.toUpperCase()
        holder.taskPrioritya.text = list.priority
        holder.Completed.isChecked = list.completed
        holder.edit.setOnClickListener {
            val BottomSheet = EditFragment()

            todlViewModel.selectedListMutableLiveData.postValue(list)
            BottomSheet.show(manger,"")

        }


        if (list.dueDate.isNotEmpty()) {

            val currentDate = Date()
            val formata = SimpleDateFormat("yyyy/MM/dd")
            val duedate = formata.parse(list.dueDate)
            if (currentDate>duedate && !list.completed){
                holder.taskTitlea.setBackgroundColor(Color.parseColor("#60FF0000"))// // احمر
            }
            else if (list.completed){
                holder.taskTitlea.setBackgroundColor(Color.parseColor("#9EFF00"))//اخضر


            }
            else{
                holder.taskTitlea.setBackgroundColor(Color.parseColor("#6800B8FF"))//ازرق
            }

        }




        holder.Completed.setOnClickListener(){
            if(holder.Completed.isChecked){
                holder.taskTitlea.setBackgroundColor(Color.parseColor("#9EFF00"))//اخضر
                Toast.makeText(holder.itemView.context, "Completed", Toast.LENGTH_SHORT).show()

            }
            else{
                holder.taskTitlea.setBackgroundColor(Color.parseColor("#6800B8FF"))//ازرق
            }
            list.completed = holder.Completed.isChecked
            todlViewModel.updateList(list)
        }

        when(list.priority){
            "High"-> holder.taskPrioritya.setTextColor(Color.parseColor("#9FFD2E2E"))
            "Med"-> holder.taskPrioritya.setTextColor(Color.parseColor("#A1FFB74D"))
            "Low" -> holder.taskPrioritya.setTextColor(Color.parseColor("#9F6DFF4D"))
        }



        holder.itemView.setOnClickListener{it ->
            todlViewModel.selectedItemId = list.taskId
            todlViewModel.selectedListMutableLiveData.postValue(listTask[position].task)//ارسال السب تاسك الخاصة بالمين الى فراقمنت الساب التاسك
            it.findNavController().navigate(R.id.action_todlListFragment2_to_subListFragment)

        }
        holder.delete.setOnClickListener {
            val alertDialog = AlertDialog
                .Builder(holder.itemView.context,R.style.AlertDialogTheme)
                .setTitle("Delete ${list.taskTitle.uppercase()}")
                .setMessage("Are you sure you want to delete the ${list.taskTitle.uppercase()} list?")
            alertDialog.setPositiveButton("Yes") { _, _ ->

                todlViewModel.deleteList(list)
            }

            alertDialog.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }

            alertDialog.create().show()

        }

    }
    /**
     * getItemCount(): RecyclerView calls this method to get the size of the data set.
    For example, in an address book app, this might be the total number of addresses.
    RecyclerView uses this to determine when there are no more items that can be displayed.
     */
    override fun getItemCount(): Int {
        return listTask.size
    }
}

