package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.customer.EnterPackingInfoDialog;
import com.deppon.foss.module.pickup.creating.client.vo.PackingYokeVo;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * 录入打包装信息至VO
 * @author 218371-foss-zhaoyanjun
 *
 */
public class PackingYokeEnterAction extends AbstractButtonActionListener<EnterPackingInfoDialog>{
	/**
	 * 国际化
	 */
	private II18n i18n=I18nManager.getI18n(PackingYokeEnterAction.class);
	//打包装录入界面
	private EnterPackingInfoDialog yoke;
	
	/**
	 * 录入打包装信息
	 */
	public void actionPerformed(ActionEvent e){
		try {
			//重新计算运费
			yoke.sum();
			//获得运单
			WaybillEditUI waybillEditUI=yoke.getWaybillEditUI();
			//根据运单对象获得绑定的对象Map
			HashMap<String,IBinder<WaybillPanelVo>> map=waybillEditUI.getBindersMap();
			//获取绑定对象
			IBinder<WaybillPanelVo> waybillBinder=map.get("waybillBinder");
			//获取与UI绑定的waybillPanelVo对象
			WaybillPanelVo bean=waybillBinder.getBean();
			//货物体积
			BigDecimal volume =bean.getGoodsVolumeTotal()!=null?bean.getGoodsVolumeTotal():BigDecimal.ZERO;
			//缓冲物费用不能大于货物体积*20
			BigDecimal bufferPrice = new BigDecimal(StringUtils.isEmpty(yoke.getBufferLabel().getText())?"0":yoke.getBufferLabel().getText());
			//BigDecimal bufferPrice = bean.getBufferPrice()!=null?bean.getBufferPrice():BigDecimal.ZERO;
			if(bufferPrice.compareTo(volume.multiply(new BigDecimal("20")))==1){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.enterPackingInfoDialog.bufferPrice"));
			}
			//录入打包装信息
			packingYokeEnter(bean);
			//关闭窗口
			yoke.dispose();
		} catch(WaybillValidateException w)
		{
			MsgBox.showInfo(w.getMessage());
		}
	}
	
	private void packingYokeEnter(WaybillPanelVo bean) {
		//数据集合
		int[] date=new int[NumberConstants.NUMBER_14];
		//获取纸箱1号客户数量
		int paperBoxOne=Common.nullOrEmptyToInt(yoke.getPaperBoxOneTextField().getText().trim());
		date[0]=paperBoxOne;
		//获取纸箱2号客户数量
		int paperBoxTwo=Common.nullOrEmptyToInt(yoke.getPaperBoxTwoTextField().getText().trim());
		date[1]=paperBoxTwo;
		//获取纸箱3号客户数量
		int paperBoxThree=Common.nullOrEmptyToInt(yoke.getPaperBoxThreeTextField().getText().trim());
		date[2]=paperBoxThree;
		//获取纸箱4号客户数量
		int paperBoxFour=Common.nullOrEmptyToInt(yoke.getPaperBoxFourTextField().getText().trim());
		date[NumberConstants.NUMBER_3]=paperBoxFour;
		//获取纤袋1号蓝数量
		int fibelBagBlueOne=Common.nullOrEmptyToInt(yoke.getFibelBagOneBlueTextField().getText().trim());
		date[NumberConstants.NUMBER_4]=fibelBagBlueOne;
		//获取纤袋2号蓝数量
		int fibelBagBlueTwo=Common.nullOrEmptyToInt(yoke.getFibelBagTwoBlueTextField().getText().trim());
		date[NumberConstants.NUMBER_5]=fibelBagBlueTwo;
		//获取纤袋3号蓝数量
		int fibelBagBlueThree=Common.nullOrEmptyToInt(yoke.getFibelBagThreeBlueTextField().getText().trim());
		date[NumberConstants.NUMBER_6]=fibelBagBlueThree;
		//获取纤袋4号蓝数量
		int fibelBagBlueFour=Common.nullOrEmptyToInt(yoke.getFibelBagFourBlueTextField().getText().trim());
		date[NumberConstants.NUMBER_7]=fibelBagBlueFour;
		//获取纤袋1号白数量
		int fibelBagWhiteOne=Common.nullOrEmptyToInt(yoke.getFibelBagOneWhiteTextField().getText().trim());
		date[NumberConstants.NUMBER_8]=fibelBagWhiteOne;
		//获取纤袋2号白数量
		int fibelBagWhiteTwo=Common.nullOrEmptyToInt(yoke.getFibelBagTwoWhiteTextField().getText().trim());
		date[NumberConstants.NUMBER_9]=fibelBagWhiteTwo;
		//获取纤袋3号白数量
		int fibelBagWhiteThree=Common.nullOrEmptyToInt(yoke.getFibelBagThreeWhiteTextField().getText().trim());
		date[NumberConstants.NUMBER_10]=fibelBagWhiteThree;
		//获取纤袋4号白数量
		int fibelBagWhiteFour=Common.nullOrEmptyToInt(yoke.getFibelBagFourWhiteTextField().getText().trim());
		date[NumberConstants.NUMBER_11]=fibelBagWhiteFour;
		//获取纤袋5号白数量
		int fibelBagWhiteClothFive=Common.nullOrEmptyToInt(yoke.getFibelBagFiveWhiteTextField().getText().trim());
		date[NumberConstants.NUMBER_12]=fibelBagWhiteClothFive;
		//获取纤袋6号白数量
		int fibelBagWhiteClothSix=Common.nullOrEmptyToInt(yoke.getFibelBagSixWhiteTextField().getText().trim());
		date[NumberConstants.NUMBER_13]=fibelBagWhiteClothSix;
		
		//设置WaybillPanelVo的参数
		setWaybillPanelVo(bean,date);
		
		//将费用存入bean中
		setWaybillPanelVo(bean);
	}
	
	/**
	 * 将数据存入bean,指的是价格
	 * 依次为：纸箱总价，纤袋总价，合计总价
	 */
	private void setWaybillPanelVo(WaybillPanelVo bean) {
		PackingYokeVo vo=yoke.getVo();
		bean.setPaperBoxTotlePrice(vo.getPaperBoxTotlePrice());
		bean.setFibelBagTotlePrice(vo.getFibelBagTotlePrice());
		bean.setBufferPrice(vo.getBuffer());
		bean.setDiscountRate(vo.getDiscount());
//		bean.setOtherTotle(vo.getOther());
//		bean.setPackingTotle(vo.getTotle());
	}

	/**
	 * 将数据存入bean,指的是数量
	 * @param bean
	 * @param date
	 */
	private void setWaybillPanelVo(WaybillPanelVo bean,int[] date) {
		bean.setPaperBoxOne(date[0]);
		bean.setPaperBoxTwo(date[1]);
		bean.setPaperBoxThree(date[2]);
		bean.setPaperBoxFour(date[NumberConstants.NUMBER_3]);
		bean.setFibelBagBlueOne(date[NumberConstants.NUMBER_4]);
		bean.setFibelBagBlueTwo(date[NumberConstants.NUMBER_5]);
		bean.setFibelBagBlueThree(date[NumberConstants.NUMBER_6]);
		bean.setFibelBagBlueFour(date[NumberConstants.NUMBER_7]);
		bean.setFibelBagWhiteOne(date[NumberConstants.NUMBER_8]);
		bean.setFibelBagWhiteTwo(date[NumberConstants.NUMBER_9]);
		bean.setFibelBagWhiteThree(date[NumberConstants.NUMBER_10]);
		bean.setFibelBagWhiteFour(date[NumberConstants.NUMBER_11]);
		bean.setFibelBagWhiteClothFive(date[NumberConstants.NUMBER_12]);
		bean.setFibelBagWhiteClothSix(date[NumberConstants.NUMBER_13]);
	}

	@Override
	public void setIInjectUI(EnterPackingInfoDialog yoke) {
		// TODO Auto-generated method stub
		this.yoke=yoke;
	}
	
	
}
