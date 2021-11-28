import scala.io.Source
import java.io._

object InvertedIndex {
  // From http://cowa.github.io/2015/12/07/inverted-index-scala/
  // Added some steps to understand it better!
  // And output the file.
  /**
   * Build an inverted index from a file with this format:
   *   doc0 w0 w1 ...
   *   doc1 w2 w4 ...
   * Each elements of a line is separated by whitespaces
   *
   * @param path Path to the file containing data to invert
   *
   * @return An inverted index
   *   w0 -> doc0 doc2 ...
   *   w1 -> doc0 doc5 ...
   */
  def apply(path: String): Map[String, Vector[String]] = { 
    val file = Source.fromFile(path)
    val lines = file.getLines().toList
    file.close()

    println(lines)
    // List(document0.txt word0 word1 word5, document1.txt word0 word5 word7)
    
    println(lines.map(_.split(" ")).map(_.toList.toString))
    // List(List(document0.txt, word0, word1, word5), List(document1.txt, word0, word5, word7))

    println(lines.map(_.split(" "))
      .flatMap(x => x.drop(1).map(y => (y, x(0)))))
    // List((word0,document0.txt), (word1,document0.txt), (word5,document0.txt), (word0,document1.txt), (word5,document1.txt), (word7,document1.txt))
    
    println(lines.map(_.split(" "))
      .flatMap(x => x.drop(1).map(y => (y, x(0))))
      .groupBy(_._1))
    // HashMap(word5 -> List((word5,document0.txt), (word5,document1.txt)), word7 -> List((word7,document1.txt)), word1 -> List((word1,document0.txt)), word0 -> List((word0,document0.txt), (word0,document1.txt)))

    println(lines.map(_.split(" "))
      .flatMap(x => x.drop(1).map(y => (y, x(0))))
      .groupBy(_._1)
      .map(p => (p._1, p._2.map(_._2).toVector)))
    // HashMap(word5 -> Vector(document0.txt, document1.txt), word7 -> Vector(document1.txt), word1 -> Vector(document0.txt), word0 -> Vector(document0.txt, document1.txt))
    
    lines.map(_.split(" "))
      .flatMap(x => x.drop(1).map(y => (y, x(0))))
      .groupBy(_._1)
      .map(p => (p._1, p._2.map(_._2).toVector))
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val iiMap = InvertedIndex("input.txt")
    val file = new File("output.txt")
    val bw = new BufferedWriter(new FileWriter(file))
    // for ((k, v) <- iiMap) {
    //   bw.write(k.concat(" ").concat(v.mkString(" ")).concat("\n"))
    // }
    iiMap.foreach{case (k,v) => bw.write(k.concat(" ").concat(v.mkString(" ")).concat("\n"))}
    bw.close()
  }
}
