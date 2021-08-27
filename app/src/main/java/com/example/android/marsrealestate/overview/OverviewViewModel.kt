package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {


    private var viewModelJob= Job()
    private val  coroutineScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _status


    private val _property=MutableLiveData<MarsProperty>()
    val property:LiveData<MarsProperty>
    get() = _property

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
        val getPropertiesDeferred=MarsApi.retrofitService.getProperties()
            try{
                var listResult=getPropertiesDeferred.await()

                if(listResult.size>0)
                {
                    _property.value=listResult[0]
                }
            }
            catch(t:Throwable) {
                _status.value = "Failure: " + t.message
            }
        }
        }
    }
