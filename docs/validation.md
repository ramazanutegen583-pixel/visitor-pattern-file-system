# Validation

Checked on **31 October 2026** with **Microsoft JDK 17.0.20.1** on Windows.

## Build and tests

Command: mvn -B verify.

Result: **19 tests, 0 failures, 0 errors, 0 skipped; BUILD SUCCESS**.

Coverage includes nested traversal, both element types, size and search results, empty and zero-byte entries, invalid inputs, overflow, defensive copies, result snapshots, independent visitor state, repeated parallel analyses and all text-export entries.

## Demonstration

- Three JAR executions completed with exit code 0 and identical output.
- The output has 41 lines and ends with DEMONSTRATION COMPLETED SUCCESSFULLY.
- The tree contains 6 files and 3 directories.
- Total size: 13000 bytes.
- Search suffix .txt: notes.txt and todo.txt, two matches.
- Text export: nine entries, preserving preorder.
- Parallel size and search results match the sequential results.
- run-demo.cmd compiled and executed the same demonstration.
- An archived source copy extracted into a path containing spaces also ran successfully with automatic JDK discovery and no JAVA_HOME.

## Structure and report

- Source audit found no runtime-type checking chains.
- The extension commit leaves all existing elements, visitors, traversal and parallel analysis unchanged.
- The JDK compiler tree API extracted every authored declaration.
- The diagram includes all declared fields, methods and explicit constructors, including private helpers and test methods.
- The report uses vector UML, has two A4 pages, and was rendered for visual review.

See demo-output.txt, validation-results.json and code-inventory.json for the corresponding evidence.
