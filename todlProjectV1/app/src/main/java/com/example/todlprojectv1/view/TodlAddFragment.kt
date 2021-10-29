package com.example.todlprojectv1.view

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
        val taskTitle: EditText = view.findViewById(R.id.SubTasktitle_edittext_add)
        val priority: RadioGroup = view.findViewById(R.id.radio_group_SubList)
        val addButton: Button = view.findViewById(R.id.Add_button_addlist_SubList)
        val cancleButton: Button = view.findViewById(R.id.Cancle_button_addlist_SubList)
        val calnder:Button = view.findViewById(R.id.Calendar_button_add)
        val calnderinstance = Calendar.getInstance()
        var due: Long? = null

        addButton.setOnClickListener {
            val task = taskTitle.text.toString()
            var priorityRadioButton: RadioButton = view.findViewById(priority.checkedRadioButtonId)
            var prio = priorityRadioButton.text.toString()
                todlViewModel.addList(TodlModelList(task,prio,due,false))
            findNavController().popBackStack()
        }
            cancleButton.setOnClickListener {
                findNavController().popBackStack()
            }
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, _, _, _ ->
                calnderinstance.set(Calendar.YEAR,0)
                calnderinstance.set(Calendar.MONTH,0)
                calnderinstance.set(Calendar.DAY_OF_MONTH,0)
                due = calnderinstance.timeInMillis
            }

        calnder.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateSetListener,
                calnderinstance.get(Calendar.YEAR),
                calnderinstance.get(Calendar.MONTH),
                calnderinstance.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}

