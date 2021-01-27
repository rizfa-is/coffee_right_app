package com.istekno.coffeebreakapp.main.editprofile

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityEditProfileBinding
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseActivityViewModel<ActivityEditProfileBinding, EditProfileViewModel>() {

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;

        const val FIELD_REQUIRED = "Fields cannot be empty"
        const val FIELD_DIGITS_ONLY = "Can only contain numerics"
    }

    private lateinit var sharedPref: SharedPreferenceUtil
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var myCalendar: Calendar
    private lateinit var deadline: DatePickerDialog.OnDateSetListener

    private var pathImage: String? = null
    private var gender: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_profile
        setViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        sharedPref = SharedPreferenceUtil(this)
        userDetail = sharedPref.getAccountUser()

        binding.btnChangeImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }

        myCalendar = Calendar.getInstance()
        deadlineProject()
        setViewModel()
        subscribeLiveData()

        binding.etDob.setOnClickListener{
            DatePickerDialog(
                this, deadline, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnSaveUpdate.setOnClickListener{
            val name = binding.etName.text.toString()
            val phoneNumber = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()

            if (name.isEmpty()) {
                binding.etName.error = FIELD_REQUIRED
                return@setOnClickListener
            }
            if (phoneNumber.isEmpty()) {
                binding.etPhone.error = FIELD_DIGITS_ONLY
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                binding.etAddress.error = FIELD_REQUIRED
                return@setOnClickListener
            }
            when (binding.radioButton.checkedRadioButtonId) {
                binding.female.id -> {
                    gender = "Female"
                }
                binding.male.id -> {
                    gender = "Male"
                }
            }
            if (pathImage != null) {
                viewModel.updateAPI(
                    csId = sharedPref.getPreference().roleID!!,
                    acId = sharedPref.getPreference().acID!!,
                    acName = name,
                    acPhone = phoneNumber,
                    csGender = createPartFromString(gender!!),
                    csBirthday = createPartFromString(binding.etDob.text.toString()),
                    csAddress = createPartFromString(binding.etAddress.text.toString()),
                    image = createPartFromFile(pathImage!!)
                )
            }
        }

        binding.ivBack.setOnClickListener{
            this.finish()
        }
    }

    /*override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_dob -> {
                DatePickerDialog(
                    this, deadline, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_save_update -> {
                val name = binding.etName.text.toString()
                val phoneNumber = binding.etPhone.text.toString()
                val address = binding.etAddress.text.toString()

                if (name.isEmpty()) {
                    binding.etName.error = FIELD_REQUIRED
                    return
                }
                if (phoneNumber.isEmpty()) {
                    binding.etPhone.error = FIELD_DIGITS_ONLY
                    return
                }
                if (address.isEmpty()) {
                    binding.etAddress.error = FIELD_REQUIRED
                    return
                }
                when (binding.radioButton.checkedRadioButtonId) {
                    binding.female.id -> {
                        gender = "Female"
                    }
                    binding.male.id -> {
                        gender = "Male"
                    }
                }
                if (pathImage != null) {
                    viewModel.updateAPI(
                        csId = sharedPref.getPreference().roleID!!,
                        acId = sharedPref.getPreference().acID!!,
                        acName = name,
                        acPhone = phoneNumber,
                        csGender = createPartFromString(gender!!),
                        csBirthday = createPartFromString(binding.etDob.text.toString()),
                        csAddress = createPartFromString(binding.etAddress.text.toString()),
                        image = createPartFromFile(pathImage!!)
                    )
                }
            }
            R.id.iv_back -> {
                this.finish()
            }
        }
    }*/

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imageProfile.setImageURI(data?.data)
            pathImage = getPath(this, data?.data!!)
        }
    }

    private fun createPartFromString(descriptionString: String): RequestBody {
        return descriptionString.toRequestBody(MultipartBody.FORM)
    }

    private fun createPartFromFile(path: String): MultipartBody.Part {
        val file = File(path)
        val reqFile: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, reqFile)
    }

    private fun getPath(context: Context, uri: Uri): String? {
        var realPath = String()
        uri.path?.let { path ->

            val databaseUri: Uri
            val selection: String?
            val selectionArgs: Array<String>?
            if (path.contains("/document/image:")) {
                databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                selection = "_id=?"
                selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
            } else {
                databaseUri = uri
                selection = null
                selectionArgs = null
            }

            try {
                val column = "_data"
                val projection = arrayOf(column)
                val cursor = context.contentResolver.query(
                    databaseUri,
                    projection,
                    selection,
                    selectionArgs,
                    null
                )
                cursor?.let {
                    if (it.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndexOrThrow(column)
                        realPath = cursor.getString(columnIndex)
                    }
                    cursor.close()
                }
            } catch (e: Exception) {
                println(e)
            }
        }
        return realPath
    }


    private fun deadlineProject() {
        deadline = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_dob)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        viewModel.setServiceCustomer(createApi(this))
        viewModel.setServiceAccount(createApi(this))
    }

    private fun subscribeLiveData() {
        viewModel.onSuccessLiveData.observe(this) {
            if (it) {
                setResult(RESULT_OK)
                this.finish()
            }
        }

    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context)!!.create(ApiService::class.java)
    }
}