package cc.clv.diablo3ircbot

import cc.clv.diablo3ircbot.conf.IRCBotConfig
import org.jibble.pircbot._

class IRCBot extends PircBot {
    def init(config: IRCBotConfig) {
        setName(config.userName)
        setLogin(config.loginName)
        setEncoding(config.encoding)
        connect(config.serverHost, config.serverPort)
        for (channel <- config.joinChannels) joinChannel(channel)
    }
    
    override def onConnect {
        println("connected")
        sendNotice("#clvcctest", "aaaaa")
    }
}
    
