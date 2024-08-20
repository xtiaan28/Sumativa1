package com.example.sumativa1

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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

@Composable

fun Registro(viewModel: UsuariosViewModel){
    val context = LocalContext.current
    val gradientColor = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF808080)
    )

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColor)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Img Login",
            modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = nombre, onValueChange = {
            nombre = it
        },
            label = { Text(text = "Nombre") })
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = telefono, onValueChange = {
            telefono = it
        },
            label = {Text(text = "Teléfono")})
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = email, onValueChange = {
            email = it
        },
            label = {Text(text = "Correo Electrónico")})
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = usuario, onValueChange = {
            usuario = it
        },
            label = {Text(text = "Usuario")})
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = password, onValueChange = {
            password = it
        },
            label = {Text(text = "Contraseña")})
        Spacer(modifier = Modifier.height(5.dp))

        Button(onClick = {
            val nuevoUsuario = Usuario(nombre, telefono, email, usuario, password)
            viewModel.listaUsuarios.add(nuevoUsuario)
            // Limpia los campos de texto después de guardar
            nombre = ""
            telefono = ""
            email = ""
            usuario = ""
            password = ""

            Toast.makeText(context, "¡Usuario Registrado!", Toast.LENGTH_SHORT).show()
        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF004d00)
            )
        ) {
            Text(text = "Crear Cuenta")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(text = "Volver",
                modifier = Modifier.clickable{
                    val navigate = Intent(context, MainActivity::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold)
        }
    }


}
@Preview
@Composable
fun VistaPreviaRegistro(){
    val viewModel =  ViewModelSingleton.usuariosViewModel
    Registro(viewModel)
}