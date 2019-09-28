import interfaces.OnCheckBoxChooseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToolsChooseDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox updateToolsCheckBox;
    private JCheckBox integerUtilCheckBox;
    private JCheckBox listUtilCheckBox;
    private JCheckBox updateToolCheckBox;
    private JCheckBox codeUtilCheckBox;
    private JCheckBox jpaExampleUtilCheckBox;
    private JCheckBox textUtilCheckBox;
    private JCheckBox passwordUtilCheckBox;
    private JCheckBox randomUtilCheckBox;
    private JCheckBox reviewDateFormatUtlCheckBox;
    private JCheckBox reviewFileUtilsCheckBox;
    private JCheckBox sizeConverterCheckBox;
    private JCheckBox tokenUtilsCheckBox;
    private JCheckBox webMvcConfCheckBox;
    private JCheckBox swagger2ConfigCheckBox;
    private JCheckBox configCheckBox;
    private JCheckBox authenticationInterceptorCheckBox;
    private JCheckBox adminRunnerCheckBox;
    private JCheckBox currentUserCheckBox;
    private JCheckBox permissionCheckCheckBox;
    private JCheckBox fileServiceCheckBox;

    private OnCheckBoxChooseListener onCheckBoxChooseListener;

    public void setOnCheckBoxChooseListener(OnCheckBoxChooseListener onCheckBoxChooseListener) {
        this.onCheckBoxChooseListener = onCheckBoxChooseListener;
    }

    public ToolsChooseDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
        setLocation(x, y);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        onCheckBoxChooseListener.done(new boolean[]{
                updateToolsCheckBox.isSelected(),
                integerUtilCheckBox.isSelected(),
                listUtilCheckBox.isSelected(),
                updateToolCheckBox.isSelected(),
                codeUtilCheckBox.isSelected(),
                jpaExampleUtilCheckBox.isSelected(),
                textUtilCheckBox.isSelected(),
                passwordUtilCheckBox.isSelected(),
                randomUtilCheckBox.isSelected(),
                reviewDateFormatUtlCheckBox.isSelected(),
                reviewFileUtilsCheckBox.isSelected(),
                sizeConverterCheckBox.isSelected(),
                tokenUtilsCheckBox.isSelected(),
                webMvcConfCheckBox.isSelected(),
                swagger2ConfigCheckBox.isSelected(),
                configCheckBox.isSelected(),
                authenticationInterceptorCheckBox.isSelected(),
                adminRunnerCheckBox.isSelected(),
                currentUserCheckBox.isSelected(),
                permissionCheckCheckBox.isSelected(),
                fileServiceCheckBox.isSelected()
        });
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {

    }
}
