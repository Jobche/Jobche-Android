package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.UserProfile
import okhttp3.Credentials
import org.joda.time.LocalDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.joda.time.Years


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

    private val _acceptUserEventLiveData = SingleLiveData<Any>()


    val acceptUserEventLiveData: LiveData<Any>
        get() = _acceptUserEventLiveData

    fun getAuthToken(): String {
        return Credentials.basic(email, password)

    }

    fun dateTimeToYears(dateOfBirth: DateOfBirth): String {
        val now = LocalDate()
        val localDate = LocalDate(dateOfBirth.year, dateOfBirth.month, dateOfBirth.day)
        val age = Years.yearsBetween(localDate, now).toString()
        return age.substring(1, age.length - 1)

    }

    fun getUser() : UserProfile {
        val call: Call<UserProfile> = RetrofitClient().api
            .getUser(getAuthToken(), userId)

        call.enqueue(object : Callback<UserProfile> {
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Log.d("Get user onFailure ", t.message.toString())
                userProfile = UserProfile()

            }

            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                Log.d("Get user onSuccess", response.body().toString())
                if (response.body() != null) {
                    userProfile = response.body()!!
                    yearsOld = dateTimeToYears(userProfile.dateOfBirth!!)
                }
            }
        })
        return userProfile
    }

    fun onAccept() {
        val call: Call<Application> = RetrofitClient().api
            .acceptApplier(getAuthToken(), applicationId)

        call.enqueue(object : Callback<Application> {
            override fun onFailure(call: Call<Application>, t: Throwable) {
                Log.d("Accept applier Failure ", t.message.toString())
            }

            override fun onResponse(call: Call<Application>, response: Response<Application>) {
                Log.d("Accept applier Success", response.body().toString())
                _acceptUserEventLiveData.call()
            }
        })
    }

}