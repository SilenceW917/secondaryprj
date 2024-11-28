package com.student.view;

import com.student.entity.Group;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GroupListPanel extends JPanel {
    String[] headers = {"序号", "小组名称", "分数"};
    String[][] data = null;
    JTable classTable;
    JTextField txtName = new JTextField();
    JTextField txtScore = new JTextField();
    JButton btnEdit = new JButton("修改");
    JButton btnDelete = new JButton("删除");

    public GroupListPanel() {
        this.setBorder(new TitledBorder(new EtchedBorder(), "小组列表"));
        this.setLayout(new BorderLayout());
        // TODO 列举小组

        data = new String[1][3];

        DefaultTableModel tableModel = new DefaultTableModel(data, headers);
        classTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        classTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(classTable);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.add(txtName);
        txtName.setPreferredSize(new Dimension(200, 30));
        btnPanel.add(txtScore);
        txtScore.setPreferredSize(new Dimension(100, 30));
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        this.add(btnPanel, BorderLayout.SOUTH);

        classTable.getSelectionModel().addListSelectionListener(e -> {
            // TODO 获取选中小组
        });

        btnEdit.addActionListener(e -> {
            // TODO 修改小组
            JOptionPane.showMessageDialog(this, "修改成功", "", JOptionPane.INFORMATION_MESSAGE);

        });
        btnDelete.addActionListener(e -> {
            // TODO 删除小组
            JOptionPane.showMessageDialog(this, "删除小组成功", "", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
