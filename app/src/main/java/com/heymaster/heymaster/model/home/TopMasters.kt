package com.heymaster.heymaster.model.home

import com.heymaster.heymaster.model.Authority
import com.heymaster.heymaster.model.Object
import com.heymaster.heymaster.model.Roles

data class TopMasters(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val active: Boolean,
    val approximateEndTime: Any,
    val attachments: List<Any>,
    val authorities: List<Authority>,
    val birthDate: Any,
    val busy: Boolean,
    val createAt: String,
    val createdBy: Any,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean,
    val experienceYear: Int,
    val fullName: String,
    val gender: Any,
    val generatePassword: Any,
    val id: Int,
    val location: Any,
    val password: Any,
    val peopleReitedCount: Int,
    val phoneNumber: String,
    val professionList: List<Object>,
    val profilePhoto: Any,
    val roles: Roles,
    val salary: Any,
    val totalMark: Int,
    val updateAt: String,
    val updatedBy: Any,
    val username: String
)