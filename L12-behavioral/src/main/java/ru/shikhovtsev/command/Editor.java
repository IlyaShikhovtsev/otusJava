package ru.shikhovtsev.command;

import javax.swing.*;
import java.awt.*;

public class Editor {
  public JTextArea textField;
  public String clipboard;
  private CommandHistory history = new CommandHistory();

  public void init() {
    JFrame frame = new JFrame("Text editor");
    var content = new JPanel();
    frame.setContentPane(content);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    textField = new JTextArea();
    textField.setLineWrap(true);
    content.add(textField);
    var buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
    var ctrlC = new JButton("Ctrl + C");
    var ctrlX = new JButton("Ctrl + X");
    var ctrlV = new JButton("Ctrl + V");
    var ctrlZ = new JButton("Ctrl + Z");
    Editor editor = this;

    ctrlC.addActionListener(e -> executeCommand(new CopyCommand(editor)));
    ctrlX.addActionListener(e -> executeCommand(new CutCommand(editor)));
    ctrlV.addActionListener(e -> executeCommand(new PasteCommand(editor)));
    ctrlZ.addActionListener(e -> undo());
    buttons.add(ctrlC);
    buttons.add(ctrlX);
    buttons.add(ctrlV);
    buttons.add(ctrlZ);
    content.add(buttons);
    frame.setSize(450, 200);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private void executeCommand(Command command) {
    if (command.execute()) {
      history.push(command);
    }
  }

  private void undo() {
    if (history.isEmpty()) return;

    Command command = history.pop();
    if (command != null) {
      command.undo();
    }
  }
}
