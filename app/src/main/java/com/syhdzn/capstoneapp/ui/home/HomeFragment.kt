package com.syhdzn.capstoneapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.syhdzn.capstoneapp.R
import com.syhdzn.capstoneapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    private val imageIds = listOf(
        R.drawable.makanan1,
        R.drawable.makanan2,
        R.drawable.makanan3
    )

    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private val autoScrollRunnable = Runnable {
        val currentPosition = binding.vpHomeCarousel.currentItem
        val newPosition = (currentPosition + 1) % imageIds.size
        binding.vpHomeCarousel.currentItem = newPosition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        recyclerView = binding.rvHome

        setupViewPager()
        setupPageIndicator()
        setupRecyclerView()
    }


    private fun setupViewPager() {
        val viewPager: ViewPager2 = binding.vpHomeCarousel
        val adapter = HomeAdapter(imageIds)
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.setCurrentPage(position)
                startAutoScroll()
            }
        })
        viewModel.currentPage.observe(viewLifecycleOwner, { position ->
            updatePageIndicator(position)
        })
    }

    private fun setupPageIndicator() {
        val indicatorContainer = binding.indicatorContainer
        val context = indicatorContainer.context

        for (i in imageIds.indices) {
            val indicator = LayoutInflater.from(context).inflate(
                R.layout.carousel_indicator, indicatorContainer, false
            ) as ImageView
            indicatorContainer.addView(indicator)
        }
    }

    private fun updatePageIndicator(position: Int) {
        val indicatorContainer = binding.indicatorContainer
        for (i in 0 until indicatorContainer.childCount) {
            val indicator = indicatorContainer.getChildAt(i) as ImageView
            indicator.setImageResource(
                if (i == position % imageIds.size) R.drawable.indicator_dot_selected
                else R.drawable.indicator_dot_unselected
            )
        }
    }

    private fun startAutoScroll() {
        stopAutoScroll()
        val autoScrollInterval = 4000L
        autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollInterval)
    }

    private fun stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val itemList = createItemList()
        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter
    }

    private fun createItemList(): List<Item> {
        val itemList = mutableListOf<Item>()
        itemList.add(Item("Item 1", R.drawable.makanan1))
        itemList.add(Item("Item 2", R.drawable.makanan2))
        itemList.add(Item("Item 1", R.drawable.makanan1))
        itemList.add(Item("Item 2", R.drawable.makanan2))
        itemList.add(Item("Item 1", R.drawable.makanan1))
        itemList.add(Item("Item 2", R.drawable.makanan2))
        itemList.add(Item("Item 1", R.drawable.makanan1))
        itemList.add(Item("Item 2", R.drawable.makanan2))
        itemList.add(Item("Item 1", R.drawable.makanan1))
        itemList.add(Item("Item 2", R.drawable.makanan2))
        // ... tambahkan lebih banyak item sesuai kebutuhan
        return itemList
    }
}