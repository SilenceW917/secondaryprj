package com.student.view;

import com.student.entity.Group;
import com.student.entity.Student;
import com.student.util.Constant;
import java.awt.LayoutManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RandomGroupPanel extends JPanel {
    private Timer groupTimer;
    private Timer studentTimer;
    private Random random = new Random();
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

    public RandomGroupPanel() {
        this.setBorder(new TitledBorder(new EtchedBorder(), "随机小组点名"));
        this.setLayout((LayoutManager)null);
        this.add(this.lbl1);
        this.add(this.lbl2);
        this.add(this.lbl3);
        this.add(this.txtGroup);
        this.add(this.txtStudent);
        this.add(this.lblPic);
        this.add(this.btnChooseGroup);
        this.add(this.btnChooseStudent);
        this.add(this.btnAbsence);
        this.add(this.btnLeave);
        this.add(this.lbl4);
        this.add(this.txtScore);
        this.add(this.btnScore);
        this.lbl1.setBounds(50, 50, 100, 30);
        this.txtGroup.setBounds(50, 90, 100, 30);
        this.txtGroup.setEditable(false);
        this.btnChooseGroup.setBounds(50, 130, 100, 30);
        this.lbl4.setBounds(50, 190, 100, 30);
        this.txtScore.setBounds(50, 230, 100, 30);
        this.btnScore.setBounds(50, 270, 100, 30);
        this.lbl2.setBounds(220, 50, 100, 30);
        this.txtStudent.setBounds(220, 90, 130, 30);
        this.txtStudent.setEditable(false);
        this.lblPic.setBounds(220, 130, 130, 150);
        this.btnChooseStudent.setBounds(220, 300, 100, 30);
        this.btnAbsence.setBounds(220, 340, 60, 30);
        this.btnLeave.setBounds(290, 340, 60, 30);
        this.btnChooseGroup.addActionListener((e) -> {
            if (e.getActionCommand().equals("停")) {
                this.btnChooseGroup.setText("随机小组");
                if (this.groupTimer != null && this.groupTimer.isRunning()) {
                    this.groupTimer.stop();
                }
            } else {
                this.btnChooseGroup.setText("停");
                this.startRandomGroup();
            }

        });
        this.btnChooseStudent.addActionListener((e) -> {
            if (this.txtGroup.getText() != null && !this.txtGroup.getText().isEmpty()) {
                if (e.getActionCommand().equals("停")) {
                    this.btnChooseStudent.setText("随机学生");
                    if (this.studentTimer != null && this.studentTimer.isRunning()) {
                        this.studentTimer.stop();
                    }
                } else {
                    this.btnChooseStudent.setText("停");
                    this.startRandomStudent();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先随机抽取小组", "", 1);
            }

        });
        this.btnAbsence.addActionListener((e) -> {
            if (this.txtStudent.getText() != null && !this.txtStudent.getText().isEmpty()) {
                String studentName = this.txtStudent.getText();
                Iterator var3 = Constant.groups.keySet().iterator();

                while(true) {
                    while(var3.hasNext()) {
                        Group group = (Group)var3.next();
                        Iterator var5 = ((List)Constant.groups.get(group)).iterator();

                        while(var5.hasNext()) {
                            Student student = (Student)var5.next();
                            if (student.getName().equals(studentName)) {
                                student.setAbsent(true);
                                group.setScore(group.getScore() - 5);

                                try {
                                    File groupFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/groups.txt");
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile));
                                    Iterator var9 = Constant.groups.keySet().iterator();

                                    while(var9.hasNext()) {
                                        Group g = (Group)var9.next();
                                        String var10001 = g.getGroupName();
                                        writer.write(var10001 + "," + g.getScore() + "\n");
                                    }

                                    writer.close();
                                    break;
                                } catch (IOException var11) {
                                    var11.printStackTrace();
                                    JOptionPane.showMessageDialog(this, "更新分数失败", "", 1);
                                    return;
                                }
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(this, "已记录缺勤并扣除5分", "", 1);
                    break;
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先随机选择学生", "", 1);
            }

        });
        this.btnLeave.addActionListener((e) -> {
            if (this.txtStudent.getText() != null && !this.txtStudent.getText().isEmpty()) {
                String studentName = this.txtStudent.getText();
                Iterator var3 = Constant.groups.keySet().iterator();

                while(true) {
                    while(var3.hasNext()) {
                        Group group = (Group)var3.next();
                        Iterator var5 = ((List)Constant.groups.get(group)).iterator();

                        while(var5.hasNext()) {
                            Student student = (Student)var5.next();
                            if (student.getName().equals(studentName)) {
                                student.setLeave(true);
                                group.setScore(group.getScore() - 2);

                                try {
                                    File groupFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/groups.txt");
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile));
                                    Iterator var9 = Constant.groups.keySet().iterator();

                                    while(var9.hasNext()) {
                                        Group g = (Group)var9.next();
                                        String var10001 = g.getGroupName();
                                        writer.write(var10001 + "," + g.getScore() + "\n");
                                    }

                                    writer.close();
                                    break;
                                } catch (IOException var11) {
                                    var11.printStackTrace();
                                    JOptionPane.showMessageDialog(this, "更新分数失败", "", 1);
                                    return;
                                }
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(this, "已记录请假并扣除2分", "", 1);
                    break;
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先随机选择学生", "", 1);
            }

        });
        this.btnScore.addActionListener((e) -> {
            if (this.txtGroup.getText() != null && !this.txtGroup.getText().isEmpty()) {
                if (this.txtScore.getText() != null && !this.txtScore.getText().isEmpty()) {
                    String groupName = this.txtGroup.getText();
                    int score = Integer.parseInt(this.txtScore.getText());
                    Iterator var4 = Constant.groups.keySet().iterator();

                    while(var4.hasNext()) {
                        Group group = (Group)var4.next();
                        if (group.getGroupName().equals(groupName)) {
                            group.setScore(score);
                            break;
                        }
                    }

                    try {
                        File groupFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/groups.txt");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile));
                        Iterator var6 = Constant.groups.keySet().iterator();

                        while(var6.hasNext()) {
                            Group groupx = (Group)var6.next();
                            String var10001 = groupx.getGroupName();
                            writer.write(var10001 + "," + groupx.getScore() + "\n");
                        }

                        writer.close();
                        JOptionPane.showMessageDialog(this, "打分成功", "", 1);
                    } catch (IOException var8) {
                        var8.printStackTrace();
                        JOptionPane.showMessageDialog(this, "打分失败", "", 1);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "请填写分数", "", 1);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先抽取小组", "", 1);
            }
        });
    }

    private void startRandomGroup() {
        List<String> groupNames = new ArrayList();
        Iterator var2 = Constant.groups.keySet().iterator();

        while(var2.hasNext()) {
            Group group = (Group)var2.next();
            groupNames.add(group.getGroupName());
        }

        if (groupNames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "没有可用的小组", "", 1);
        } else {
            if (this.groupTimer != null && this.groupTimer.isRunning()) {
                this.groupTimer.stop();
            }

            this.groupTimer = new Timer(50, (e) -> {
                int index = this.random.nextInt(groupNames.size());
                String groupName = (String)groupNames.get(index);
                this.txtGroup.setText(groupName);
            });
            this.groupTimer.start();
        }
    }

    private void startRandomStudent() {
        String groupName = this.txtGroup.getText();
        if (this.studentTimer != null && this.studentTimer.isRunning()) {
            this.studentTimer.stop();
        }

        this.studentTimer = new Timer(50, (e) -> {
            Iterator var3 = Constant.groups.keySet().iterator();

            while(var3.hasNext()) {
                Group group = (Group)var3.next();
                if (group.getGroupName().equals(groupName)) {
                    List<Student> students = (List)Constant.groups.get(group);
                    if (!students.isEmpty()) {
                        int index = this.random.nextInt(students.size());
                        Student student = (Student)students.get(index);
                        this.txtStudent.setText(student.getName());
                    }
                    break;
                }
            }

        });
        this.studentTimer.start();
    }
}
