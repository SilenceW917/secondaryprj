package com.student.view;

import com.student.util.Constant;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RandomGroupPanel extends JPanel {
    private JLabel lbl1 = new JLabel("小组名：");
    private JLabel lbl2 = new JLabel("学生姓名：");
    private JLabel lbl3 = new JLabel("学生照片：");
    private JLabel lblPic = new JLabel("照片");
    private JLabel lbl4 = new JLabel("小组评分");
    private JTextField txtGroup = new JTextField();
    private JTextField txtStudent = new JTextField();
    private JTextField txtScore = new JTextField();
    private JButton btnChooseGroup = new JButton("随机小组");
    private JButton btnChooseStudent = new JButton("随机学生");
    private JButton btnAbsence = new JButton("缺勤");
    private JButton btnLeave = new JButton("请假");
    private JButton btnScore = new JButton("小组评分");
    Thread threadGroup = null;   // 随机抽取小组的线程
    Thread threadStudent = null;   // 随机抽取小组的线程

    public RandomGroupPanel() {
        this.setBorder(new TitledBorder(new EtchedBorder(), "随机小组点名"));
        this.setLayout(null);
        this.add(lbl1);
        this.add(lbl2);
        this.add(lbl3);
        this.add(txtGroup);
        this.add(txtStudent);
        this.add(lblPic);
        this.add(btnChooseGroup);
        this.add(btnChooseStudent);
        this.add(btnAbsence);
        this.add(btnLeave);
        this.add(lbl4);
        this.add(txtScore);
        this.add(btnScore);

        lbl1.setBounds(50, 50, 100, 30);
        txtGroup.setBounds(50, 90, 100, 30);
        txtGroup.setEditable(false);
        btnChooseGroup.setBounds(50, 130, 100, 30);

        lbl4.setBounds(50, 190, 100, 30);
        txtScore.setBounds(50, 230, 100, 30);
        btnScore.setBounds(50, 270, 100, 30);

        lbl2.setBounds(220, 50, 100, 30);
        txtStudent.setBounds(220, 90, 130, 30);
        txtStudent.setEditable(false);
        lblPic.setBounds(220, 130, 130, 150);
        btnChooseStudent.setBounds(220, 300, 100, 30);
        btnAbsence.setBounds(220, 340, 60, 30);
        btnLeave.setBounds(290, 340, 60, 30);
        // 添加按钮功能
        // 随机小组
        btnChooseGroup.addActionListener(e -> {
            if (e.getActionCommand().equals("停")) {
                btnChooseGroup.setText("随机小组");

            } else {

            }
        });
        // 随机学生
        btnChooseStudent.addActionListener(e -> {
            if (txtGroup.getText() == null || txtGroup.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请先随机抽取小组", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (e.getActionCommand().equals("停")) {
                    btnChooseStudent.setText("随机学生");

                } else {
                    btnChooseStudent.setText("停");

                }
            }
        });
        // TODO 缺勤
        btnAbsence.addActionListener(e -> {
            if (txtStudent.getText() == null || txtStudent.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请先随机选择学生", "", JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(this, "已记录缺勤", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // TODO 请假
        btnLeave.addActionListener(e -> {
            if (txtStudent.getText() == null || txtStudent.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请先随机选择学生", "", JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(this, "已记录请假", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // TODO 给小组打分
        btnScore.addActionListener(e -> {
            if (txtGroup.getText() == null || txtGroup.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请先抽取小组", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (txtScore.getText() == null || txtScore.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写分数", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

        });
    }
}
