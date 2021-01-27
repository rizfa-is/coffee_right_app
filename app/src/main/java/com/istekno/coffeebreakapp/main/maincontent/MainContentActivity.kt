package com.istekno.coffeebreakapp.main.maincontent

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityMainContentBinding
import com.istekno.coffeebreakapp.main.cart.CartActivity
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFragment
import com.istekno.coffeebreakapp.main.maincontent.order.OrderFragment
import com.istekno.coffeebreakapp.main.maincontent.profile.ProfileFragment
import com.istekno.coffeebreakapp.main.mainpage.MainPageActivity
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class MainContentActivity : BaseActivity<ActivityMainContentBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private lateinit var sharePref: SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main_content
        super.onCreate(null)
        setSupportActionBar(binding.tbMenuMaincontent)
        drawer = binding.drawerLayout
        sharePref = SharedPreferenceUtil(this)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToggle = ActionBarDrawerToggle(this, drawer, binding.tbMenuMaincontent,
            R.string.open,
            R.string.close
        )

        binding.navView.setNavigationItemSelectedListener(this)
        initialHomePage()
        clickListener()
    }

    private fun clickListener() {
        binding.btnSignOut.setOnClickListener {
            showDialogLogout()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbar_cart -> {
                intent<CartActivity>(this)
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        } else super.onBackPressed()
    }

    private fun fragmentProperties(mFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_maincontent, mFragment)
            commit()
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val toolbar = binding.tbMenuMaincontent
        val title = binding.toolbarTitle

        when (item.itemId) {
            R.id.nav_home -> {
                fragmentProperties(HomeFragment(toolbar, title))
            }

            R.id.nav_profile -> {
                fragmentProperties(ProfileFragment(toolbar, title))
            }

            R.id.nav_order -> {
                fragmentProperties(OrderFragment(toolbar, title))
            }
        }
        return false
    }

    private fun initialHomePage() {
        val toolbar = binding.tbMenuMaincontent
        val title = binding.toolbarTitle

        fragmentProperties(HomeFragment(toolbar, title))
    }

    private fun showDialogLogout() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Log Out")
        builder.setMessage("are you sure? Logging out will remove all data from this device.")
        builder.setPositiveButton("Yes") { dialogInterface : DialogInterface, i : Int ->
            sharePref.clear()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface : DialogInterface, i : Int ->}
        builder.show()
    }
}