package com.example.todlprojectv1.view.SubList

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todlprojectv1.R
import com.example.todlprojectv1.database.MainTaskWithSubTask
import com.example.todlprojectv1.view.TodlViewModel
import com.example.todlprojectv1.view.list.TodlAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SubListFragment : Fragment() {
    private val todelList = mutableListOf<MainTaskWithSubTask>()
    private val todlViewModel: TodlViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todelsubRecycleView: RecyclerView = view.findViewById(R.id.recycle_View_Sub)
        val todlsubAdapter = TodlSubAdapter(todelList, todlViewModel)

        todelsubRecycleView.adapter = todlsubAdapter
        todlViewModel.todlList.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                todelList.clear()
                todelList.addAll(list)
                todlsubAdapter.notifyDataSetChanged() } })
        val fab: FloatingActionButton = view.findViewById(R.id.floating_subList)
        fab.setOnClickListener {
            var mediaPlayer = MediaPlayer.create(context, R.raw.clicksound)
            mediaPlayer.start()// To start the sound on clicking

            findNavController().navigate(R.id.action_subListFragment_to_subListAddFragment)
        } }
    }
