package edu.aitu.visitor.operation;

import edu.aitu.visitor.model.DirectoryElement;
import edu.aitu.visitor.model.FileElement;
import java.util.ArrayList;
import java.util.List;

/** Exports a preorder text listing without changing any element class. */
public final class TextExportVisitor implements FileSystemVisitor {
    private final List<String> lines = new ArrayList<>();

    @Override
    public void visitFile(FileElement file) {
        lines.add("FILE " + file.getName() + " (" + file.getSizeBytes() + " bytes)");
    }

    @Override
    public void visitDirectory(DirectoryElement directory) {
        lines.add("DIR " + directory.getName());
    }

    public List<String> getLines() {
        return List.copyOf(lines);
    }
}
