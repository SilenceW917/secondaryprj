package com.student.view;

import com.student.entity.Group;
import com.student.entity.Student;
import com.student.util.Constant;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class StudentAddPanel extends JPanel {
    public StudentAddPanel() {
        this.setLayout((LayoutManager)null);
        this.setBorder(new TitledBorder(new EtchedBorder(), "新增学生"));
        JLabel lblId = new JLabel("学号：");
        JTextField txtId = new JTextField();
        JLabel lblName = new JLabel("姓名：");
        JTextField txtName = new JTextField();
        JLabel lblGroup = new JLabel("小组:");
        JTextField txtGroup = new JTextField();
        JButton btnName = new JButton("确认");
        this.add(lblId);
        this.add(txtId);
        this.add(lblName);
        this.add(txtName);
        this.add(lblGroup);
        this.add(txtGroup);
        this.add(btnName);
        lblId.setBounds(200, 60, 100, 30);
        txtId.setBounds(200, 100, 100, 30);
        lblName.setBounds(200, 140, 100, 30);
        txtName.setBounds(200, 180, 200, 30);
        lblGroup.setBounds(200, 220, 100, 30);
        txtGroup.setBounds(200, 260, 100, 30);
        btnName.setBounds(200, 300, 100, 30);
        btnName.addActionListener((e) -> {
            if (txtId.getText() != null && !txtId.getText().isEmpty()) {
                if (txtName.getText() != null && !txtName.getText().isEmpty()) {
                    if (txtGroup.getText() != null && !txtGroup.getText().isEmpty()) {
                        Iterator var5 = Constant.groups.keySet().iterator();

                        Group targetGroup;
                        Iterator var7;
                        while(var5.hasNext()) {
                            targetGroup = (Group)var5.next();
                            var7 = ((List)Constant.groups.get(targetGroup)).iterator();

                            while(var7.hasNext()) {
                                Student studentx = (Student)var7.next();
                                if (studentx.getId().equals(txtId.getText())) {
                                    JOptionPane.showMessageDialog(this, "学号已存在", "", 1);
                                    return;
                                }
                            }
                        }

                        String groupName = txtGroup.getText().trim();
                        targetGroup = null;
                        var7 = Constant.groups.keySet().iterator();

                        while(var7.hasNext()) {
                            Group group = (Group)var7.next();
                            if (group.getGroupName().equals(groupName)) {
                                targetGroup = group;
                                break;
                            }
                        }

                        if (targetGroup == null) {
                            targetGroup = new Group(groupName);
                            Constant.groups.put(targetGroup, new ArrayList());
                        }

                        Student student = new Student(txtId.getText(), txtName.getText());
                        student.setGroup(targetGroup);
                        ((List)Constant.groups.get(targetGroup)).add(student);

                        try {
                            File studentFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/students.txt");
                            BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile));
                            Iterator var10 = Constant.groups.keySet().iterator();

                            while(var10.hasNext()) {
                                Group groupx = (Group)var10.next();
                                Iterator var12 = ((List)Constant.groups.get(groupx)).iterator();

                                while(var12.hasNext()) {
                                    Student s = (Student)var12.next();
                                    String var10001 = s.getId();
                                    writer.write(var10001 + "," + s.getName() + "," + s.getGroup().getGroupName() + "\n");
                                }
                            }

                            writer.close();
                            txtId.setText("");
                            txtName.setText("");
                            txtGroup.setText("");
                            JOptionPane.showMessageDialog(this, "新增学生成功", "", 1);
                            Window window = SwingUtilities.getWindowAncestor(this);
                            if (window instanceof MainFrame) {
                                MainFrame mainFrame = (MainFrame)window;
                                Component[] var27 = mainFrame.getContentPane().getComponents();
                                int var28 = var27.length;

                                for(int var14 = 0; var14 < var28; ++var14) {
                                    Component comp = var27[var14];
                                    if (comp instanceof ChangeClassPanel) {
                                        ChangeClassPanel changeClassPanel = (ChangeClassPanel)comp;
                                        int totalStudents = 0;

                                        List students;
                                        for(Iterator var18 = Constant.groups.values().iterator(); var18.hasNext(); totalStudents += students.size()) {
                                            students = (List)var18.next();
                                        }

                                        changeClassPanel.infoLbl.setText("班级：" + Constant.CLASS_PATH + "，班级学生总数：" + totalStudents);
                                        changeClassPanel.repaint();
                                        break;
                                    }
                                }
                            }
                        } catch (IOException var20) {
                            var20.printStackTrace();
                            JOptionPane.showMessageDialog(this, "新增学生成功", "", 1);
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "请填写小组", "", 1);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "请填写学生姓名", "", 1);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请填写学号", "", 1);
            }
        });
    }
}
