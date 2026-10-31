# File System Visitor

Assignment 6, official Option A. Java 17.

The client traverses one immutable in-memory tree using FileSystemWalker. File and directory accept methods dispatch to the matching visitor operation. SizeCalculatorVisitor sums file sizes; SearchVisitor finds names ending with a suffix. ParallelAnalysis uses separate visitors on a two-thread executor.

## Run in IntelliJ IDEA

1. Extract the ZIP completely.
2. Use **File > Open** and select the **pom.xml** beside this README. Open it as a project.
3. Let Maven import finish. Select **Microsoft JDK 17** if IntelliJ asks for an SDK.
4. Open **FileSystemDemo** in the client package and run its **main** method.

No program arguments or input files are required. The shared run configuration is **Visitor File System Demo**, using module **visitor-file-system**.

## Build

    mvn verify
    java -jar target/visitor-file-system-1.0.0.jar

The sample has 6 files in 3 directories. Its size is **13000 bytes** and the .txt search finds **notes.txt** and **todo.txt**.
