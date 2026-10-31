package edu.aitu.visitor.client;

import edu.aitu.visitor.concurrent.ParallelAnalysis;
import edu.aitu.visitor.model.DirectoryElement;
import edu.aitu.visitor.model.FileElement;
import edu.aitu.visitor.model.FileSystemElement;
import edu.aitu.visitor.operation.SearchVisitor;
import edu.aitu.visitor.operation.SizeCalculatorVisitor;
import edu.aitu.visitor.operation.TextExportVisitor;
import edu.aitu.visitor.traversal.FileSystemWalker;
import java.util.List;
import java.util.concurrent.ExecutionException;

/** Builds one sample tree and applies independent operations without arguments. */
public final class FileSystemDemo {
    private FileSystemDemo() { }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("============================================================");
        System.out.println("FILE SYSTEM - VISITOR PATTERN DEMONSTRATION");
        System.out.println("Sample: coursework, with 6 files and 3 directories.");
        System.out.println("The sample is an in-memory model with fixed file sizes.");
        System.out.println("============================================================");
        DirectoryElement root = createSampleTree();
        showSize(root);
        showSearch(root, ".txt");
        showTextExport(root);
        showParallelAnalysis(root);
        System.out.println("\nDOUBLE DISPATCH");
        System.out.println("1. walk() calls the actual element's accept(visitor).");
        System.out.println("2. accept() calls visitFile(this) or visitDirectory(this).");
        System.out.println("3. The actual visitor supplies the operation implementation.");
        System.out.println("\nAll operations used the same tree; the elements stayed unchanged.");
        System.out.println("DEMONSTRATION COMPLETED SUCCESSFULLY");
    }

    private static DirectoryElement createSampleTree() {
        return new DirectoryElement("coursework", List.of(
                new FileElement("README.md", 800),
                new DirectoryElement("documents", List.of(
                        new FileElement("notes.txt", 1200),
                        new FileElement("report.pdf", 4800),
                        new FileElement("todo.txt", 600))),
                new DirectoryElement("images", List.of(
                        new FileElement("diagram.png", 2400))),
                new FileElement("archive.zip", 3200)));
    }

    private static void showSize(FileSystemElement root) {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        FileSystemWalker.walk(root, visitor);
        System.out.println("\n1. SIZE CALCULATOR VISITOR");
        System.out.println("Operation: sum the byte sizes of every file.");
        System.out.println("Root: " + root.getName());
        System.out.println("Total size: " + visitor.getTotalBytes() + " bytes");
    }

    private static void showSearch(FileSystemElement root, String suffix) {
        SearchVisitor visitor = new SearchVisitor(suffix);
        FileSystemWalker.walk(root, visitor);
        System.out.println("\n2. SEARCH VISITOR");
        System.out.println("Operation: find file names ending with " + suffix);
        for (String name : visitor.getMatches()) {
            System.out.println("Match: " + name);
        }
        System.out.println("Matches found: " + visitor.getMatches().size());
    }

    private static void showParallelAnalysis(FileSystemElement root)
            throws InterruptedException, ExecutionException {
        System.out.println("\n4. INDEPENDENT PARALLEL VISITORS");
        for (String result : ParallelAnalysis.analyze(root, ".txt")) {
            System.out.println(result);
        }
        System.out.println("Two tasks, separate visitors, one immutable sample tree.");
    }

    private static void showTextExport(FileSystemElement root) {
        TextExportVisitor visitor = new TextExportVisitor();
        FileSystemWalker.walk(root, visitor);
        System.out.println("\n3. NEW OPERATION: TEXT EXPORT VISITOR");
        System.out.println("This visitor was added without changing FileElement or DirectoryElement.");
        for (String line : visitor.getLines()) {
            System.out.println(line);
        }
    }
}
