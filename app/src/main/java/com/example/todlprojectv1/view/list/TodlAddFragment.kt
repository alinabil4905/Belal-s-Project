package com.example.todlprojectv1.view.list

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todlprojectv1.R
import com.example.todlprojectv1.database.TodlModelList
import com.example.todlprojectv1.view.TodlViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TodlAddFragment : BottomSheetDialogFragment() {
    private val todlViewModel: TodlViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todl_add, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskTitle: EditText = view.findViewById(R.id.Tasktitle_edittext_add)
        val priority: RadioGroup = view.findViewById(R.id.radio_group)
        val addButton: Button = view.findViewById(R.id.Add_button_addlist)
        val cancleButton: Button = view.findViewById(R.id.Cancle_button_addlist)
        val calnder:Button = view.findViewById(R.id.Calendar_button_add)
        val creationDate = Calendar.getInstance().timeInMillis
        val datePicker = DatePickerDialog(requireActivity())
        var due: String =""

        addButton.setOnClickListener {
            val task = taskTitle.text.toString()
            var priorityRadioButton: RadioButton = view.findViewById(priority.checkedRadioButtonId)
            var prio = priorityRadioButton.text.toString()
                todlViewModel.addList(TodlModelList(task,prio, calnder.text.toString(),creationDate,false))
                dismiss()
        }
            cancleButton.setOnClickListener {
                dismiss()
            }
      datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
          calnder.setText("${year}/${month+1}/$dayOfMonth")
      }
        calnder.setOnClickListener {
            datePicker.show()
        }
    }
}

