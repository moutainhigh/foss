/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/mergeTable/CombineTableUI.java
 * 
 * FILE NAME        	: CombineTableUI.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.ui.mergeTable;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
  
/**
 * 重画UI
 * @author 026123-foss-lifengteng
 * @date 2012-10-22 上午8:48:54
 */
public class CombineTableUI extends BasicTableUI { 
  
    /** 
     * 画面板
     * @author 026123-foss-lifengteng
     * @date 2012-10-22 上午8:49:41
     * @see javax.swing.plaf.basic.BasicTableUI#paint(java.awt.Graphics, javax.swing.JComponent)
     */
    @Override
    public void paint(Graphics g, JComponent c) { 
        Rectangle r = g.getClipBounds(); 
        rendererPane.removeAll(); 
  
        int firstCol = table.columnAtPoint(new Point(r.x, 0)); 
        int lastCol = table.columnAtPoint(new Point(r.x + r.width, 0)); 
        if (lastCol < 0) { 
            lastCol = table.getColumnCount() - 1; 
        } 
        for (int i = firstCol; i <= lastCol; i++) { 
            paintCol(i, g); 
        } 
  
        paintGrid(g, 0, table.getRowCount() - 1, 0, table.getColumnCount() - 1); 
    } 
  
    /**
     * 画列
     * @author 026123-foss-lifengteng
     * @date 2012-10-22 上午8:50:16
     */
    private void paintCol(int col, Graphics g) { 
        Rectangle r = g.getClipBounds(); 
        for (int i = 0; i < table.getRowCount(); i++) { 
            Rectangle r1 = table.getCellRect(i, col, true); 
            if (r1.intersects(r)) // at least a part is visible 
            { 
                int sk = ((CombineTable) table).combineData.visibleCell(i, col); 
                paintCell(sk, col, g, r1); 
                i += ((CombineTable) table).combineData.span(sk, col) - 1; 
            } 
        } 
    } 
  
    /**
     * 画单元格
     * @author 026123-foss-lifengteng
     * @date 2012-10-22 上午8:50:29
     */
    private void paintCell(int row, int column, Graphics g, Rectangle area) { 
        int verticalMargin = table.getRowMargin(); 
        int horizontalMargin = table.getColumnModel().getColumnMargin(); 
  
        area.setBounds(area.x + horizontalMargin / 2, area.y + verticalMargin / 2, area.width - horizontalMargin, area.height - verticalMargin); 
  
        if (table.isEditing() && table.getEditingRow() == row && table.getEditingColumn() == column) { 
            Component component = table.getEditorComponent(); 
            component.setBounds(area); 
            component.validate(); 
        } else { 
            TableCellRenderer renderer = table.getCellRenderer(row, column); 
            Component component = table.prepareRenderer(renderer, row, column); 
            if (component.getParent() == null) { 
                rendererPane.add(component); 
            } 
            rendererPane.paintComponent(g, component, table, area.x, area.y, 
                    area.width, area.height, true); 
        } 
    } 
  
    /**
     * 画格子
     * @author 026123-foss-lifengteng
     * @date 2012-10-22 上午8:51:01
     */
    private void paintGrid(Graphics g, int rMin, int rMax, int cMin, int cMax) { 
        g.setColor(table.getGridColor()); 
  
        Rectangle minCell = table.getCellRect(rMin, cMin, true); 
        Rectangle maxCell = table.getCellRect(rMax, cMax, true); 
        Rectangle damagedArea = minCell.union(maxCell); 
  
        if (table.getShowHorizontalLines()) { 
            CombineData cMap = ((CombineTable) table).combineData; 
            for (int row = rMin; row <= rMax; row++) { 
                for (int column = cMin; column <= cMax; column++) { 
                    Rectangle cellRect = table.getCellRect(row, column, true); 
  
                    if (cMap.combineColumns.contains(column)) { 
                        int visibleCell = cMap.visibleCell(row, column); 
                        int span = cMap.span(row, column); 
                        if (!(span > 1 && row < visibleCell + span - 1)) { 
                            g.drawLine(cellRect.x, cellRect.y + cellRect.height - 1, cellRect.x + cellRect.width - 1, cellRect.y + cellRect.height - 1); 
                        } 
                    } else { 
                        g.drawLine(cellRect.x, cellRect.y + cellRect.height - 1, cellRect.x + cellRect.width - 1, cellRect.y + cellRect.height - 1); 
                    } 
                } 
            } 
        } 
        if (table.getShowVerticalLines()) { 
            TableColumnModel cm = table.getColumnModel(); 
            int tableHeight = damagedArea.y + damagedArea.height; 
            int x; 
            if (table.getComponentOrientation().isLeftToRight()) { 
                x = damagedArea.x; 
                for (int column = cMin; column <= cMax; column++) { 
                    int w = cm.getColumn(column).getWidth(); 
                    x += w; 
                    g.drawLine(x - 1, 0, x - 1, tableHeight - 1); 
                } 
            } else { 
                x = damagedArea.x; 
                for (int column = cMax; column >= cMin; column--) { 
                    int w = cm.getColumn(column).getWidth(); 
                    x += w; 
                    g.drawLine(x - 1, 0, x - 1, tableHeight - 1); 
                } 
            } 
        } 
    } 
}