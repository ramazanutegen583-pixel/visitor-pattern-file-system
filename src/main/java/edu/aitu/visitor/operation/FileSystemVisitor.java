package edu.aitu.visitor.operation;

import edu.aitu.visitor.model.DirectoryElement;
import edu.aitu.visitor.model.FileElement;

public interface FileSystemVisitor {
    void visitFile(FileElement file);
    void visitDirectory(DirectoryElement directory);
}
