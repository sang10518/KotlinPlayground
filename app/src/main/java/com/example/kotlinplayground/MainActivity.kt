package com.example.kotlinplayground

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    lateinit var mCurFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCurFragment = HomeFragment.newInstance("","")

        toolbar = supportActionBar!!

        navigationView.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        displayFragment(HomeFragment.TAG)
                        return true
                    }
                    R.id.navigation_compass -> {
                        displayFragment(CompassFragment.TAG)
                        return true
                    }
                    R.id.navigation_my -> {
                        displayFragment(MyFragment.TAG)
                        return true
                    }
                    R.id.navigation_misc -> {
                        displayFragment(MiscFragment.TAG)
                        return true
                    }
                    R.id.navigation_notifications -> {
                        displayFragment(NotiFragment.TAG)
                        return true
                    }
                }
                return false
            }

        })

        navigationView.selectedItemId = R.id.navigation_home
    }

    private fun displayFragment(tag: String) {
        val savedFrag: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        savedFrag?.let {
            supportFragmentManager.beginTransaction().hide(mCurFragment).show(savedFrag).commit()
            supportFragmentManager.executePendingTransactions()
            mCurFragment = savedFrag!!
        } ?: run{
            val newFragment: Fragment?
            when(tag){
                HomeFragment.TAG -> newFragment = HomeFragment.newInstance("","")
                CompassFragment.TAG -> newFragment = CompassFragment.newInstance("", "")
                MiscFragment.TAG -> newFragment = MiscFragment.newInstance("","")
                MyFragment.TAG -> newFragment = MyFragment.newInstance("","")
                NotiFragment.TAG -> newFragment = NotiFragment.newInstance("","")
                else -> newFragment = null
            }
            newFragment?.let{
                supportFragmentManager.beginTransaction().hide(mCurFragment).add(R.id.container, newFragment, tag).addToBackStack(null).commit()
                supportFragmentManager.executePendingTransactions()
//                Log.e("swc", "bscount after add: " +supportFragmentManager.backStackEntryCount )
                mCurFragment = newFragment
            }
        }

    }

    override fun onBackPressed(){
//        Log.e("swc", "bscount: " +supportFragmentManager.backStackEntryCount )
        if (supportFragmentManager.backStackEntryCount > 1){
            supportFragmentManager.popBackStackImmediate()
            //move to next top in back stacck
            val topFrag = supportFragmentManager.findFragmentById(R.id.container)
            when(topFrag?.tag){
                HomeFragment.TAG -> navigationView.selectedItemId = R.id.navigation_home
                CompassFragment.TAG -> navigationView.selectedItemId = R.id.navigation_compass
                MiscFragment.TAG -> navigationView.selectedItemId = R.id.navigation_misc
                MyFragment.TAG -> navigationView.selectedItemId = R.id.navigation_my
                NotiFragment.TAG -> navigationView.selectedItemId = R.id.navigation_notifications
            }
        } else {
            finish()
        }
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar.title = "Songs"
                true
            }
            R.id.navigation_compass -> {
                toolbar.title = "Albums"
                true
            }
            R.id.navigation_my -> {
                toolbar.title = "Artists"
                true
            }
        }
        false
    }


}
