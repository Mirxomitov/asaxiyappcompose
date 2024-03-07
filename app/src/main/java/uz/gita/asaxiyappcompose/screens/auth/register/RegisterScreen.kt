package uz.gita.asaxiyappcompose.screens.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.asaxiyappcompose.R
import uz.gita.asaxiyappcompose.data.model.UserData
import uz.gita.asaxiyappcompose.ui.theme.AsaxiyAppComposeTheme

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<RegisterViewModel>()
        RegisterContent(
            eventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun RegisterContent(eventDispatcher: (RegisterIntent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(196.dp), contentDescription = "icon register", painter = painterResource(id = R.drawable.ic_register)
        )

        Spacer(modifier = Modifier.weight(1f))

        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), value = firstName, onValueChange = { firstName = it }, label = {
                Text(text = "First Name")
            }
        )



        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), value = lastName, onValueChange = { lastName = it }, label = {
                Text(text = "Last Name")
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), value = phone, onValueChange = { phone = it }, label = {
                Text(text = "Phone")
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), value = email, onValueChange = { email = it }, label = {
                Text(text = "E-mail")
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), value = password, onValueChange = { password = it }, label = {
                Text(text = "Password")
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            eventDispatcher(
                RegisterIntent.CreateAccount(
                    userData = UserData(
                        firstName = firstName,
                        lastName = lastName,
                        phone = phone,
                        gmail = email,
                    ), password = password
                )
            )
        }) {
            Text(text = "Create account", fontSize = 18.sp)
        }

        Text(text = "Sign in", Modifier.clickable {
            eventDispatcher(
                RegisterIntent.SignIn
            )
        })
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterContentPreview() {
    AsaxiyAppComposeTheme {
        RegisterContent({})
    }
}

