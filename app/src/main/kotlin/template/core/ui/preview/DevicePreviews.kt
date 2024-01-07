package template.core.ui.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "phone",
    showSystemUi = true,
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480"
)
@Preview(
    name = "landscape",
    showSystemUi = true,
    device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480"
)
@Preview(
    name = "foldable",
    showSystemUi = true,
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480"
)
@Preview(
    name = "tablet",
    showSystemUi = true,
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480"
)
annotation class DevicePreviews
