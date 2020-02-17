package ru.shikhovtsev.command;

public class CutCommand extends Command {

  public CutCommand(Editor editor) {
    super(editor);
  }

  @Override
  public boolean execute() {
    if (editor.textField.getSelectedText().isEmpty()) return false;

    backup();
    String source = editor.textField.getText();
    editor.clipboard = editor.textField.getSelectedText();
    editor.textField.setText(cutString(source));
    return true;
  }

  private String cutString(String soource) {
    String start = soource.substring(0, editor.textField.getSelectionStart());
    String end = soource.substring(editor.textField.getSelectionEnd());
    return start + end;
  }
}
