package com.example.gradecalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat


val fontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Medium),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_bolditalic, FontWeight.Bold),
)





class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            println("Hello World")
            val painter = painterResource(id = R.drawable.android_superhero3)
            val description = "Notification background"
            val title = "Superhero hello"

            val homeworkList = remember { mutableStateListOf<Int>() }

            var finalScore by remember { mutableStateOf(0.0) }

            val scrollState = rememberScrollState()
            val df = DecimalFormat("#.##")

            var midterm1 = remember { TextFieldState() }
            var midterm2 = remember { TextFieldState() }
            var participation = remember { TextFieldState() }
            var groupPresentation = remember { TextFieldState() }
            var finalProject = remember { TextFieldState() }

            //var homeworkGrades = mutableListOf(100,50,20,30,60)

//            Box(modifier = Modifier.fillMaxWidth(0.5f).padding(16.dp)) {
//                ImageCard(painter = painter, contentDescription = description, title = title)
//            }
            Column {
                LazyColumn (){
                    itemsIndexed(homeworkList) {index, item ->
                        HomeworkGradeBox(index+1, item)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var value = remember { TextFieldState() }
                    GradeBox("Add Homework", 100, value)
                    Button(onClick = {
                        if(homeworkList.count() < 5) {
                            homeworkList.add(value.text.toInt());
                        }
                    }) {
                        Text("Add")
                    }
                    Button(onClick = {
                        homeworkList.clear()
                    }) {
                        Text("Reset Homeworks")
                    }
                }
                Column(
                    //modifier = Modifier.background(Color.Green),
                    modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GradeBox("Midterm 1", 100, midterm1)
                    GradeBox("Midterm 2", 100, midterm2)
                    GradeBox("Participation", 100, participation)
                    GradeBox("Group Presentation", 100, groupPresentation)
                    GradeBox("Final Project", 100, finalProject)



                    Text("Final Grade $finalScore", fontSize = 25.sp)
                    Button(onClick = {
                        var final = calculateGrade(homeworkList,midterm1.text.toInt(), midterm2.text.toInt(), participation.text.toInt(), groupPresentation.text.toInt(), finalProject.text.toInt())
                        finalScore = df.format(final).toDouble()
                    }) {
                        Text("Calculate")
                    }
                }
            }


        }
    }

    fun calculateGrade (homeworkGrades : List<Int>, midterm1 : Int,
                        midterm2 : Int, participation: Int, groupPresentation: Int, finalProject: Int)  : Double {
        var homework = 0f;
        for (grade in homeworkGrades){
            homework += grade;
        }
        if(homeworkGrades.isNotEmpty()) {
            homework /= homeworkGrades.count();
        }
        var finalGrade = 0.2f * homework + 0.1f * groupPresentation + 0.1f * participation + 0.1 * midterm1 + 0.2 * midterm2 + 0.3 * finalProject;
        return finalGrade;
    }

    @Composable
    fun HomeworkGradeBox(
        id: Int,
        value: Int
    ) {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Surface(modifier = Modifier.padding(2.dp)) {
                    Text("Homework$id: $value")
                }
            }
        }
    }

    @Composable
    fun GradeBox(
        title: String,
        defaultValue: Int,
        textState : TextFieldState = remember { TextFieldState() }
    ) {
        val pattern = remember { Regex("^\\d+\$")}
        var isError by rememberSaveable { mutableStateOf(false) }
        textState.text = defaultValue.toString()

        fun validateInput(text: String) {
            isError = text.toInt() > 100
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.fillMaxWidth(0.8f)){
                Surface(modifier = Modifier.padding(5.dp)){
                    Text(title)
                }
                Surface(modifier = Modifier.padding(5.dp)){
                    TextField(
                        value = textState.text,
                        label = {
                            Text("Enter your grade")
                        },
                        onValueChange =  {
                            //if(textState.text.matches(pattern)) {
                                textState.text = it
                           // }

//                            validateInput(it)
                        },
//                        isError = isError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
//                    if (isError) {
//                        Text(
//                            text = "Grade should be from 0-100",
//                            color = MaterialTheme.colors.error,
//                            style = MaterialTheme.typography.caption,
//                            modifier = Modifier.padding(start = 16.dp)
//                        )
//                    }
                }
            }

        }
    }



    @Composable
    fun ImageCard(

        painter: Painter,
        contentDescription : String,
        title: String,
        modifier: Modifier = Modifier
    ) {

        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ) {
            Box(modifier = modifier.height(200.dp)) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = modifier.fillMaxSize().background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
                )
                Box (
                    modifier = modifier.fillMaxSize().padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        title, style = TextStyle(color = Color.White, fontSize = 16.sp, fontFamily = fontFamily)
                    )
                }
            }
        }
    }

}
class TextFieldState() {
    var text: String by mutableStateOf("")
}

