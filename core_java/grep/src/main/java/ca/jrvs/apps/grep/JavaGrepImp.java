package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JavaGrepImp implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length !=3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setOutFile(args[1]);
        javaGrepImp.setRootPath(args[2]);

        try{
            javaGrepImp.process();
        } catch (Exception ex){
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = null;
        for(File file: listFiles(getRootPath())){
            for(String line : readLines(file)){
                if(containsPattern(line)){
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File dir = new File(rootDir);
        return Arrays.asList(dir.listFiles());
    }

    @Override
    public List<String> readLines(File inputFile) {
        return Arrays.asList(inputFile.list());
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(getRegex());
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        FileWriter writer = new FileWriter(getOutFile());
        for(String line : lines){
            writer.write(line);
            writer.write(System.lineSeparator());
        }
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
