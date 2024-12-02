package com.student.view;

import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClassAddPanel extends JPanel {
    public ClassAddPanel() {
        this.setLayout((LayoutManager)null);
        this.setBorder(new TitledBorder(new EtchedBorder(), "新增班级"));
        JLabel lblName = new JLabel("班级名称：");
        JTextField txtName = new JTextField();
        JButton btnName = new JButton("确认");
        this.add(lblName);
        this.add(txtName);
        this.add(btnName);
        lblName.setBounds(200, 80, 100, 30);
        txtName.setBounds(200, 130, 200, 30);
        btnName.setBounds(200, 180, 100, 30);
        btnName.addActionListener((e) -> {
            if (txtName.getText() != null && !txtName.getText().isEmpty()) {
                try {
                    File classDir = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity", txtName.getText());
                    if (!classDir.exists()) {
                        classDir.mkdirs();
                        File groupFile = new File(classDir, "groups.txt");
                        groupFile.createNewFile();
                        File studentFile = new File(classDir, "students.txt");
                        studentFile.createNewFile();
                        JOptionPane.showMessageDialog(this, "新增班级成功", "", 1);
                        MainFrame mainFrame = (MainFrame)SwingUtilities.getWindowAncestor(this);
                        mainFrame.refreshClassList();
                    } else {
                        JOptionPane.showMessageDialog(this, "班级已存在", "", 1);
                    }
                } catch (IOException var7) {
                    var7.printStackTrace();
                    JOptionPane.showMessageDialog(this, "创建班级失败", "", 1);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请填写班级名称", "", 1);
            }

        });
    }
}
