package com.student;

import com.student.view.StudentAddPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static Class currentClass;
    private static List<Class> classes = new ArrayList<>();

    public static void main(String[] args) {
        // 设置UI样式
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 初始化示例数据
        initSampleData();

        // 启动主窗口
        MainFrame mainFrame = new MainFrame();
    }

    private static void initSampleData() {
        // 创建示例班级
        Class class1 = new Class("计算机1班");
        Class class2 = new Class("计算机2班");

        // 创建小组
        Group group1 = new Group(1);
        Group group2 = new Group(2);
        Group group3 = new Group(3);

        // 添加学生到小组
        group1.addStudent(new Student("张三", "001"));
        group1.addStudent(new Student("李四", "002"));
        group2.addStudent(new Student("王五", "003"));
        group2.addStudent(new Student("赵六", "004"));
        group3.addStudent(new Student("孙七", "005"));
        group3.addStudent(new Student("周八", "006"));

        // 将小组添加到班级
        class1.addGroup(group1);
        class1.addGroup(group2);
        class2.addGroup(group3);

        // 添加班级到列表
        classes.add(class1);
        classes.add(class2);
    }

    // 获取当前选中的班级
    public static Class getCurrentClass() {
        return currentClass;
    }

    // 设置当前选中的班级
    public static void setCurrentClass(Class selectedClass) {
        currentClass = selectedClass;
    }

    // 获取所有班级列表
    public static List<Class> getClasses() {
        return classes;
    }

    // 添加新班级
    public static void addClass(Class newClass) {
        classes.add(newClass);
    }

    // 删除班级
    public static void removeClass(Class classToRemove) {
        classes.remove(classToRemove);
    }

    // 随机选择学生
    public static StudentAddPanel getRandomStudent() {
        if (currentClass != null) {
            return currentClass.getRandomStudent();
        }
        return null;
    }

    // 随机选择小组
    public static Group getRandomGroup() {
        if (currentClass != null) {
            return currentClass.getRandomGroup();
        }
        return null;
    }
}
