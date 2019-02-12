package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.Years
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupBirthViewModel: BaseObservable() {

    private var birthDate: LocalDate? = null

    private var formattedBirthDate: String = ""

    private var email:String = ""

    private var registerUser = User()

    private var toastMsg: String = ""

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    private val _birthDateEventLiveData = SingleLiveData<Any>()


    fun getBirthDate(): LocalDate? {
        return this.birthDate
    }

    fun setBirthDate(birthDate:LocalDate) {
        this.birthDate = birthDate
        formatBirthDate(birthDate)
    }

    @Bindable
    fun getFormattedBirthDate(): String {
        return this.formattedBirthDate
    }

    fun setFormattedBirthDate(formattedBirthDate: String) {
        this.formattedBirthDate = formattedBirthDate
        notifyPropertyChanged(BR.formattedBirthDate)
    }

    fun formatBirthDate(birthDate: LocalDate) {
        val formattedBirthDate = String.format("%02d", birthDate.dayOfMonth) + "." +
                                        String.format("%02d", birthDate.monthOfYear) + "." +
                                        birthDate.year.toString()
        setFormattedBirthDate(formattedBirthDate)
    }

    @Bindable
    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email:String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    fun setRegisterUser(user: User){
        this.registerUser = user
    }


    fun getToastMsg(): String {
        return toastMsg
    }

    fun setToastMsg(toastMsg: String) {
        this.toastMsg = toastMsg
    }

    fun getYearsOld(): Int {

        val age = Years.yearsBetween(birthDate, LocalDate()).toString()
        return (age.substring(1, age.length - 1)).toInt()
    }

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData

    val birthDateEventLiveData: LiveData<Any>
        get() = _birthDateEventLiveData

    val nextEventLiveData : LiveData<Any>
        get() = _nextEventLiveData

    fun onClickBirthDate() {
        _birthDateEventLiveData.call()
    }

    fun onClick() {
        if(getEmail() == "" || getBirthDate() == null) {
            setToastMsg("Попълнете празните полета.")
            _toastEventLiveData.call()
        }
//        else if(getBirthDate())
        registerUser.email = getEmail()
        registerUser.dateOfBirth = getBirthDate()

        val paramObject = JsonObject()
        paramObject.addProperty("firstName", registerUser.firstName)
        paramObject.addProperty("lastName", registerUser.lastName)
        paramObject.addProperty("email", registerUser.email)
        paramObject.addProperty("password", registerUser.password)
        paramObject.add("dateOfBirth", Gson().toJsonTree(DateOfBirth(birthDate!!.dayOfMonth, birthDate!!.monthOfYear, birthDate!!.year)))


        val call: Call<User> = RetrofitClient().getApi()
            .createUser(paramObject)

        call.enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Sign up onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("Sign up onSuccess:", response.body().toString())
                _nextEventLiveData.call()
            }

        })

    }
}