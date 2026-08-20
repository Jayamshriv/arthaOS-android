package com.jayam.artha_os.feature.budget.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jayam.artha_os.core.ui.common_components.withoutTopPadding

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    icon: ImageVector,
    title: String,
    description: String,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null,
    iconTint: Color = Color.White.copy(alpha = 0.3f),
    titleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    descriptionColor: Color = Color.White.copy(alpha = 0.4f),
    buttonContainerColor: Color = MaterialTheme.colorScheme.primary,
    buttonContentColor: Color = Color.Black
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding.withoutTopPadding()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(48.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = title,
                color = titleColor
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = description,
                color = descriptionColor,
                style = MaterialTheme.typography.bodySmall
            )

            if (buttonText != null && onButtonClick != null) {
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonContainerColor,
                        contentColor = buttonContentColor
                    )
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}