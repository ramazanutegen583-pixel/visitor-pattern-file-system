package edu.aitu.visitor;

import edu.aitu.visitor.concurrent.ParallelAnalysis;
import edu.aitu.visitor.model.DirectoryElement;
import edu.aitu.visitor.model.FileElement;
import edu.aitu.visitor.model.FileSystemElement;
import edu.aitu.visitor.operation.SearchVisitor;
import edu.aitu.visitor.operation.SizeCalculatorVisitor;
import edu.aitu.visitor.traversal.FileSystemWalker;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VisitorTest {
    @Test
    void sizeOfNestedTree() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        FileSystemWalker.walk(sampleTree(), visitor);
        assertEquals(13000, visitor.getTotalBytes());
    }

    @Test
    void searchFindsTextFiles() {
        SearchVisitor visitor = new SearchVisitor(".txt");
        FileSystemWalker.walk(sampleTree(), visitor);
        assertEquals(List.of("notes.txt", "todo.txt"), visitor.getMatches());
    }

    @Test
    void emptyDirectoryHasZeroSize() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        FileSystemWalker.walk(new DirectoryElement("empty", List.of()), visitor);
        assertEquals(0, visitor.getTotalBytes());
    }

    @Test
    void zeroByteFileContributesZero() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        FileSystemWalker.walk(new FileElement("empty.txt", 0), visitor);
        assertEquals(0, visitor.getTotalBytes());
    }

    @Test
    void negativeSizeRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FileElement("bad.txt", -1));
    }

    @Test
    void missingNameRejected() {
        assertThrows(NullPointerException.class, () -> new FileElement(null, 1));
    }

    @Test
    void missingTreeRejected() {
        assertThrows(NullPointerException.class,
                () -> FileSystemWalker.walk(null, new SizeCalculatorVisitor()));
    }

    @Test
    void missingVisitorRejected() {
        assertThrows(NullPointerException.class,
                () -> FileSystemWalker.walk(sampleTree(), null));
    }

    @Test
    void childrenAreUnmodifiable() {
        DirectoryElement directory = sampleTree();
        assertThrows(UnsupportedOperationException.class,
                () -> directory.getChildren().add(new FileElement("extra.txt", 1)));
    }

    @Test
    void constructorCopiesChildren() {
        List<FileSystemElement> children = new ArrayList<>();
        children.add(new FileElement("first.txt", 1));
        DirectoryElement directory = new DirectoryElement("copy", children);
        children.add(new FileElement("second.txt", 2));
        assertEquals(1, directory.getChildren().size());
    }

    @Test
    void searchResultsAreSnapshots() {
        SearchVisitor visitor = new SearchVisitor(".txt");
        new FileElement("first.txt", 1).accept(visitor);
        List<String> snapshot = visitor.getMatches();
        new FileElement("second.txt", 2).accept(visitor);
        assertEquals(List.of("first.txt"), snapshot);
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add("other.txt"));
    }

    @Test
    void nullChildRejected() {
        List<FileSystemElement> children = new ArrayList<>();
        children.add(null);
        assertThrows(NullPointerException.class, () -> new DirectoryElement("bad", children));
    }

    @Test
    void visitorStatesAreIndependent() {
        DirectoryElement root = sampleTree();
        SizeCalculatorVisitor first = new SizeCalculatorVisitor();
        SizeCalculatorVisitor second = new SizeCalculatorVisitor();
        FileSystemWalker.walk(root, first);
        FileSystemWalker.walk(root, first);
        FileSystemWalker.walk(root, second);
        assertEquals(26000, first.getTotalBytes());
        assertEquals(13000, second.getTotalBytes());
    }

    @Test
    void overflowIsReported() {
        DirectoryElement root = new DirectoryElement("large", List.of(
                new FileElement("largest", Long.MAX_VALUE), new FileElement("one", 1)));
        assertThrows(ArithmeticException.class,
                () -> FileSystemWalker.walk(root, new SizeCalculatorVisitor()));
    }

    @Test
    void parallelMatchesSequential() throws Exception {
        assertEquals(List.of("Size: 13000 bytes", "Search .txt: notes.txt, todo.txt"),
                ParallelAnalysis.analyze(sampleTree(), ".txt"));
    }

    @Test
    void repeatedParallelRunsStayIndependent() throws Exception {
        DirectoryElement root = sampleTree();
        for (int run = 0; run < 5; run++) {
            assertEquals(List.of("Size: 13000 bytes", "Search .txt: notes.txt, todo.txt"),
                    ParallelAnalysis.analyze(root, ".txt"));
        }
    }

    @Test
    void directoryAcceptOnlyDispatches() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        sampleTree().accept(visitor);
        assertEquals(0, visitor.getTotalBytes());
    }

    private static DirectoryElement sampleTree() {
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
}
