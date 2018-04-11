package com.jam.utils.homelayoutsimple

/**
 * Created by hejiaming on 2018/4/11.
 * @desciption:
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.simple_frag.*

/**
 * Created by hejiaming on 2018/3/24.
 * @desciption:
 */
class SimpleFragment : Fragment() {

    val simpleName by lazy { arguments.getString("KEY_SIMPLE_NAME") }

    companion object {
        fun newInstance(simpleName: String): Fragment {
            val argument = Bundle()
            argument.putString("KEY_SIMPLE_NAME", simpleName)
            val fragment = SimpleFragment()
            fragment.arguments = argument
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.simple_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = simpleName
    }
}