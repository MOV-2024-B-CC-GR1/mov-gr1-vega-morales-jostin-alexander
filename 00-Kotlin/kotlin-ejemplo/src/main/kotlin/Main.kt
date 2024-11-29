package org.example

fun main( args : Array<String> ) {

    var mutable: String = "Jostin"

    val inmutable: String = "Vega"
    // inmutable = "Morales" // Error!

    val ejemploVariable = " Jostin Vega "
    val ejemploVariable1 = ejemploVariable.trim()

    // Imprimir la variable sin espacios
    println(ejemploVariable1)

    /*val esSoltero = (estadoCivilWhen == "S")

    when (estadoCivilWhen){
        ("C")->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }*/

    //val coqueteo = if (esSoltero) "Si" else "No"

    imprimirNombre("Jostin")

    calcularSueldo(10.00, bonoEspecial = 20.00)

    val sumaA = Suma(1,1)
    val sumaB = Suma(null,1)
    val sumaC = Suma(1,null)
    val sumaD = Suma(null, null)

    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // Arreglos
    // Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico.contentToString());

    //Dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)

    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH => Unit
    //ayuda a iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach{valorActual: Int -> // - > = ->
            println("Valor actual (valorActual) for Each: ${valorActual}");
        }
    //"it (en ingles "eso) significa el elemento iterado
    arregloDinamico.forEach{println("Valor actual (it) for each: ${it}")}

    //MAP -> MUTA(Modifica cambia) el arreglo
    //1) Enviamos el nuevo valor de la iteración
    //2) Nos devuelve un NUEVO ARREGLO con valores de las iteraciones

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00 //Suma 100 al valorActual
        }
    //Operador map devuelve un arreglo
    //Cuando se quiere devolver el valor del map devuelvo usando un @
    println(respuestaMap)


    //*****************************************************************


    //Filter -> Filtrar el arreglo
    // 1) Devolver una expresión (TRUE o FALSE)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinamico
        .filter{ valorActual:Int ->
            //Expresion o CONDICION
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter{ it <= 5}
    println("Respuesta Filter >5: $respuestaFilter")
    println("Respuesta Filter<=5: $respuestaFilterDos")

    //OR AND
    //OR -> ANY (Alguno Cumple?)
    //AND -> ALL (Todos Cumplen?)
    val respuestaAny: Boolean = arregloDinamico
        .any{ valorActual:Int ->
            return@any (valorActual > 5)
        }
    println("Respuesta ANY: " + respuestaAny) //TRUE

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println("Respuesta All: $respuestaAll") //FALSE



    //*************************************************************


    //REDUCE -> Valor acumulado
    //VAlor acumulado = 0 (Siempre empieza en Kotlin)
    //[1,2,3,4,5] -> Acumular "SUMAR" estos valores del arreglo
    // valorItereción1 = valorEmpieza + 1 = 1 -> Iteración 1
    // valorIteración2 = valorAcumuladoIteración1 + 2 (arreglo) = 1  + 2 =3  -> Iteración2
    // valorIteración3 = valorAcumuladoIteración2 + 3 (arreglo) = 3  + 3 =6  -> Iteración3
    // valorIteración4 = valorAcumuladoIteración3 + 4 (arreglo) = 6  + 4 =10 -> Iteración4
    // valorIteración5 = valorAcumuladoIteración4 + 5 (arreglo) = 10 + 5 =15 -> Iteración5

    val respuestaReduce: Int = arregloDinamico
        .reduce{ acumulado:Int, valorActual:Int ->
            return@reduce (acumulado + valorActual) // -> Cambiar o usar la lógica de negocio
        }
    println("Respuesta Reduce:  $respuestaReduce");
}

fun imprimirNombre(nombre:String): Unit{
    fun otraFuncionAdentro(){
        println("Otra función adentro")
    }
    println ("Nombre: ${nombre}") //Template Strings
}


fun calcularSueldo(
    sueldo:Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial:Double? = null //Opcional (nullable)
):Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial==null) {
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}
//abstract class Numeros1(protected val numeroUno: Double){}



abstract class Numeros1(
protected val numeroUno1: Int,
protected val numeroDos: Int,
parametroNoUsadoNoPropiedadDeLaClase : Int? = null
){
    init { //
        this.numeroUno1
        this.numeroDos
        println(" Inicializando")
    }
}


class Suma( // Constructor
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
):Numeros1( // Close papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
) {
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito: String = "Publico Implicito"

    init{
        //bloque constructor primario
        this.numeroUno1
        this.numeroDos
        numeroUno1 //this.optional [propiedades y métodos]
        numeroDos //this.optional [propiedades y métodos]
        this.soyPublicoImplicito
        soyPublicoExplicito
    }

    constructor(
        uno: Int?,
        dos: Int,
    ):this(
        if(uno == null) 0 else uno,
        dos
    ){
        //Bloque de código del constructr secundario
    }

    constructor(
        uno: Int,
        dos: Int?,
    ):this(
        uno,
        if(dos == null) 0 else dos
    )

    constructor(
        uno: Int?,
        dos: Int?,
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )

    fun sumar ():Int{
        val total =  numeroUno1 + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object { // Comparte entre todas las instancias, similar al STATIC
        // funciones, variables
        // NombreClse.metodo, NombreClase.funcion =>
        // Suma.pi
        val pi = 3.14
        //Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{ return num* num}
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorTotalSuma:Int){ //Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)
        }
    }

}



