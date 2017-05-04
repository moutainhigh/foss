/**   
* @Title: CopyContent.java 
* @Package com.deppon.foss.module.pickup.creatingexp.client.common 
* @Description:  TODOtable封装复制内容
* @author 270293   
* @date 2015-7-17 下午6:48:27 
* @version V1.0   
*/
package com.deppon.foss.module.pickup.creatingexp.client.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;


/** 
 * @ClassName: CopyContent 
 * @Description: TODOtable封装复制内容
 * @author 270293-foss-zhangfeng 
 * @date 2015-7-17 下午6:48:27 
 *  
 */
public class CopyContent {

	private static final int NUM_255 = 255;
	
	private static final int NUM_206 = 206;

	private static final int NUM_231 = 231;
	
	public CopyContent(JXTable table, String i18n) {
		initPopupMenu(table,i18n);
	}

	/**
	 * (非 Javadoc) 
		* @author 270293-foss-zhangfeng
		* @date 2015-7-16 下午3:22:10 
	    * <p>Title: initPopupMenu</p> 
	    * <p>Description:初始化鼠标右击弹出复制菜单</p> 
	    * @param @param table
	    * @return void
	 */
	public void initPopupMenu(JXTable table,String i18n){
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem copyItem = new JMenuItem(i18n);
		copyItem.setFont(new Font("alias", Font.PLAIN, NumberConstants.NUMBER_12));
		popupMenu.add(copyItem);
		mouseListener(copyItem);
		copyCotentListener(table,copyItem);
		showPopupMenu(table,popupMenu);
	}
	
	/**
	 * (非 Javadoc) 
		* @author 270293-foss-zhangfeng
		* @date 2015-7-16 下午3:29:17 
	    * <p>Title: showPopupMenu</p> 
	    * <p>Description:显示鼠标菜单</p> 
	    * @param @param table
	    * @return void
	 */
	public  void showPopupMenu(final JXTable table,final JPopupMenu popupMenu ) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				int r = table.getSelectedRow();
				int c = table.getSelectedColumn();
				if (evt.getButton() == MouseEvent.BUTTON3) {
					if (r != -1 && c != -1) {
						int focusedRowIndex = table.rowAtPoint(evt.getPoint());
						if (focusedRowIndex == -1) {
							return;
						}
						// 将表格所选项设为当前右键点击的行
						table.setRowSelectionInterval(focusedRowIndex,focusedRowIndex);
						popupMenu.show(evt.getComponent(), evt.getX(),evt.getY());
					}
				}
			}
		});
	}
	/**
	 * (非 Javadoc) 
		* @author 270293-foss-zhangfeng
		* @date 2015-7-16 下午3:23:00 
	    * <p>Title: copyCotentListener</p> 
	    * <p>Description: ctr+c快捷键、弹出菜单复制内容</p> 
	    * @param @param table
	    * @param @param copyItem
	    * @return void
	 */
	public  void copyCotentListener(final JXTable table,JMenuItem copyItem ){
		//ctr+c
		table.getActionMap().put("copy", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				copyCotent(table);
			}
		});
		
		// 菜单复制
		copyItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				copyCotent(table);
			}
		});
	}
	
	/**
	 * (非 Javadoc) 
		* @author 270293-foss-zhangfeng
		* @date 2015-7-16 下午3:22:43 
	    * <p>Title: copyCotent</p> 
	    * <p>Description:复制table单元格内容</p> 
	    * @param @param table
	    * @return void
	 */
	private  void copyCotent(JXTable table) {
		int column = table.getSelectedColumn();
		int row = table.getSelectedRow();
		if (column != -1 && row != -1) {
			Object value = table.getValueAt(row, column);
			StringSelection stringSelection = new StringSelection(value+ "");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
	}
	
	/**
	 * (非 Javadoc) 
		* @author 270293-foss-zhangfeng
		* @date 2015-7-17 下午7:35:36 
	    * <p>Title: fucntion</p> 
	    * <p>Description:渲染table</p> 
	    * @param @param table
	    * @return void
	 */

	public static void makeFace(JTable table) {
		   try {
		    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
		     public Component getTableCellRendererComponent(JTable table,
		       Object value, boolean isSelected, boolean hasFocus,
		       int row, int column) {
		      if (row % 2 == 0)
		    	  table.setBackground(new Color(NUM_206, NUM_231, NUM_255)); // 设置奇数行底色
		      else if (row % 2 == 1)
		    	  table.setBackground(Color.white); // 设置偶数行底色
		      return super.getTableCellRendererComponent(table, value,
		        isSelected, hasFocus, row, column);
		     }
		    };
		   } catch (Exception ex) {
		    ex.printStackTrace();
		   }
		}
	/**
	 * (非 Javadoc) 
		* @author 270293-foss-zhangfeng
		* @date 2015-7-22 下午3:28:29 
	    * <p>Title: mouseListener</p> 
	    * <p>Description:鼠标经过事件:扯淡不起左右</p> 
	    * @param @param copyItem
	    * @return void
	 */
	
	 public void mouseListener(final JMenuItem copyItem){
		 copyItem.addMouseListener(new MouseAdapter() {
			 /** (非 Javadoc) 
			* @author 270293-pkpfoss-zhangfeng 
			* @date 2015-7-22 下午3:26:52 
			* <p>Title: mouseEntered</p> 
			* <p>Description: </p> 
			* @param e 
			* @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent) 
			*/
			@Override
			public void mouseEntered(MouseEvent e) {
				copyItem.setBackground(Color.pink);	
				copyItem.setFont(new Font("alias", Font.PLAIN, NumberConstants.NUMBER_13));
				copyItem.setForeground(Color.black);
			}
		});
	 }
		
}
