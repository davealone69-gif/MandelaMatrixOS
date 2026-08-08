package com.mandela.matrixos.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import com.mandela.matrixos.ui.screens.*
import kotlinx.coroutines.launch

enum class MandelaScreen(val title: String, val icon: ImageVector) {
    MatrixCore("Core", Icons.Default.GridView),
    DevatorLab("Devator", Icons.Default.Science),
    Evaluateor("Eval", Icons.Default.Tune),
    MandelaCore("Mandela", Icons.Default.BlurOn),
    Knowledge("Know", Icons.Default.MenuBook),
    GeminiAI("Gemini", Icons.Default.AutoAwesome),
    FreeAI("FreeAI", Icons.Default.Psychology),
    ImageTools("Image", Icons.Default.Image),
    ApkAuditor("APK", Icons.Default.Verified),
    Training("Train", Icons.Default.School),
    Swarm("Swarm", Icons.Default.Build)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandelaApp() {
    val screens = MandelaScreen.entries
    val pagerState = rememberPagerState(pageCount = { screens.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mandela Matrix OS • ${screens[pagerState.currentPage].title}") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 8.dp
            ) {
                screens.forEachIndexed { index, screen ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        text = { Text(screen.title) },
                        icon = { Icon(screen.icon, contentDescription = screen.title) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (screens[page]) {
                    MandelaScreen.MatrixCore -> MatrixCoreScreen()
                    MandelaScreen.DevatorLab -> DevatorLabScreen()
                    MandelaScreen.Evaluateor -> EvaluateorScreen()
                    MandelaScreen.MandelaCore -> MandelaCoreScreen()
                    MandelaScreen.Knowledge -> KnowledgeScreen()
                    MandelaScreen.GeminiAI -> GeminiAiScreen()
                    MandelaScreen.FreeAI -> FreeAiScreen()
                    MandelaScreen.ImageTools -> ImageToolsScreen()
                    MandelaScreen.ApkAuditor -> ApkAuditorScreen()
                    MandelaScreen.Training -> TrainingCentreScreen()
                    MandelaScreen.Swarm -> SwarmBuilderScreen()
                }
            }
        }
    }
}
