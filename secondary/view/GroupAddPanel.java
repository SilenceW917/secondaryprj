package com.student.view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GroupAddPanel extends JPanel {
    public GroupAddPanel() {
        this.setLayout(null);
        this.setBorder(new TitledBorder(new EtchedBorder(), "新增小组"));
        JLabel lblName = new JLabel("小组名称：");
        JTextField txtName = new JTextField();
        JButton btnName = new JButton("确认");
        this.add(lblName);
        this.add(txtName);
        this.add(btnName);
        lblName.setBounds(200, 80, 100, 30);
        txtName.setBounds(200, 130, 200, 30);
        btnName.setBounds(200, 180, 100, 30);

        btnName.addActionListener(e -> {
            if (txtName.getText() == null || txtName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写小组名称", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // TODO 新增小组
                JOptionPane.showMessageDialog(this, "新增小组成功", "", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

}
