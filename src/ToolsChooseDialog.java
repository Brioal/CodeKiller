import bean.GitFileBean;
import interfaces.OnListChooseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ToolsChooseDialog extends JDialog {
    private JPanel contentPane;
    private JList list1;
    private JButton btnOk;
    private List<GitFileBean> list;
    private OnListChooseListener onListChooseListener;

    public void setOnListChooseListener(OnListChooseListener onListChooseListener) {
        this.onListChooseListener = onListChooseListener;
    }

    public ToolsChooseDialog(List<GitFileBean> list) {
        this.list = list;
        setContentPane(contentPane);
        setModal(true);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
        setLocation(x, y);

        list1.setModel(new AbstractListModel() {
            @Override
            public int getSize() {
                return list.size();
            }

            @Override
            public Object getElementAt(int index) {
                return list.get(index).getName();
            }
        });


        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        int[] indexs = list1.getSelectedIndices();
        boolean shouldClose = true;
        for (int i = 0; i < indexs.length; i++) {
            if (onListChooseListener != null) {
                boolean success = onListChooseListener.choose(indexs[i]);
                if (!success) {
                    shouldClose = false;
                }
            }
        }
        if (shouldClose) {
            dispose();
        }
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
//        ToolsChooseDialog toolsChooseDialog = new ToolsChooseDialog();
//        toolsChooseDialog.show();
    }


}
