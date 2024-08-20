package com.example.sumativa1

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sumativa1.ui.theme.Sumativa1Theme
import com.example.sumativa1.ViewModelSingleton
import java.util.Locale

class HomeActivity : ComponentActivity() {
    private lateinit var tts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel =  ViewModelSingleton.usuariosViewModel
        setContent {
            ListaUsuarios(viewModel)
        }
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Configura el idioma
                tts.language = Locale.US
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        // Libera recursos del TextToSpeech
        tts.shutdown()
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    @Composable
    fun ListaUsuarios(viewModel: UsuariosViewModel){
        val context = LocalContext.current
        val gradientColor = listOf(
            Color(0xFFFFFFFF),
            Color(0xFF808080)
        )
        var textoAVoz by remember { mutableStateOf("") }

        val activity = context as HomeActivity
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColor)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){
            Spacer(modifier = Modifier.height(10.dp))
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF004d00),
                    contentColor = Color.White
                )
            )
            {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Img Login",
                        modifier = Modifier.size(80.dp))

                    Text(text = "Hola",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray),
                verticalArrangement = Arrangement.Center
            ){
                Text(text = "Estos son tus colaboradores:",
                    fontSize = 15.sp,
                    fontWeight= FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(20.dp, 20.dp))
                LazyColumn {
                    items(viewModel.listaUsuarios){
                            usuario -> Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 5.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
                        colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Image(painter = painterResource(R.drawable.logo), contentDescription = "Login Imagen",
                                modifier = Modifier.size(50.dp))

                            Text(text = usuario.nombre,
                                fontSize = 16.sp,
                                fontWeight= FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp))

                        }
                    }
                    }
                }
            }
            Column (modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColor)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(value = textoAVoz, onValueChange = {
                    textoAVoz = it
                },
                    label = {Text(text = "Texto a voz")})

                Spacer(modifier = Modifier.height(5.dp))

                Button(onClick = {
                    activity.speak(textoAVoz)
                },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(280.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF004d00)
                    )
                ) {
                    Text(text = "Reproducir")
                }
            }
        }
    }

    @Preview
    @Composable
    fun VistaPreviaListaUsuarios(){
        val viewModel =  ViewModelSingleton.usuariosViewModel
        ListaUsuarios(viewModel)
    }
}