package com.jayam.artha_os.core.di.module

import android.content.Context
import androidx.room.Room
import com.jayam.artha_os.core.database.local.database.ArthaOSDatabase
import com.jayam.artha_os.feature.analytics.data.local.AnalyticsDao
import com.jayam.artha_os.feature.budget.data.local.BudgetDao
import com.jayam.artha_os.feature.profile.data.local.ProfileDao
import com.jayam.artha_os.feature.receipt_ocr.data.local.ReceiptDao
import com.jayam.artha_os.feature.sms.data.local.SmsInfoDao
import com.jayam.artha_os.feature.transaction.data.local.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArthaOSDatabase(
        @ApplicationContext context: Context
    ): ArthaOSDatabase = Room.databaseBuilder(
        context,
        ArthaOSDatabase::class.java,
        "arthaos_db"
    )
        .fallbackToDestructiveMigration() // remove before real releases; fine while schema is in flux
        .build()

    @Provides
    @Singleton
    fun provideTransactionDao(database: ArthaOSDatabase): TransactionDao = database.transactionDao()

    @Provides
    @Singleton
    fun provideBudgetDao(database: ArthaOSDatabase): BudgetDao = database.budgetDao()

    @Provides
    @Singleton
    fun provideProfileDao(database: ArthaOSDatabase): ProfileDao = database.profileDao()

    @Provides
    @Singleton
    fun provideSmsDao(database: ArthaOSDatabase): SmsInfoDao = database.smsLogDao()


    @Provides
    @Singleton
    fun provideReceiptDao(database: ArthaOSDatabase): ReceiptDao = database.receiptDao()


    @Provides
    @Singleton
    fun provideAnalyticsDao(database: ArthaOSDatabase): AnalyticsDao = database.analyticsDao()




}