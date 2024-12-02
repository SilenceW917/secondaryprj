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

public class RandomStudentPanel extends JPanel {
    private Timer randomTimer;
    private Random random = new Random();
    private JLabel lbl2 = new JLabel("学生姓名：");
    private JLabel lbl3 = new JLabel("学生照片：");
    private JLabel lblPic = new JLabel("照片");
    private JTextField txtStudent = new JTextField();
    private JButton btnChooseStudent = new JButton("随机学生");
    private JButton btnAbsence = new JButton("缺勤");
    private JButton btnLeave = new JButton("请假");
    private JButton btnAnswer = new JButton("回答");

    public RandomStudentPanel() {
        this.setBorder(new TitledBorder(new EtchedBorder(), "随机学生点名"));
        this.setLayout((LayoutManager)null);
        this.add(this.lbl2);
        this.add(this.lbl3);
        this.add(this.txtStudent);
        this.add(this.lblPic);
        this.add(this.btnChooseStudent);
        this.add(this.btnAbsence);
        this.add(this.btnLeave);
        this.add(this.btnAnswer);
        this.lbl2.setBounds(160, 50, 100, 30);
        this.txtStudent.setBounds(160, 90, 130, 30);
        this.txtStudent.setEditable(false);
        this.lblPic.setBounds(160, 130, 130, 150);
        this.btnChooseStudent.setBounds(160, 300, 130, 30);
        this.btnAbsence.setBounds(160, 340, 60, 30);
        this.btnLeave.setBounds(230, 340, 60, 30);
        this.btnAnswer.setBounds(300, 340, 60, 30);
        this.btnChooseStudent.addActionListener((e) -> {
            if (e.getActionCommand().equals("停")) {
                this.btnChooseStudent.setText("随机学生");
                if (this.randomTimer != null && this.randomTimer.isRunning()) {
                    this.randomTimer.stop();
                }
            } else {
                this.btnChooseStudent.setText("停");
                this.startRandomStudent();
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
        this.btnAnswer.addActionListener((e) -> {
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
                                student.setAnswered(true);
                                group.setScore(group.getScore() + 3);

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

                    JOptionPane.showMessageDialog(this, "回答正确，加3分！", "", 1);
                    break;
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先随机选择学生", "", 1);
            }

        });
    }

    private void startRandomStudent() {
        if (this.randomTimer != null && this.randomTimer.isRunning()) {
            this.randomTimer.stop();
        }

        this.randomTimer = new Timer(50, (e) -> {
            ArrayList<Student> allStudents = new ArrayList();
            Iterator var3 = Constant.groups.values().iterator();

            while(var3.hasNext()) {
                List<Student> students = (List)var3.next();
                allStudents.addAll(students);
            }

            if (!allStudents.isEmpty()) {
                int index = this.random.nextInt(allStudents.size());
                Student student = (Student)allStudents.get(index);
                this.txtStudent.setText(student.getName());
            }

        });
        this.randomTimer.start();
    }
}
