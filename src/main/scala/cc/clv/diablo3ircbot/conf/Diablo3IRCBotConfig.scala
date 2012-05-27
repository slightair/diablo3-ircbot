package cc.clv.diablo3ircbot.conf

trait Diablo3IRCBotConfig {
    val ircBotConfig: IRCBotConfig
    val postedInfoDBFile: String
    val getMaintenanceInfoInterval: Int
}