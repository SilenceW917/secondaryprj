package com.student.view;

import com.student.util.Constant;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class ClassListPanel extends JPanel {
    String[] headers = {"序号", "班级名称"};
    JTable classTable;
    JTextField txtName = new JTextField();
    JButton btnEdit = new JButton("修改");
    JButton btnDelete = new JButton("删除");

    public ClassListPanel() {
        this.setBorder(new TitledBorder(new EtchedBorder(), "班级列表"));
        this.setLayout(new BorderLayout());
        // TODO 列举班级
        File[] classes = new File[1];
        classes[0] = new File(Constant.FILE_PATH);

        String[][] data = new String[classes.length][2];
        for (int i = 0; i < classes.length; i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = classes[i].getName();
        }
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
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        this.add(btnPanel, BorderLayout.SOUTH);

        classTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = classTable.getSelectedRow();
            if(selectedRow >= 0) {
                txtName.setText(data[selectedRow][1]);
            }
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = classTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "请先选择班级", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (txtName.getText() == null || txtName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写班级名称", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // TODO 修改班级
            JOptionPane.showMessageDialog(this, "修改成功", "", JOptionPane.INFORMATION_MESSAGE);
        });
        btnDelete.addActionListener(e -> {
            int selectedRow = classTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "请先选择班级", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this, "删除班级会把小组、学生和成绩都删除，您确定要删除这个班级？", "", JOptionPane.YES_NO_OPTION) != 0){
                return;
            }
            // TODO 删除班级
            JOptionPane.showMessageDialog(this, "删除班级成功", "", JOptionPane.INFORMATION_MESSAGE);

        });
    }
}
