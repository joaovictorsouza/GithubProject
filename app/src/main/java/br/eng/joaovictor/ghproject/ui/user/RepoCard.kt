package br.eng.joaovictor.ghproject.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.eng.joaovictor.ghproject.model.Repo

@Composable
fun RepoCard(
    modifier: Modifier = Modifier,
    item: Repo
){
    Card(modifier.height(100.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = item.name,
                modifier = Modifier.fillMaxWidth(),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif
            )

            Text(text = item.description ?: "")
        }
    }
}
