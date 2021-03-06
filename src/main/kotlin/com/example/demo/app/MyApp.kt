package com.example.demo.app

import com.example.demo.view.MainView
import tornadofx.App

class MyApp: App(AlumneEditor::class, Styles::class)

fun main(args: Array<String>) {
    tornadofx.launch<MyApp>(args)
}