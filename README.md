# sort
Custom Lambda Sort<br>

####I must express how I hate this(6084)!! I thought I was wrong and it really is.
I am wrong at many parts of this easy question: I was using C++ to solve this problem, then something mysterious happens with runtime errors with virtually useless debug informations in my code where I don't even know which line the error is. Instead of painfully debugging, I just decided to transpile such code manually to Kotlin by converting keywords and switching the C++ lambda comparator with the Kotlin one. Then many things more mysterious happens, now I now which part in Kotlin I have done wrong (not the C++ parts yet, it still remains a mystery). But now let's see the code.<br>

My C++ wrong code with mysterious runtime errors
``` C++
class Solution {
public:
    string largestWordCount(vector<string>& messages, vector<string>& senders) {
//         w -> wcounts
//         map[si] += wi
//          -- - extract and sort
        map<string, int> fmap;
        for (int i = 0; (int)messages.size(); i++) {
            string str = messages[i];
            
            int cnt = str.size() ? 1 : 0;
            for (int i = 0; i < (int)str.size(); i++) {
                char ch = str[i];
                if (ch == ' ') cnt++;
            }
            
            if (fmap.find(senders[i])==fmap.end())
                fmap[senders[i]] = cnt;
            else
                fmap[senders[i]] += cnt;
        }
        
        vector<pair<string,int>> ext;
        for (auto it : fmap) {
            ext.emplace_back(make_pair(it.first,it.second));
        }
        
        sort(ext.begin(),ext.end(),
             [](pair<string,int> p1, pair<string,int> p2){
                return p1.second == p2.second ?
                    p1.first < p2.first : p1.second < p2.second;
        });
        
        return ext[ext.size()-1].first; 
    }
};
```
The correct Kotlin code.
``` Kotlin
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
```
I searched the whole internet and it painfully does not help at all and many answers arises from JVM world which is not idomatic Kotlin at all and cumbersome, I hate them.<br>

how to sort 2d arrays? 
how to sort pairs? 
sort them first by second element then by first element if the second element are equal? 
sort them first by first element then by second element if the first element are equal? 
sort 3d arrays? sort 9d arrays? sort consecutively in one expression? sort by lambda custom comparators in a anonyous function? 
painfully internet does not help, not at all on at least today for me


I don't want to write with old language either.. Maybe I'll hang out with C++ just on Leet.<br>
####Okay now the thing is: 
sortedBy is a out-place function, so it does not modify your array (my pitfall#1), then leet does not support newer language features that I attempt to use and then it's errors everywhere that I do not know to whiteboard-ly fix (my pitfall#2)<br>
Anyway, lets see how to actually sort 2d things.

``` Kotlin
a.sortWith(Comparator<Pair<String, Int>>{a,b -> //legacy
    if (a.second == b.second) b.first.compareTo(a.first)
    else b.second-a.second
})
```
this means sort by `first` if the `second`s are equal and sort by `second` if the `second`s are not equal. and by default the language sort the strings lexicographically, in this function it means, sort by 2nd desc then by 1st desc. if you want to sort other things just replace the `Pair<String, Int>` with virtually anything else then modify the lambda body to compare whatever things by whichever orders.
