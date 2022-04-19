package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepImp implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length !=3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setOutFile(args[2]);
        javaGrepImp.setRootPath(args[1]);

        try{
            javaGrepImp.process();
        } catch (Exception ex){
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        for(File file: listFiles(getRootPath()).collect(Collectors.toList())){
            for(String line : readLines(file).collect(Collectors.toList())){
                if(containsPattern(line)){
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines.stream());
    }

    @Override
    public Stream<File> listFiles(String rootDir) {
        File dir = new File(rootDir);
        List<File> listOfFiles = new ArrayList<>();
        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                listOfFiles = listFiles(file.getPath()).collect(Collectors.toList());
            }
            else{
                listOfFiles.add(file);
            }
        }
        return listOfFiles.stream();
    }

    @Override
    public Stream<String> readLines(File inputFile) {
        try {
            if(inputFile.isFile()) {
                return Files.readAllLines(inputFile.toPath()).stream();
            }
        } catch (Exception ex){
            JavaGrepImp javaGrepImp = new JavaGrepImp();
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
        return Stream.of("");
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(getRegex());
    }

    @Override
    public void writeToFile(Stream<String> lines) throws IOException {
        FileWriter writer = new FileWriter(getOutFile());
        lines.forEach((line) -> {
            try {
                writer.write(line);
                writer.write(System.lineSeparator());

            } catch (IOException e) {
                JavaGrepImp javaGrepImp = new JavaGrepImp();
                javaGrepImp.logger.error("Error: Unable to process", e);
            }
        });
        writer.close();
    }

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

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
