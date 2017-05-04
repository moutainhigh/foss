package com.deppon.foss.module.pickup.common.client.ui.commonUI;


import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.deppon.foss.base.util.define.NumberConstants;

public class TableDoubleClickTest extends JFrame {

        private static final long serialVersionUID = 1L;

		private static final int TEN = 10;

        private JTable table;

        private TableModel model;

        private String[][] data;

        public static void main(String[] args) {
                TableDoubleClickTest test = new TableDoubleClickTest();
                test.setVisible(true);
        }

        public TableDoubleClickTest() throws HeadlessException {
                super("DoubleClickTest");

                data = new String[TEN][TEN];
                for (int i = 0; i < data.length; i++) {
                        for (int j = 0; j < data[i].length; j++) {
                                data[i][j] = "Col:" + j + " Row:" + i;
                        }
                }
                model = new DefaultTableModel(data, data[0]) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                };

                table = new JTable(model);
                table.setColumnSelectionAllowed(false);
                table.setRowSelectionAllowed(true);

                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                table.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                                if (evt.getClickCount() == 2) {
                                        setTitle((String) model.getValueAt(table.getSelectedRow(),
                                                        table.getSelectedColumn()));
                                }
                        }
                });
                table.setSize(NumberConstants.NUMBER_800, NumberConstants.NUMBER_600);
                
                setLayout(new BorderLayout());
                getContentPane().add(table, BorderLayout.CENTER);
                setSize(NumberConstants.NUMBER_800, NumberConstants.NUMBER_600);
                pack();
                setLocation(NumberConstants.NUMBER_200, NumberConstants.NUMBER_200);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
}
