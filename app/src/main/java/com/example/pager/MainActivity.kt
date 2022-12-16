package com.example.pager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pager.ui.theme.PagerTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Pager()
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun Pager() {
    var list1 by remember { mutableStateOf(cards) }
    var list2 by remember { mutableStateOf(cards) }

    val pagerState = rememberPagerState()
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(state = lazyListState) {
        stickyHeader {
            TabRow(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(size = 16.dp)
                    ),
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
            ) {
                Tabs.values().forEachIndexed { index, tab ->
                    Tab(
                        modifier = Modifier
                            .padding(2.dp)
                            .background(
                                color = if (pagerState.currentPage == index) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(15.dp)),
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(page = index) } },
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tab.name,
                                color = Color.Red,
                            )
                        }
                    }
                }
            }
        }

        item {
            HorizontalPager(
                modifier = Modifier.background(color = Color.Green.copy(alpha = 0.5f)),
                count = Tabs.values().size,
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                when (Tabs.values()[page]) {
                    Tabs.Left -> {
                        Column {
                            list1.forEach { card ->
                                Column(
                                    modifier = Modifier
                                        .padding(30.dp)
                                        .fillMaxWidth()
                                        .background(color = Color.White),
                                ) {
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                                        text = card.title
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp, start = 25.dp),
                                        text = card.description
                                    )
                                }
                            }

                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 40.dp)
                                    .fillMaxWidth()
                                    .padding(top = 15.dp),
                                onClick = { list1 += cards }
                            ) {
                                Text(text = "Click me")
                            }
                        }
                    }

                    Tabs.Right -> {
                        Column {
                            list2.forEach { card ->
                                Column(
                                    modifier = Modifier
                                        .padding(30.dp)
                                        .fillMaxWidth()
                                        .background(color = Color.White)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                                        text = card.title
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp, start = 25.dp),
                                        text = card.description
                                    )
                                }
                            }

                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 40.dp)
                                    .fillMaxWidth()
                                    .padding(top = 15.dp),
                                onClick = { list2 += cards }
                            ) {
                                Text(text = "Click me")
                            }
                        }
                    }
                }
            }
        }

    }
}

val cards = listOf(
    Card(title = "title 1", description = "description 1"),
    Card(title = "title 2", description = "description 2"),
)

data class Card(
    val title: String,
    val description: String
)

enum class Tabs {
    Left, Right
}
