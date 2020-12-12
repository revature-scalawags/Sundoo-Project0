package submissions

object RequestsByHondaOnwers extends App{

    println("Date, Make, Model Select, Tint Removal Request, Comments")

    val file = io.Source.fromFile("ERS.csv")
    for(lines <- file.getLines()){
        val columns = lines.split(",")
        println(s"${columns(0)} | ${columns(1)} | ${columns(2)}")
    }
}