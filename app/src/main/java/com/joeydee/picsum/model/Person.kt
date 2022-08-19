package com.joeydee.picsum.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity("persons")
@Parcelize
data class Person(

    @PrimaryKey val id: String,
    val author: String,
    val width: Double,
    val height: Double,
    val url: String,
    val download_url: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false
        if (author != other.author) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (url != other.url) return false
        if (download_url != other.download_url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + download_url.hashCode()
        return result
    }
}