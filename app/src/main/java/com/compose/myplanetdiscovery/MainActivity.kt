package com.compose.myplanetdiscovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compose.myplanetdiscovery.core.navigation.SetupNavGraph
import com.compose.myplanetdiscovery.ui.theme.MyPlanetDiscoveryTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPlanetDiscoveryTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}