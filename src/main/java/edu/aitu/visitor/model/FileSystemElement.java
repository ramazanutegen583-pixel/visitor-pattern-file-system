package edu.aitu.visitor.model;

import edu.aitu.visitor.operation.FileSystemVisitor;
import java.util.List;

/** Common element contract; leaves have no children. */
public interface FileSystemElement {
    String getName();

    default List<FileSystemElement> getChildren() {
        return List.of();
    }

    void accept(FileSystemVisitor visitor);
}
