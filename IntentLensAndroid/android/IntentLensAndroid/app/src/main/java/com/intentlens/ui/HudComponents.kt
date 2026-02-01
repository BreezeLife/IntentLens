package com.intentlens.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HudPanel(modifier: Modifier = Modifier, title: String, content: @Composable ColumnScope.() -> Unit) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .border(1.dp, HudColors.Stroke, RoundedCornerShape(14.dp)),
    colors = CardDefaults.cardColors(containerColor = HudColors.Panel)
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Text(title, color = HudColors.Cyan)
      content()
    }
  }
}

@Composable
fun HudKeyValue(key: String, value: String, valueColor: Color = HudColors.Text) {
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
    Text(key, color = HudColors.Muted)
    Text(value, color = valueColor)
  }
}
