package com.muneeb.homesafe.feature.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.DestructiveRed
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.AddedInviteButton
import com.muneeb.homesafe.ui.components.CodeBox
import com.muneeb.homesafe.ui.components.ContactRow
import com.muneeb.homesafe.ui.components.DashedActionButton
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.OrDivider
import com.muneeb.homesafe.ui.components.SectionCard

private data class TrustedContactUi(
    val initial: String,
    val name: String,
    val added: Boolean
)

@Composable
fun StartOutingScreen(
    onBackClick: () -> Unit,
    onAddTrustedContactClick: () -> Unit,
    onStartOutingClick: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: StartOutingUiState = StartOutingUiState()
) {
    var outingName by remember { mutableStateOf("Friday Night Out") }
    val trustedContacts = remember {
        mutableStateListOf(
            TrustedContactUi(initial = "A", name = "Ahmed", added = true),
            TrustedContactUi(initial = "A", name = "Ali", added = false),
            TrustedContactUi(initial = "S", name = "Sara", added = false)
        )
    }
    val companions = trustedContacts.filter { it.added }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollState.isScrollInProgress) focusManager.clearFocus()
    }

    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            HomeSafeTopBar(
                onLeadingClick = onBackClick,
                leadingContentDescription = stringResource(R.string.cd_back),
                title = stringResource(R.string.outing_start_title)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeSafePrimaryButton(
                    text = stringResource(R.string.outing_start_button),
                    onClick = onStartOutingClick,
                    enabled = !uiState.isStarting,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_walking),
                            contentDescription = null,
                            modifier = Modifier.width(18.dp)
                        )
                    }
                )

                if (uiState.error != null) {
                    Text(
                        text = uiState.error,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = DestructiveRed,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null,
                        tint = Faint,
                        modifier = Modifier.width(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.outing_privacy_footer),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Faint
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = stringResource(R.string.outing_name_label),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Ink
                )
                OutlinedTextField(
                    value = outingName,
                    onValueChange = { outingName = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 15.sp, color = Ink),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedBorderColor = DeepTeal,
                        unfocusedBorderColor = Border
                    )
                )
            }

            SectionCard {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = stringResource(R.string.outing_invite_companions_title),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Ink
                        )
                        Text(
                            text = stringResource(R.string.outing_invite_companions_subtitle),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Subtle
                        )
                    }

                    Text(
                        text = stringResource(R.string.outing_trusted_contacts_label),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Subtle
                    )

                    Column {
                        trustedContacts.forEachIndexed { index, contact ->
                            ContactRow(
                                initial = contact.initial,
                                name = contact.name,
                                subtitle = stringResource(R.string.outing_trusted_contact_subtitle),
                                modifier = Modifier.padding(vertical = 12.dp),
                                trailing = {
                                    AddedInviteButton(
                                        added = contact.added,
                                        text = if (contact.added) {
                                            stringResource(R.string.outing_added_label)
                                        } else {
                                            stringResource(R.string.outing_invite_label)
                                        },
                                        onClick = {
                                            trustedContacts[index] = contact.copy(added = !contact.added)
                                        }
                                    )
                                }
                            )
                            if (index != trustedContacts.lastIndex) {
                                HorizontalDivider(color = Divider, thickness = 1.dp)
                            }
                        }
                    }

                    DashedActionButton(
                        text = stringResource(R.string.outing_add_trusted_contact_button),
                        onClick = onAddTrustedContactClick
                    )

                    OrDivider(text = stringResource(R.string.outing_or_invite_anyone))

                    CodeBox(
                        code = "7K4P",
                        onQrClick = {},
                        onShareClick = {},
                        qrContentDescription = stringResource(R.string.cd_qr_code),
                        shareContentDescription = stringResource(R.string.cd_share_code)
                    )
                }
            }

            Text(
                text = stringResource(R.string.outing_companions_count_format, companions.size),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Subtle,
                modifier = Modifier.padding(start = 4.dp)
            )

            companions.forEach { companion ->
                ContactRow(
                    initial = companion.initial,
                    name = companion.name,
                    subtitle = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(PureWhite)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    trailing = {
                        Text(
                            text = stringResource(R.string.outing_will_be_notified),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Subtle
                        )
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(BrandTint)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home_stroke),
                    contentDescription = null,
                    tint = DeepTeal,
                    modifier = Modifier.width(18.dp)
                )
                Text(
                    text = stringResource(R.string.outing_notify_hint),
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.Normal,
                    color = DeepTeal,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StartOutingScreenPreview() {
    HomeSafeTheme {
        StartOutingScreen(
            onBackClick = {},
            onAddTrustedContactClick = {},
            onStartOutingClick = {}
        )
    }
}
