package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp extends JavaGrepImp {
  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    List<File> listFiles = listFiles(getRootPath());

    listFiles.stream()
        .map(this::readLines)
        .flatMap(Function.identity())
        .filter(this::containsPattern)
        .forEach(matchedLines::add);

    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<>();
    File dir = new File(rootDir);
    try {
      Files.walk(dir.toPath())
          .filter(Files::isRegularFile)
          .forEach(path -> files.add(path.toFile()));

    } catch (IOException e) {
      logger.error("Error: Unable to open file, IO issue related to the file", e);
    }
    return files;
  }

  @Override
  public Stream<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();


    try {
      return Files.lines(inputFile.toPath())
          .filter(Objects::nonNull)
          .map(line -> inputFile + ":" + line);

    } catch (IOException e) {
      this.logger.error("Error: Unable to read file, IO issue related to this file", e);
    }
    return null;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Incorrect number of arguments: Usage: [regex] [rootPath] [Outfile]");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepLambdaImp jGLI = new JavaGrepLambdaImp();
    jGLI.setRegex(args[0]);
    jGLI.setRootPath(args[1]);
    jGLI.setOutFile(args[2]);

    try {
      jGLI.process();
    } catch (Exception e) {
      jGLI.logger.error("Error: Unable to process", e);
    }
  }
}
