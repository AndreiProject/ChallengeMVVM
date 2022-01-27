package com.paramonov.challenge.ui.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.ActivityMainBinding
import java.lang.RuntimeException
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener, ToolbarContract {

    private var binding: ActivityMainBinding? = null
    private val mBinding get() = binding!!
    private val model: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        mBinding.navView.setNavigationItemSelectedListener(this)
        initBarDrawerToggle()
    }

    override fun setTitleToolbar(resId: Int) {
        mBinding.toolbar.setTitle(resId)
    }

    private fun initBarDrawerToggle() {
        val drawer = mBinding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            mBinding.toolbar,
            R.string.nav_open_drawer,
            R.string.nav_close_drawer
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_statistics -> {
                getNavController().navigate(R.id.statisticsFragment, null, getNavOptions())
            }
            R.id.nav_collection -> {
                getNavController().navigate(R.id.collectionFragment, null, getNavOptions())
            }
            R.id.nav_category_list -> {
                getNavController().navigate(R.id.categoryListFragment, null, getNavOptions())
            }
            R.id.nav_planner -> {
                getNavController().navigate(R.id.plannerFragment, null, getNavOptions())
            }
            R.id.nav_arithmetic -> {
                getNavController().navigate(R.id.arithmeticActivity)
            }
            else -> throw RuntimeException("Item not found")
        }

        val drawer = mBinding.drawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_log_out -> {
                model.logOut()
                getNavController().navigate(R.id.loginActivity)
                finish()
                true
            }
            R.id.action_settings -> {
                getNavController().navigate(R.id.settingsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val driver = mBinding.drawerLayout
        if (driver.isDrawerOpen(GravityCompat.START)) {
            driver.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}