package com.muneeb.homesafe.feature.companions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.HomeSafeOutlineButton
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.OrDivider
import com.muneeb.homesafe.ui.components.SectionCard

@Composable
fun AddTrustedContactScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onChoosePhoneContactsClick: () -> Unit = {},
    onSendInviteClick: () -> Unit = {},
    onShowQrClick: () -> Unit = {},
    onShareLinkClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
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
                title = stringResource(R.string.cd_add_trusted_contact)
            )
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
            Text(
                text = stringResource(R.string.add_trusted_contact_lead),
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = Muted
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PureWhite)
                    .clickable(onClick = onChoosePhoneContactsClick)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconTile(iconRes = R.drawable.ic_contact_card)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.add_trusted_contact_choose_phone_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                    Text(
                        text = stringResource(R.string.add_trusted_contact_choose_phone_subtitle),
                        fontSize = 12.sp,
                        color = Subtle
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = null,
                    tint = Faint,
                    modifier = Modifier
                        .size(14.dp)
                        .scale(scaleX = -1f, scaleY = 1f)
                )
            }

            OrDivider(text = stringResource(R.string.add_trusted_contact_or_manual))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = stringResource(R.string.add_trusted_contact_full_name_label),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Ink
                )
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.add_trusted_contact_full_name_hint),
                            fontSize = 14.sp,
                            color = Subtle
                        )
                    },
                    textStyle = TextStyle(fontSize = 14.sp, color = Ink),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedBorderColor = DeepTeal,
                        unfocusedBorderColor = Border
                    )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = stringResource(R.string.add_trusted_contact_email_label),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Ink
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.add_trusted_contact_email_hint),
                            fontSize = 14.sp,
                            color = Subtle
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mail),
                            contentDescription = null,
                            tint = Subtle
                        )
                    },
                    textStyle = TextStyle(fontSize = 14.sp, color = Ink),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(BrandTint)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info_circle),
                    contentDescription = null,
                    tint = DeepTeal,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = stringResource(R.string.add_trusted_contact_info_note),
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = DeepTeal,
                    modifier = Modifier.weight(1f)
                )
            }

            HomeSafePrimaryButton(
                text = stringResource(R.string.add_trusted_contact_send_invite_button),
                onClick = onSendInviteClick,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )

            OrDivider(text = stringResource(R.string.add_trusted_contact_or_spot))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HomeSafeOutlineButton(
                    text = stringResource(R.string.add_trusted_contact_show_qr_button),
                    onClick = onShowQrClick,
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_qr_code),
                            contentDescription = null,
                            tint = DeepTeal,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                )
                HomeSafeOutlineButton(
                    text = stringResource(R.string.add_trusted_contact_share_link_button),
                    onClick = onShareLinkClick,
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = DeepTeal,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddTrustedContactScreenPreview() {
    HomeSafeTheme {
        AddTrustedContactScreen(onBackClick = {})
    }
}
