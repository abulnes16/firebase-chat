package com.abulnes16.firebasechat.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abulnes16.firebasechat.R
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.ui.components.AuthFooter
import com.abulnes16.firebasechat.ui.components.PasswordInput
import com.abulnes16.firebasechat.ui.components.Screen
import com.abulnes16.firebasechat.viewmodels.AuthViewModel
import com.abulnes16.firebasechat.viewmodels.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun SignInScreen(
    onSignUp: () -> Unit,
    onSuccessSignIn: () -> Unit,
    authProvider: FirebaseAuth,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authProvider)),
) {
    val focusManager = LocalFocusManager.current
    Screen(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            TextField(
                value = authViewModel.email,
                onValueChange = { value -> authViewModel.onChangeEmail(value) },
                placeholder = {
                    Text(text = stringResource(R.string.email))
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
            PasswordInput(
                value = authViewModel.password,
                onValueChange = { value -> authViewModel.onChangePassword(value) },
                onDone = { focusManager.clearFocus() },
            )
            when (authViewModel.requestStatus) {
                RequestState.NONE,
                RequestState.FAILED -> Button(
                    // TODO: Add Failed path
                    onClick = { authViewModel.onSignIn(onSuccessSignIn, {}) },
                    enabled = authViewModel.onValidateSignIn(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = stringResource(R.string.sign_in))
                }

                RequestState.LOADING -> CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                else -> {
                    // TODO: Add else statement
                }
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
    val auth = Firebase.auth
    SignInScreen({}, {}, authProvider = auth)
}