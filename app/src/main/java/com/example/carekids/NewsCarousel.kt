package com.example.carekids

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.carekids.data.model.Story
import com.example.carekids.data.model.allStories
import com.example.carekids.ui.theme.CareKidsLightPink
import com.example.carekids.ui.theme.CareKidsTheme
import com.example.carekids.ui.theme.CareKidsYellow
import com.example.carekids.ui.theme.FredokaFamily
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun StoriePreview() {
    CareKidsTheme {
        StoriesCarousel(modifier = Modifier, stories = allStories, {} )
    }
}

@Composable
fun StoriesCarousel(
    modifier: Modifier = Modifier,
    stories: List<Story> = allStories,
    onStoryClick: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        // Balloon-style heading
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(CareKidsLightPink, CareKidsYellow)
                    )
                )
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(modifier = Modifier
                .fillMaxWidth(),
                text = "📚 Cuentos para niños",
                style = TextStyle(
                    fontFamily = FredokaFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            stories.forEach { story ->
                StoryCard(story = story, onClick = { onStoryClick(story.id) })
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
private fun StoryCard(story: Story, onClick: () -> Unit) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(200.dp)
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                scope.launch {
                    scale.animateTo(1.08f, animationSpec = tween(120))
                    scale.animateTo(1f, animationSpec = tween(120))
                    onClick()
                }
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(story.coverImageUrl)
                    .addHeader("User-Agent", "CareKids/1.0 (jowell1921@gmail.com)")
                    .crossfade(true)
                    .build(),
                contentDescription = story.title,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_hospital),
                error = painterResource(R.drawable.ic_hospital),
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay for text legibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
                            startY = 80f
                        )
                    )
            )

            // Points badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(CareKidsYellow)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "⭐ ${story.pointsReward}",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = Color.White
                    )
                )
            }

            // Story info at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = "${story.emoji} ${story.title}",
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 17.sp
                )
                Text(
                    text = story.author,
                    style = TextStyle(
                        fontFamily = FredokaFamily,
                        fontStyle = FontStyle.Italic,
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
            }
        }
    }
}
