package com.example.infotainment_car_health_digital

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HALF_INDICATOR_WIDTH = 5.dp
private val DEFAULT_INDICATOR_POSITION = 0.dp
private const val ANIM_DURATION = 250

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun Tabs(
    tabs: List<TabItem>,
    onTabSelect: (TabItem) -> Unit,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
) {
    val buttonBackGroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF255AF5).copy(alpha = 1f),
            Color(0xFF0B1112).copy(alpha = 0.1f)
        )
    )

    val rowBackGroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF111841).copy(alpha = 1f),
            Color(0xFF050812).copy(alpha = 1f)
        )
    )

    val transparentGradient = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.Transparent
        )
    )

    val density = LocalDensity.current
    val tabIndicatorPositions = remember { mutableStateListOf<Dp>() }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .background(
                        brush = rowBackGroundGradient,
                        shape = RoundedCornerShape(10.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ){
                tabs.forEachIndexed { index, tabItem ->

                    Text(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                if (tabIndicatorPositions.size < tabs.size) {
                                    val position =
                                        with(density) { coordinates.boundsInRoot().bottomCenter.x.toDp() } -
                                                HALF_INDICATOR_WIDTH
                                    if (position >= DEFAULT_INDICATOR_POSITION) {
                                        tabIndicatorPositions.add(position)
                                    }
                                }
                            }
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource()
                            ) {
                                onTabSelect(tabItem)
                            }
                            .background(
                                brush = if (selectedIndex == index) buttonBackGroundGradient else transparentGradient,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .padding(10.dp),
                        text = tabItem.title,
                        maxLines = 1,
                        style = TextStyle(color = Color.White, fontSize = 14.sp)
                    )

//                    Box(
//                        modifier = Modifier
//                            .background(
//                                brush = if (selectedIndex == index) {
//                                    buttonBackGroundGradient
//                                } else {
//                                    rowBackGroundGradient
//                                },
//                                shape = RoundedCornerShape(
//                                    topStart = 4.dp,
//                                    topEnd = 4.dp,
//                                    bottomEnd = 0.dp,
//                                    bottomStart = 0.dp
//                                )
//
//                            )
//                            .border(
//                                width = 1.dp,
//                                color = if (selectedIndex == index) {
//                                    Color(0xFF1786B6)
//                                } else {
//                                    Color(0xFF3C4042)
//                                },
//                                shape = RoundedCornerShape(
//                                    topStart = 4.dp,
//                                    topEnd = 4.dp,
//                                    bottomEnd = 0.dp,
//                                    bottomStart = 0.dp
//                                )
//                            )
//                            .padding(
//                                horizontal = 15.dp,
//                                vertical = 10.dp
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            modifier = Modifier
//                                .onGloballyPositioned { coordinates ->
//                                    if (tabIndicatorPositions.size < tabs.size) {
//                                        val position =
//                                            with(density) { coordinates.boundsInRoot().bottomCenter.x.toDp() } -
//                                                    HALF_INDICATOR_WIDTH
//                                        if (position >= DEFAULT_INDICATOR_POSITION) {
//                                            tabIndicatorPositions.add(position)
//                                        }
//                                    }
//                                }
//                                .clickable(
//                                    indication = null,
//                                    interactionSource = MutableInteractionSource()
//                                ) {
//                                    onTabSelect(tabItem)
//                                },
//                            text = tabItem.title,
//                            color = Color(0xFFF1F1F1),
//                            style = TextStyle(
//                                fontSize = 14.sp,
//                                lineHeight = 20.sp,
//                                fontWeight = FontWeight(500),
//                                color = Color(0xFFF3EBE6),
//                            )
//                        )
//                    }
                }
            }
        }
    }
}

data class TabItem(
    val id: Int,
    val title: String,
)

@Preview
@Composable
fun PreviewTabs() {
    val tabs = listOf(
        TabItem(0, "Service Estimate"),
        TabItem(1, "Inspection Report"),
        TabItem(2, "Inspection Pictures")
    )
    var currentTabSelect by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
    ) {
        Tabs(
            tabs = tabs,
            onTabSelect = {
                currentTabSelect = it.id
            },
            selectedIndex = currentTabSelect,
        )
    }
}