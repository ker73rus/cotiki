package com.example.catfacts

import io.realm.RealmObject

open class Cat :RealmObject(){
    lateinit var text: String
    lateinit var fav: String
    //   val image: String
    }