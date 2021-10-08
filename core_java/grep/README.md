# Introduction
The Java Grep app is an java implementation that mimics the features and functionalities of the native Linux grep command for string pattern searching via regular expressions. The app employs industry standard build environment using Maven to build and manage dependencies in IntelliJ, SLF4J for logging, and meticulously unit tested with JUnit 4. Functionally, this app takes advantage of many Java 8 features like Lambda and Stream API. For deployment the entire project is containerized via Docker and remotely pushed to Dockerhub. 

# Quick Start
### Maven Build
1. Compile and package the Java code using Maven (Maven can be downloaded here: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi))
```
mvn clean package
```
2. Run the Jar file. The program takes three command line arguments: 

```
java -jar target/grep-1.0-SNAPSHOT.jar {regex} {rootPath} {outFile}
```

- `regex`: string pattern
- `rootPath`: root directory path
- `outFile`: output path
### Docker Build
```
$ docker pull chopgye/grep

$ docker run --rm -v <directory> -v <out_file> chopgye/grep <regex/pattern> <directory> <out_file>
```

## Core Interfaces and Implementations 
- `JavaGrepp.java`; This is a public interface file with all the methods required to traverse a directory, read a file,
  check to see if a line contains the regex_pattern, and return it to another file.

- `JavaGrepImp.java`; This is the loop implementation of the Java Grep Application.

- `JavaGrepLambdaImp.java`; This is the Lambda/Steam implementation of the Java Grep Application.

# Implementation
## Pseudocode
1. Loop Implemenation
 ```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)

 ```
2. Lambda and Stream Implementation
 ```
 listFiles(getRootPath()).stream()
    .forEach(writeToFile(readLines(file).stream()
    .filter(containsPattern(line)).toList)
 ```

## Performance Issue
This project contains large data files to traverse through, for instance the `ShakeSpear.txt` is approximately 5 mbs. A common error is `java.lang.OutOfMemoryError` which indicates insufficient memory space in the heap. 

This issue can be resolved by manually setting initial, minimum heap and maximum heap size. [Oracle Documentation](https://docs.oracle.com/cd/E15523_01/web.1111/e13814/jvm_tuning.htm#PERFM161)

```
$ java -Xns5m -Xms30m -Xmx30m
```
- `Xns: Setting the nursery `
- `Xms: Setting the minimum`
- `Xmx: Setting the maximum`

# Test
This application has been tested manually with several sample files of different file sizes to compare results and efficiency.

The application also utilizes JUnit 4 to conduct unit test on individual methods. 

# Deployment
Maven `package` was used to create a Uber/Fat Jar file that contains all the dependencies for the project. The Jar file is containerized in docker using `docker build` and uploaded to DockerHub using `docker push`. 
# Improvement
1. Update the interface to return Streams instead of Lists for certain methods. This will optimize memory usage.
2. In output file, display line numbers and file names for better readibility and more useful information.
3. Allow a depth magnitude for searching down directories.
 