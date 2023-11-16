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

class PantallaRegister : Fragment() {

    private lateinit var authentication: FirebaseAuth

    lateinit var botonRegistrar: Button
    lateinit var botonAtras: Button
    lateinit var email: EditText
    lateinit var contrasena: EditText
    lateinit var confirmarContrasena: EditText
    lateinit var datoMail: String
    lateinit var datoPassword: String
    lateinit var datoConfPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pantalla_register, container, false)
        botonRegistrar = view.findViewById(R.id.botonCrearCuenta)
        botonAtras = view.findViewById(R.id.botonAtras)


        email = view.findViewById(R.id.textUsuario)
        contrasena = view.findViewById(R.id.textContraseña)
        confirmarContrasena = view.findViewById(R.id.textoRepContra)

        botonAtras.setOnClickListener {

            findNavController().navigate(R.id.action_pantallaRegister_to_pantallaLogin)

        }

        botonRegistrar.setOnClickListener {

            if (email.text.isNotEmpty() && contrasena.text.isNotEmpty() && confirmarContrasena.text.isNotEmpty()) {

                datoMail = email.text.toString()
                datoPassword = contrasena.text.toString()
                datoConfPassword = confirmarContrasena.text.toString()

                if (datoPassword.length < 5) {

                    Snackbar.make(it, "La contraseña es muy corta. Ingrese una contraseña de 5 carateres como minimo", Snackbar.LENGTH_SHORT,).show()

                } else if (datoPassword == datoConfPassword) {

                    authentication = Firebase.auth
                    authentication.createUserWithEmailAndPassword(datoMail, datoPassword)
                        .addOnCompleteListener { }
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                val user = authentication.currentUser
                                findNavController().navigate(R.id.action_pantallaRegister_to_pantallaLogin)

                            }

                            else {

                                Snackbar.make(it, "Mail invalido o ya registrado", Snackbar.LENGTH_SHORT).show()

                            }

                        }

                }

                else {

                    Snackbar.make(it, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()

                }

            } else {

                Snackbar.make(it, "Complete todos los campos", Snackbar.LENGTH_SHORT).show()

            }

        }

        return view
    }

}