package cc.clv.diablo3ircbot

import java.io.File
import com.twitter.util.Eval
import cc.clv.diablo3ircbot.conf.Diablo3IRCBotConfig

object Diablo3IRCBotMain extends App {
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
            val configFileName = args(0)
            val configFile = new File(configFileName)
            val eval = new Eval()
            var config = eval[Diablo3IRCBotConfig](configFile)
            val bot = new Diablo3IRCBot(config)
            bot.init
        }
    }
}