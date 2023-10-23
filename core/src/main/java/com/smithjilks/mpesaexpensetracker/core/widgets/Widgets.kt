package com.smithjilks.mpesaexpensetracker.core.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.smithjilks.mpesaexpensetracker.core.model.AppButton
import com.smithjilks.mpesaexpensetracker.core.model.Category
import com.smithjilks.mpesaexpensetracker.core.utils.CoreUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppInputTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    prefix: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    val keyboardControlController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        enabled = isEnabled,
        readOnly = isReadOnly,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
        ),
        minLines = minLines,
        maxLines = maxLines,
        label = {
            Text(text = label)
        },
        prefix = prefix,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        visualTransformation = VisualTransformation.None,
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardControlController?.hide()
        }),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp)
    )

}


@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSpinner(
    modifier: Modifier = Modifier,
    label: String,
    parentOptions: List<Category>,
    value: String = "",
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember {
        val defaultOption = parentOptions.firstOrNull { it.name == value }
        if (defaultOption != null) {
            mutableStateOf(defaultOption)
        } else {
            mutableStateOf(parentOptions[0])
        }
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        AppInputTextField(
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth(),
            text = selectedOption.name,
            label = label,
            isReadOnly = true,
            isEnabled = false,
            leadingIcon = if (selectedOption.imageId != null) {
                {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = selectedOption.imageId!!),
                        contentDescription = "${selectedOption.name} Icon",
                    )
                }
            } else null,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            onTextChange = {},
            onImeAction = {}
        )

        DropdownMenu(
            modifier = modifier
                .exposedDropdownSize(true)
                .padding(horizontal = 8.dp)
                .background(color = Color.Transparent),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            parentOptions.forEach { option ->
                DropdownMenuItem(
                    modifier = modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .exposedDropdownSize(true),
                    text = {
                        Text(
                            text = option.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        onValueChange(option.name)
                    },
                    leadingIcon = if (option.imageId != null) {
                        {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = option.imageId!!),
                                contentDescription = "${selectedOption.name} Icon",
                            )
                        }
                    } else null,
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
                Divider(modifier = modifier.padding(horizontal = 8.dp))
            }
        }


    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFilterChipsGroup(
    modifier: Modifier = Modifier,
    chipLabels: List<String>,
    selectedChip: String,
    onSelectedChange: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        items(chipLabels) {
            FilterChip(
                modifier = modifier.padding(4.dp),
                label = {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                },
                selected = selectedChip == it,
                onClick = {
                    onSelectedChange(it)
                },
            )
        }

    }
}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(showDatePickerDialog: Boolean, onValueChange: (String?) -> Unit) {
    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                onValueChange(null)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onValueChange(CoreUtils.formatDate(datePickerState.selectedDateMillis!!))
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onValueChange(null)
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePicker(showTimePicker: Boolean, onValueChange: (String?) -> Unit) {
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState()
        val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
        TimePickerDialog(
            onCancel = { onValueChange(null) },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                cal.set(Calendar.MINUTE, timePickerState.minute)
                cal.isLenient = false

                onValueChange(CoreUtils.formatTime(cal.time.time))
            },
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun AppButtonToggleGroup(
    modifier: Modifier = Modifier,
    buttons: List<AppButton>
) {
    var selectedOption by remember {
        mutableStateOf(buttons.firstOrNull()?.title ?: "")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    val selectedButtonIconTint = Color.White
    val unSelectedButtonIconTint = MaterialTheme.colorScheme.primary

    val selectedButtonBackground = MaterialTheme.colorScheme.primary
    val unSelectedButtonBackground = Color.White

    Card(
        modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            buttons.forEach { button ->
                Row(
                    modifier = modifier
                        .clickable {
                            onSelectionChange(button.title)
                            button.callback.invoke()
                        }
                        .background(
                            color = if (button.title == selectedOption) {
                                selectedButtonBackground
                            } else {
                                unSelectedButtonBackground
                            }
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    button.iconResource?.let {
                        Icon(
                            modifier = modifier
                                .padding(4.dp)
                                .size(24.dp),
                            imageVector = ImageVector.vectorResource(it),
                            tint = if (button.title == selectedOption) {
                                selectedButtonIconTint
                            } else {
                                unSelectedButtonIconTint
                            },
                            contentDescription = "${button.title} icon"
                        )
                    }

                    if (!button.isIconButton) {
                        Text(
                            text = button.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (button.title == selectedOption) {
                                Color.White
                            } else {
                                Color.Black
                            },
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

