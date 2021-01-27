import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragmentViewModel
import com.istekno.coffeebreakapp.databinding.FragmentProfileBinding
import com.istekno.coffeebreakapp.main.editpassword.EditPasswordActivity
import com.istekno.coffeebreakapp.main.editprofile.EditProfileActivity
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeAdapter
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class ProfileFragment(private val toolbar: MaterialToolbar, private val title: TextView) : BaseFragmentViewModel<FragmentProfileBinding, ProfileViewModel>() {

    companion object {
        const val img = "http://184.72.105.243:3000/images/"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLayout = R.layout.fragment_profile
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        val service = ApiClient.getApiClient(view.context)!!.create(ProfileService::class.java)
        val preference = SharedPreferenceUtil(view.context)

        viewModel.setService(service)
        viewModel.setSharedPref(preference)

        viewModel.getCustomerByID()
        subscribeLiveData(view)
        viewListener(view)
    }

    private fun viewListener(view: View) {
        binding.btnEditProfile.setOnClickListener {
            intent<EditProfileActivity>(view.context)
        }
        binding.btnEditPassword.setOnClickListener {
            intent<EditPasswordActivity>(view.context)
        }
        binding.tvProfileSeemore1.setOnClickListener {
            intent<OrderHistoryActivity>(view.context)
        }
    }

    private fun subscribeLiveData(view: View) {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.pgProfile.visibility = View.VISIBLE
                binding.svProfile.visibility = View.GONE
            } else {
                binding.pgProfile.visibility = View.GONE
                binding.svProfile.visibility = View.VISIBLE
            }
        })

        viewModel.listData.observe(viewLifecycleOwner, {
            binding.model = it[0]

            Glide.with(view.context).load(img + it[0].accountImage)
                .placeholder(R.drawable.ic_avatar_en).into(binding.shapeableImageView2)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        toolbar.menu.findItem(R.id.toolbar_cart).isVisible = false
        toolbar.menu.findItem(R.id.toolbar_search).isVisible = false

        title.text = "My Profile"
    }
}