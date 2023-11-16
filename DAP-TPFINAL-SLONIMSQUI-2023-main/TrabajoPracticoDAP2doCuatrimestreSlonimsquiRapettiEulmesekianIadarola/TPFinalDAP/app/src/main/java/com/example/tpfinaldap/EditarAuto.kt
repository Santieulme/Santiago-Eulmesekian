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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarAuto : Fragment() {

    private lateinit var idCompartido: sharedData
    private var dataBase = Firebase.firestore

    private lateinit var modeloNuevo: EditText
    private lateinit var marcaNuevo: EditText
    private lateinit var fotoNuevo: EditText
    private lateinit var descripcionNuevo: EditText

    private lateinit var autoID: String

    private lateinit var botonSubirDatos: Button
    private lateinit var botonAtras: Button

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_auto, container, false)

        modeloNuevo = view.findViewById(R.id.modeloNuevo)
        marcaNuevo = view.findViewById(R.id.marcaNuevo)
        fotoNuevo = view.findViewById(R.id.fotoNuevo)
        descripcionNuevo = view.findViewById(R.id.descripcionNuevo)

        botonSubirDatos = view.findViewById(R.id.botonSubirDatos)
        botonAtras = view.findViewById(R.id.botonAtras)
        dataBase = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            dataBase.collection("Autos").document(data1).get().addOnSuccessListener {

            modeloNuevo.setText(it.data?.get("modelo").toString())
            marcaNuevo.setText(it.data?.get("marca").toString())
            fotoNuevo.setText(it.data?.get("foto").toString())
            descripcionNuevo.setText(it.data?.get("descripcion").toString())
            autoID = it.data?.get("idAuto").toString()

        }

        .addOnFailureListener {

            Toast.makeText(context, "No se encontraron los datos requeridos", Toast.LENGTH_SHORT).show()

        }

            botonAtras.setOnClickListener {

                findNavController().navigate(R.id.action_editarAuto_to_pantallaInicio)

            }

        botonSubirDatos.setOnClickListener {

            if (modeloNuevo.text.isNotEmpty() && marcaNuevo.text.isNotEmpty() && fotoNuevo.text.isNotEmpty() && descripcionNuevo.text.isNotEmpty() ){

            val autoNuevo = hashMapOf(

                "modelo" to modeloNuevo.text.toString(),
                "marca" to marcaNuevo.text.toString(),
                "foto" to fotoNuevo.text.toString(),
                "descripcion" to descripcionNuevo.text.toString(),
                "idauto" to autoID

            )

                dataBase.collection("Autos").document(data1).set(autoNuevo).addOnSuccessListener {

                    Toast.makeText(context, "Subido!", Toast.LENGTH_SHORT).show()

                }

                .addOnFailureListener { e ->

                    Toast.makeText(context, "No se logro subir :(", Toast.LENGTH_SHORT).show()

                }

            findNavController().navigate(R.id.action_editarAuto_to_pantallaInicio)

        }

            else{

            Snackbar.make(it, "Complete todos los campos", Snackbar.LENGTH_SHORT).show()

        }

        }

        }

        return view

    }

}