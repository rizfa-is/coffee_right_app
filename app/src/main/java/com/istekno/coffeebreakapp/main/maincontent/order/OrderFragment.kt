package com.istekno.coffeebreakapp.main.maincontent.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragmentViewModel
import com.istekno.coffeebreakapp.databinding.FragmentOrderBinding
import com.istekno.coffeebreakapp.main.maincontent.MainContentActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class OrderFragment(private val toolbar: MaterialToolbar, private val title: TextView) : BaseFragmentViewModel<FragmentOrderBinding, OrderViewModel>(),
OrderAdapter.OnListOrderClickListenerr{

    private var listOrder = ArrayList<OrderResponse.Data>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLayout = R.layout.fragment_order
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SharedPreferenceUtil(requireContext())
        viewModel.setSharedPref(sharedPref)
        val service = ApiClient.getApiClient(requireContext())?.create(OrderService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        viewModel.callOrderApi()

        setRecyclerView(view)
        subscribeLiveData()
        subscribeLoadingLiveData()
        viewListener(view)

    }

    private fun viewListener(view: View) {
        binding.btnStartOrder.setOnClickListener {
            intent<MainContentActivity>(view.context)
        }
    }

    private fun subscribeLiveData() {
        viewModel.getListData.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.listData.observe(viewLifecycleOwner) { list->
                    (binding.rvOrderHistory.adapter as OrderAdapter).setData(list)
                }
                binding.rvOrderHistory.visibility = View.VISIBLE
                binding.historyNotFound.visibility = View.GONE
                binding.tvInfo.visibility = View.GONE
                binding.tvNoHistoryYet.visibility = View.GONE
            } else {
                binding.historyNotFound.visibility = View.VISIBLE
                binding.tvInfo.visibility = View.GONE
                binding.tvNoHistoryYet.visibility = View.GONE
            }
        })
    }

    private fun subscribeLoadingLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun setRecyclerView(view: View) {
        binding.rvOrderHistory.isNestedScrollingEnabled = false
        binding.rvOrderHistory.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        val adapter = OrderAdapter(listOrder, this)
        binding.rvOrderHistory.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        toolbar.menu.findItem(R.id.toolbar_cart).isVisible = false
        toolbar.menu.findItem(R.id.toolbar_search).isVisible = false

        title.text = "Order"
    }

    override fun onOrderItemClicked(position: Int) {
        Toast.makeText(requireContext(), "item ${listOrder[position].orderDetailId} clicked", Toast.LENGTH_SHORT).show()

    }
}