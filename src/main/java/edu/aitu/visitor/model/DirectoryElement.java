package edu.aitu.visitor.model;

import edu.aitu.visitor.operation.FileSystemVisitor;
import java.util.List;
import java.util.Objects;

public final class DirectoryElement implements FileSystemElement {
    private final String name;
    private final List<FileSystemElement> children;

    public DirectoryElement(String name, List<FileSystemElement> children) {
        this.name = Objects.requireNonNull(name, "Directory name must not be null.");
        this.children = List.copyOf(children);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<FileSystemElement> getChildren() {
        return children;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visitDirectory(this);
    }
}
