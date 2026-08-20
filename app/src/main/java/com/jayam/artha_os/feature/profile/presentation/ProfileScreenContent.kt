package com.jayam.artha_os.feature.profile.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayam.artha_os.core.database.local.entities.ParseStatus
import com.jayam.artha_os.core.ui.theme.ArthaOSTheme
import com.jayam.artha_os.core.ui.theme.ArthaTheme
import com.jayam.artha_os.feature.sms.domain.SmsInfo

@Preview
@Composable
fun ProfileScreenContent(
    userName: String = "Johnny",
    onEditProfile: () -> Unit = {},
    onBiometricToggle: (Boolean) -> Unit = {},
    onBackupClick: () -> Unit = {},
    onCategoriesClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onParserPlaygroundClick: () -> Unit = {},
    onExportDataClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onSignOut: () -> Unit = {},
    biometricEnabled: Boolean = true,
    backupEnabled: Boolean = false,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val sms by viewModel.allSms.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var hasSmsPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        hasSmsPermission = results.values.all { it }
    }

    LaunchedEffect(Unit) {
        if (!hasSmsPermission) {
            permissionLauncher.launch(
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS)
            )
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
           contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── Header ────────────────────────────────────────────────
            item {
                ProfileHeader(
                    userName = userName,
                    onEditProfile = onEditProfile
                )
            }

            items(sms, key = { it.uuid.toString() }) { info ->
                SmsLogItem(info)
            }

            // ── Quick stats ───────────────────────────────────────────
            item {
                ProfileStatsCard()
            }

            // ── Security section ──────────────────────────────────────
            item {
                SectionLabel("Security")
            }
            item {
                SettingsGroup {
                    SettingsToggleRow(
                        icon = Icons.Outlined.Fingerprint,
                        title = "Biometric unlock",
                        subtitle = "Use fingerprint or face to open the app",
                        checked = biometricEnabled,
                        onCheckedChange = onBiometricToggle
                    )
                }
            }

            // ── Data section ──────────────────────────────────────────
            item {
                SectionLabel("Data")
            }
            item {
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Outlined.CloudUpload,
                        title = "Backup & restore",
                        subtitle = if (backupEnabled) "Synced with Google account" else "Off — data stays on device",
                        onClick = onBackupClick
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Outlined.Category,
                        title = "Manage categories",
                        subtitle = "Edit spending categories & rules",
                        onClick = onCategoriesClick
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Outlined.FileDownload,
                        title = "Export data",
                        subtitle = "Download transactions as CSV",
                        onClick = onExportDataClick
                    )
                }
            }

            // ── Preferences section ───────────────────────────────────
            item {
                SectionLabel("Preferences")
            }
            item {
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Outlined.Notifications,
                        title = "Notifications",
                        subtitle = "Budget alerts & spending insights",
                        onClick = onNotificationsClick
                    )
                }
            }

            // ── Developer section ─────────────────────────────────────
            item {
                SectionLabel("Developer")
            }
            item {
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Outlined.Terminal,
                        title = "Parser playground",
                        subtitle = "Test SMS parsing in real time",
                        onClick = onParserPlaygroundClick
                    )
                }
            }

            // ── About & sign out ──────────────────────────────────────
            item {
                SectionLabel("About")
            }
            item {
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Outlined.Info,
                        title = "About ArthaOS",
                        subtitle = "Version 0.1.0",
                        onClick = onAboutClick
                    )
                }
            }

            item {
                Spacer(Modifier.height(4.dp))
                OutlinedButton(
                    onClick = onSignOut,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))
                ) {
                    Icon(Icons.Outlined.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Sign out of Google backup")
                }
            }

            item {
                Text(
                    "Artha (अर्थ) — wealth, purpose, meaning.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────
// Components
// ─────────────────────────────────────────────────────────────────────────

@Composable
private fun ProfileHeader(
    userName: String,
    onEditProfile: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(ArthaTheme.colors.saffronDim),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.take(1).uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                userName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                "No account · Data stored on device",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(onClick = onEditProfile) {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = "Edit profile",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProfileStatsCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ArthaTheme.colors.cardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStat(label = "Tracked since", value = "Jul 2026")
            VerticalStatDivider()
            ProfileStat(label = "Transactions", value = "1,204")
            VerticalStatDivider()
            ProfileStat(label = "Categories", value = "9")
        }
    }
}

@Composable
private fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(2.dp))
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun VerticalStatDivider() {
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(1.dp)
            .background(ArthaTheme.colors.cardBorder)
    )
}
@Composable
private fun SmsLogItem(info: SmsInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = info.senderId,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = info.parseStatus.name,
                style = MaterialTheme.typography.labelSmall,
                color = when (info.parseStatus) {
                    ParseStatus.PARSED -> Color(0xFF3EA05B)
                    ParseStatus.FAILED -> MaterialTheme.colorScheme.error
                    ParseStatus.PENDING -> MaterialTheme.colorScheme.onSurfaceVariant
                    ParseStatus.IGNORED -> MaterialTheme.colorScheme.outline
                }
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = info.rawSms,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        info.amount?.let { amount ->
            Spacer(Modifier.height(6.dp))
            Text(
                text = "₹$amount" + (info.transactionType?.let { " · ${it.name}" } ?: ""),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        letterSpacing = 0.8.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
    )
}

@Composable
private fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ArthaTheme.colors.cardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ArthaTheme.colors.saffronDim),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Icon(
            Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun SettingsToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ArthaTheme.colors.saffronDim),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 66.dp),
        color = ArthaTheme.colors.cardBorder,
        thickness = 1.dp
    )
}
// ── Default state, light theme ─────────────────────────────────────────
@Preview(name = "Light — Default", showBackground = true)
@Composable
fun ProfileScreenContentPreview_Light() {
    ArthaOSTheme(darkTheme = false) {
        ProfileScreenContent(
            userName = "Johnny",
            biometricEnabled = true,
            backupEnabled = false
        )
    }
}

// ── Default state, dark theme ──────────────────────────────────────────
@Preview(name = "Dark — Default", showBackground = true)
@Composable
fun ProfileScreenContentPreview_Dark() {
    ArthaOSTheme(darkTheme = true) {
        ProfileScreenContent(
            userName = "Johnny",
            biometricEnabled = true,
            backupEnabled = false
        )
    }
}

// ── Backup enabled, biometric off ──────────────────────────────────────
@Preview(name = "Dark — Backup synced, biometric off", showBackground = true)
@Composable
fun ProfileScreenContentPreview_BackupOn() {
    ArthaOSTheme(darkTheme = true) {
        ProfileScreenContent(
            userName = "Priya Sharma",
            biometricEnabled = false,
            backupEnabled = true
        )
    }
}

// ── Long username, edge-case text wrapping ───────────────────────────────
@Preview(name = "Light — Long name", showBackground = true)
@Composable
fun ProfileScreenContentPreview_LongName() {
    ArthaOSTheme(darkTheme = false) {
        ProfileScreenContent(
            userName = "Venkataramanan Subramaniam",
            biometricEnabled = true,
            backupEnabled = true
        )
    }
}

// ── Small phone width ────────────────────────────────────────────────────
@Preview(
    name = "Dark — Compact width",
    showBackground = true,
    widthDp = 320,
    heightDp = 640
)
@Composable
fun ProfileScreenContentPreview_Compact() {
    ArthaOSTheme(darkTheme = true) {
        ProfileScreenContent(userName = "Johnny")
    }
}

// ── Large / tablet width ─────────────────────────────────────────────────
@Preview(
    name = "Light — Tablet width",
    showBackground = true,
    widthDp = 720,
    heightDp = 900
)
@Composable
fun ProfileScreenContentPreview_Tablet() {
    ArthaOSTheme(darkTheme = false) {
        ProfileScreenContent(userName = "Johnny")
    }
}

// ── Font scale — accessibility check ─────────────────────────────────────
@Preview(
    name = "Dark — Large font scale",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
fun ProfileScreenContentPreview_LargeFont() {
    ArthaOSTheme(darkTheme = true) {
        ProfileScreenContent(userName = "Johnny")
    }
}

// ── System UI dark/light toggle via uiMode (real device-like preview) ────
@Preview(
    name = "System Dark UI",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenContentPreview_SystemDark() {
    ArthaOSTheme(true) {
        ProfileScreenContent(userName = "Johnny")
    }
}

@Preview(
    name = "System Light UI",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ProfileScreenContentPreview_SystemLight() {
    ArthaOSTheme(true) {
        ProfileScreenContent(userName = "Johnny")
    }
}

// ── Multi-preview annotation: combine several device/theme configs at once ─
@Preview(name = "Pixel 5", device = "spec:width=393dp,height=851dp")
@Preview(name = "Pixel Tablet", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun ProfileScreenContentPreview_MultiDevice() {
    ArthaOSTheme(darkTheme = true) {
        ProfileScreenContent(userName = "Johnny")
    }
}