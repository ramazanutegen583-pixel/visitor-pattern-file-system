# Assignment 6 requirements

Topic: official **Option A File System Visitor**. Runtime: **Java 17**.

| Requirement | Implementation |
| --- | --- |
| Element interface with accept | FileSystemElement.accept(FileSystemVisitor) |
| At least two concrete elements | FileElement and DirectoryElement |
| Matching visit method from accept | visitFile(this) and visitDirectory(this) |
| Visitor interface | FileSystemVisitor has one visit method per element type |
| At least two concrete visitors | SizeCalculatorVisitor and SearchVisitor |
| New operation without editing elements | TextExportVisitor added in commit b4c8ff1 |
| Every visitor implements both methods | File and directory operations are explicit in each visitor |
| Object structure and traversal | Nested immutable directories; shared preorder FileSystemWalker |
| No type-checking chains | No instanceof, getClass or isAssignableFrom in application or test sources |
| Five justified Clean Code examples | Three from Chapter 12 and two from Chapter 13, each with an annotated excerpt |
| Chapter 12 | Runs all tests, no duplicate traversal, expressive names and client methods |
| Chapter 13 | Collection snapshots and independent task-local visitors |
| UML matches code | All 11 authored types, 8 fields, 48 methods and 6 explicit constructors |
| Required report sections | Introduction, UML, Clean Code, sample output, encapsulation conclusion and repository link |
| PDF format | reports/visitor-pattern-report.pdf |
| Sample output on the same structure | Size 13000 bytes; notes.txt and todo.txt found on the same root |
| Clear repository and README | model, operation, traversal, concurrent and client packages |
| Incremental Git history | Base operations, third visitor, documentation |
| Live demonstration | FileSystemDemo.main(), no arguments |

Upload the repository link and PDF to Moodle by the course deadline. The student performs the in-class defense.
