package com.example.johninocente_comp304lab3_ex1.Model

data class Weather(val main: mainObj,
                   val sys: sysObj,
                   val weather: List<weatherObj>,
                   val wind: windObj,
                   val name: String) {
}

data class mainObj(var temp: Double,
                   var temp_min: Double,
                   var temp_max: Double,
                   var feels_like: Double ) {}

data class weatherObj(val id: Int,
                      val main: String,
                      val description: String,
                      val icon: String ){}

data class sysObj(val country: String,
                  val sunrise: Long,
                  val sunset: Long
) {}

data class windObj(val speed: Double){}