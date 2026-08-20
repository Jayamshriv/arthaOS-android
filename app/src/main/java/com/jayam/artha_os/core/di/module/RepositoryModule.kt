package com.jayam.artha_os.core.di.module

import com.jayam.artha_os.feature.analytics.data.local.AnalyticsDao
import com.jayam.artha_os.feature.analytics.data.repository.AnalyticsRepositoryImpl
import com.jayam.artha_os.feature.analytics.domain.AnalyticsRepository
import com.jayam.artha_os.feature.budget.data.BudgetRepositoryImpl
import com.jayam.artha_os.feature.budget.data.local.BudgetDao
import com.jayam.artha_os.feature.budget.domain.BudgetRepository
import com.jayam.artha_os.feature.dashboard.data.DashboardRepositoryImpl
import com.jayam.artha_os.feature.dashboard.data.local.DashboardDao
import com.jayam.artha_os.feature.dashboard.domain.DashboardRepository
import com.jayam.artha_os.feature.profile.data.ProfileRepositoryImpl
import com.jayam.artha_os.feature.profile.data.local.ProfileDao
import com.jayam.artha_os.feature.profile.domain.ProfileRepository
import com.jayam.artha_os.feature.receipt_ocr.data.ReceiptRepositoryImpl
import com.jayam.artha_os.feature.receipt_ocr.data.local.ReceiptDao
import com.jayam.artha_os.feature.receipt_ocr.domain.ReceiptRepository
import com.jayam.artha_os.feature.sms.data.SmsRepositoryImpl
import com.jayam.artha_os.feature.sms.data.local.SmsInfoDao
import com.jayam.artha_os.feature.sms.domain.SmsRepository
import com.jayam.artha_os.feature.transaction.data.TransactionRepositoryImpl
import com.jayam.artha_os.feature.transaction.data.local.TransactionDao
import com.jayam.artha_os.feature.transaction.domain.repo.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule  {
    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        analyticsDao: AnalyticsDao,
    ): AnalyticsRepository = AnalyticsRepositoryImpl(dao = analyticsDao)

    @Provides
    @Singleton
    fun provideBudgetRepository(
        budgetDao: BudgetDao,
    ): BudgetRepository =
        BudgetRepositoryImpl(dao = budgetDao)

    @Provides
    @Singleton
    fun provideDashboardRepository(
        dashboardDao: DashboardDao,
    ): DashboardRepository =
        DashboardRepositoryImpl(dao = dashboardDao)

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileDao: ProfileDao,
    ): ProfileRepository =
        ProfileRepositoryImpl(dao = profileDao)

    @Provides
    @Singleton
    fun provideReceiptRepository(
        receiptDao: ReceiptDao,
    ): ReceiptRepository =
        ReceiptRepositoryImpl(dao = receiptDao)

   @Provides
    @Singleton
    fun provideSmsInfoRepository(
       smsInfoDao: SmsInfoDao,
    ): SmsRepository  =
       SmsRepositoryImpl(dao = smsInfoDao)


   @Provides
    @Singleton
    fun provideTransactionRepository(
       transactionDao: TransactionDao,
    ): TransactionRepository  =
       TransactionRepositoryImpl(dao = transactionDao)


}