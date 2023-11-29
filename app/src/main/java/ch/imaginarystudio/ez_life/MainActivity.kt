package ch.imaginarystudio.ez_life

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.imaginarystudio.ez_life.ui.theme.Ez_lifeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ez_lifeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        TopSection()
        MiddleSection()
        BottomSection()
    }
}

@Composable
fun BottomSection() {
    Text(text = "Bottom")
}



class TodoItem(s: String, i: Int, id: Int) {
    var priority = i
    var text = s
    var id = id
}


var todos = mutableStateListOf(
    TodoItem("Hello my", 1, 0),
    TodoItem("Dear todos", 2, 1),
    TodoItem("wtf", 3, 2),
    TodoItem("do it", 2, 3),
)

@Composable
fun MiddleSection() {
    InputSection()
    TodoListSection(todos.groupBy { it.priority })
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoListSection(groupedTodos: Map<Int, List<TodoItem>>) {
    LazyColumn (
        modifier = Modifier
            .padding(10.dp)
    ){
        groupedTodos.forEach {
            (prio, todoItems) ->
            stickyHeader {
                PrioHeader(prio)
            }

            items(todoItems) {todoItem ->
                TodoItemView(todoItem)
            }
        }
    }
}

@Composable
fun TodoItemView(todoItem: TodoItem) {
    Row (modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
        )
    {
        IconButton(onClick =
        {
            var i = todos.find { it.id == todoItem.id }
            if (i != null) {
                i.id = i.id + 1
            }
        },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .size(25.dp)
        ) {
            Icon(
                Icons.Filled.KeyboardArrowUp,
                "up",
            )
        }
        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .size(25.dp)
        ) {
            Icon(
                Icons.Filled.KeyboardArrowDown,
                "down",
            )
        }

        Text(
            text = todoItem.text,
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .weight(1f)
                .background(MaterialTheme.colorScheme.secondary),
            fontSize = 20.sp
        )

        IconButton(onClick = { /*TODO*/ },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .size(25.dp)
        ) {
            Icon(
                Icons.Filled.Favorite,
                "check"
            )
        }
    }
}

@Composable
fun PrioHeader(prio: Int) {
    if (prio==1)
    {
        Text(
            text = "High Priority",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary)
        )
    } else if (prio==2){
        Text(
            text = "Medium Priority",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary)
        )
    } else {
        Text(
            text = "Low Priority",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary)
        )
    }
}




@Composable
fun InputSection() {

    Row (
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp, 0.dp)
        ) {
            InputField()
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .background(color=MaterialTheme.colorScheme.secondary)
            ) {
            Text(
                text = "+",
                fontSize = 30.sp
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField() {

    var task_input_text by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = task_input_text,
        onValueChange = {x -> task_input_text=x},
        label = {
            Text(
                text = stringResource(id = R.string.hint_add_todo))
                },
        modifier = Modifier
            .padding(0.dp)
            .background(color = MaterialTheme.colorScheme.secondary)
            .fillMaxWidth(),
        singleLine = false,
        maxLines = 4
    )

}


@Composable
fun TopSection() {
    Row (
        modifier = Modifier
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth(),
    ){
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 30.sp,
            modifier = Modifier
                .padding(10.dp),
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Title icon",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(10.dp)
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.tertiary)
        )

    }
}
