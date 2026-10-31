package edu.aitu.visitor.model;

import edu.aitu.visitor.operation.FileSystemVisitor;
import java.util.Objects;

public final class FileElement implements FileSystemElement {
    private final String name;
    private final long sizeBytes;

    public FileElement(String name, long sizeBytes) {
        this.name = Objects.requireNonNull(name, "File name must not be null.");
        if (sizeBytes < 0) {
            throw new IllegalArgumentException("File size must not be negative.");
        }
        this.sizeBytes = sizeBytes;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visitFile(this);
    }
}
