package gautam.sarthak.textextractor.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(viewModel: MainViewModel= hiltViewModel()) {

    val text= viewModel.extractedText.collectAsState().value

    val galleryLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent() ,
        onResult = {
            if (it != null) {
                viewModel.getTextFromSelectedImage(it)
            }
        })

    val cameraLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {viewModel.getTextFromCapturedImage(it!!)}
    )
        Column(modifier= Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Card {
                LazyColumn(modifier= Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)){
                    item {
                        SelectionContainer {
                                Text(text = text,modifier=Modifier.padding(vertical = 3.dp, horizontal = 3.dp))
                        }
                    }
                }
            }
            Button(onClick = viewModel::copyTextToClipboard,
                enabled = text.isNotBlank()) {
                Text(text = "Copy Text")
            }
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                    Button(onClick = { cameraLauncher.launch() }) {
                        Text(text = "Start Camera")
                    }
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Text(text = "Start Camera")
                }
            }
        }
}