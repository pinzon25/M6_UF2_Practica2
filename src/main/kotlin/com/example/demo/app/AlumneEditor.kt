package com.example.demo.app

import com.example.demo.controller.AlumneController
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import tornadofx.*

class AlumneEditor: View() {
    val botoAfegir: Button by fxid("Bt_Afegir")
    val botoEsborrar:Button by fxid("Bt_Esborrar")
    val botoActualitzar:Button by fxid("Bt_Actualitzar")
    val botoReset:Button by fxid("Bt_Reset")
    val campNom: TextField by fxid("Tf_Nom")
    val campCognom:TextField by fxid("Tf_Cognom")
    val campEdat:TextField by fxid("Tf_Edat")

    override val root: AnchorPane by fxml()
    var llistatAlumnes: MutableList<Alumne> = ArrayList()
    var t: javafx.scene.control.TableView<Alumne>? = null
    val controller: AlumneController by inject()
    val model:AlumneModel = AlumneModel(null)
    var ind:Int?=null



    init {
        llistatAlumnes = controller.carregaAlumnes() //FUNCIONA BE.

        var a = FXCollections.observableArrayList(llistatAlumnes.observable())
        with(root) {
            t = tableview(a) {
                //column("Id", Alumne::idProperty)
                column("Nom", Alumne::nomProperty)
                column("Cognoms", Alumne::cognomsProperty)
                column("Edat", Alumne::edatProperty)
                isEditable = true
                prefHeight = 365.0
                prefWidth = 345.0
                layoutX = 370.0
                layoutY = 100.0

                model.rebindOnChange(this) { //personaSeleccionada ->
                    //item = personaSeleccionada ?: Person()
                    item = t?.selectedItem
                    ind= t?.selectionModel?.selectedIndex
                    campNom.text = item.nom
                    campCognom.text = item.cognoms
                    campEdat.text = item.edat.toString()
                    println("Item seleccionat: "+item)
                    print(""+ind)
                }

                bindSelected(model)
            }

            botoAfegir.setOnMouseClicked {
                try {
                    val alumne = Alumne(0, campNom.getText(), campCognom.getText(), campEdat.getText().toInt())
                    if(campNom.getText().equals("")||campCognom.getText().equals("")){
                        alert(Alert.AlertType.ERROR, "", "Hi ha camps buits!!!")
                    }else {
                        controller.CrearNouAlumne(alumne)
                        t!!.items.add(alumne)
                    }
                }catch(error:NullPointerException){
                    alert(Alert.AlertType.ERROR, "", "No has introduit les dades!!!")
                }catch(error:NumberFormatException){
                    alert(Alert.AlertType.ERROR, "", "El camp Edat no te el format correcte o esta buit!!!")
                }
                inicialitzacamps()
            }

            botoEsborrar.setOnMouseClicked {
                try {
                    model.item = t!!.selectedItem
                    controller.esborraAlumne(model.item.id)
                    t!!.items.removeAt(ind!!)
                } catch (error: NullPointerException) {
                    alert(Alert.AlertType.ERROR, "", "No has seleccionat cap alumne!!!")
                }
                inicialitzacamps()
            }

            botoActualitzar.setOnMouseClicked {
                println("Item inicial: " + model.item)
                try {
                    var al = Alumne(model.item.id, campNom.text, campCognom.text, campEdat.text.toInt())
                    model.item = al

                    //model.commit()
                    save()
                    println("Item un cop modificat: " + model.item)

                    t!!.items.add(ind!!, al)
                    t!!.items.removeAt(ind!! + 1)

                    controller.actualitza(model.item)
                }catch (error: NullPointerException) {
                    alert(Alert.AlertType.ERROR, "", "No has seleccionat cap alumne per actualitzar!!!")
                }
            }

            botoReset.setOnMouseClicked {
                try {
                    campNom.text = model.item.nom.toString()
                    campCognom.text = model.item.cognoms.toString()
                    campEdat.text = model.item.edat.toString()
                }catch(error:NullPointerException){
                    alert(Alert.AlertType.ERROR, "", "Per resetejar les dades has d'haver seleccionat un alumne!!!!")
                }

                model.rollback() }
        }




    }
    private fun save() {
        model.commit()
        val person = model.item
        controller.save(person)
    }

    fun inicialitzacamps(){
        campEdat.text = ""
        campNom.text=""
        campCognom.text=""
    }
}




