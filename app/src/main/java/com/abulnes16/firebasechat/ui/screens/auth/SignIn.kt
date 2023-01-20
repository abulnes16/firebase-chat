package com.abulnes16.firebasechat.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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


@Composable
fun SignInScreen(
    onSignUp: () -> Unit,
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
                    Text(text = stringResource(R.string.email))
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TextField(
                value = "", onValueChange = {},
                placeholder = {
                    Text(text = stringResource(R.string.password))
                }
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = stringResource(R.string.sign_in))
            }

            OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.sign_in_with_google))
            }
            AuthFooter(
                leftText = R.string.account_creation,
                rightText = R.string.sign_up,
                onClick = onSignUp
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen({})
}