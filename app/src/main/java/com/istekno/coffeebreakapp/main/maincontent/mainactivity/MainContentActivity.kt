package com.istekno.coffeebreakapp.main.maincontent.mainactivity

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityMainContentBinding
import com.istekno.coffeebreakapp.main.cart.CartActivity
import com.istekno.coffeebreakapp.main.checkout.CheckoutActivity
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFragment
import com.istekno.coffeebreakapp.main.maincontent.order.OrderFragment
import com.istekno.coffeebreakapp.main.maincontent.profile.ProfileFragment
import com.istekno.coffeebreakapp.main.mainpage.MainPageActivity
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryFragment
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class MainContentActivity : BaseActivityViewModel<ActivityMainContentBinding, MainContentViewModel>(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val img = "http://184.72.105.243:3000/images/"
    }

    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private lateinit var sharePref: SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main_content
        setViewModel = ViewModelProvider(this).get(MainContentViewModel::class.java)
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbMenuMaincontent)

        val service = ApiClient.getApiClient(this)!!.create(MainContentService::class.java)
        sharePref = SharedPreferenceUtil(this)
        viewModel.setService(service)
        viewModel.setSharePref(sharePref)

        setNavHeaderData()
        setDrawer()

        initialHomePage()
        viewListener()
    }

    private fun setDrawer() {
        drawer = binding.drawerLayout

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToggle = ActionBarDrawerToggle(this, drawer, binding.tbMenuMaincontent,
            R.string.open,
            R.string.close
        )
    }

    private fun setNavHeaderData() {
        val navHeader = binding.navView.getHeaderView(0)
        val avatar = navHeader.findViewById<ImageView>(R.id.img_nav_header)
        val name = navHeader.findViewById<TextView>(R.id.tv_nav_drawer_name)
        val email = navHeader.findViewById<TextView>(R.id.tv_nav_drawer_email)
        val data = intent.getIntExtra("data", -1)
        val imageUri = intent.getParcelableExtra<Uri>("image_URI")
        val newName = intent.getStringExtra("name")
        val newEmail = intent.getStringExtra("email")

        if (data == 1) {
            if (imageUri != null) {
                avatar.setImageURI(imageUri)
            } else {
                Glide.with(this).load(img + sharePref.getPreference().acImage)
                    .placeholder(R.drawable.ic_avatar_en).into(avatar)
            }

            name.text = newName
            email.text = newEmail

            viewModel.getCustomerData()
        } else {
            Glide.with(this).load(img + sharePref.getPreference().acImage)
                .placeholder(R.drawable.ic_avatar_en).into(avatar)

            name.text = sharePref.getPreference().acName
            email.text = sharePref.getPreference().acEmail
        }
    }

    private fun viewListener() {
        binding.btnSignOut.setOnClickListener {
            showDialogLogout()
        }

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun initialHomePage() {
        val toolbar = binding.tbMenuMaincontent
        val title = binding.toolbarTitle
        val navDrawer = binding.navView
        val data = intent.getIntExtra("data", -1)

        when (data) {
            0 -> { fragmentProperties(OrderFragment(toolbar, title, navDrawer)) }
            1 -> { fragmentProperties(ProfileFragment(toolbar, title, navDrawer)) }
            else -> fragmentProperties(HomeFragment(toolbar, title, navDrawer))
        }
    }

    private fun showDialogLogout() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure ?\nLogging out will remove all data\nfrom this device.")
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            sharePref.clear()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { _: DialogInterface, i : Int ->}
        builder.show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbar_cart -> {
                intent<CartActivity>(this)
            }
            R.id.toolbar_search -> {
                intent<CheckoutActivity>(this)
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        } else super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val toolbar = binding.tbMenuMaincontent
        val title = binding.toolbarTitle
        val navDrawer = binding.navView

        when (item.itemId) {
            R.id.nav_home -> {
                fragmentProperties(HomeFragment(toolbar, title, navDrawer))
            }

            R.id.nav_profile -> {
                fragmentProperties(ProfileFragment(toolbar, title, navDrawer))
            }

            R.id.nav_order -> {
                fragmentProperties(OrderFragment(toolbar, title, navDrawer))
            }

            R.id.nav_order_history -> {
                fragmentProperties(OrderHistoryFragment(toolbar, title, navDrawer))
            }
        }
        return false
    }

}