import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun readLineSplit() = readLine()!!.split(" ").map { it.toString() }
fun readLineTrim() = readLine()!!.trim()
var articlesLastId = 0
val articles = mutableListOf<Article>()

fun addArticles(title: String, body: String): Int {
    val id = articlesLastId + 1

    val reg = LocalDateTime.now()
    val up = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val forReg = reg.format((formatter))
    val forUp = up.format((formatter))
    val article = Article(id, title, body, forReg, forUp)
    articles.add(article)
    articlesLastId = id
    return id
}

fun makeTestArticles() {
    for (id in 1..1000) {
        val title = "제목$id"
        val body = "내용$id"

        addArticles(title, body)
    }


}

fun main() {
    println("== 게시판 관리 프로그램 시작 ==")

    // 가장 마지막에 입력된 게시물 번호
    makeTestArticles()


    loop@ while (true) {

        print("명령어) ")
        val command = readLineSplit()

        when (command[0] + command[1]) {
            "systemexit" -> {
                println("프로그램을 종료합니다.")
                break@loop
            }
            "articlewrite" -> {

                print("제목 : ")
                val title = readLineTrim()
                print("내용 : ")
                val body = readLineTrim()
                val id = addArticles(title, body)

                println("${id}번 게시물이 작성되었습니다.")



                articlesLastId = id
            }
            "articlelist" -> {
                val temp = articles.reversed()
                if(command.size==3) {

                    for (i in ((command[2].toInt() - 1) * 10) until (command[2].toInt() * 10)) {
                        println("${temp[i].id} / ${temp[i].title} / ${temp[i].body} / ${temp[i].regDate} / ${temp[i].updateDate}")
                    }
                }else if(command.size==4){

                    var arr2 = mutableListOf<Article>()
                    for (i in 0 until 1000){
                        if(temp[i].title.contains(command[2]))
                            arr2.add(temp[i])
                    }
                    for (i in ((command[3].toInt() - 1) * 5) until (command[3].toInt() * 5)) {
                        println("${arr2[i].id} / ${arr2[i].title} / ${arr2[i].body} / ${arr2[i].regDate} / ${arr2[i].updateDate}")
                    }

                }

            }

            "articledelete" -> {

                if (articles.any { it.id == command[2].toInt() }) {
                    println("${command[2]} was deleted")
                    articles.removeAt(command[2].toInt() - 1)
                } else {
                    println("${command[2]} is not exist")
                }
            }
            "articlemodify" -> {
                println("${command[2]} is modify")

                if (articles.any { it.id == command[2].toInt() }) {
                    print("새제목 : ")
                    val title = readLineTrim()
                    print("새내용 : ")
                    val body = readLineTrim()
                    val num = articles[command[2].toInt() - 1].id
                    val currentDateTime = Calendar.getInstance().time
                    val update = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDateTime)
                    val reg = articles[command[2].toInt() - 1].regDate
                    val temp = Article(num, title, body, reg, update)
                    articles.removeAt(command[2].toInt() - 1)
                    articles.add(command[2].toInt() - 1, temp)

                } else {
                    println("${command[2]} is not exist")
                }
            }
            "articledetail" -> {

                if (articles.any { it.id == command[2].toInt() }) {
                    println("번호 : ${articles[command[2].toInt() - 1].id}")
                    println("작성 날짜 : ${articles[command[2].toInt() - 1].regDate}")
                    println("갱신 날짜 : ${articles[command[2].toInt() - 1].updateDate}")
                    println("제목 : ${articles[command[2].toInt() - 1].title}")
                    println("내용 : ${articles[command[2].toInt() - 1].body}")

                } else {
                    println("${command[2]} is not exist")
                }
            }
            else -> {
                println("`$command` 은(는) 존재하지 않는 명령어 입니다.")
            }

        }
    }

    println("== 게시판 관리 프로그램 끝 ==")
}



data class Article(
    val id: Int,
    val title: String,
    val body: String,
    val regDate: String,
    val updateDate: String
)