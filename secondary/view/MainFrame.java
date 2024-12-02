package com.student.view;

import com.student.entity.Group;
import com.student.entity.Student;
import com.student.util.Constant;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.setTitle("学生课堂管理系统");
        this.setSize(800, 600);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        this.initMenus();
        File classDir = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity");
        File[] files = classDir.listFiles(File::isDirectory);
        if (files != null && files.length != 0) {
            this.switchPanel(new ChangeClassPanel(this));
        } else {
            this.switchPanel(new ClassAddPanel());
        }

        this.setVisible(true);
    }

    public void initMenus() {
        JMenuBar mainMenu = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenuItem changeClassMenuItem = new JMenuItem("切换当前班");
        JMenuItem exportScoreMenuItem = new JMenuItem("导出当前班成绩");
        JMenuItem exitMenuItem = new JMenuItem("退出");
        JMenu classMenu = new JMenu("班级管理");
        JMenuItem addClassMenuItem = new JMenuItem("新增班级");
        JMenuItem classListMenuItem = new JMenuItem("班级列表");
        JMenu groupMenu = new JMenu("小组管理");
        JMenuItem addGroupMenuItem = new JMenuItem("新增小组");
        JMenuItem groupListMenuItem = new JMenuItem("小组列表");
        JMenu studentMenu = new JMenu("学生管理");
        JMenuItem addStudentMenuItem = new JMenuItem("新增学生");
        JMenuItem studentListMenuItem = new JMenuItem("学生列表");
        JMenu onClassMenu = new JMenu("课堂管理");
        JMenuItem randomGroupMenuItem = new JMenuItem("随机小组");
        JMenuItem randomStudentMenuItem = new JMenuItem("随机学生");
        this.getContentPane().add(mainMenu, "North");
        mainMenu.add(fileMenu);
        mainMenu.add(classMenu);
        mainMenu.add(groupMenu);
        mainMenu.add(studentMenu);
        mainMenu.add(onClassMenu);
        fileMenu.add(changeClassMenuItem);
        fileMenu.add(exportScoreMenuItem);
        fileMenu.add(exitMenuItem);
        classMenu.add(addClassMenuItem);
        classMenu.add(classListMenuItem);
        groupMenu.add(addGroupMenuItem);
        groupMenu.add(groupListMenuItem);
        studentMenu.add(addStudentMenuItem);
        studentMenu.add(studentListMenuItem);
        onClassMenu.add(randomGroupMenuItem);
        onClassMenu.add(randomStudentMenuItem);
        changeClassMenuItem.addActionListener((e) -> {
            File classDir = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity");
            File[] files = classDir.listFiles(File::isDirectory);
            if (files != null && files.length != 0) {
                this.switchPanel(new ChangeClassPanel(this));
            } else {
                JOptionPane.showMessageDialog(this, "请先创建班级", "", 1);
            }
        });
        exportScoreMenuItem.addActionListener((e) -> {
            if (Constant.CLASS_PATH.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请先选择班级", "", 1);
            } else {
                try {
                    File exportFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/scores.csv");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile));
                    writer.write("小组名称,小组得分\n");
                    Iterator var4 = Constant.groups.keySet().iterator();

                    while(var4.hasNext()) {
                        Group group = (Group)var4.next();
                        int totalScore = group.getScore();
                        Iterator var7 = ((List)Constant.groups.get(group)).iterator();

                        while(var7.hasNext()) {
                            Student student = (Student)var7.next();
                            if (student.isAbsent()) {
                                totalScore -= 5;
                            } else if (student.isLeave()) {
                                totalScore -= 2;
                            } else if (student.isAnswered()) {
                                totalScore += 3;
                            }
                        }

                        String var10001 = group.getGroupName();
                        writer.write(var10001 + "," + totalScore + "\n");
                    }

                    writer.close();
                    JOptionPane.showMessageDialog(this, "成绩已导出", "", 1);
                } catch (IOException var9) {
                    var9.printStackTrace();
                    JOptionPane.showMessageDialog(this, "导出失败", "", 1);
                }
            }

        });
        exitMenuItem.addActionListener((e) -> {
            System.exit(0);
        });
        addClassMenuItem.addActionListener((e) -> {
            this.switchPanel(new ClassAddPanel());
        });
        classListMenuItem.addActionListener((e) -> {
            this.switchPanel(new ClassListPanel());
        });
        addGroupMenuItem.addActionListener((e) -> {
            this.switchPanel(new GroupAddPanel());
        });
        groupListMenuItem.addActionListener((e) -> {
            this.switchPanel(new GroupListPanel());
        });
        addStudentMenuItem.addActionListener((e) -> {
            this.switchPanel(new StudentAddPanel());
        });
        studentListMenuItem.addActionListener((e) -> {
            this.switchPanel(new StudentListPanel());
        });
        randomGroupMenuItem.addActionListener((e) -> {
            this.switchPanel(new RandomGroupPanel());
        });
        randomStudentMenuItem.addActionListener((e) -> {
            this.switchPanel(new RandomStudentPanel());
        });
    }

    private void switchPanel(Component panel) {
        if (this.needCheckClass(panel) && Constant.CLASS_PATH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先选择班级", "", 1);
        } else {
            this.getContentPane().removeAll();
            this.initMenus();
            this.getContentPane().add(panel, "Center");
            this.getContentPane().repaint();
            this.getContentPane().validate();
        }
    }

    private boolean needCheckClass(Component panel) {
        return panel instanceof GroupAddPanel || panel instanceof GroupListPanel || panel instanceof StudentAddPanel || panel instanceof StudentListPanel || panel instanceof RandomGroupPanel || panel instanceof RandomStudentPanel;
    }

    public void refreshClassList() {
        this.getContentPane().removeAll();
        this.initMenus();
        ClassListPanel classListPanel = new ClassListPanel();
        this.getContentPane().add(classListPanel, "Center");
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }

    public void refreshStudentList() {
        this.getContentPane().removeAll();
        this.initMenus();
        StudentListPanel studentListPanel = new StudentListPanel();
        this.getContentPane().add(studentListPanel, "Center");
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }

    public void refreshGroupList() {
        this.getContentPane().removeAll();
        this.initMenus();
        GroupListPanel groupListPanel = new GroupListPanel();
        this.getContentPane().add(groupListPanel, "Center");
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }
}
