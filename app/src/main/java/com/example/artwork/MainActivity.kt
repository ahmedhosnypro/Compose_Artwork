package com.example.artwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artwork.ui.theme.ArtworkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtworkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ArtworkLayout()
                }
            }
        }
    }
}


var imageList: Array<ImageData> = arrayOf(
    ImageData(R.drawable.lemon_tree, R.string.lemon_tree, R.string.artist, R.string.year),
    ImageData(R.drawable.lemon_squeeze, R.string.lemon_squeeze, R.string.artist, R.string.year),
    ImageData(R.drawable.lemon_drink, R.string.lemon_drink, R.string.artist, R.string.year),
    ImageData(R.drawable.lemon_restart, R.string.lemon_restart, R.string.artist, R.string.year),
)


@Composable
fun ArtworkLayout() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        val imageData = remember {
            mutableStateOf(imageList[0])
        }
        val index = imageList.indexOf(imageData.value)

        ArtworkImage(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f),
            imageId = imageData.value.imageId,
        )


        ArtworkDescriptor(
            modifier =  Modifier.fillMaxHeight(0.2f),
            title = stringResource(imageData.value.titleId),
            artist = stringResource(imageData.value.aristId),
            year = stringResource(imageData.value.yearId)
        )


        Spacer(modifier = Modifier.size(16.dp))

        DisplayController(
            modifier =  Modifier.fillMaxHeight(0.1f),
            onNext = {
                when (index) {
                    imageList.size - 1 -> {
                        imageData.value = imageList[0]
                    }

                    else -> {
                        imageData.value = imageList[index + 1]
                    }
                }
            },
            onPrevious = {
                when (index) {
                    0 -> {
                        imageData.value = imageList[imageList.size - 1]
                    }

                    else -> {
                        imageData.value = imageList[index - 1]
                    }
                }
            },
        )
    }
}

@Composable
fun ArtworkImage(
    @DrawableRes imageId: Int, modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(32.dp)
            .shadow(3.dp, MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}


@Composable
fun ArtworkDescriptor(
    title: String, artist: String, year: String, modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = title,
            fontSize = 36.sp
        )

        Text(
            text = "$artist ($year)"
        )
    }
}


@Composable
fun DisplayController(
    onNext: () -> Unit, onPrevious: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onNext) {
            Text(text = stringResource(R.string.previous))
        }

        Button(onClick = onPrevious) {
            Text(text = stringResource(R.string.next))
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
)
@Composable
fun ArtworkPreview() {
    ArtworkTheme {
        ArtworkLayout()
    }
}