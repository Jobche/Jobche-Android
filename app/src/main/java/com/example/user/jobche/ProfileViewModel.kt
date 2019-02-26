package com.example.user.jobche

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.Applications
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.UserProfile
import com.google.gson.JsonObject
import okhttp3.Credentials
import org.joda.time.LocalDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.joda.time.Years


class ProfileViewModel : BaseObservable() {

    private lateinit var email: String

    private lateinit var password: String

    private var applicationId: Int = 0

    private var userId: Int = 0

    private var firstName: String = ""

    private var lastName: String = ""

    private var yearsOld: String = ""

    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPassword(): String {
        return this.password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getApplicationId(): Int {
        return this.applicationId
    }

    fun setApplicationId(applicationId: Int) {
        this.applicationId = applicationId
    }

    fun getUserId(): Int {
        return this.userId
    }

    fun setUserId(userId: Int) {
        this.userId = userId
    }

    @Bindable
    fun getFirstName(): String {
        return this.firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    fun getLastName(): String {
        return this.lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
        notifyPropertyChanged(BR.lastName)
    }

    @Bindable
    fun getYearsOld(): String {
        return this.yearsOld
    }

    fun setYearsOld(yearsOld: String) {
        this.yearsOld = yearsOld
        notifyPropertyChanged(BR.yearsOld)
    }

    fun getAuthToken(): String {
        return Credentials.basic(getEmail(), getPassword())

    }

    fun getUser() {
        val call: Call<UserProfile> = RetrofitClient().api
            .getUser(getAuthToken(), getUserId())

        call.enqueue(object : Callback<UserProfile> {
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Log.d("Get user onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                Log.d("Get user onSuccess", response.body().toString())
                val userProfile = response.body()
                if (userProfile != null) {
                    setFirstName(userProfile.firstName)
                    setLastName(userProfile.lastName)
                    val birthDate = LocalDate(
                        userProfile.dateOfBirth.year,
                        userProfile.dateOfBirth.month,
                        userProfile.dateOfBirth.day
                    )
                    val age = Years.yearsBetween(birthDate, LocalDate()).toString()
                    setYearsOld(age.substring(1, age.length - 1))
                }
            }
        })
    }

    fun onAccept() {
        val call: Call<Application> = RetrofitClient().api
            .acceptApplier(getAuthToken(), getApplicationId())

        call.enqueue(object : Callback<Application> {
            override fun onFailure(call: Call<Application>, t: Throwable) {
                Log.d("Accept applier Failure ", t.message.toString())
            }

            override fun onResponse(call: Call<Application>, response: Response<Application>) {
                Log.d("Accept applier Success", response.body().toString())
            }
        })
    }

}