package com.jayam.artha_os.core.database.local.helper.converters

import androidx.room.TypeConverter
import com.jayam.artha_os.core.database.local.entities.ParseStatus
import com.jayam.artha_os.core.database.local.helper.TransactionSource
import com.jayam.artha_os.core.database.local.helper.TransactionType
import com.jayam.artha_os.core.database.local.entities.ReceiptStatus
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType
import java.math.BigDecimal
import kotlin.time.Instant
import kotlin.uuid.Uuid

class ArthaOsConverters {

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String = type.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)

    @TypeConverter
    fun fromTransactionSource(source: TransactionSource): String = source.name

    @TypeConverter
    fun toTransactionSource(value: String): TransactionSource = TransactionSource.valueOf(value)


    @TypeConverter
    fun fromReceiptStatus(status: ReceiptStatus): String = status.name

    @TypeConverter
    fun toReceiptStatus(value: String): ReceiptStatus = ReceiptStatus.valueOf(value)

    @TypeConverter
    fun fromUuid(uuid: Uuid): String = uuid.toString()

    @TypeConverter
    fun toUuid(value: String): Uuid = Uuid.parse(value)

    @TypeConverter
    fun fromInstant(instant: Instant): Long = instant.toEpochMilliseconds()

    @TypeConverter
    fun toInstant(value: Long): Instant = Instant.fromEpochMilliseconds(value)

    @TypeConverter
    fun fromBigDecimal(value: BigDecimal): String = value.toPlainString()

    @TypeConverter
    fun toBigDecimal(value: String): BigDecimal = BigDecimal(value)

    @TypeConverter
    fun fromParseStatus(status: ParseStatus): String = status.name

    @TypeConverter
    fun toParseStatus(value: String): ParseStatus = ParseStatus.valueOf(value)

    @TypeConverter
    fun fromAnalyticsPeriodType(type: AnalyticsPeriodType): String = type.name

    @TypeConverter
    fun toAnalyticsPeriodType(value: String): AnalyticsPeriodType =
        AnalyticsPeriodType.valueOf(value)
}