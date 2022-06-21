package com.heymaster.heymaster.model.masterprofile

import android.content.Context
import com.heymaster.heymaster.model.AttachmentInfo
import java.util.*
import kotlin.collections.ArrayList

sealed class Portfolio(){

    val id: Int = 0

    class Add(val name: String): Portfolio()

    class Image(val attachmentInfo: AttachmentInfo): Portfolio()
}