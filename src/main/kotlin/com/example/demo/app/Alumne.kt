package com.example.demo.app

import javafx.beans.property.Property
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Commit
import tornadofx.ItemViewModel

data class Alumne(var id:Int,var nom:String, var cognoms:String, var edat:Int) {

    val idProperty = SimpleIntegerProperty(id)

    val nomProperty = SimpleStringProperty(nom)

    val cognomsProperty = SimpleStringProperty(cognoms)

    val edatProperty = SimpleIntegerProperty(edat)
}



class AlumneModel(alumne: Alumne?) : ItemViewModel<Alumne>(alumne){
    val id = bind(Alumne::idProperty)
    val nom = bind(Alumne::nomProperty)
    val cognoms = bind(Alumne::cognomsProperty)
    val edat = bind(Alumne::edatProperty)


    override fun onCommit() {
        super.onCommit()
        println("onCommit invoked")
    }

    override fun onCommit(commits: List<Commit>) {
        // The println will only be called if findChanged is not null
        commits.findChanged(nom)?.let { println("Last-Name changed from ${it.first} to ${it.second}")}
        commits.findChanged(cognoms)?.let { println("Last-Name changed from ${it.second} to ${it.first}")}
        commits.findChanged(edat)?.let { println("Last-Name changed from ${it.second} to ${it.first}")}
    }


    private fun <T> List<Commit>.findChanged(ref: Property<T>): Pair<T, T>? {
        val commit = find { it.property == ref && it.changed}
        return commit?.let { (it.newValue as T) to (it.oldValue as T) }
    }
}




