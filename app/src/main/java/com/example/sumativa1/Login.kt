package com.example.sumativa1

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.accompanist.insets.LocalWindowInsets


@Composable
fun Login(){
    val usuarios = arrayOf(
        User("user1", "password1"),
        User("user2", "password2"),
        User("user3", "password3")
    )
    val context = LocalContext.current
    val gradientColor = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF808080)
    )


    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showAlertDialog by remember { mutableStateOf(false) }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColor))
            .windowInsetsPadding(WindowInsets.ime) // Esto añade padding cuando el teclado es visible
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Img Login",
            modifier = Modifier.size(100.dp))

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "TextVoice",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        OutlinedTextField(value = username, onValueChange = {
            username = it
        },
            label = {Text(text = "Usuario")})

        Spacer(modifier = Modifier
            .height(10.dp)
        )

        OutlinedTextField(value = password, onValueChange = {
            password = it
        },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Botón password visibilidad")
                }
            },
            label = {Text(text = "Contraseña")})

        Spacer(modifier = Modifier
            .height(10.dp)
        )

        Button(onClick = {
            val inputUsername = username
            val inputPassword = password
            if(username.isEmpty()){
                Toast.makeText(context, "Ingrese un usuario", Toast.LENGTH_LONG).show()
                return@Button
            }
            if(password.isEmpty()){
                Toast.makeText(context, "Ingrese la contraseña", Toast.LENGTH_LONG).show()
                return@Button
            }
            val loginSuccess = validaUsuario(usuarios, inputUsername, inputPassword)
            if (loginSuccess) {
                val navigate = Intent(context, HomeActivity::class.java)
                context.startActivity(navigate)
            }else{
                showAlertDialog = true
            }

        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF004d00)
            )
        ) {
            Text(text = "Iniciar Sesión")
        }
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = { Text(text = "Error") },
                text = { Text("Usuario o contraseña incorrectos.") },
                confirmButton = {
                    Button(
                        onClick = { showAlertDialog = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            Text(text = "Olvidaste tu contraseña?",
                modifier = Modifier.clickable{
                    val navigate = Intent(context, RecuperarActivity::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold)
            Text(text = "Registrate!",
                modifier = Modifier.clickable{
                    val navigate = Intent(context, RegistrarActivity::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold)
        }
    }
}
data class User(val username: String, val password: String)
fun validaUsuario(users: Array<User>, username: String, password: String):Boolean{
    for (user in users) {
        if (user.username == username && user.password == password) {
            return true
        }
    }
    return false
}
@Preview
@Composable
fun VistaPrevia(){
    Login()
}