package ru.akulinina.musicplayer.category.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.category.presentation.model.CategoryItem
import ru.akulinina.musicplayer.ui.theme.SeparateColor
import ru.akulinina.musicplayer.ui.theme.TextViewStyle

//@Preview(showBackground = true)
@Composable
fun CategoryRow(categoryItem: CategoryItem, mediator: TrackCategoryMediator?) {
    Box(modifier = Modifier
        .background(SeparateColor)
        .defaultMinSize(minHeight = dimensionResource(id = R.dimen.track_row_item_text_size))
        .fillMaxWidth()
        .clickable { })
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 2.dp)
                .background(Color.White)
                .defaultMinSize(minHeight = dimensionResource(id = R.dimen.track_row_item_text_size))
                .fillMaxWidth()
                .clickable {
                    //Log.d("MyLog", categoryItem.name)
                    mediator?.onCategoryClicked(categoryItem.name)
                }) {

            Text(
                text = categoryItem.name,
                style = TextViewStyle,
                modifier = Modifier
                    .weight(1.0f)
                    .padding(dimensionResource(id = R.dimen.padding)),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.img_arrow_right),
                contentDescription = "img1",//categoryItem.name,
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(end = dimensionResource(id = R.dimen.padding)),
                alignment = Alignment.CenterEnd
            )
        }
    }
}