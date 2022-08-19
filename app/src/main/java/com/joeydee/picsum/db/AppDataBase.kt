package com.joeydee.picsum.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joeydee.picsum.db.dao.PersonDao
import com.joeydee.picsum.db.dao.RemoteKeyDao
import com.joeydee.picsum.model.Person
import com.joeydee.picsum.model.RemoteKey

@Database(
    entities = [Person::class, RemoteKey::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}