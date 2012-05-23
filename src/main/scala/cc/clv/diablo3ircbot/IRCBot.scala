package cc.clv.diablo3ircbot

import cc.clv.diablo3ircbot.conf.IRCBotConfig
import org.jibble.pircbot._

class IRCBot extends PircBot {
    def init(config: IRCBotConfig) {
        println(config.userName)
        
        setName(config.userName)
        setLogin(config.loginName)
        setEncoding(config.encoding)
        connect(config.serverHost, config.serverPort)
        joinChannel(config.joinChannel)
    }
    
    override def onConnect {
        println("connected")
        sendNotice("#clvcctest", "aaaaa")
    }
}
    
