package com.istekno.coffeebreakapp.main.editprofile

import android.Manifest
import android.annotation.SuppressLint
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
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityEditProfileBinding
import com.istekno.coffeebreakapp.main.maincontent.maincontent.MainContentActivity
import com.istekno.coffeebreakapp.main.maincontent.profile.ProfileModel
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

class EditProfileActivity :
    BaseActivityViewModel<ActivityEditProfileBinding, EditProfileViewModel>() {
    private lateinit var sharedPref: SharedPreferenceUtil
    private lateinit var myCalendar: Calendar
    private lateinit var deadline: DatePickerDialog.OnDateSetListener

    private var pathImage: String? = null
    private var imageUri: Uri? = null
    private var gender: String? = null

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
        const val img = "http://184.72.105.243:3000/images/"

    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_profile
        setViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        super.onCreate(savedInstanceState)

        sharedPref = SharedPreferenceUtil(this)

        val data = intent.getParcelableExtra<ProfileModel>("Data")
        binding.etName.setText(data?.accountName)
        binding.etAddress.setText(data?.accountAddress)
        binding.etPhone.setText(data?.accountPhone)
        binding.etEmail.setText(data?.accountEmail)
        Glide.with(this).load(img + data?.accountImage)
            .placeholder(R.drawable.ic_avatar_en).into(binding.imageProfile)
        binding.etDob.setText(data?.accountBirthday?.split('T')?.get(0))

        gender = when (binding.radioButton.checkedRadioButtonId) {
            binding.female.id -> {
                "Female"
            }
            binding.male.id -> {
                "Male"
            }
            else -> ""
        }

        when (data?.accountGender) {
            "Female" -> {
                binding.female.isChecked = true
            }
            "Male" -> {
                binding.male.isChecked = true
            }
            else -> {
                binding.female.isChecked = false
                binding.male.isChecked = false

            }
        }

        myCalendar = Calendar.getInstance()
        deadlineProject()
        setViewModel()
        subscribeLiveData()

        binding.btnChangeImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }

        binding.etDob.setOnClickListener {
            DatePickerDialog(
                this, deadline, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnSaveUpdate.setOnClickListener {
            val acName = binding.etName.text.toString()
            val acPhone = binding.etPhone.text.toString()
            val acEmail = binding.etEmail.text.toString()

            gender = when (binding.radioButton.checkedRadioButtonId) {
                binding.female.id -> {
                    "Female"
                }
                binding.male.id -> {
                    "Male"
                } else -> ""
            }

            if (sharedPref.getPreference().roleID != 0) {
                if (pathImage != null) {
                    viewModel.updateAPIAccount(
                        acId = sharedPref.getPreference().acID!!,
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone
                    )
                    viewModel.updateAPICustomer(
                        csId = sharedPref.getPreference().roleID!!,
                        csBirthday = createPartFromString(binding.etDob.text.toString()),
                        csAddress = createPartFromString(binding.etAddress.text.toString()),
                        csGender = createPartFromString(gender!!),
                        image = createPartFromFile(pathImage!!)
                    )
                } else {
                    viewModel.updateAPIAccount(
                        acId = sharedPref.getPreference().acID!!,
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone
                    )
                    viewModel.updateAPICustomer(
                        csId = sharedPref.getPreference().roleID!!,
                        csBirthday = createPartFromString(binding.etDob.text.toString()),
                        csAddress = createPartFromString(binding.etAddress.text.toString()),
                        csGender = createPartFromString(gender!!)
                    )
                }
            }

            moveActivity(acName, acEmail)
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

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
            imageUri = data.data
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

    private fun getPath(context: Context, uri: Uri): String {
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

    private fun moveActivity(name: String, email: String) {
        val sendIntent = Intent(this, MainContentActivity::class.java)
        sendIntent.putExtra("data", 1)
        sendIntent.putExtra("image_URI", imageUri)
        sendIntent.putExtra("name", name)
        sendIntent.putExtra("email", email)
        startActivity(sendIntent)
        finishAffinity()
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
        viewModel.setService(createApi(this))
    }

    private fun subscribeLiveData() {
        viewModel.onSuccessLiveData.observe(this) {
            if (it) {
                setResult(RESULT_OK)
            }
        }

    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context)!!.create(ApiService::class.java)
    }

}
