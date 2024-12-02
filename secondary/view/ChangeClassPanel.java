package com.student.view;

import com.student.entity.Group;
import com.student.entity.Student;
import com.student.util.Constant;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ChangeClassPanel extends JScrollPane {
    JLabel infoLbl = new JLabel();
    ButtonGroup classGroup = new ButtonGroup();
    JPanel radioPanel = new JPanel();

    public ChangeClassPanel(MainFrame mainFrame) {
        this.setLayout((LayoutManager)null);
        this.setBorder(new TitledBorder(new EtchedBorder(), "新选择班级"));
        File classDir = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity");
        File[] files = classDir.listFiles(File::isDirectory);
        if (files != null && files.length != 0) {
            JLabel lblClass = new JLabel("选择班级：");
            JButton btnConfirm = new JButton("确认");
            this.radioPanel.setLayout(new BoxLayout(this.radioPanel, 1));
            if (files != null) {
                File[] var6 = files;
                int var7 = files.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    File file = var6[var8];
                    JRadioButton radioBtn = new JRadioButton(file.getName());
                    this.classGroup.add(radioBtn);
                    this.radioPanel.add(radioBtn);
                }
            }

            JScrollPane scrollPane = new JScrollPane(this.radioPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            this.add(lblClass);
            this.add(scrollPane);
            this.add(btnConfirm);
            lblClass.setBounds(250, 80, 100, 30);
            scrollPane.setBounds(250, 130, 200, 150);
            btnConfirm.setBounds(250, 300, 100, 30);
            btnConfirm.addActionListener((e) -> {
                ButtonModel selectedBtn = this.classGroup.getSelection();
                if (selectedBtn == null) {
                    JOptionPane.showMessageDialog(this, "请选择班级", "", 1);
                } else {
                    Constant.groups.clear();
                    String className = "";
                    Enumeration<AbstractButton> buttons = this.classGroup.getElements();

                    while(buttons.hasMoreElements()) {
                        AbstractButton button = (AbstractButton)buttons.nextElement();
                        if (button.isSelected()) {
                            className = button.getText();
                            break;
                        }
                    }

                    Constant.CLASS_PATH = className;
                    this.loadClassData(className);
                    JOptionPane.showMessageDialog(this, "选择班级成功", "", 1);
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "请先创建班级", "", 1);
        }

    }

    private void loadClassData(String className) {
        Constant.groups.clear();
        int totalStudents = 0;

        try {
            File groupFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + className + "/groups.txt");
            if (groupFile.exists()) {
                BufferedReader groupReader = new BufferedReader(new FileReader(groupFile));

                String line;
                while((line = groupReader.readLine()) != null) {
                    String[] groupInfo = line.split(",");
                    if (groupInfo.length >= 2) {
                        Group group = new Group(groupInfo[0]);
                        group.setScore(Integer.parseInt(groupInfo[1]));
                        Constant.groups.put(group, new ArrayList());
                    }
                }

                groupReader.close();
            }

            File studentFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + className + "/students.txt");
            if (studentFile.exists()) {
                BufferedReader studentReader = new BufferedReader(new FileReader(studentFile));

                label47:
                while(true) {
                    while(true) {
                        String[] studentInfo;
                        do {
                            String line;
                            if ((line = studentReader.readLine()) == null) {
                                studentReader.close();
                                break label47;
                            }

                            studentInfo = line.split(",");
                        } while(studentInfo.length < 3);

                        String studentId = studentInfo[0].trim();
                        String studentName = studentInfo[1].trim();
                        String groupName = studentInfo[2].trim();
                        Iterator var11 = Constant.groups.keySet().iterator();

                        while(var11.hasNext()) {
                            Group group = (Group)var11.next();
                            if (group.getGroupName().equals(groupName)) {
                                Student student = new Student(studentId, studentName);
                                student.setGroup(group);
                                ((List)Constant.groups.get(group)).add(student);
                                ++totalStudents;
                                break;
                            }
                        }
                    }
                }
            }

            this.removeAll();
            this.infoLbl.setText("班级：" + className + "，班级学生总数：" + totalStudents);
            this.infoLbl.setBounds(160, 100, 200, 30);
            this.add(this.infoLbl);
            this.repaint();
            this.validate();
        } catch (Exception var14) {
            var14.printStackTrace();
        }

    }
}
