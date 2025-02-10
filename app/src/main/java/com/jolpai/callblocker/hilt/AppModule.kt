package com.jolpai.callblocker.hilt

import android.content.Context
import androidx.room.Room
import com.jolpai.callblocker.db.AppDatabase
import com.jolpai.callblocker.db.BlockedNumberDao
import com.jolpai.callblocker.db.BlockedNumberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//@InstallIn(SingletonComponent::class)
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "app_call_blocker_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBlockedNumberDao(database: AppDatabase): BlockedNumberDao {
        return database.blockedNumberDao()
    }

    @Provides
    @Singleton
    fun provideBlockedNumberRepository(dao: BlockedNumberDao): BlockedNumberRepository {
        return BlockedNumberRepository(dao)
    }
}