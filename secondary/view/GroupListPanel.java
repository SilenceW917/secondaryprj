package com.student.view;

import com.student.entity.Group;

import com.student.entity.Student;
import com.student.util.Constant;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class GroupListPanel extends JPanel {
    String[] headers = new String[]{"序号", "小组名称", "分数"};
    String[][] data = null;
    JTable classTable;
    JTextField txtName = new JTextField();
    JTextField txtScore = new JTextField();
    JButton btnEdit = new JButton("修改");
    JButton btnDelete = new JButton("删除");

    public GroupListPanel() {
        this.setBorder(new TitledBorder(new EtchedBorder(), "小组列表"));
        this.setLayout(new BorderLayout());
        int i = 0;
        this.data = new String[Constant.groups.size()][3];

        for(Iterator var2 = Constant.groups.keySet().iterator(); var2.hasNext(); ++i) {
            Group group = (Group)var2.next();
            this.data[i][0] = String.valueOf(i + 1);
            this.data[i][1] = group.getGroupName();
            this.data[i][2] = String.valueOf(group.getScore());
        }

        DefaultTableModel tableModel = new DefaultTableModel(this.data, this.headers);
        this.classTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.classTable.getSelectionModel().setSelectionMode(0);
        JScrollPane scrollPane = new JScrollPane(this.classTable);
        this.add(scrollPane, "Center");
        JPanel btnPanel = new JPanel();
        btnPanel.add(this.txtName);
        this.txtName.setPreferredSize(new Dimension(200, 30));
        btnPanel.add(this.txtScore);
        this.txtScore.setPreferredSize(new Dimension(100, 30));
        btnPanel.add(this.btnEdit);
        btnPanel.add(this.btnDelete);
        this.add(btnPanel, "South");
        this.classTable.getSelectionModel().addListSelectionListener((e) -> {
            int selectedRow = this.classTable.getSelectedRow();
            if (selectedRow >= 0) {
                this.txtName.setText(this.data[selectedRow][1]);
                this.txtScore.setText(this.data[selectedRow][2]);
            }

        });
        this.btnEdit.addActionListener((e) -> {
            int selectedRow = this.classTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "请先选择小组", "", 1);
            } else if (this.txtName.getText() != null && !this.txtName.getText().isEmpty()) {
                if (this.txtScore.getText() != null && !this.txtScore.getText().isEmpty()) {
                    String oldName = this.data[selectedRow][1];
                    String newName = this.txtName.getText();

                    int score;
                    try {
                        score = Integer.parseInt(this.txtScore.getText());
                    } catch (NumberFormatException var14) {
                        JOptionPane.showMessageDialog(this, "分数必须是数字", "", 1);
                        return;
                    }

                    Group targetGroup = null;
                    Iterator var7 = Constant.groups.keySet().iterator();

                    while(var7.hasNext()) {
                        Group groupx = (Group)var7.next();
                        if (groupx.getGroupName().equals(oldName)) {
                            targetGroup = groupx;
                            groupx.setGroupName(newName);
                            groupx.setScore(score);
                            break;
                        }
                    }

                    if (targetGroup != null) {
                        try {
                            File groupFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/groups.txt");
                            BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile));
                            Iterator var9 = Constant.groups.keySet().iterator();

                            String var10001;
                            while(var9.hasNext()) {
                                Group groupxx = (Group)var9.next();
                                var10001 = groupxx.getGroupName();
                                writer.write(var10001 + "," + groupxx.getScore() + "\n");
                            }

                            writer.close();
                            File studentFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/students.txt");
                            writer = new BufferedWriter(new FileWriter(studentFile));
                            Iterator var19 = Constant.groups.keySet().iterator();

                            while(var19.hasNext()) {
                                Group group = (Group)var19.next();
                                Iterator var12 = ((List)Constant.groups.get(group)).iterator();

                                while(var12.hasNext()) {
                                    Student student = (Student)var12.next();
                                    var10001 = student.getId();
                                    writer.write(var10001 + "," + student.getName() + "," + student.getGroup().getGroupName() + "\n");
                                }
                            }

                            writer.close();
                            this.data[selectedRow][1] = newName;
                            this.data[selectedRow][2] = String.valueOf(score);
                            this.classTable.updateUI();
                            JOptionPane.showMessageDialog(this, "修改成功", "", 1);
                            MainFrame mainFrame = (MainFrame)SwingUtilities.getWindowAncestor(this);
                            mainFrame.refreshGroupList();
                            this.txtName.setText("");
                            this.txtScore.setText("");
                        } catch (IOException var15) {
                            var15.printStackTrace();
                            JOptionPane.showMessageDialog(this, "修改失败", "", 1);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "请填写分数", "", 1);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请填写小组名称", "", 1);
            }
        });
        this.btnDelete.addActionListener((e) -> {
            int selectedRow = this.classTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "请先选择小组", "", 1);
            } else {
                String groupName = this.data[selectedRow][1];
                Group targetGroup = null;
                Iterator var5 = Constant.groups.keySet().iterator();

                while(var5.hasNext()) {
                    Group group = (Group)var5.next();
                    if (group.getGroupName().equals(groupName)) {
                        targetGroup = group;
                        if (!((List)Constant.groups.get(group)).isEmpty()) {
                            JOptionPane.showMessageDialog(this, "该小组还有学生，不能删除", "", 1);
                            return;
                        }
                        break;
                    }
                }

                if (targetGroup != null) {
                    Constant.groups.remove(targetGroup);

                    try {
                        File groupFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/groups.txt");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile));
                        Iterator var7 = Constant.groups.keySet().iterator();

                        String var10001;
                        while(var7.hasNext()) {
                            Group groupx = (Group)var7.next();
                            var10001 = groupx.getGroupName();
                            writer.write(var10001 + "," + groupx.getScore() + "\n");
                        }

                        writer.close();
                        File studentFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/students.txt");
                        writer = new BufferedWriter(new FileWriter(studentFile));
                        Iterator var16 = Constant.groups.keySet().iterator();

                        while(var16.hasNext()) {
                            Group groupxx = (Group)var16.next();
                            Iterator var10 = ((List)Constant.groups.get(groupxx)).iterator();

                            while(var10.hasNext()) {
                                Student student = (Student)var10.next();
                                var10001 = student.getId();
                                writer.write(var10001 + "," + student.getName() + "," + student.getGroup().getGroupName() + "\n");
                            }
                        }

                        writer.close();
                        DefaultTableModel model = (DefaultTableModel)this.classTable.getModel();
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "删除小组成功", "", 1);
                        MainFrame mainFrame = (MainFrame)SwingUtilities.getWindowAncestor(this);
                        mainFrame.refreshGroupList();
                        this.txtName.setText("");
                        this.txtScore.setText("");
                    } catch (IOException var12) {
                        var12.printStackTrace();
                        JOptionPane.showMessageDialog(this, "删除失败", "", 1);
                    }
                }

            }
        });
    }
}
