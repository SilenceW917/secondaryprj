package com.student.view;

import com.student.entity.Group;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.util.List;

public class StudentAddPanel extends JPanel {
    public StudentAddPanel() {
        this.setLayout(null);
        this.setBorder(new TitledBorder(new EtchedBorder(), "新增学生"));
        JLabel lblId = new JLabel("学号：");
        JTextField txtId = new JTextField();
        JLabel lblName = new JLabel("姓名：");
        JTextField txtName = new JTextField();
        JLabel lblGroup = new JLabel("小组:");
        JComboBox<String> cmbGroup = new JComboBox<>();
        JButton btnName = new JButton("确认");
        this.add(lblId);
        this.add(txtId);
        this.add(lblName);
        this.add(txtName);
        this.add(lblGroup);
        this.add(cmbGroup);
        this.add(btnName);
        lblId.setBounds(200, 60, 100, 30);
        txtId.setBounds(200, 100, 100, 30);
        lblName.setBounds(200, 140, 100, 30);
        txtName.setBounds(200, 180, 200, 30);
        lblGroup.setBounds(200, 220, 100, 30);
        cmbGroup.setBounds(200, 260, 100, 30);
        btnName.setBounds(200, 300, 100, 30);
        // TODO 列举小组
        cmbGroup.addItem("请选择小组");

        btnName.addActionListener(e -> {
            if (txtId.getText() == null || txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写学号", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (txtName.getText() == null || txtName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写学生姓名", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // TODO 添加学生

        });
    }
}
