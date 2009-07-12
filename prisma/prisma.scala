import java.io.File
import scala.util.parsing.json.JSON.parseFull

//object Prisma {
    def mkPath(p : Seq[String]): String = p mkString File.separator
    def file(segments: String*): File = {
        val dir = new File(mkPath(segments.elements.toList.init))
            dir.mkdirs
        new File(dir,segments.last)
    }
    val DEFAULT = mkPath(List(System.getProperty("user.home"), ".prisma"))

            def follow(x: String) = {
            if (file(DEFAULT, x) createNewFile)
                println("following "+x)
            else
                println("already following "+ x)
        }

        def show (x: String)= println("Data is "+ search(x))

        def list() = println("Following\n"+ formatListing)

        def read(key:String):String = io.Source.fromURL("http://search.twitter.com/search.json?q="+key) mkString

        def search(key: String): String = getTexts(parseFull(read(key))) mkString "\n"

        def getTexts( p :Option[Any]): Seq[String] =  {
            val results = p match {
                case None => return Nil
                case Some( x :Map[String,Any]) => x 
            }
            results("results") match {
                case r: List[List[Tuple2[String,String]]] => r map (_(0)._2) 
                case _ => Nil
            }
        }

        def formatListing() = interests mkString ", "  

        def interests() = file(DEFAULT).listFiles map(_.getName)

            def usage() = { 
            println("usage:\n [follow <word>|show <word>|list]")
            exit(1)
        }

        def main(args: Array[String]) {
            args match {
                case Array("follow", word) => follow(word)
                case Array("show", word)   => show(word)
                case Array("list")         => list()
                case _ => usage()
            }
        }
        //}


        main(args)

        // vim: set ts=2 sw=2 et:
