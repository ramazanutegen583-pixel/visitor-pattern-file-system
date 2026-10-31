package edu.aitu.visitor.operation;

import edu.aitu.visitor.model.DirectoryElement;
import edu.aitu.visitor.model.FileElement;

/** Accumulates bytes during one traversal; each task owns its visitor. */
public final class SizeCalculatorVisitor implements FileSystemVisitor {
    private long totalBytes;

    @Override
    public void visitFile(FileElement file) {
        totalBytes = Math.addExact(totalBytes, file.getSizeBytes());
    }

    @Override
    public void visitDirectory(DirectoryElement directory) {
        // Directory metadata adds no bytes; the walker visits its children.
    }

    public long getTotalBytes() {
        return totalBytes;
    }
}
