package com.istekno.coffeebreakapp.main.editprofile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityEditProfileBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity
import com.istekno.coffeebreakapp.main.maincontent.profile.ProfileModel
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.Dialog
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EditProfileActivity : BaseActivityViewModel<ActivityEditProfileBinding, EditProfileViewModel>() {
    private lateinit var sharedPref: SharedPreferenceUtil
    private lateinit var myCalendar: Calendar
    private lateinit var deadline: DatePickerDialog.OnDateSetListener
    private lateinit var dialog: Dialog

    private var pathImage: String? = null
    private var imageUri: Uri? = null
    private var gender: String? = null
    private var myBirthday = ""

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
        const val img = "http://184.72.105.243:3000/images/"

        const val FIELD_REQUIRED = "Field must not empty"
        const val FIELD_IS_NOT_VALID = "Email format is not valid\nRequired '@' and '.' character"

    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_profile
        setViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        super.onCreate(savedInstanceState)

        sharedPref = SharedPreferenceUtil(this)
        dialog = Dialog()

        val data = intent.getParcelableExtra<ProfileModel>("Data")
        val date = intent.getParcelableExtra<ProfileModel>("Data")?.accountBirthday?.split('T')?.get(0)

        binding.etName.setText(data?.accountName)
        binding.etAddress.setText(data?.accountAddress)
        binding.etPhone.setText(data?.accountPhone)
        binding.etEmail.setText(data?.accountEmail)

        if (date != null) {
            Glide.with(this).load(img + data?.accountImage)
                .placeholder(R.drawable.ic_avatar_en).into(binding.imageProfile)
            binding.etDob.setText(dateFormatter(date))
            myBirthday = date
        }

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
        dateOfBirth()
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
            val csBirthday = myBirthday
            val delAddress = binding.etAddress.text.toString()
            val csGender = binding.radioButton.checkedRadioButtonId


            if (acName.isEmpty()) {
                showToast(FIELD_REQUIRED)
                return@setOnClickListener
            }
            if (!acEmail.contains('@') || !acEmail.contains('.')) {
                showToast(FIELD_IS_NOT_VALID)
                return@setOnClickListener
            }
            if (acPhone.isEmpty()) {
                showToast(FIELD_REQUIRED)
                return@setOnClickListener
            }
            if (csBirthday.isEmpty()) {
                showToast(FIELD_REQUIRED)
                return@setOnClickListener
            }
            if (delAddress.isEmpty()) {
                showToast(FIELD_REQUIRED)
                return@setOnClickListener
            }
            if (csGender == -1) {
                showToast(FIELD_REQUIRED)
                return@setOnClickListener
            }

            gender = when (csGender) {
                binding.female.id -> {
                    "Female"
                }
                binding.male.id -> {
                    "Male"
                }
                else -> ""
            }
            if (sharedPref.getPreference().roleID != 0) {
                if (pathImage != null) {
                    viewModel.updateApi(
                        acId = sharedPref.getPreference().acID!!,
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone,
                        csId = sharedPref.getPreference().roleID!!,
                        csBirthday = createPartFromString(csBirthday),
                        csAddress = createPartFromString(delAddress),
                        csGender = createPartFromString(gender!!),
                        image = createPartFromFile(pathImage!!)
                    )
                } else {
                    viewModel.updateApi(
                        acId = sharedPref.getPreference().acID!!,
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone,
                        csId = sharedPref.getPreference().roleID!!,
                        csBirthday = createPartFromString(csBirthday),
                        csAddress = createPartFromString(delAddress),
                        csGender = createPartFromString(gender!!)
                    )
                }
            }
            dialog.dialogUpdating(this, this) { moveActivity(acName, acEmail) }
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

    private fun getPath(context: Context, contentUri: Uri): String? {
        var result: String? = null
        val imageProfile = arrayOf(MediaStore.Images.Media.DATA)

        val cursorLoader = CursorLoader(context, contentUri, imageProfile, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }

        return result
    }

    private fun moveActivity(name: String, email: String) {
        val sendIntent = Intent(this, MainContentActivity::class.java)
        sendIntent.putExtra("data", 1)
        sendIntent.putExtra("image_URI", imageUri)
        sendIntent.putExtra("name", name)
        sendIntent.putExtra("email", email)
        startActivity(sendIntent)
        finish()
    }

    private fun dateOfBirth() {
        deadline = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = binding.etDob
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val date = sdf.format(myCalendar.time)

            day.setText(dateFormatter(date))
            myBirthday = date
        }
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun dateFormatter(date: String): String {
        val myDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        val dateFormatted = myDate.format(formatter)

        return dateFormatted.replace("-".toRegex(), " ")
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

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context)!!.create(ApiService::class.java)
    }

}
