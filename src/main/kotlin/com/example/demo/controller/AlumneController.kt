package com.example.demo.controller

import com.example.demo.app.Alumne
import com.example.demo.app.Connexio
import com.example.demo.app.AlumneEditor
import javafx.scene.control.Alert
import tornadofx.Controller
import tornadofx.alert
import tornadofx.observable
import java.sql.Connection


class AlumneController : Controller() {
    var c:Connection = Connexio().connexio()

    companion object {
       // var mPrincipal = MenuPrincipal()
    }

    //AQUEST METODE RETORNA TOTS ELS ALUMNES DE LA BASE DE DADES EN FORMA DE MUTABLELIST.
    fun carregaAlumnes() : MutableList<Alumne>{
        //var c: Connection = Connexio().connexio()
        var llistatAlumnes:MutableList<Alumne> = ArrayList()
        val ps = c.createStatement().executeQuery("SELECT * FROM Alumne")
        while (ps.next()) {
            val id = ps.getInt("id_alumne")
            val nom = ps.getString("Nom")
            val cognom = ps.getString("Cognoms")
            val edat = ps.getInt("edat")

            val al = Alumne(id, nom, cognom, edat)

            llistatAlumnes.add(al)
        }
        return llistatAlumnes
        //c.close()
    }

    fun esborraAlumne(id: Int?){
        //val l = MenuPrincipal().llistatAlumnes
        val ps = c.prepareStatement("DELETE FROM Alumne WHERE id_alumne = ?")

        if (id != null) {
            ps.setInt(1, id)
            ps.executeUpdate()
            alert(Alert.AlertType.CONFIRMATION, "", "L'alumne s'ha esborrat correctament!")
        } else {
            alert(Alert.AlertType.ERROR, "", "No has escollit ningun alumne!")
        }

        //c.close()
    }

    fun CrearNouAlumne(alumne:Alumne){
        println("Has entrat al metode CrearNouAlumne.")
        val ps = c.prepareStatement("INSERT INTO Alumne (Nom,Cognoms,Edat) VALUES (?,?,?)")
        ps.setString(1,alumne.nom)
        ps.setString(2,alumne.cognoms)
        ps.setInt(3,alumne.edat)
        ps.executeUpdate()
        //c.close()
        //mPrincipal.model.commit()
        //mPrincipal.llistatAlumnes.add(alumne)

    }

    fun actualitza(a:Alumne){
        //println("Has entrat al metode on actualitzem els camps al SQL.")

        val ps = c.prepareStatement("UPDATE Alumne SET Nom = ?, Cognoms = ?,  edat = ? WHERE id_alumne = ?")
        ps.setString(1, a.nom)
        ps.setString(2,a.cognoms)
        ps.setInt(3,a.edat)
        ps.setInt(4,a.id)
        val rs = ps.executeUpdate()
    }

    fun save(alumne: Alumne?) {
        println("Saving ${alumne?.nom} / ${alumne?.cognoms}/${alumne?.edat}")
    }
}