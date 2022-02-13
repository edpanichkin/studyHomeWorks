import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm {
  private JPanel mainPanel;
  private JTextField surnameTextField;
  private JTextField nameTextField;
  private JTextField patronymicTextField;
  private JButton collapseAndExpandButton;
  private JTextField fioTextField;
  private JLabel surnameLabel;
  private JLabel patronymicLabel;
  private JLabel nameLabel;
  private boolean buttonSwitcher = true;


  public MainForm() {
    surnameTextField.setText("Panichkin");
    nameTextField.setText("Egor");
    patronymicTextField.setText("Dmitrievich");

    fioTextField.setVisible(false);
    System.out.println(surnameTextField.getX() + " " + surnameTextField.getY());

    collapseAndExpandButton.addActionListener(new Action() {
      @Override
      public Object getValue(String key) {
        return null;
      }

      @Override
      public void putValue(String key, Object value) {

      }

      @Override
      public void setEnabled(boolean b) {

      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void addPropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void removePropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void actionPerformed(ActionEvent e) {
        if (buttonSwitcher) {
          collapseAction();
        } else {
          expandAction();
        }
      }
    });
  }

  private void collapseAction() {
    if (checkFieldsFill()) {
      fioFieldsVisibility(!buttonSwitcher);
      fioTextField.setText(getTextFromFields());

      collapseAndExpandButton.setText("Expand");
      changeButtonSwitcher();
      mainPanel.updateUI();
    } else {
      showErrorDialog("Поля 'Фамилия' и 'Имя' должны быть заполнены");
    }
  }

  private void expandAction() {
    if (fioFieldsFiller()) {
      fioFieldsVisibility(!buttonSwitcher);
      changeButtonSwitcher();

      collapseAndExpandButton.setText("Collapse");
      mainPanel.updateUI();
    }
  }

  private boolean fioFieldsFiller() {
    String[] arr = fioTextField.getText().trim().split("\s+");
    if (arr.length >= 2 && arr.length <= 3) {
      surnameTextField.setText(arr[0]);
      nameTextField.setText(arr[1]);
      patronymicTextField.setText("");
      if (arr.length == 3) {
        patronymicTextField.setText(arr[2]);
      }
      return true;
    } else {
      showErrorDialog("Должно быть не менее двух значений, но не более трех");
      return false;
    }
  }

  private void changeButtonSwitcher() {
    buttonSwitcher = !buttonSwitcher;
  }

  private void fioFieldsVisibility(boolean flag) {
    surnameTextField.setVisible(flag);
    nameTextField.setVisible(flag);
    patronymicTextField.setVisible(flag);

    surnameLabel.setVisible(flag);
    nameLabel.setVisible(flag);
    patronymicLabel.setVisible(flag);

    fioTextField.setVisible(!flag);
  }

  private String getTextFromFields() {
    return surnameTextField.getText()
            + " " + nameTextField.getText()
            + (patronymicTextField.getText().isEmpty() ? "" : " " + patronymicTextField.getText());
  }

  private void showErrorDialog(String str) {
    JOptionPane.showMessageDialog(mainPanel, str,
            "ОШИБКА", JOptionPane.PLAIN_MESSAGE);
  }

  private Boolean checkFieldsFill() {
    return !surnameTextField.getText().isEmpty() && !nameTextField.getText().isEmpty();
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
