package com.example.user.jobche.UI.Fragments

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.ProfileViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private val RESULT_LOAD_IMAGE: Int = 1
    private lateinit var email: String
    private lateinit var password: String
    private var userId: Int = 0
    private val profileViewModel = ProfileViewModel()
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Профил"
            (activity as HomeActivity).showBackButton(false)
        }

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        userId = sharedPreferences.getInt("ID", 0)
        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val view: View = binding.root
        binding.viewModel = profileViewModel
        profileViewModel.userId = userId.toLong()
        profileViewModel.email = email
        profileViewModel.password = password
        profileViewModel.getUser()

        profileViewModel.getImageEventLiveData.observe(this, Observer {
            Picasso.get().load(profileViewModel.userProfile.profilePicture).resize(400, 400).centerCrop().into(image_profile)
        })

        profileViewModel.onClickImageEventLiveData.observe(this, Observer {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(intent, RESULT_LOAD_IMAGE)
        })

        return view
    }

    //is called when an image is selected
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            val selectedImage: Uri = data.data!!

            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            // Get the cursor
            val cursor = activity!!.contentResolver.query(selectedImage, filePathColumn, null, null, null)
            // Move to first row
            cursor!!.moveToFirst()
            //Get the column index of MediaStore.Images.Media.DATA
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            //Gets the String value in the column
            val imgDecodableString = cursor.getString(columnIndex)
            cursor.close()
            binding.imageProfile.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString))
            profileViewModel.uploadImage(imgDecodableString)
        }
    }
}
