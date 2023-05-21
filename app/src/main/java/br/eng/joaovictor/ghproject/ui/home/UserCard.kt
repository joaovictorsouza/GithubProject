@file:OptIn(ExperimentalMaterial3Api::class)

package br.eng.joaovictor.ghproject.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.eng.joaovictor.ghproject.model.User
import coil.compose.AsyncImage

@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(onClick = onClick) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {

            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp, 80.dp),
                model = user.avatarUrl, contentDescription = user.name
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Column {
                Text(
                    text = user.login,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif
                )
            }

        }
    }
}