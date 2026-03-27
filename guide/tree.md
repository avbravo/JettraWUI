# Tree Component

The `Tree` component displays elements in a hierarchical tree structure with collapsible items.

## Usage

```java
Tree myTree = new Tree();

Tree.TreeItem root = new Tree.TreeItem("Projects");
Tree.TreeItem sub1 = new Tree.TreeItem("JettraWUI");
sub1.addItem(new Tree.TreeItem("src"));
sub1.addItem(new Tree.TreeItem("test"));

root.addItem(sub1);
root.addItem(new Tree.TreeItem("JettraWebExample"));

myTree.addItem(root);
```

## Structure

### Tree
- Root container for the tree.
- `addItem(TreeItem item)`: Adds a top-level item to the tree.

### TreeItem
- An item in the tree. Can contain sub-items.
- `addItem(TreeItem item)`: Adds a sub-item to this item.
- `add(UIComponent component)`: Adds a component (if it's a `TreeItem`, adds as a sub-item; otherwise adds to the item's content area).

## Events
Tree items respond to clicks by toggling their children. The toggle animation is powered by JavaScript and CSS.

## CSS Classes
- `.j-tree`: Main tree class.
- `.j-tree-item`: Individual item class.
- `.j-tree-header`: Item header class (label + toggle).
- `.j-tree-toggle`: Toggle icon class.
- `.j-tree-content`: Children container class.
