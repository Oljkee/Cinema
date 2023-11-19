package com.example.lvl1final.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentIntroBinding


class FragmentIntro : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(layoutInflater, container, false)

        viewPager = binding.viewPager
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        _binding?.textViewSkip?.setOnClickListener {
            viewPager.setCurrentItem(viewPagerAdapter.itemCount - 1, true)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                _binding?.apply {
                    when (position) {
                        0 -> {
                            dot1.setImageResource(R.drawable.dot_selected)
                            dot2.setImageResource(R.drawable.dot_unselected)
                            dot3.setImageResource(R.drawable.dot_unselected)
                        }
                        1 -> {
                            dot1.setImageResource(R.drawable.dot_unselected)
                            dot2.setImageResource(R.drawable.dot_selected)
                            dot3.setImageResource(R.drawable.dot_unselected)
                        }
                        2 -> {
                            dot1.setImageResource(R.drawable.dot_unselected)
                            dot2.setImageResource(R.drawable.dot_unselected)
                            dot3.setImageResource(R.drawable.dot_selected)
                        }
                        3 -> {
                            _binding!!.textViewSkip.visibility = View.GONE
                            _binding!!.indicatorContainer.visibility = View.GONE
                        }
                    }
                }
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}