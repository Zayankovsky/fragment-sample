package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle

class RootFragment : Fragment(R.layout.fragment_root) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val fragment = ScreenFragment.create(0)
            childFragmentManager.commit {
                add(R.id.screen_fragment_container, fragment)
                setPrimaryNavigationFragment(fragment)
            }
        }
    }

    fun push(number: Int) {
        val fragment = ScreenFragment.create(number)
        childFragmentManager.commit {
            for (addedFragment in childFragmentManager.fragments) {
                if (!addedFragment.isHidden) hide(addedFragment)
                setMaxLifecycle(addedFragment, Lifecycle.State.STARTED)
            }
            add(R.id.screen_fragment_container, fragment)
            setPrimaryNavigationFragment(fragment)
            addToBackStack(null)
        }
    }

    fun pop() {
        childFragmentManager.popBackStack()
    }

    fun clear() {
        childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        fun create() = RootFragment()
    }
}