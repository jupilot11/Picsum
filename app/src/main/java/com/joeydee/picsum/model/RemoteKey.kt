package com.joeydee.picsum.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(@PrimaryKey val repoId: String, val nextKey: Int?, val prevKey: Int?)