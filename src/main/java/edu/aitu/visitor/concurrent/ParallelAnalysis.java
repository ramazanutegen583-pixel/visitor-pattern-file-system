package edu.aitu.visitor.concurrent;

import edu.aitu.visitor.model.FileSystemElement;
import edu.aitu.visitor.operation.SearchVisitor;
import edu.aitu.visitor.operation.SizeCalculatorVisitor;
import edu.aitu.visitor.traversal.FileSystemWalker;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** Tasks share the immutable sample tree; each creates its own mutable visitor. */
public final class ParallelAnalysis {
    private ParallelAnalysis() { }

    public static List<String> analyze(FileSystemElement root, String suffix)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Future<Long> size = executor.submit(() -> {
                SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
                FileSystemWalker.walk(root, visitor);
                return visitor.getTotalBytes();
            });
            Future<List<String>> matches = executor.submit(() -> {
                SearchVisitor visitor = new SearchVisitor(suffix);
                FileSystemWalker.walk(root, visitor);
                return visitor.getMatches();
            });
            return List.of("Size: " + size.get() + " bytes",
                    "Search " + suffix + ": " + String.join(", ", matches.get()));
        } finally {
            executor.shutdown();
        }
    }
}
