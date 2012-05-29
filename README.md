diablo3-ircbot
====

diablo3-ircbot は Diablo3 に関する情報を指定した IRC チャンネルに流す ircbot です。
具体的には Diablo3 公式サイトのフォーラム (http://us.battle.net/d3/en/forum/5394512/) からメンテナンス情報を取得して IRC チャンネルに投稿します。
このフォーラムに書かれずにメンテナンスが行われた場合は通知できません。

使い方
----
    cp config/config.scala.template config/config.scala
    vi config/config.scala
    sbt "run config/config.scala"

テストを動かすには
----
http://us.battle.net/d3/en/forum/5394512/ にアクセスして取得できる HTML のソースコードを以下のパスに置いてください

    src/test/resources/cc/clv/diablo3ircbot/service_status.html