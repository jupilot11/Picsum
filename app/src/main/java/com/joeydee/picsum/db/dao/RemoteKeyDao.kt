package com.joeydee.picsum.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joeydee.picsum.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE repoId = :id")
    suspend fun remoteKeysQuery(id: String): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

}