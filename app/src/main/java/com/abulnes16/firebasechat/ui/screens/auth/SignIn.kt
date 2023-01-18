package com.abulnes16.firebasechat.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Email")
            },
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TextField(
            value = "", onValueChange = {},
            placeholder = {
                Text(text = "Password")
            }
        )

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Sign in")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Sign in with Google")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Doesn't have an account?", modifier = Modifier.padding(end = 16.dp))
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Sign up")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}