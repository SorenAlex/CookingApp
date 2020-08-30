package com.example.cookingapp.avatar

import android.app.AlertDialog
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
import com.example.cookingapp.avatar.headPack.HeadPack
import com.example.cookingapp.avatar.headPack.HeadPackAdapter
import com.example.cookingapp.makeToast

class AvatarBackgroundFragment: Fragment() {

    lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var headPackAdapter: HeadPackAdapter

    private var userCoins = 0

    companion object {
        fun newInstance(): AvatarBackgroundFragment {
            return AvatarBackgroundFragment()
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
            val list = mutableListOf<HeadPack>()
            for (pack in it) {
                if (pack.packSet == "bg") {
                    list.add(pack)
                }
            }

            headPackAdapter.setHeadPacks(list)
        })
        mainViewModel.currentUser.observe(viewLifecycleOwner, Observer {
            userCoins = it.coins.toInt()
        })

        headPackAdapter.onItemClick = {pack ->
            if (!pack.isBought) {
                if (userCoins > 20) {
                    val user = mainViewModel.currentUser.value!!

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("Buy for 20 coins.")
                    builder.setPositiveButton("Buy") {dialog, which ->


                        // buy the exercise pack
                        pack.buy(mainViewModel)
                        mainViewModel.updateHeadPack(pack)
                    }
                    builder.setNegativeButton("Cancel") { dialog, _ ->
                        dialog?.dismiss()
                    }
                    builder.create().show()
                } else {
                    makeToast("Not enough coins", requireContext())
                }
            } else {
                //nothing happens if already bought
                pack.setUser(mainViewModel, true)
            }
        }
    }

    private fun initialiseRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_packs)

        val mLayoutManager = GridLayoutManager(activity, 3)
        headPackAdapter = HeadPackAdapter(requireContext())


        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = headPackAdapter
    }
}