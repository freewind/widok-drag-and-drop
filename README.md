Widok drag & drop example
=========================

How to do the drag & drop with the amazing scalajs web framework [widok]()

Build the custom widok locally
-------------------------------

Since some required drag & drop events have not been implemented in widok yet, you have to
clone this repo: <http://github.com/freewind/widok> and publish it to local:

    sbt publishLocal

After official widok add these events, you don't need to do this anymore

Run
----

```
./sbt fastOptJS
open target/scala-2.11/classes/application.html
```

Then you can drag the image to different box.
