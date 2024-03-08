package uz.gita.asaxiyappcompose.screens.main.tabs.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.asaxiyappcompose.R


class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<ProfileViewModel>()
        ProfileContent(viewModel::onEventDispatchers)
    }
}

@Composable
fun ProfileContent(eventDispatchers: (ProfileIntent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Black),
            ) {
            Text(text = "Profile", fontSize = 18.sp, color = White)
        }

        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(144.dp)
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier.size(128.dp),
                    contentDescription = "", painter = painterResource(id = R.drawable.profile_img)
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                ) {
                    Text(text = "Name")
                    Text(text = "Surname")
                    Text(text = "Gmail Address")
                }
            }
        }
    }
}