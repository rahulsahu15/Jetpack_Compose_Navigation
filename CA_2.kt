package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme

class CA_2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Appnav()
            }
        }
    }
}

@Composable
fun Appnav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home(nav = navController)
        }

        composable(
            route = "review/{subName}",
            arguments = listOf(navArgument("subName") { type = NavType.StringType })
        ) { backStackEntry ->
            val subject = backStackEntry.arguments?.getString("subName") ?: "Unknown"

            ReviewScreen(
                subjectName = subject,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
@Composable
fun Home(nav : NavHostController){
    var sub by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var final by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = sub,
            onValueChange = { sub = it },
            label = { Text("Enter The Subject Name") },
            modifier = Modifier.fillMaxWidth().padding(20.dp).background(color = Color.LightGray)
        )

        TextField(
            value = hours,
            onValueChange = { hours = it },
            label = { Text("Enter The Hours") },
            modifier = Modifier.fillMaxWidth().padding(20.dp).background(color = Color.LightGray)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = final, onCheckedChange = {final = it })

            Text(text = "Confirm thr Finalized Subject")
        }

        Button(onClick = {
            val hr = hours.toIntOrNull() ?: 0
            if (hr > 0 && final && sub.isNotEmpty()) {
                nav.navigate("review/$sub")
            } else {
                feedbackMessage = "Please enter valid details and confirm the plan."
            }
        }) {
            Text("Plan Study")
        }

        if (feedbackMessage.isNotEmpty()) {
            Text(text = feedbackMessage, color = Color.Red, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
fun ReviewScreen(subjectName: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Review Your Plan")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Subject: $subjectName")

        Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Edit Plan")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    MyApplicationTheme {
        Appnav()
    }
}