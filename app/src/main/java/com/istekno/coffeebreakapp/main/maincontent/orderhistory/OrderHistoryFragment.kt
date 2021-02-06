package com.istekno.coffeebreakapp.main.maincontent.orderhistory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragmentViewModel
import com.istekno.coffeebreakapp.databinding.FragmentOrderHistoryBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class OrderHistoryFragment(
    private val title: TextView,
    private val navDrawer: NavigationView
) : BaseFragmentViewModel<FragmentOrderHistoryBinding, OrderHistoryViewModel>(),
    OrderHistoryRecyclerViewAdapter.OnListOrderHistoryClickListener {

    companion object {
        const val ORDER_HISTORY_KEY = "orID_KEY"
    }

    private lateinit var sharedPref: SharedPreferenceUtil
    private var listOrderHistory = ArrayList<OrderHistoryResponse.Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setLayout = R.layout.fragment_order_history
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        navDrawer.setCheckedItem(R.id.nav_order_history)

        sharedPref = SharedPreferenceUtil(view.context)

        val service =
            ApiClient.getApiClient(view.context)?.create(OrderHistoryApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        val customerId = sharedPref.getPreference().roleID

        if (sharedPref.getPreference().level == 0) {
            viewModel.callOrderHistoryApi(customerId!!)
        } else {
            viewModel.callOrderHistoryByAdminApi()
        }

        setRecyclerView(sharedPref.getPreference().level!!)
        subscribeLiveData()
        onClickListener(view)
    }

    private fun onClickListener(view: View) {
        binding.btnStartOrder.setOnClickListener {
            intent<MainContentActivity>(view.context)
            activity?.finishAffinity()
        }
    }

    private fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvOrderHistory.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvOrderHistory.visibility = View.VISIBLE
            }
        }

        viewModel.isGetList.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.listData.observe(viewLifecycleOwner, Observer { list ->
                    (binding.rvOrderHistory.adapter as OrderHistoryRecyclerViewAdapter).addList(list)
                })
                binding.historyNotFound.visibility = View.GONE
                binding.rvOrderHistory.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else {
                binding.historyNotFound.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.rvOrderHistory.visibility = View.GONE
            }
        })

    }

    private fun setRecyclerView(role: Int) {
        binding.rvOrderHistory.isNestedScrollingEnabled = false
        binding.rvOrderHistory.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = OrderHistoryRecyclerViewAdapter(listOrderHistory, this, role)
        binding.rvOrderHistory.adapter = adapter
    }

    override fun onOrderHistoryItemClicked(position: Int) {
        val sendIntent = Intent(context, DetailOrderHistoryActivity::class.java)
        sendIntent.putExtra(ORDER_HISTORY_KEY, listOrderHistory[position])
        startActivity(sendIntent)
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        title.text = "Order History"
    }
}