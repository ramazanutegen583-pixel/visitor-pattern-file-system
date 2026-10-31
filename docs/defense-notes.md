# Visitor defense notes

## Main idea

Visitor adds operations to a stable object structure. The element classes store data; visitors implement operations across the element types.

## Participants

- FileSystemElement: common element interface.
- FileElement and DirectoryElement: concrete elements.
- FileSystemVisitor: common visitor interface.
- SizeCalculatorVisitor, SearchVisitor and TextExportVisitor: concrete operations.
- FileSystemWalker: reusable traversal.
- FileSystemDemo: client.
- ParallelAnalysis: independent task coordination.

## Double dispatch

The walker holds an element through FileSystemElement. Java first calls the actual element's accept method. FileElement routes to visitFile(this); DirectoryElement routes to visitDirectory(this). Java then calls that method on the actual visitor object.

Both the element type and visitor type determine the executed behavior. There is no runtime type-checking chain.

## Traversal

The walker visits the root before its children. FileSystemElement supplies an empty child list for a leaf. DirectoryElement returns its children. This keeps one recursive traversal for every visitor.

## Extension and trade-off

TextExportVisitor was added without editing elements or existing visitors. Adding another element type would require a new method in the visitor interface and an implementation in every visitor.

Visitors need public read access to element data. Private fields and immutable child lists protect modification, while the operations remain coupled to the exposed model.

## Clean Code

Chapter 12: pass all tests, remove duplicated traversal and use expressive names.

Chapter 13: copy collection data and keep workers independent. Two tasks share an immutable sample tree but each constructs its own mutable visitor. Results are retrieved through Future.get. The example makes no claim of a speed improvement.

## What to show

Run FileSystemDemo.main(). Explain the 13000-byte result, two .txt matches, text listing and independent parallel results. Then point to accept(visitor), visitFile(this), visitDirectory(this) and the third visitor's separate commit.
