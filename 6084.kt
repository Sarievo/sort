import java.util.Comparator

// HORRIBLE, I can't find this lambda thing on the whole internet

fun largestWordCount(messages: Array<String>, senders: Array<String>): String {
    if (senders.size == 1) return senders[0];

    val fmap = HashMap<String, Int>()
    for (i in messages.indices) {
        val str = messages[i];
        val cnt = str.split(' ').size;
        fmap[senders[i]] = fmap.getOrDefault(senders[i], 0) + cnt;
    }

    val a = ArrayList<Pair<String, Int>>();
    for ((k, v) in fmap) {
        println("$k, $v")
        a.add(Pair(k, v));
    }
//    a.sortWith { p1, p2 ->
//        if (p1.second == p2.second)
//            p2.first.compareTo(p1.first)// sort by first desc if second ones are equal
//        else p2.second - p1.second// sort by second ones desc
//    }
    a.sortWith(Comparator<Pair<String, Int>>{a,b -> // older language, leetcode doesn't support new language features lol
        if (a.second == b.second) b.first.compareTo(a.first)
        else b.second-a.second
    })
    a.forEach { e-> println("${e.first}, ${e.second}") }
    return a[0].first
}

fun main() {
    var a = arrayOf("Hello userTwooo","Hi userThree","Wonderful day Alice","Nice day userThree")
    var b = arrayOf("Alice","userTwo","userThree","Alice")
    println(largestWordCount(a, b) == "Alice")

    a = arrayOf("How is leetcode for everyone","Leetcode is useful for practice")
    b = arrayOf("Bob","Charlie")
    println(largestWordCount(a, b) == "Charlie")
}
