package cc.clv.diablo3ircbot

import scala.io.Source
import scala.xml.XML
import java.net.URL

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
    
    implicit var url = getClass.getResource("./service_status.html")
    // implicit var url = new URL("http://us.battle.net/d3/en/forum/5394512/")
    getMaintenanceInfoList foreach println
}