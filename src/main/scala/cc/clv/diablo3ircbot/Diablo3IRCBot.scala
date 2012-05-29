package cc.clv.diablo3ircbot

import scala.io.Source
import scala.xml.XML
import java.net.URL
import java.io.FileOutputStream
import java.io.PrintWriter
import cc.clv.diablo3ircbot.conf._
import org.jibble.pircbot._

class Diablo3IRCBot(val config:Diablo3IRCBotConfig) extends PircBot {
    case class MaintenanceInfo(id: String, title: String, url: String)
    
    var joinedChannels: Int = 0
    
    setName(config.ircBotConfig.userName)
    setLogin(config.ircBotConfig.loginName)
    setEncoding(config.ircBotConfig.encoding)
    
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
    
    def startWatcher = {
        implicit var url = new URL("http://us.battle.net/d3/en/forum/5394512/")
        
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
                        val notice = info.title + " -> " + info.url
                        for (channel <- config.ircBotConfig.joinChannels) sendNotice(channel, notice)
                        out.println(info.id)
                    }
                    case _ =>
                }
            }
            out.flush
            
            Thread.sleep(config.getMaintenanceInfoInterval)
        }
    }
    
    def init = {
        connect(config.ircBotConfig.serverHost, config.ircBotConfig.serverPort)
        for (channel <- config.ircBotConfig.joinChannels) joinChannel(channel)
    }
    
    override def onJoin(channel:String, sender:String, login:String, hostname:String) = {
        joinedChannels += 1
        if (joinedChannels == config.ircBotConfig.joinChannels.size) startWatcher
    }
}