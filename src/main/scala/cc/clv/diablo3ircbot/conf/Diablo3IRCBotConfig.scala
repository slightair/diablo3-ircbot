package cc.clv.diablo3ircbot.conf

trait Diablo3IRCBotConfig {
    val ircUserName: String
    val ircLoginName: String
    val ircEncoding: String
    val ircServerHost: String
    val ircServerPort: Int
    val ircJoinChannel: String
}