package com.example.tp2.userinfo

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.tp2.BuildConfig
import com.example.tp2.R
import com.example.tp2.Task
import com.example.tp2.network.Api
import com.example.tp2.network.UserInfo
import com.example.tp2.network.UserInfoRepository
import com.example.tp2.task.TaskActivity
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserInfoActivity: AppCompatActivity() {

    companion object {
        const val USER_INFO_KEY = 646
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewModelUser: UserInfoViewModel by viewModels()
        val repository = UserInfoRepository()

        setContentView(R.layout.user_info_activity)

        findViewById<Button>(R.id.take_picture_button).setOnClickListener {
            askCameraPermissionAndOpenCamera()
        }

        findViewById<Button>(R.id.upload_image_button).setOnClickListener {
            uploadImage()
        }

        findViewById<Button>(R.id.button_validate_image).setOnClickListener {
            lifecycleScope.launch {
                val oldUserInfo = repository.loadInfo()
                val newEmail = findViewById<EditText>(R.id.edit_mail).text.toString()
                val newLastName = findViewById<EditText>(R.id.edit_lastname).text.toString()
                val newFirstName = findViewById<EditText>(R.id.edit_firstname).text.toString()
                val oldAvatar = oldUserInfo!!.avatar
                val newUserInfo = oldUserInfo?.copy(lastName = newLastName,firstName = newFirstName, email = newEmail)
                //val newUserInfo = UserInfo(newEmail,newFirstName,newLastName,OldAvatar)
                val respond = viewModelUser.updateUserInfo(newUserInfo)
                finish()
            }
        }

        lifecycleScope.launch {
            //Charger les valeurs existantes
            val userInfo = Api.INSTANCE.userService.getInfo().body()
            val imageView = findViewById<ImageView>(R.id.image_view)
            val text_edit_firstN = findViewById<EditText>(R.id.edit_firstname)
            val text_edit_lastN = findViewById<EditText>(R.id.edit_lastname)
            val text_edit_mail = findViewById<EditText>(R.id.edit_mail)

            text_edit_firstN.setText(userInfo?.firstName)
            text_edit_lastN.setText(userInfo?.lastName)
            text_edit_mail.setText(userInfo?.email)
            imageView.load(userInfo?.avatar)
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) openCamera()
            else showExplanationDialog()
        }

    private fun requestCameraPermission() =
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

    private fun askCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> openCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showExplanationDialog()
            else -> requestCameraPermission()
        }
    }

    private fun showExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("On a besoin de la caméra sivouplé ! 🥺")
            setPositiveButton("Bon, ok") { _, _ ->
                requestCameraPermission()
            }
            setCancelable(true)
            show()
        }
    }

    private fun handleImage(uri: Uri) {
        lifecycleScope.launch {
            val userInfo = Api.INSTANCE.userService.updateAvatar(convert(uri))
            val newAvatar = userInfo.body()?.avatar
            val imageView = findViewById<ImageView>(R.id.image_view)
            imageView?.load(newAvatar)
        }
    }

    // convert
    private fun convert(uri: Uri) =
        MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = contentResolver.openInputStream(uri)!!.readBytes().toRequestBody()
        )

    // create a temp file and get a uri for it
    private val photoUri by lazy {
        FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID +".fileprovider",
            File.createTempFile("avatar", ".jpeg", externalCacheDir)

        )
    }

    // register
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) handleImage(photoUri)
        else Toast.makeText(this, "Erreur ! 😢", Toast.LENGTH_LONG).show()
    }

    // use
    private fun openCamera() = takePicture.launch(photoUri)

    // register
    private val pickInGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            handleImage(it)
        }

    // use
    private fun uploadImage() = pickInGallery.launch("image/*")
}