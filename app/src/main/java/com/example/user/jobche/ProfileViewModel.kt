package com.example.user.jobche

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
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

//    private var id:Int = 0

    private lateinit var email:String

    private lateinit var password: String

    private var firstName: String = ""

    private var lastName: String = ""

    private var yearsOld:String = ""
//
//    fun getId() : Int {
//        return this.id
//    }
//
//    fun setId(id: Int) {
//        this.id = id
//    }

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

    fun createUser(id: Int) {

        val authToken = Credentials.basic(getEmail(), getPassword())

        val call: Call<UserProfile> = RetrofitClient().getApi()
            .getUser(authToken, id)

        call.enqueue(object : Callback<UserProfile> {
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Log.d("Get User onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                Log.d("Get User onSuccess", response.body().toString())
                val userProfile = response.body()
                if (userProfile != null) {
                    setFirstName(userProfile.firstName!!)
                    setLastName(userProfile.lastName!!)
                    val birthDate = LocalDate(userProfile.dateOfBirth!!.year, userProfile.dateOfBirth!!.month, userProfile.dateOfBirth!!.day)
                    val now = LocalDate()
                    Log.d("GODINIKTE", userProfile.dateOfBirth!!.year.toString())
                    Log.d("MQSEC", userProfile.dateOfBirth!!.month.toString())
                    Log.d("DENCHI", userProfile.dateOfBirth!!.day.toString())
                    val age = Years.yearsBetween(birthDate, now).toString()
                    setYearsOld(age.substring(1, age.length - 1))
                }
            }
        })
    }

}