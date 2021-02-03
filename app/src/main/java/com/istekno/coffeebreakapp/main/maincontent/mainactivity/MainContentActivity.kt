package com.istekno.coffeebreakapp.main.maincontent.mainactivity

import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.navigation.NavigationView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityMainContentBinding
import com.istekno.coffeebreakapp.main.cart.CartActivity
import com.istekno.coffeebreakapp.main.detailproduct.DetailProductActivity
import com.istekno.coffeebreakapp.main.maincontent.homepage.GetProductResponse
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFavoriteAdapter
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFragment
import com.istekno.coffeebreakapp.main.maincontent.order.OrderFragment
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryFragment
import com.istekno.coffeebreakapp.main.maincontent.profile.ProfileFragment
import com.istekno.coffeebreakapp.main.mainpage.MainPageActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class MainContentActivity :
    BaseActivityViewModel<ActivityMainContentBinding, MainContentViewModel>(),
    NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val img = "http://184.72.105.243:3000/images/"
        private val listFilter = arrayOf("All", "Food", "Drink")
    }

    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private lateinit var sharePref: SharedPreferenceUtil
    private var doubleBackToExitPressedOnce = false

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
        chipViewInit()

        initialHomePage()
        setRecyclerView()
        subscribeLiveData()
        viewListener()
    }

    private fun setDrawer() {
        drawer = binding.drawerLayout

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToggle = ActionBarDrawerToggle(
            this, drawer, binding.tbMenuMaincontent,
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

            viewModel.updateSharedPref()
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

        binding.cgFilter.setOnCheckedChangeListener { _, checkedId ->
            val chip: Chip = findViewById(checkedId)
            val id = chip.id

            viewModel.getAllProductByQuery(0, "", id)
        }

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun initialHomePage() {
        val toolbar = binding.tbMenuMaincontent
        val title = binding.toolbarTitle
        val navDrawer = binding.navView
        val data = intent.getIntExtra("data", -1)

        when (data) {
            0 -> {
                fragmentProperties(OrderFragment(toolbar, title, navDrawer))
            }
            1 -> {
                fragmentProperties(ProfileFragment(toolbar, title, navDrawer))
            }
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
            finishAffinity()
        }
        builder.setNegativeButton("No") { _: DialogInterface, i: Int -> }
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

    private fun setRecyclerView() {
        val rvAdapter = HomeFavoriteAdapter()

        binding.rvSearchListProduct.apply {
            rvAdapter.notifyDataSetChanged()
            layoutManager = GridLayoutManager(this@MainContentActivity, 2)

            rvAdapter.setOnItemClicked(object : HomeFavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: GetProductResponse.DataProduct) {
                    val sendIntent =
                        Intent(this@MainContentActivity, DetailProductActivity::class.java)
                    sendIntent.putExtra(HomeFragment.HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }
    }

    private fun chipViewInit() {
        for (element in listFilter) {
            val chip = layoutInflater.inflate(
                R.layout.item_chipgroup_choice,
                binding.cgFilter,
                false
            ) as Chip

            chip.id = listFilter.indexOf(element)
            chip.text = element
            binding.cgFilter.addView(chip)
        }
    }

    private fun subscribeLiveData() {
        viewModel.productAction.observe(this, {
            if (it) {
                binding.pgHomeengfrg.visibility = View.VISIBLE
                binding.rvSearchListProduct.visibility = View.GONE
            } else {
                binding.pgHomeengfrg.visibility = View.GONE
                binding.rvSearchListProduct.visibility = View.VISIBLE
            }
        })

        viewModel.listProduct.observe(this, {
            (binding.rvSearchListProduct.adapter as HomeFavoriteAdapter).setData(it)
        })

        viewModel.isFailedStatus.observe(this, {
            if (it) {
                binding.pgHomeengfrg.visibility = View.GONE
                binding.rvSearchListProduct.visibility = View.GONE

                binding.imgDataNotFound.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.VISIBLE
            } else {
                binding.imgDataNotFound.visibility = View.GONE
                binding.tvNotFound.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.toolbar_search).actionView as SearchView
        val searchItem = menu.findItem(R.id.toolbar_search)
        var checkedID = binding.cgFilter.checkedChipId

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "ex: Hazelnut Latte"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.cgFilter.setOnCheckedChangeListener { _, checkedId ->
                        val chip: Chip = findViewById(checkedId)
                        val id = chip.id
                        checkedID = id

                        viewModel.getAllProductByQuery(1, query, id)
                    }

                    viewModel.getAllProductByQuery(1, query, checkedID)
                } else if (query == " ") {
                    binding.cgFilter.setOnCheckedChangeListener { _, checkedId ->
                        val chip: Chip = findViewById(checkedId)
                        val id = chip.id
                        checkedID = id

                        viewModel.getAllProductByQuery(0, " ", id)
                    }

                    viewModel.getAllProductByQuery(0, "", checkedID)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! >= 5) {
                    binding.cgFilter.setOnCheckedChangeListener { _, checkedId ->
                        val chip: Chip = findViewById(checkedId)
                        val id = chip.id
                        checkedID = id

                        viewModel.getAllProductByQuery(1, newText, id)
                    }

                    viewModel.getAllProductByQuery(1, newText, checkedID)
                } else if (newText == "") {
                    binding.cgFilter.setOnCheckedChangeListener { _, checkedId ->
                        val chip: Chip = findViewById(checkedId)
                        val id = chip.id
                        checkedID = id

                        viewModel.getAllProductByQuery(0, " ", id)
                    }

                    viewModel.getAllProductByQuery(0, "", checkedID)
                }

                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                binding.cgFilter.check(0)

                binding.flMaincontent.visibility = View.GONE
                binding.toolbarTitle.visibility = View.GONE

                binding.clSearch.visibility = View.VISIBLE
                viewModel.getAllProductByQuery(0, "", 0)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                binding.flMaincontent.visibility = View.VISIBLE
                binding.toolbarTitle.visibility = View.VISIBLE

                binding.clSearch.visibility = View.GONE
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_cart -> {
                intent<CartActivity>(this)
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        showToast("Please click BACK again to exit")
        Handler(mainLooper).postDelayed( { doubleBackToExitPressedOnce = false }, 2000)
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

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}