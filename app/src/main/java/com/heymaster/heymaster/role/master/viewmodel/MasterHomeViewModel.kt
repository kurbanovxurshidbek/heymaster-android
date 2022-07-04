package com.heymaster.heymaster.role.master.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.heymaster.heymaster.role.master.repository.MasterHomeRepository
import com.heymaster.heymaster.model.ErrorResponse
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.auth.Object
import com.heymaster.heymaster.model.auth.Profession
import com.heymaster.heymaster.model.home.*
import com.heymaster.heymaster.utils.UiStateList
import com.heymaster.heymaster.utils.UiStateObject
import com.heymaster.heymaster.utils.extensions.userMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MasterHomeViewModel(
    private val repository: MasterHomeRepository,
) : ViewModel() {

    private val _home = MutableStateFlow<UiStateObject<HomeResponse>>(UiStateObject.EMPTY)
    val home = _home

    private val _service = MutableStateFlow<UiStateObject<Service>>(UiStateObject.EMPTY)
    val service = _service

    private val _ads = MutableStateFlow<UiStateObject<Advertising>>(UiStateObject.EMPTY)
    val ads = _ads

    private val _professionsFromCategory = MutableStateFlow<UiStateList<Object>>(UiStateList.EMPTY)
    val professionsFromCategory = _professionsFromCategory

    private val _mastersFromProfessionId = MutableStateFlow<UiStateObject<MastersResponse>>(UiStateObject.EMPTY)
    val mastersFromProfessionId = _mastersFromProfessionId

    private val _activeMasters = MutableStateFlow<UiStateObject<ActiveMasters>>(UiStateObject.EMPTY)
    val activeMasters = _activeMasters

    private val _professions = MutableStateFlow<UiStateObject<Profession>>(UiStateObject.EMPTY)
    val professions = _professions

    private val _categories = MutableStateFlow<UiStateObject<CategoryResponse>>(UiStateObject.EMPTY)
    val categories = _categories

    private val _masterNotifications = MutableStateFlow<UiStateObject<NotificationResponse>>(UiStateObject.EMPTY)
    val masterNotifications = _masterNotifications


    fun getHome() = viewModelScope.launch {
        _home.value = UiStateObject.LOADING
        try {
            val response = repository.getHome()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _home.value = UiStateObject.ERROR(error.errorMessage)
            } else {
                _home.value = UiStateObject.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _home.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun getAds() = viewModelScope.launch {
        _ads.value = UiStateObject.LOADING

        try {
            val response = repository.getAds()
            _ads.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _ads.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun getProfessionsFromCategory(id: String) = viewModelScope.launch {
        _professionsFromCategory.value = UiStateList.LOADING

        try {
            val response = repository.getProfessionFromCategory(id)
            _professionsFromCategory.value = UiStateList.SUCCESS(response.body())


        } catch (e: Exception) {
            _professionsFromCategory.value = UiStateList.ERROR(e.userMessage())
        }
    }

    fun getMastersFromProfessionId(id: Int) = viewModelScope.launch {
        _mastersFromProfessionId.value = UiStateObject.LOADING

        try {
            val response = repository.getMasterFromProfessionId(id)

            _mastersFromProfessionId.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _mastersFromProfessionId.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun getProfessions() = viewModelScope.launch {
        _professions.value = UiStateObject.LOADING

        try {
            val response = repository.getProfessions()
            _professions.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _professions.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun getAllCategory() = viewModelScope.launch {
        _categories.value = UiStateObject.LOADING

        try {
            val response = repository.getAllCategory()
            _categories.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _categories.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun getActiveMasters() = viewModelScope.launch {
        _activeMasters.value = UiStateObject.LOADING

        try {
            val response = repository.getActiveMasters()
            _activeMasters.value = UiStateObject.SUCCESS(response.body()!!)


        } catch (e: Exception) {
            _activeMasters.value = UiStateObject.ERROR(e.userMessage())
        }
    }

    fun getNotifications() = viewModelScope.launch {
        _masterNotifications.value = UiStateObject.LOADING

        try {
            val response = repository.getNotifications()
            _masterNotifications.value = UiStateObject.SUCCESS(response.body()!!)

        } catch (e: Exception) {
            _masterNotifications.value = UiStateObject.ERROR(e.userMessage())
        }
    }
}