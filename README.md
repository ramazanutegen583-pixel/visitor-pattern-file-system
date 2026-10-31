# File System Visitor

Assignment 6, official Option A. Java 17.

The client traverses one immutable in-memory tree using FileSystemWalker. Element accept methods dispatch to the matching visitor operation. SizeCalculatorVisitor totals file sizes; SearchVisitor finds names ending with a suffix; TextExportVisitor produces a text listing. ParallelAnalysis uses separate visitors on a two-thread executor.

## Run in IntelliJ IDEA

1. Extract the ZIP completely.
2. Use **File > Open** and select the **pom.xml** beside this README. Open it as a project.
3. Let Maven import finish. Select **Microsoft JDK 17** if IntelliJ asks for an SDK.
4. Open **FileSystemDemo** in the client package and run its **main** method.

No program arguments or input files are required. The shared run configuration is **Visitor File System Demo**, using module **visitor-file-system**.

If you opened an outer ZIP folder, select the inner **pom.xml** as a Maven project. The project folder must directly contain pom.xml, src and run-demo.cmd. If prompted, set **Project SDK = ms-17** and **Language level = SDK default**.

## Run on Windows

Double-click **run-demo.cmd**. It locates an installed JDK, compiles the application and leaves the console open. It also works without Maven. For automated execution, use **run-demo.cmd --no-pause**.

## Build

    mvn verify
    java -jar target/visitor-file-system-1.0.0.jar

The sample has 6 files in 3 directories. Its size is **13000 bytes** and the .txt search finds **notes.txt** and **todo.txt**.

The demonstration prints **41 lines**: size, search results, all nine elements, independent parallel analyses and the double-dispatch steps. Search is case sensitive and returns file names in preorder. Visitor state belongs to one traversal; create a fresh visitor for each new analysis.

## Deliverables

- [Concise PDF report](reports/visitor-pattern-report.pdf) and [text source](reports/visitor-pattern-report.md).
- [Complete UML](docs/class-diagram.png), [SVG](docs/class-diagram.svg) and [editable PlantUML](docs/class-diagram.puml).
- [Actual console output](docs/demo-output.txt).
- [Requirements checklist](docs/requirements-checklist.md), [validation](docs/validation.md) and [defense notes](docs/defense-notes.md).

The UML includes every authored class and interface, including the test class, with all declared fields, methods and explicit constructors. The [source inventory](docs/code-inventory.json) records signatures, modifiers, local declarations and source hashes.

## Design

The two element types dispatch through FileSystemVisitor using visitFile(this) and visitDirectory(this). The walker calls accept on every element without checking runtime types. Each visitor owns one operation.

Fields are private. FileElement and DirectoryElement are immutable, the directory copies its child list, and visitors return read-only result snapshots. Each parallel task creates its own visitor; mutable visitor state is never shared between tasks.

Clean Code examples apply Chapters **12 Emergence** and **13 Concurrency**.

## Incremental history

The first commit implements two visitors and isolated parallel analysis. The next adds TextExportVisitor without changing the elements, visitor interface, walker or existing visitors. The final documentation commit includes the verified PDF and UML.

Repository: https://github.com/ramazanutegen583-pixel/visitor-pattern-file-system
