package com.practica.ejemplodagger.sis.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.practica.ejemplodagger.R

class ChangeFragment {

    fun change(id:Int=0, string:String="" , fragment:Fragment,
               parentFragmentManager: FragmentManager, back:Boolean=false){

        val bundle = Bundle()
        bundle.putString("detail", string)
        bundle.putInt("id", id)
        fragment.arguments = bundle
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
            R.anim.fade_in, R.anim.fade_out)
        fragmentTransition.replace(R.id.fragment_container,fragment)

        if(back) fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }


}