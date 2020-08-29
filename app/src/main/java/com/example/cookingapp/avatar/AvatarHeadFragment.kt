package com.example.cookingapp.avatar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingapp.MainViewModel
import com.example.cookingapp.R
import com.example.cookingapp.avatar.headPack.HeadPackAdapter

class AvatarHeadFragment: Fragment() {

    lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var headPackAdapter: HeadPackAdapter

    companion object {
        fun newInstance(): AvatarHeadFragment {
            return AvatarHeadFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_avatar_packs, container, false)
        initialiseRecyclerView()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.currentHeadPacks.observe(viewLifecycleOwner, Observer {
            headPackAdapter.setHeadPacks(it)
        })
    }

    private fun initialiseRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_packs)

        val mLayoutManager = GridLayoutManager(activity, 3)
        headPackAdapter = HeadPackAdapter(requireContext())


        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = headPackAdapter
    }
}