package com.student.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ClassListPanel extends JPanel {
    String[] headers = new String[]{"序号", "班级名称"};
    JTable classTable;
    JTextField txtName = new JTextField();
    JButton btnEdit = new JButton("修改");
    JButton btnDelete = new JButton("删除");

    public ClassListPanel() {
        this.setBorder(new TitledBorder(new EtchedBorder(), "班级列表"));
        this.setLayout(new BorderLayout());
        File classDir = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity");
        if (!classDir.exists()) {
            classDir.mkdirs();
        }

        File[] classes = classDir.listFiles((file) -> {
            return file.isDirectory();
        });
        if (classes == null) {
            classes = new File[0];
        }

        String[][] data = new String[classes.length][2];

        for(int i = 0; i < classes.length; ++i) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = classes[i].getName();
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, this.headers);
        this.classTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.classTable.getSelectionModel().setSelectionMode(0);
        JScrollPane scrollPane = new JScrollPane(this.classTable);
        this.add(scrollPane, "Center");
        JPanel btnPanel = new JPanel();
        btnPanel.add(this.txtName);
        this.txtName.setPreferredSize(new Dimension(200, 30));
        btnPanel.add(this.btnEdit);
        btnPanel.add(this.btnDelete);
        this.add(btnPanel, "South");
        this.classTable.getSelectionModel().addListSelectionListener((e) -> {
            int selectedRow = this.classTable.getSelectedRow();
            if (selectedRow >= 0) {
                this.txtName.setText(data[selectedRow][1]);
            }

        });
        this.btnEdit.addActionListener((e) -> {
            int selectedRow = this.classTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "请先选择班级", "", 1);
            } else if (this.txtName.getText() != null && !this.txtName.getText().isEmpty()) {
                String oldName = data[selectedRow][1];
                String newName = this.txtName.getText();
                File oldFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + oldName);
                File newFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + newName);
                if (newFile.exists()) {
                    JOptionPane.showMessageDialog(this, "班级名称已存在", "", 1);
                } else {
                    if (oldFile.renameTo(newFile)) {
                        data[selectedRow][1] = newName;
                        this.classTable.updateUI();
                        JOptionPane.showMessageDialog(this, "修改成功", "", 1);
                        MainFrame mainFrame = (MainFrame)SwingUtilities.getWindowAncestor(this);
                        mainFrame.refreshClassList();
                        this.txtName.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "修改失败", "", 1);
                    }

                }
            } else {
                JOptionPane.showMessageDialog(this, "请填写班级名称", "", 1);
            }
        });
        this.btnDelete.addActionListener((e) -> {
            int selectedRow = this.classTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "请先选择班级", "", 1);
            } else if (JOptionPane.showConfirmDialog(this, "删除班级会把小组、学生和成绩都删除，您确定要删除这个班级？", "", 0) == 0) {
                String className = data[selectedRow][1];
                File classDir1 = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity", className);
                if (this.deleteDirectory(classDir1)) {
                    DefaultTableModel model = (DefaultTableModel)this.classTable.getModel();
                    model.removeRow(selectedRow);
                    this.txtName.setText("");
                    JOptionPane.showMessageDialog(this, "删除成功", "", 1);
                    MainFrame mainFrame = (MainFrame)SwingUtilities.getWindowAncestor(this);
                    mainFrame.refreshClassList();
                    this.txtName.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败", "", 1);
                }

            }
        });
    }

    private boolean deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            File[] var3 = files;
            int var4 = files.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                File file = var3[var5];
                if (file.isDirectory()) {
                    this.deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }

        return directory.delete();
    }
}