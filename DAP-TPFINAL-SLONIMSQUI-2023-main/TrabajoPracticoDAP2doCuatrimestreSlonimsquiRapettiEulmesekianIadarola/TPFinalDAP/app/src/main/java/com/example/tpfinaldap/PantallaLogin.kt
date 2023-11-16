package com.example.tpfinaldap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PantallaLogin : Fragment() {

    private lateinit var authentication: FirebaseAuth

    lateinit var botonLogin: Button
    lateinit var botonRegistrar: Button
    lateinit var mail: EditText
    lateinit var contrasena: EditText
    lateinit var textoMail: String
    lateinit var textoPass: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pantalla_login, container, false)

        botonRegistrar = view.findViewById(R.id.buttonRegistrar)
        botonLogin = view.findViewById(R.id.botonLogin)
        mail = view.findViewById(R.id.textoUsuario)
        contrasena = view.findViewById(R.id.textoContra)
        authentication = Firebase.auth

        botonRegistrar.setOnClickListener {

            findNavController().navigate(R.id.action_pantallaLogin_to_pantallaRegister)

        }

        botonLogin.setOnClickListener {

            if (mail.text.isNotEmpty() && contrasena.text.isNotEmpty()) {

                textoMail = mail.text.toString()
                textoPass = contrasena.text.toString()

                authentication.signInWithEmailAndPassword(textoMail, textoPass).addOnCompleteListener { }
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            val user = authentication.currentUser
                            findNavController().navigate(R.id.action_pantallaLogin_to_pantallaInicio)

                        }

                        else {

                            Snackbar.make(it, "El mail y/o contraseña ingresados son incorrectos", Snackbar.LENGTH_SHORT,).show()

                        }

                    }

            }

            else{

                Snackbar.make(it, "Complete todos los campos", Snackbar.LENGTH_SHORT).show()

            }

        }

        return view

    }

}

