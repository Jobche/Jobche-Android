package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.content.ContentValues.TAG
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.UserProfile
import okhttp3.*
import org.joda.time.LocalDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.joda.time.Years
import okhttp3.ResponseBody
import okhttp3.RequestBody
import okhttp3.MultipartBody
import java.io.File

class ProfileViewModel : BaseObservable() {

    @Bindable
    var userProfile: UserProfile = UserProfile()
        set(value) {
            field = value
            notifyPropertyChanged(BR.userProfile)
        }
    lateinit var email: String

    lateinit var password: String

    lateinit var task: Task

    var applicationId: Long = 0

    var userId: Long = 0

    @Bindable
    var yearsOld: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.yearsOld)
        }

    val acceptUserEventLiveData = SingleLiveData<Any>()
    val onClickImageEventLiveData = SingleLiveData<Any>()
    val getImageEventLiveData = SingleLiveData<Any>()
    val onClickReviewsLiveData = SingleLiveData<Any>()
    val onCallEventLiveData = SingleLiveData<Any>()

    fun dateTimeToYears(dateOfBirth: DateOfBirth): String {
        val now = LocalDate()
        val localDate = LocalDate(dateOfBirth.year, dateOfBirth.month, dateOfBirth.day)
        val age = Years.yearsBetween(localDate, now).toString()
        return age.substring(1, age.length - 1)
    }

    fun getUser() {
        val call: Call<UserProfile> = RetrofitClient().api
            .getUser(Credentials.basic(email, password), userId)

        call.enqueue(object : Callback<UserProfile> {
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Log.d("Get user onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                Log.d("Get user onSuccess", response.body().toString())
                if (response.body() != null) {
                    userProfile = response.body()!!
                    yearsOld = dateTimeToYears(userProfile.dateOfBirth!!)
                    if(userProfile.profilePicture != null) {
                        getImageEventLiveData.call()
                    }
                }
            }
        })
    }

    fun onClickReviews() {
        onClickReviewsLiveData.call()
    }

    fun onCall() {
        onCallEventLiveData.call()
    }

    fun onAccept() {
        val call: Call<Application> = RetrofitClient().api
            .acceptApplier(Credentials.basic(email, password), applicationId)

        call.enqueue(object : Callback<Application> {
            override fun onFailure(call: Call<Application>, t: Throwable) {
                Log.d("Accept applier Failure ", t.message.toString())
            }

            override fun onResponse(call: Call<Application>, response: Response<Application>) {
                Log.d("Accept applier Success", response.body().toString())
                    acceptUserEventLiveData.call()
            }
        })
    }

    fun onClickImage() {
        onClickImageEventLiveData.call()
    }

    fun uploadImage(selectedImagePath: String) {

        val file = File(selectedImagePath)

        if (file.exists()){
            Log.d(TAG, "onActivityResult: file exist")
        }

        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val call: Call<ResponseBody> = RetrofitClient().api
            .uploadImage(Credentials.basic(email, password), body)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("Upload image Failure ", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("Upload image Success", response.body().toString())
            }
        })

    }
}
