package com.jayam.artha_os.core.database.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jayam.artha_os.core.database.local.entities.AnalyticsSnapshotEntity
import com.jayam.artha_os.core.database.local.entities.BudgetEntity
import com.jayam.artha_os.core.database.local.entities.ReceiptEntity
import com.jayam.artha_os.core.database.local.entities.SmsInfoEntity
import com.jayam.artha_os.core.database.local.entities.TransactionEntity
import com.jayam.artha_os.core.database.local.entities.UserProfileEntity
import com.jayam.artha_os.core.database.local.helper.converters.ArthaOsConverters
import com.jayam.artha_os.feature.analytics.data.local.AnalyticsDao
import com.jayam.artha_os.feature.budget.data.local.BudgetDao
import com.jayam.artha_os.feature.profile.data.local.ProfileDao
import com.jayam.artha_os.feature.receipt_ocr.data.local.ReceiptDao
import com.jayam.artha_os.feature.sms.data.local.SmsInfoDao
import com.jayam.artha_os.feature.transaction.data.local.TransactionDao

@Database(
    entities = [
        TransactionEntity::class,
        BudgetEntity::class,
        UserProfileEntity::class,
        SmsInfoEntity::class,
        ReceiptEntity::class,
        AnalyticsSnapshotEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(ArthaOsConverters::class)
abstract class ArthaOSDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun profileDao(): ProfileDao
    abstract fun smsLogDao(): SmsInfoDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun analyticsDao(): AnalyticsDao
}