package com.muneeb.homesafe.feature.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.ContactRow
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.RemovableChip
import com.muneeb.homesafe.ui.components.SelectionCheck

private data class PickerContact(
    val initial: String,
    val name: String,
    val subtitle: String,
    val selected: Boolean,
    val section: String
)

@Composable
fun InviteCompanionsScreen(
    onBackClick: () -> Unit,
    onAddCompanionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val contacts = remember {
        mutableStateListOf(
            PickerContact("A", "Ahmed Khan", "4 outings together", selected = true, section = "FREQUENT"),
            PickerContact("H", "Hamza Iqbal", "2 outings together", selected = false, section = "FREQUENT"),
            PickerContact("A", "Ali Raza", "+92 321 9034567", selected = true, section = "A"),
            PickerContact("A", "Ayesha Noor", "+92 323 7834521", selected = false, section = "A"),
            PickerContact("B", "Bilal Ahmed", "+92 333 5819043", selected = false, section = "B")
        )
    }
    val selectedContacts = contacts.filter { it.selected }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) focusManager.clearFocus()
    }

    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            HomeSafeTopBar(
                onLeadingClick = onBackClick,
                leadingContentDescription = stringResource(R.string.cd_back),
                title = stringResource(R.string.outing_invite_title)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp)
            ) {
                HomeSafePrimaryButton(
                    text = stringResource(R.string.outing_add_companions_button_format, selectedContacts.size),
                    onClick = onAddCompanionsClick
                )
            }
        }
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                },
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "search") {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.outing_search_hint),
                            fontSize = 14.sp,
                            color = Subtle
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = Subtle
                        )
                    },
                    textStyle = TextStyle(fontSize = 14.sp, color = Ink),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = { focusManager.clearFocus() }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedBorderColor = DeepTeal,
                        unfocusedBorderColor = Border
                    )
                )
            }

            if (selectedContacts.isNotEmpty()) {
                item(key = "selected") {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item(key = "selected_label") {
                            Text(
                                text = stringResource(R.string.outing_selected_count_format, selectedContacts.size),
                                fontSize = 12.sp,
                                color = Subtle
                            )
                        }
                        items(selectedContacts, key = { it.name }) { contact ->
                            RemovableChip(
                                text = contact.name.substringBefore(" "),
                                onRemove = {
                                    val index = contacts.indexOf(contact)
                                    contacts[index] = contact.copy(selected = false)
                                },
                                removeContentDescription = stringResource(
                                    R.string.cd_remove_contact_format,
                                    contact.name.substringBefore(" ")
                                )
                            )
                        }
                    }
                }
            }

            contacts.groupBy { it.section }.forEach { (section, sectionContacts) ->
                item(key = "section_$section") {
                    val sectionLabel = if (section == "FREQUENT") {
                        stringResource(R.string.outing_frequent_section_label)
                    } else {
                        section
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = sectionLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Faint
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(PureWhite)
                                .padding(horizontal = 16.dp)
                        ) {
                            sectionContacts.forEachIndexed { index, contact ->
                                val contactIndex = contacts.indexOf(contact)
                                ContactRow(
                                    initial = contact.initial,
                                    name = contact.name,
                                    subtitle = contact.subtitle,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    avatarBackground = if (contact.selected) DeepTeal else BrandTint,
                                    avatarContentColor = if (contact.selected) PureWhite else DeepTeal,
                                    onClick = {
                                        contacts[contactIndex] = contact.copy(selected = !contact.selected)
                                    },
                                    trailing = { SelectionCheck(selected = contact.selected) }
                                )
                                if (index != sectionContacts.lastIndex) {
                                    HorizontalDivider(color = Divider, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InviteCompanionsScreenPreview() {
    HomeSafeTheme {
        InviteCompanionsScreen(
            onBackClick = {},
            onAddCompanionsClick = {}
        )
    }
}
