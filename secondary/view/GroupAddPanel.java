package com.student.view;

import com.student.entity.Group;
import com.student.util.Constant;
import java.awt.LayoutManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GroupAddPanel extends JPanel {
    public GroupAddPanel() {
        this.setLayout((LayoutManager)null);
        this.setBorder(new TitledBorder(new EtchedBorder(), "新增小组"));
        JLabel lblName = new JLabel("小组名称：");
        JTextField txtName = new JTextField();
        JButton btnName = new JButton("确认");
        this.add(lblName);
        this.add(txtName);
        this.add(btnName);
        lblName.setBounds(200, 80, 100, 30);
        txtName.setBounds(200, 130, 200, 30);
        btnName.setBounds(200, 180, 100, 30);
        btnName.addActionListener((e) -> {
            if (txtName.getText() != null && !txtName.getText().isEmpty()) {
                Iterator var3 = Constant.groups.keySet().iterator();

                while(var3.hasNext()) {
                    Group group = (Group)var3.next();
                    if (group.getGroupName().equals(txtName.getText())) {
                        JOptionPane.showMessageDialog(this, "小组已存在", "", 1);
                        return;
                    }
                }

                Group newGroup = new Group(txtName.getText());
                Constant.groups.put(newGroup, new ArrayList());
                File groupFile = new File("C:\\Users\\lenovo\\Desktop\\java\\secondary\\entity" + Constant.CLASS_PATH + "/groups.txt");

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(groupFile, true));

                    try {
                        writer.write(txtName.getText() + ",0\n");
                    } catch (Throwable var9) {
                        try {
                            writer.close();
                        } catch (Throwable var8) {
                            var9.addSuppressed(var8);
                        }

                        throw var9;
                    }

                    writer.close();
                } catch (IOException var10) {
                    var10.printStackTrace();
                    JOptionPane.showMessageDialog(this, "新增小组成功", "", 1);
                    return;
                }

                JOptionPane.showMessageDialog(this, "新增小组成功", "", 1);
            } else {
                JOptionPane.showMessageDialog(this, "请填写小组名称", "", 1);
            }

        });
    }
}
