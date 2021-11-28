# inverted-index
Making an inverted index in Scala, modified (only barely) from http://cowa.github.io/2015/12/07/inverted-index-scala/.

The following tranformation:

```scala
lines.map(_.split(" "))
  .flatMap(x => x.drop(1).map(y => (y, x(0))))
  .groupBy(_._1)
  .map(p => (p._1, p._2.map(_._2).toVector))
```

Inverts this input:
```
document0.txt word0 word1 word5
document1.txt word0 word5 word7
```


Into this output (after some string formatting):
```
word5 document0.txt document1.txt
word7 document1.txt
word1 document0.txt
word0 document0.txt document1.txt
```

Pretty cool, Scala.
