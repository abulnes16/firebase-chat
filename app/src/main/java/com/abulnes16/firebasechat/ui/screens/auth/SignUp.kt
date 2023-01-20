package com.abulnes16.firebasechat.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abulnes16.firebasechat.R
import com.abulnes16.firebasechat.ui.components.AuthFooter
import com.abulnes16.firebasechat.ui.components.Screen
import com.abulnes16.firebasechat.viewmodels.AuthViewModel

@Composable
fun SignUpScreen(
    onSuccessSignUp: () -> Unit,
    onGoToSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Screen(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = stringResource(R.string.name))
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = stringResource(R.string.email))
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextField(
                value = "", onValueChange = {},
                placeholder = {
                    Text(text = stringResource(R.string.password))
                }
            )
            Button(
                onClick = { onSuccessSignUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = stringResource(R.string.sign_up))
            }

            AuthFooter(
                leftText = R.string.already_have_account,
                rightText = R.string.login,
                onClick = onGoToSignIn
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen({}, {})
}