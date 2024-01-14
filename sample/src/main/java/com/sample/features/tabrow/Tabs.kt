package com.sample.features.tabrow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tabs(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String
) {
    List(
        unselectedIcon = Icons.Outlined.List,
        selectedIcon = Icons.Filled.List,
        title = "List"
    ),
    Favourite(
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
        title = "Favourite"
    ),
    Settings(
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        title = "Settings"
    )
}
