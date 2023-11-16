package com.example.tpfinaldap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataAuto : Fragment() {

    private lateinit var idCompartido: sharedData
    private var dataBase = Firebase.firestore

    private lateinit var modeloData: TextView
    private lateinit var marcaData: TextView
    private lateinit var botonAtras: Button
    private lateinit var fotoData: ImageView
    private lateinit var descripcionData: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_data_auto, container, false)

        modeloData = view.findViewById(R.id.marcaData)
        marcaData = view.findViewById(R.id.modeloData)
        fotoData = view.findViewById(R.id.fotoData)
        descripcionData = view.findViewById(R.id.descripcionData)
        botonAtras = view.findViewById(R.id.botonAtras)

        dataBase = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)

        botonAtras.setOnClickListener {

            findNavController().navigate(R.id.action_dataAuto_to_pantallaInicio)

        }

        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            dataBase.collection("Autos").document(data1).get().addOnSuccessListener { //Se bajan de Firestore los datos del item clickeado y se representan los datos en los text view del archivo xml

                modeloData.text = (it.data?.get("modelo").toString())
                marcaData.text = (it.data?.get("marca").toString())
                descripcionData.text = (it.data?.get("descripcion").toString())
                Glide.with(fotoData.context).load(it.data?.get("foto").toString()).into(fotoData)

            }.addOnFailureListener {

                Toast.makeText(context, "No se encontraron los datos requeridos", Toast.LENGTH_SHORT).show()

            }

        }

        return view

    }

}