package edu.aitu.visitor.operation;

import edu.aitu.visitor.model.DirectoryElement;
import edu.aitu.visitor.model.FileElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Finds file names with a case-sensitive suffix, in traversal order. */
public final class SearchVisitor implements FileSystemVisitor {
    private final String suffix;
    private final List<String> matches = new ArrayList<>();

    public SearchVisitor(String suffix) {
        this.suffix = Objects.requireNonNull(suffix, "Search suffix must not be null.");
    }

    @Override
    public void visitFile(FileElement file) {
        if (file.getName().endsWith(suffix)) {
            matches.add(file.getName());
        }
    }

    @Override
    public void visitDirectory(DirectoryElement directory) {
        // This operation searches files; directory names are ignored.
    }

    public List<String> getMatches() {
        return List.copyOf(matches);
    }
}
