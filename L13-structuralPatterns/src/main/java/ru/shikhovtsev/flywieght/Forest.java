package ru.shikhovtsev.flywieght;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Forest extends JFrame {
  private List<Tree> trees = new ArrayList<>();

  public void plantTree(int x, int y, String name, Color color, String otherTreeData) {
    var type = TreeFactory.getTreeType(name, color, otherTreeData);
    var tree = new Tree(x, y, type);
    trees.add(tree);
  }

  @Override
  public void paint(Graphics g) {
    trees.forEach(t -> t.draw(g));
  }
}
