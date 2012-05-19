package cc.clv

import org.specs2.mutable._

import Diablo3IRCBot._

object Diablo3IRCBotSpec extends Specification {
    "main" should {
        "getMaintenanceInfoList" in {
            implicit var url = getClass.getResource("./service_status.html")
            val list = getMaintenanceInfoList
            
            list.size must_== 6
            list.head.id must_== "5149177831"
            list.head.title must_== "Scheduled Maintenance - 05/19/2012"
            list.head.url must_== "http://us.battle.net/d3/en/forum/topic/5149177831"
        }
    }
}
