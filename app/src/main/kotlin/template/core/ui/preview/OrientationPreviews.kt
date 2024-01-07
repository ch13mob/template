package template.core.ui.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Landscape Mode",
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 640
)
@Preview(name = "Portrait Mode", showBackground = true, device = Devices.PIXEL_4)
annotation class OrientationPreviews
