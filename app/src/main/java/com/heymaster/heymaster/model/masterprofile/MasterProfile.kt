package com.heymaster.heymaster.model.masterprofile

sealed class MasterProfile(open val id: Int){
    data class getImage (
        override val id: Int,
        val image:String
    ) : MasterProfile(id)

    data class addImage (
        override val id: Int,
    ) : MasterProfile(id)
}
