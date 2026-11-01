package com.muneeb.homesafe.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.PureWhite

enum class JourneyStepState { DONE, CURRENT, PENDING }

data class JourneyStep(
    val label: String,
    val state: JourneyStepState,
    @DrawableRes val iconRes: Int
)

@Composable
fun JourneyStepper(
    steps: List<JourneyStep>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(PureWhite)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, step ->
            JourneyStepNode(step = step)
            if (index != steps.lastIndex) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 14.dp, start = 6.dp, end = 6.dp)
                        .height(2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(if (step.state == JourneyStepState.DONE) DeepTeal else Border)
                )
            }
        }
    }
}

@Composable
private fun JourneyStepNode(step: JourneyStep) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .then(
                    when (step.state) {
                        JourneyStepState.DONE -> Modifier.background(DeepTeal)
                        JourneyStepState.CURRENT -> Modifier
                            .background(PureWhite)
                            .border(2.dp, DeepTeal, CircleShape)
                        JourneyStepState.PENDING -> Modifier.background(Divider)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = step.iconRes),
                contentDescription = null,
                tint = when (step.state) {
                    JourneyStepState.DONE -> PureWhite
                    JourneyStepState.CURRENT -> DeepTeal
                    JourneyStepState.PENDING -> Faint
                },
                modifier = Modifier.size(15.dp)
            )
        }
        Text(
            text = step.label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (step.state == JourneyStepState.PENDING) Faint else DeepTeal
        )
    }
}
