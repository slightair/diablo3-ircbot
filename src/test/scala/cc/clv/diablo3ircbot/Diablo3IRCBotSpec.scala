package cc.clv.diablo3ircbot

import org.specs2.mutable._
import cc.clv.diablo3ircbot.conf._

object Diablo3IRCBotSpec extends Specification {
    "main" should {
        val config = new Diablo3IRCBotConfig {
            val ircBotConfig = new IRCBotConfig {
                val userName = "dia3ircbot"
                val loginName = "dia3ircbot"
                val encoding = "UTF-8"
                val serverHost = "chat.freenode.net"
                val serverPort = 6667
                val joinChannels = List("#dia3ircbot")
            }
            val postedInfoDBFile = "postedInfo.db"
            val getMaintenanceInfoInterval = 1000 * 60 * 5
        }
        val bot = new Diablo3IRCBot(config)
        
        "getMaintenanceInfoList" in {
            implicit var url = getClass.getResource("./service_status.html")
            val list = bot.getMaintenanceInfoList
            
            list.size must_== 6
            list.head.id must_== "5149177831"
            list.head.title must_== "Scheduled Maintenance - 05/19/2012"
            list.head.url must_== "http://us.battle.net/d3/en/forum/topic/5149177831"
            list.head.description must_== "“We will be performing maintenance on Saturday, May 19th. Maintenance will begin at 5:00 AM PDT and conclude at approximately 6:00 AM PDT. During this time, servers and many web services will be unavailable. Thank you for your patience.”"
        }
    }
}
