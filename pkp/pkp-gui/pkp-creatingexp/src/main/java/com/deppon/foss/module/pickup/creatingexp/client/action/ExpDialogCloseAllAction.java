/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpDialogCloseAllAction  extends
AbstractButtonActionListener<JDialog> {

JDialog dialog;

/**
* 
* 关闭dialog
* @author 025000-FOSS-helong
* @date 2013-1-15 上午10:00:38
* @param e
*/
public void actionPerformed(ActionEvent e) {
dialog.dispose();
}

@Override
public void setIInjectUI(JDialog dialog) {

this.dialog = dialog;

}
}
