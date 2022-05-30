package com.heymaster.heymaster.ui.user.booking

import com.heymaster.heymaster.ui.auth.AuthRepository

class UserBookingViewModel(
    private val repository: UserBookingRepository,
) : ViewModel() {

    private val _bookings = MutableStateFlow<UiStateList<Service>>(UiStateList.EMPTY)
    val bookings = _bookings

    fun getBookings() = viewModelScope.launch {
        _bookings.value = UiStateList.LOADING
        try {
            val response = repository.getBookings()
            if (response.code() >= 400) {
                val error =
                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                _bookings.value = UiStateList.ERROR(error.errorMessage)
            } else {
                _bookings.value = UiStateList.SUCCESS(response.body()!!)
            }
        } catch (e: Exception) {
            _bookings.value = UiStateList.ERROR(e.userMessage())
        }
    }



}