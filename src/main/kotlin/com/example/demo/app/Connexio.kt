package com.example.demo.app

import java.sql.Connection
import java.sql.DriverManager

class Connexio{
   var db = DriverManager.getConnection("jdbc:mysql://localhost:3306/Practica2uf2m6?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","administrador1","admin1")

    fun connexio():Connection{

        return db
    }

}

