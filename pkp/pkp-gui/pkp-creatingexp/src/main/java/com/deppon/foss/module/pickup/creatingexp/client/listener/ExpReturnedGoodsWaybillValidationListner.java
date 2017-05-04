package com.deppon.foss.module.pickup.creatingexp.client.listener;

import java.awt.Component;

import com.deppon.foss.framework.client.commons.validation.IValidationListener;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.commons.validation.ValidationErrorEvent;
import com.deppon.foss.framework.client.core.binding.BindingAssociation;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.IBallValidateWidget;

/**
 * 返货管理监听
 */
public class ExpReturnedGoodsWaybillValidationListner implements IValidationListener  {
	
	/**
	 * 功能：validationError 监听到错误后，弹出信息框
	 * 
	 * @param:ValidationErrorEvent
	 * @return:void
	 * @since:1.6
	 */
	@Override
	public void validationError(ValidationErrorEvent e) {
		for (ValidationError error : e.getErrors()) {

			final Component jComponent = ((BindingAssociation) error.getKey())
					.getComponent();
			if (jComponent instanceof IBallValidateWidget) {
				IBallValidateWidget field = (IBallValidateWidget) jComponent;
				field.verifyWrong(error.getMessage());

			} else {
				MsgBox.showError(error.getMessage());
			}
		}
		
	}

}