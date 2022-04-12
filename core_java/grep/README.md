# Introduction
This application is designed to search all files in a given directory, subdirectories included, and write all the lines that match the desired format to a file. It uses Lists, Streams, Lambdas, and regex to complete the task.

# Quick Start
This application is run from the terminal using the following line of code

`java -classpath target/classes ca.jrvs.apps.grep.JavaGrepImp Regex RootDirectory OutputFile`

where Regex is the pattern you wish to search for, the root directory is the highest level directory you wish to include in your search, and the output file is the file you wish to print he results to

#Implemenation
## Pseudocode
The following code is the pseudocode for the process method of this application, the method that calls all the other methods and holds teh main logic of the program.
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
Due to the size of the files there may be a memory error that occurs, this is due to the JVM not having enough space in the java heap to allocate objects
This issue can be solved by using streams which are able to process larger amounts of data while taking up less heap space.
# Test
Sample files were created and placed in testing folders. these files were searched for the regex using the command line. The program was tested by comparing the output files of the command line file to the application file.

# Deployment
How you dockerize your app for easier distribution?

# Improvement
List three things you can improve in this project.