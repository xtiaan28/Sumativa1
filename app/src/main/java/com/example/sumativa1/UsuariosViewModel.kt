package com.example.sumativa1

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class UsuariosViewModel: ViewModel(){
    val listaUsuarios = mutableStateListOf<Usuario>()
}