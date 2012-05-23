package cc.clv.diablo3ircbot.conf

trait IRCBotConfig {
    val userName: String
    val loginName: String
    val encoding: String
    val serverHost: String
    val serverPort: Int
    val joinChannel: String
}