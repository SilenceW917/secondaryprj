package com.student.view;

import com.student.entity.Group;

import com.student.entity.Student;

import com.student.util.Constant;



import javax.swing.*;

import javax.swing.border.EtchedBorder;

import javax.swing.border.TitledBorder;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;



public class StudentListPanel extends JPanel {

    String[] headers = {"学号", "姓名", "小组"};

    String[][] data = null;

    JTable studentTable;

    JTextField txtId = new JTextField();

    JTextField txtName = new JTextField();

    JComboBox<String> cmbGroup = new JComboBox<>();

    JButton btnEdit = new JButton("修改");

    JButton btnDelete = new JButton("删除");

    private int totalStudents = 0;



    public StudentListPanel() {

        this.setBorder(new TitledBorder(new EtchedBorder(), "学生列表"));

        this.setLayout(new BorderLayout());



        // 读取学生文件并计算总数

        List<String[]> studentList = new ArrayList<>();

        totalStudents = 0;



        try {

            File studentFile = new File(Constant.FILE_PATH + Constant.CLASS_PATH + "/students.txt");

            if (studentFile.exists()) {

                BufferedReader reader = new BufferedReader(new FileReader(studentFile));

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] studentInfo = line.split(",");

                    if (studentInfo.length >= 3) {

                        String studentId = studentInfo[0].trim();

                        String studentName = studentInfo[1].trim();

                        String groupName = studentInfo[2].trim();



                        // 将学生信息添加到列表

                        studentList.add(new String[]{studentId, studentName, groupName});

                        totalStudents++;

                    }

                }

                reader.close();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }



        // 转换为表格数据

        data = studentList.toArray(new String[0][]);



        // 更新选择班级面板的学生总数

        updateTotalStudents();



        // 创建表格

        DefaultTableModel tableModel = new DefaultTableModel(data, headers);

        studentTable = new JTable(tableModel) {

            @Override

            public boolean isCellEditable(int row, int column) {

                return false;

            }

        };

        studentTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(studentTable);

        this.add(scrollPane, BorderLayout.CENTER);



        // 初始化小组下拉框

        cmbGroup.removeAllItems();

        //  先添加默认选项

        cmbGroup.addItem("请选择小组");

        // 按照小组列表的顺序添加小组

        for (Group group : Constant.groups.keySet()) {

            cmbGroup.addItem(group.getGroupName());

        }



        JPanel btnPanel = new JPanel();

        btnPanel.add(txtId);

        txtId.setPreferredSize(new Dimension(100, 30));

        btnPanel.add(txtName);

        txtName.setPreferredSize(new Dimension(200, 30));

        btnPanel.add(cmbGroup);

        cmbGroup.setPreferredSize(new Dimension(100, 30));

        cmbGroup.addItem("请选择小组");



        btnPanel.add(btnEdit);

        btnPanel.add(btnDelete);

        this.add(btnPanel, BorderLayout.SOUTH);



        studentTable.getSelectionModel().addListSelectionListener(e -> {

            int selectedRow = studentTable.getSelectedRow();

            if (selectedRow >= 0) {

                // TODO 准备修改学生

                txtId.setText(data[selectedRow][0]);

                txtName.setText(data[selectedRow][1]);

                cmbGroup.setSelectedItem(data[selectedRow][2]);

            }

        });



        btnEdit.addActionListener(e -> {

            int selectedRow = studentTable.getSelectedRow();

            if (selectedRow < 0) {

                JOptionPane.showMessageDialog(this, "请先选择学生", "", JOptionPane.INFORMATION_MESSAGE);

                return;

            }

            if (txtId.getText() == null || txtId.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this, "请填写学号", "", JOptionPane.INFORMATION_MESSAGE);

                return;

            }

            if (txtName.getText() == null || txtName.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this, "请填写姓名", "", JOptionPane.INFORMATION_MESSAGE);

                return;

            }



            String oldId = data[selectedRow][0];

            String newId = txtId.getText();

            String newName = txtName.getText();

            String newGroupName = (String) cmbGroup.getSelectedItem();



            // 查找旧的学生和小组

            Student studentToUpdate = null;

            Group oldGroup = null;

            Group newGroup = null;



            // 查找旧的小组和学生

            for (Group group : Constant.groups.keySet()) {

                List<Student> students = Constant.groups.get(group);

                for (Student student : students) {

                    if (student.getId().equals(oldId)) {

                        studentToUpdate = student;

                        oldGroup = group;

                        break;

                    }

                }

                if (group.getGroupName().equals(newGroupName)) {

                    newGroup = group;

                }

            }



            if (studentToUpdate == null || oldGroup == null || newGroup == null) {

                JOptionPane.showMessageDialog(this, "找不到相关小组或学生信息", "", JOptionPane.INFORMATION_MESSAGE);

                return;

            }



            // 从旧小组移除学生

            Constant.groups.get(oldGroup).remove(studentToUpdate);



            // 更新学生信息

            studentToUpdate.setId(newId);

            studentToUpdate.setName(newName);

            studentToUpdate.setGroup(newGroup);



            // 添加到新小组

            Constant.groups.get(newGroup).add(studentToUpdate);



            try {

                // 更新学生文件

                File studentFile = new File(Constant.FILE_PATH + Constant.CLASS_PATH + "/students.txt");

                BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile));

                for (Group group : Constant.groups.keySet()) {

                    for (Student student : Constant.groups.get(group)) {

                        writer.write(student.getId() + "," + student.getName() + "," +

                                student.getGroup().getGroupName() + "\n");

                    }

                }

                writer.close();



                // 更新表格显示

                data[selectedRow][0] = newId;

                data[selectedRow][1] = newName;

                data[selectedRow][2] = newGroupName;  // 更新小组列

                studentTable.updateUI();



                // 清空输入框

                txtId.setText("");

                txtName.setText("");

                cmbGroup.setSelectedIndex(0);



                JOptionPane.showMessageDialog(this, "修改成功", "", JOptionPane.INFORMATION_MESSAGE);

                MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);

                mainFrame.refreshStudentList();

                txtId.setText("");

                txtName.setText("");

            } catch (IOException ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(this, "修改失败", "", JOptionPane.INFORMATION_MESSAGE);

            }

        });

        btnDelete.addActionListener(e -> {

            int selectedRow = studentTable.getSelectedRow();

            if (selectedRow < 0) {

                JOptionPane.showMessageDialog(this, "请先选择学生", "", JOptionPane.INFORMATION_MESSAGE);

                return;

            }

            if (JOptionPane.showConfirmDialog(this, "删除学生会删除他的考勤、成绩等，确认删除？", "", JOptionPane.YES_NO_OPTION) != 0) {

                return;

            }



            String studentId = data[selectedRow][0];

            Group studentGroup = null;

            Student studentToRemove = null;



            // 查找学生所在的小组

            for (Group group : Constant.groups.keySet()) {

                List<Student> students = Constant.groups.get(group);

                for (Student student : students) {

                    if (student.getId().equals(studentId)) {

                        studentGroup = group;

                        studentToRemove = student;

                        break;

                    }

                }

                if (studentGroup != null) {

                    break;

                }

            }



            if (studentGroup != null && studentToRemove != null) {

                // 从小组中移除学生

                Constant.groups.get(studentGroup).remove(studentToRemove);



                // 更新文件

                try {

                    File studentFile = new File(Constant.FILE_PATH + Constant.CLASS_PATH + "/students.txt");

                    BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile));

                    for (Group group : Constant.groups.keySet()) {

                        for (Student student : Constant.groups.get(group)) {

                            writer.write(student.getId() + "," + student.getName() + "," +

                                    student.getGroup().getGroupName() + "\n");

                        }

                    }

                    writer.close();



                    // 更新表格

                    DefaultTableModel model = (DefaultTableModel) studentTable.getModel();

                    model.removeRow(selectedRow);



                    JOptionPane.showMessageDialog(this, "删除学生成功", "", JOptionPane.INFORMATION_MESSAGE);

                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);

                    mainFrame.refreshStudentList();

                    txtId.setText("");

                    txtName.setText("");

                } catch (IOException ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(this, "删除失败", "", JOptionPane.INFORMATION_MESSAGE);

                }

            }

        });

    }



    private void updateTotalStudents() {

        Window window = SwingUtilities.getWindowAncestor(this);

        if (window instanceof JFrame) {

            for (Component comp : ((JFrame) window).getContentPane().getComponents()) {

                if (comp instanceof ChangeClassPanel) {

                    ChangeClassPanel changeClassPanel = (ChangeClassPanel) comp;

                    changeClassPanel.infoLbl.setText("班级：" + Constant.CLASS_PATH +

                            "，班级学生总数：" + totalStudents);

                    changeClassPanel.repaint();

                    break;

                }

            }

        }

    }

}