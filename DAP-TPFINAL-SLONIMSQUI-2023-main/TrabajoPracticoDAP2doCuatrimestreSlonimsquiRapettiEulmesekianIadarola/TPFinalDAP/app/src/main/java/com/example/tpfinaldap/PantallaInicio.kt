package com.example.tpfinaldap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PantallaInicio : Fragment() {

    private lateinit var idCompartido: sharedData
    private var dataBase = Firebase.firestore

    private lateinit var recyclerView: RecyclerView
    private lateinit var autoList: ArrayList<Auto>
    private lateinit var botonAdd: Button
    private lateinit var idAutoActual: String


    private lateinit var adapter : AutoAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pantalla_inicio, container, false)

        dataBase = FirebaseFirestore.getInstance()
        recyclerView = view.findViewById(R.id.recyclerAuto)
        recyclerView.layoutManager = LinearLayoutManager(context)
        autoList = arrayListOf()
        botonAdd = view.findViewById(R.id.botonAñadir)

        initRecyclerView()

        botonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaInicio_to_subirAuto)
        }
        return view
    }

    private fun initRecyclerView() {

        dataBase.collection("Autos").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val auto: Auto? = data.toObject<Auto>(Auto::class.java)
                    if (auto != null) {
                        auto.idAuto = data.id // Asignar el ID único de Firestore al objeto Auto
                        autoList.add(auto)
                        Log.d("Debug", "Auto ID added to list: ${auto.idAuto}")
                    }
                }



                adapter = AutoAdapter(autoList,
                    onDeleteClick = {position -> deleteAuto(position)
                },
                    onEditClick = {position -> editAuto(position)
                },
                    onItemClick = {position -> seeAutoData(position)})

                recyclerView.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun seeAutoData(position:Int) {

        idAutoActual = autoList[position].idAuto.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idAutoActual

        findNavController().navigate(R.id.action_pantallaInicio_to_dataAuto)
    }

    fun editAuto(position: Int) {

        idAutoActual = autoList[position].idAuto.toString()



        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idAutoActual

        findNavController().navigate(R.id.action_pantallaInicio_to_editarAuto)
    }

    fun deleteAuto(position: Int) {
        Log.d("Debug", "deleteAuto: position=$position, autoListSize=${autoList.size}")

        if (position >= 0 && position < autoList.size) {
            val autoId = autoList[position].idAuto.toString()
            dataBase.collection("Autos").document(autoId).delete()
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Auto eliminado", Toast.LENGTH_SHORT).show()
                    autoList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }

                .addOnFailureListener { Toast.makeText(requireContext(), "No se pudo eliminar el Auto", Toast.LENGTH_SHORT).show() }
        }
    }


}