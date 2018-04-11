package com.jam.utils.homelayoutsimple

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fragments by lazy {
        arrayListOf(
                SimpleFragment.newInstance("Home")
                , SimpleFragment.newInstance("Message")
                , SimpleFragment.newInstance("Worker")
                , SimpleFragment.newInstance("Contract")
                , SimpleFragment.newInstance("Profile"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        viewPager.adapter = PaAdapter(supportFragmentManager, fragments)
        indicatorLayout.setMPager(viewPager)
    }
}
