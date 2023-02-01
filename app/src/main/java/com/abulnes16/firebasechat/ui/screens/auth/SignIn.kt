package com.abulnes16.firebasechat.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abulnes16.firebasechat.R
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.repository.FirestoreService
import com.abulnes16.firebasechat.ui.components.AuthFooter
import com.abulnes16.firebasechat.ui.components.PasswordInput
import com.abulnes16.firebasechat.ui.components.Screen
import com.abulnes16.firebasechat.ui.launchers.rememberFirebaseAuthLauncher
import com.abulnes16.firebasechat.viewmodels.AuthViewModel
import com.abulnes16.firebasechat.viewmodels.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun SignInScreen(
    onSignUp: () -> Unit,
    onSuccessSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(
            authProvider = Firebase.auth,
            repository = FirestoreService
        )
    ),
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val token = stringResource(id = R.string.google_web_client_id)

    val launcher =
        rememberFirebaseAuthLauncher(onAuthComplete = { _ ->
            onSuccessSignIn()
        }, onAuthError = { _ ->
            Toast.makeText(
                context,
                R.string.error_sign_in_google,
                Toast.LENGTH_LONG
            ).show()
        })

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
            if (authViewModel.requestStatus === RequestState.LOADING) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            } else {
                Button(
                    onClick = {
                        authViewModel.onSignIn(onSuccess = onSuccessSignIn, onFailed = {
                            Toast.makeText(
                                context,
                                R.string.error_sign_in,
                                Toast.LENGTH_LONG
                            ).show()
                        })
                    },
                    enabled = authViewModel.onValidateSignIn(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = stringResource(R.string.sign_up))
                }

            }

            OutlinedButton(onClick = {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }, modifier = Modifier.fillMaxWidth()) {
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
    SignInScreen({}, {})
}