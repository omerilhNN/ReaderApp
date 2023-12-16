package com.omrilhn.readerapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omrilhn.readerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardInputField(
    modifier:Modifier = Modifier,
    text: String = "",
    hint:String = "",
    error:String = "",
    style:TextStyle = TextStyle(fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
    ),
    leadingIcon:ImageVector?=null,
    isSingleLine:Boolean = true,
    maxLines: Int =1,
    maxLength:Int = 40,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible:Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = FocusRequester()
){
    Column(modifier = modifier
        .fillMaxWidth()
        .then(modifier)){
        OutlinedTextField(
            value = text,
            label = {Text(text = hint)},
            onValueChange = {
                //if Input length is lesser maxLength then change the value
                if(it.length <= maxLength){
                    onValueChange(it)
                }
            },
            maxLines = maxLines,
            textStyle = style,
            placeholder = {
                Text(text = hint,
                    style = MaterialTheme.typography.bodySmall)
            },
            isError = error != "",
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            visualTransformation = if(isPasswordToggleDisplayed && !isPasswordVisible){
                PasswordVisualTransformation()
            }else{
                VisualTransformation.None
            },
            singleLine = isSingleLine,
            leadingIcon = if(leadingIcon != null){
                val icon: @Composable () -> Unit ={
                    Icon(imageVector = leadingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(16.dp)
                        )
                }
                icon
            }else null,
            trailingIcon = if(isPasswordToggleDisplayed){
                val icon:@Composable () -> Unit = {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!isPasswordVisible)
                        }
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            tint = Color.LightGray,
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.hidePassword)
                            } else {
                                stringResource(id = R.string.showPassword)
                            }
                        )
                    }
                }
                icon
            }else null,
            modifier = Modifier
                .padding(start = 10.dp,bottom = 10.dp,end = 10.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardActions = onAction
            )
        if(error.isNotEmpty()){
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}