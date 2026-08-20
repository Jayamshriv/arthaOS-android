package com.jayam.artha_os.core.di.module
import com.jayam.artha_os.core.database.local.database.ArthaOSDatabase
import com.jayam.artha_os.feature.analytics.data.local.AnalyticsDao
import com.jayam.artha_os.feature.budget.data.local.BudgetDao
import com.jayam.artha_os.feature.dashboard.data.local.DashboardDao
import com.jayam.artha_os.feature.profile.data.local.ProfileDao
import com.jayam.artha_os.feature.receipt_ocr.data.local.ReceiptDao
import com.jayam.artha_os.feature.sms.data.local.SmsInfoDao
import com.jayam.artha_os.feature.transaction.data.local.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideTransactionDao(database: ArthaOSDatabase): TransactionDao =
        database.transactionDao()

    @Provides
    @Singleton
    fun provideBudgetDao(database: ArthaOSDatabase): BudgetDao =
        database.budgetDao()

    @Provides
    @Singleton
    fun provideProfileDao(database: ArthaOSDatabase): ProfileDao =
        database.profileDao()

    @Provides
    @Singleton
    fun provideSmsInfoDao(database: ArthaOSDatabase): SmsInfoDao =
        database.smsLogDao()

    @Provides
    @Singleton
    fun provideReceiptDao(database: ArthaOSDatabase): ReceiptDao =
        database.receiptDao()

    @Provides
    @Singleton
    fun provideAnalyticsDao(database: ArthaOSDatabase): AnalyticsDao =
        database.analyticsDao()

    @Provides
    @Singleton
    fun provideDashboardDao(database: ArthaOSDatabase): DashboardDao =
        database.dashboardDao()
}