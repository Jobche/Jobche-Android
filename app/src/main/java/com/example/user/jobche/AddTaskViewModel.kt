package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.Location
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Credentials
import okhttp3.ResponseBody
import org.joda.time.LocalDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskViewModel: BaseObservable() {

    private var title:String = ""

    private var country:String = ""

    private var city: String = ""

    private var payment:String = ""

    private var numOfWorkers:String = ""

    private var description:String = ""

    private var date:String = ""

    private var time:String = ""

    private var dateTime: LocalDateTime = LocalDateTime.now()

    private val _dateEventLiveData = SingleLiveData<Any>()

    private val _timeEventLiveData = SingleLiveData<Any>()

    private val _addTaskEventLiveData = SingleLiveData<Any>()

    @Bindable
    fun getCountry(): String {
       return this.country
    }

    fun setCountry(country: String) {
        this.country = country
        notifyPropertyChanged(BR.country)
    }
    @Bindable
    fun getCity(): String {
        return this.city
    }

    fun setCity(city: String) {
        this.city = city
        notifyPropertyChanged(BR.city)
    }

    @Bindable
    fun getTitle(): String {
        return this.title
    }

    fun setTitle(title:String) {
        this.title = title
        notifyPropertyChanged(BR.title)
    }

    @Bindable
    fun getPayment(): String {
        return this.payment
    }

    fun setPayment(payment: String) {
        this.payment = payment
        notifyPropertyChanged(BR.payment)
    }

    @Bindable
    fun getNumOfWorkers(): String {
        return this.numOfWorkers
    }

    fun setNumOfWorkers(numOfWorkers: String) {
        this.numOfWorkers = numOfWorkers
        notifyPropertyChanged(BR.numOfWorkers)
    }

    @Bindable
    fun getDate(): String {
        return this.date
    }

    fun setDate(date:String) {
        this.date = date
        notifyPropertyChanged(BR.date)
    }

    @Bindable
    fun getTime(): String {
        return this.time
    }

    fun setTime(time:String) {
        this.time = time
        notifyPropertyChanged(BR.time)
    }

    fun getDateTime(): LocalDateTime {
        return this.dateTime
    }

    fun setDateTime(dateTime: LocalDateTime) {
        this.dateTime = dateTime
    }


    @Bindable
    fun getDescription(): String {
        return this.description
    }

    fun setDescription(description:String) {
        this.description = description
        notifyPropertyChanged(BR.description)
    }

    val dateEventLiveData : LiveData<Any>
        get() = _dateEventLiveData

    val timeEventLiveData: LiveData<Any>
        get() = _timeEventLiveData

    val addTaskEventLiveData: LiveData<Any>
        get() = _addTaskEventLiveData

    fun onClickDate() {
        _dateEventLiveData.call()
    }

    fun onClickTime() {
        _timeEventLiveData.call()
    }

    fun onClickAddTask() {

        val paramObject = JsonObject()
        paramObject.addProperty("title", getTitle())
        paramObject.addProperty("payment", getPayment().toInt())
        paramObject.addProperty("numberOfWorkers", getNumOfWorkers().toInt())
        paramObject.addProperty("description", getDescription())
        paramObject.addProperty("dateTime", getDateTime().toString())
        paramObject.add("location", Gson().toJsonTree(Location(getCountry(), getCity(), "")))

        val authToken = Credentials.basic("string", "string")


        val call: Call<ResponseBody> = RetrofitClient().getApi()
            .createTask(authToken, paramObject)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("Add Task onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("Add Task onSuccess:", response.body().toString())
                _addTaskEventLiveData.call()
            }

        })

    }

}

