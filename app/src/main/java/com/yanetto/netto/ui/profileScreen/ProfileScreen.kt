package com.yanetto.netto.ui.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yanetto.netto.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
){

    Column (modifier = modifier.fillMaxSize()){
        Text(
            text = stringResource(R.string.profile),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.displaySmall
            )

        ElevatedCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row (modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(id = R.drawable.account_circle_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .weight(0.4f)
                        .size(80.dp)
                )
                Text(
                    text = stringResource(R.string.username),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(64.dp)
        )

        ElevatedCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row (modifier = Modifier.padding(12.dp),verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(id = R.drawable.settings_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .weight(0.25f)
                        .size(36.dp)
                )
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )
            }
        }

        ElevatedCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row (modifier = Modifier.padding(12.dp),verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(id = R.drawable.logout_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .weight(0.25f)
                        .size(36.dp)
                )
                Text(
                    text = "Exit",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )
            }
        }

    }
}