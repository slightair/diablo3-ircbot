package cc.clv.diablo3ircbot

import scala.io.Source
import scala.xml.XML
import java.net.URL
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
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
    
    def startIRCBot(config: Diablo3IRCBotConfig) = {
        // val bot = new IRCBot
        // bot.init(config.ircBotConfig)
        
        implicit var url = getClass.getResource("./service_status.html")
        // implicit var url = new URL("http://us.battle.net/d3/en/forum/5394512/")
        
        var postedInfoIdList = try {
            Source.fromFile(config.postedInfoDBFile).getLines.toList
        }
        catch {
            case _ => List[String]()
        }
        
        val out = new PrintWriter(new FileOutputStream(config.postedInfoDBFile, true))
        
        while (true) {
            for (info <- getMaintenanceInfoList) {
                postedInfoIdList.indexOf(info.id) match {
                    case -1 => {
                        postedInfoIdList = postedInfoIdList :+ info.id
                        println(info)
                        out.println(info.id)
                    }
                    case _ =>
                }
            }
            out.flush
            
            // Thread.sleep(config.getMaintenanceInfoInterval)
            Thread.sleep(500)
        }
    }
    
    parseOptions(args) match {
        case None => {
            printUsage
        }
        case Some(configFilePath) => {
            val configFileName = args(0)
            val configFile = new File(configFileName)
            val eval = new Eval()
            val config = eval[Diablo3IRCBotConfig](configFile)
            
            startIRCBot(config)
        }
    }
}