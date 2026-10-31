package edu.aitu.visitor.traversal;

import edu.aitu.visitor.model.FileSystemElement;
import edu.aitu.visitor.operation.FileSystemVisitor;
import java.util.Objects;

/** One preorder traversal shared by every operation, with no element-type checks. */
public final class FileSystemWalker {
    private FileSystemWalker() { }

    public static void walk(FileSystemElement element, FileSystemVisitor visitor) {
        Objects.requireNonNull(element, "Element must not be null.");
        Objects.requireNonNull(visitor, "Visitor must not be null.");
        element.accept(visitor);
        for (FileSystemElement child : element.getChildren()) {
            walk(child, visitor);
        }
    }
}
