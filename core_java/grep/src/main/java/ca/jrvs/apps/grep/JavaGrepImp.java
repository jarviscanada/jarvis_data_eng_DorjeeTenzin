package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;
  private List<File> files;

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    List<File> listFiles = listFiles(this.rootPath);

    for (File file : listFiles) {
      List<String> readLines = readLines(file).collect(Collectors.toList());

      for (String line : readLines) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  private void listFilesRecursive(File dir) {
    if (!dir.exists()) {
      return;
    }
    if (dir.isDirectory()) {
      for (File file : Objects.requireNonNull(dir.listFiles())) {
        listFilesRecursive(file);
      }
    } else {
      this.files.add(dir);
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    this.files = new ArrayList<>();
    File dir = new File(rootDir);

    listFilesRecursive(dir);
    return this.files;
  }

  @Override
  public Stream<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();

    try {
      String line;
      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      while ((line = reader.readLine()) != null) {
        lines.add(inputFile + ":" + line);
      }
      reader.close();
    } catch (FileNotFoundException e) {
      this.logger.error("Error: File not found", e);
    } catch (Exception e) {
      this.logger.error("Some error! Try again ", e);
    }

    return lines.stream();
  }

  @Override
  public boolean containsPattern(String line) {
    return line.matches(this.regex);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(this.outFile)));

    for (String line : lines) {
      bufferedWriter.write(line);
      bufferedWriter.newLine();
    }
    bufferedWriter.close();
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Requires exactly 3 arguments");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (IOException e) {
      javaGrepImp.logger.error("Error: Unable to process", e);
    }
  }
}