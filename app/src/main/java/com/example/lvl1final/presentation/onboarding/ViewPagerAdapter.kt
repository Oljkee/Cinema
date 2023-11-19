package com.example.lvl1final.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lvl1final.R
import com.example.lvl1final.presentation.Arguments

class ViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val images = listOf(
        R.drawable.onboarding_first_image,
        R.drawable.onboarding_second_image,
        R.drawable.onboarding_third_image,
        R.drawable.onboarding_first_image
    )
    private val descriptions: Array<String> =
        fragment.resources.getStringArray(R.array.onboarding_description)

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val onBoardingItem : Fragment
        val bundle = Bundle()
        if(position == itemCount - 1) {
            onBoardingItem = OnBoardingToMainTransitionFragment()
            bundle.putInt(Arguments.ARG_IMAGE, images[position])
        } else {
            onBoardingItem = OnBoardingItem()
            bundle.putInt(Arguments.ARG_IMAGE, images[position])
            bundle.putString(Arguments.ARG_DESCRIPTION, descriptions[position])
        }
        onBoardingItem.arguments = bundle
        return onBoardingItem
    }
}