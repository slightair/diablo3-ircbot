package cc.clv.diablo3ircbot

import scala.xml.XML
import java.net.URL
import com.twitter.util.Eval
import cc.clv.diablo3ircbot.conf.Diablo3IRCBotConfig

object Diablo3IRCBot extends App {
    case class MaintenanceInfo(id: String, title: String, url: String)
    
    def getMaintenanceInfoList()(implicit url:URL) = {
        val xml = XML.load(url)
        val table = (xml \\ "table" filter(table => (table \ "@id").text == "posts"))(0)
        val posts = table \\ "tr" \\ "td" filter(td => (td \ "@class").text == "post-title") map(td => td \\ "a")
        
        posts.map(post => {
            val id = (post \ "@href").text.drop("../topic/".size)
            val title = post.text.trim
            val url = "http://us.battle.net/d3/en/forum/topic/" + id
            
            new MaintenanceInfo(id, title, url)
        })
    }
    
    def parseOptions(args: Array[String]) = args.size match {
        case 1 => Option(args.last)
        case _ => None
    }
    
    def printUsage = println("usage: diablo3ircbot config_file")
    
    parseOptions(args) match {
        case None => {
            printUsage
        }
        case Some(configFilePath) => {
            implicit var url = getClass.getResource("./service_status.html")
            // implicit var url = new URL("http://us.battle.net/d3/en/forum/5394512/")
            getMaintenanceInfoList foreach println
            
            val configFileName = args(0)
            val configFile = new java.io.File(configFileName)
            val eval = new Eval()
            val config = eval[Diablo3IRCBotConfig](configFile)
            
            println(config.ircServerHost)
        }
    }
}