package com.example.tpfinaldap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SubirAuto : Fragment() {

    companion object {
        fun newInstance() = SubirAuto()
    }

    private var dataBase = Firebase.firestore

    private lateinit var textModelo: EditText
    private lateinit var textMarca: EditText
    private lateinit var textFoto: EditText
    private lateinit var textDescripcion: EditText
    private lateinit var botonSubir: Button
    private lateinit var botonAtras: Button



    private lateinit var dataAuto: String



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_subir_auto, container, false)

        textModelo = view.findViewById(R.id.textModelo)
        textMarca = view.findViewById(R.id.textMarca)
        textFoto = view.findViewById(R.id.textFoto)
        textDescripcion = view.findViewById(R.id.textDescripcion)
        botonSubir = view.findViewById(R.id.botonSubir)
        botonAtras = view.findViewById(R.id.botonAtras)


        botonAtras.setOnClickListener {

            findNavController().navigate(R.id.action_subirAuto_to_pantallaInicio)

        }


        botonSubir.setOnClickListener {


            if (textModelo.text.isNotEmpty() && textMarca.text.isNotEmpty() && textFoto.text.isNotEmpty() && textDescripcion.text.isNotEmpty() ){

                val documentId: String = dataBase.collection("Autos").document().id

                val autoNuevo = hashMapOf(
                    "modelo" to textModelo.text.toString(),
                    "marca" to textMarca.text.toString(),
                    "foto" to textFoto.text.toString(),
                    "descripcion" to textDescripcion.text.toString(),
                    "idauto" to dataBase.collection("Autos").document().id,

                    )

                dataBase.collection("Autos").document(documentId).set(autoNuevo)

                    .addOnSuccessListener {
                        Toast.makeText(context, "Subido!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "No se logro subir :(", Toast.LENGTH_SHORT).show()

                    }

                findNavController().navigate(R.id.action_subirAuto_to_pantallaInicio)
            }



            else{

                Snackbar.make(it, "Complete todos los campos", Snackbar.LENGTH_SHORT).show()

            }

        }

        return view
    }

}