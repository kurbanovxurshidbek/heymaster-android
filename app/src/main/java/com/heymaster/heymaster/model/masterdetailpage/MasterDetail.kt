package com.heymaster.heymaster.model.masterdetailpage

data class MasterDetail(
    val `object`: Object
)

data class Authority(
    val authority: String,
    val id: Int,
    val roleName: String
)

data class Category(
    val id: Int,
    val isActive: Boolean,
    val name: String
)

data class Device(
    val deviceId: String,
    val deviceLan: Any,
    val id: Int
)

data class District(
    val id: Int,
    val nameRu: String,
    val nameUz: String,
    val region: Region
)

data class Location(
    val district: District,
    val id: Int,
    val region: RegionX
)

data class Object(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val active: Boolean,
    val approximateEndTime: Any,
    val attachments: List<Any>,
    val authorities: List<Authority>,
    val birthDate: String,
    val busy: Boolean,
    val createAt: String,
    val createdBy: Any,
    val credentialsNonExpired: Boolean,
    val device: Device,
    val enabled: Boolean,
    val experienceYear: Int,
    val fullName: String,
    val gender: Boolean,
    val generatePassword: String,
    val id: Int,
    val location: Location,
    val password: String,
    val peopleReitedCount: Int,
    val phoneNumber: String,
    val professionList: List<Profession>,
    val profilePhoto: Any,
    val roles: Roles,
    val salary: Any,
    val totalMark: Int,
    val updateAt: String,
    val updatedBy: Any,
    val username: String
)

data class Profession(
    val category: Category,
    val id: Int,
    val isActive: Boolean,
    val name: String
)

data class Region(
    val id: Int,
    val nameRu: String,
    val nameUz: String
)

data class RegionX(
    val id: Int,
    val nameRu: String,
    val nameUz: String
)

data class Roles(
    val authority: String,
    val id: Int,
    val roleName: String
)