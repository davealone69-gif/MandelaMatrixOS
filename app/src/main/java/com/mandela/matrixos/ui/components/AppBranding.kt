package com.mandela.matrixos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mandela.matrixos.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp
) {
    Image(
        painter = painterResource(id = R.drawable.re_imaginator_logo),
        contentDescription = "Re-Imaginator",
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MandelaVsMatrixBanner(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.mandela_vs_matrix_logo),
            contentDescription = "Mandela vs Matrix Re-Imaginator",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 160.dp)
                .padding(horizontal = 16.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "RE-IMAGINATOR",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
