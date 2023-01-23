package com.abulnes16.firebasechat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abulnes16.firebasechat.R

@Composable
fun MessageForm(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = stringResource(id = R.string.type_something)) },
            modifier = Modifier.padding(end = 16.dp)
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(56.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = null
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MessageFormPreview() {
    MessageForm()
}