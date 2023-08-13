# echo
Sample echo application

## Build
Java build for fat jar
```
gradle clean build --info

± ls -l build/libs
-rw-r--r--@ 1 root  staff      9599 Aug 12 18:53 echo-0.0.1-SNAPSHOT-plain.jar
-rw-r--r--@ 1 root  staff  32673169 Aug 12 18:53 echo-0.0.1-SNAPSHOT.jar
```
Generates the fat jar in build/libs
```
gradle nativeCompile --info
± ls -l build/native/nativeCompile
-rwxr-xr-x@ 1 root  staff  100430417 Aug 12 18:59 echo
```
Generates native executable in build/native/nativeCompile

## Run
Using java jvm:

java -jar build/libs/echo-0.0.1-SNAPSHOT.jar

Native execution:

build/native/nativeCompile/echo
