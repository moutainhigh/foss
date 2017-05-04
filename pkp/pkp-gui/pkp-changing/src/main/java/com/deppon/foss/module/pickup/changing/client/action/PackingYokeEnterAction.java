package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.EnterPackingInfoDialog;
import com.deppon.foss.module.pickup.changing.client.utils.Common;
import com.deppon.foss.module.pickup.changing.client.vo.PackingYokeVo;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
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
	
	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;

	private static final int EIGHT = 8;

	private static final int NINE = 9;
	
	private static final int FOURTEEN = 14;
	
	private static final int TEN = 10;
	
	private static final int ELEVEN = 11;
	
	private static final int TWELVE = 12;
	
	private static final int THIRTEEN = 13;
	
	
	
	/**
	 * 录入打包装信息
	 */
	public void actionPerformed(ActionEvent e){
		try {
			//重新计算运费
			yoke.sum();
			//获得运单
			WaybillRFCUI waybillRFCUI=yoke.getWaybillRFCUI();
			//根据运单对象获得绑定的对象Map
			WaybillInfoVo bean = waybillRFCUI.getBinderWaybill();
			//货物体积
			BigDecimal volume = bean.getGoodsVolumeTotal();
			//缓冲物费用不能大于货物体积*20
			BigDecimal bufferPrice = new BigDecimal(StringUtils.isEmpty(yoke.getBufferLabel().getText())?"0":yoke.getBufferLabel().getText());
			//BigDecimal bufferPrice = bean.getBufferPrice()!=null?bean.getBufferPrice():BigDecimal.ZERO;
			if(bufferPrice.compareTo(volume.multiply(new BigDecimal("20")))==1){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.enterPackingInfoDialog.bufferPrice"));
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
	
	private void packingYokeEnter(WaybillInfoVo bean) {
		//数据集合
		int[] date=new int[FOURTEEN];
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
		date[THREE]=paperBoxFour;
		//获取纤袋1号蓝数量
		int fibelBagBlueOne=Common.nullOrEmptyToInt(yoke.getFibelBagOneBlueTextField().getText().trim());
		date[FOUR]=fibelBagBlueOne;
		//获取纤袋2号蓝数量
		int fibelBagBlueTwo=Common.nullOrEmptyToInt(yoke.getFibelBagTwoBlueTextField().getText().trim());
		date[FIVE]=fibelBagBlueTwo;
		//获取纤袋3号蓝数量
		int fibelBagBlueThree=Common.nullOrEmptyToInt(yoke.getFibelBagThreeBlueTextField().getText().trim());
		date[SIX]=fibelBagBlueThree;
		//获取纤袋4号蓝数量
		int fibelBagBlueFour=Common.nullOrEmptyToInt(yoke.getFibelBagFourBlueTextField().getText().trim());
		date[SEVEN]=fibelBagBlueFour;
		//获取纤袋1号白数量
		int fibelBagWhiteOne=Common.nullOrEmptyToInt(yoke.getFibelBagOneWhiteTextField().getText().trim());
		date[EIGHT]=fibelBagWhiteOne;
		//获取纤袋2号白数量
		int fibelBagWhiteTwo=Common.nullOrEmptyToInt(yoke.getFibelBagTwoWhiteTextField().getText().trim());
		date[NINE]=fibelBagWhiteTwo;
		//获取纤袋3号白数量
		int fibelBagWhiteThree=Common.nullOrEmptyToInt(yoke.getFibelBagThreeWhiteTextField().getText().trim());
		date[TEN]=fibelBagWhiteThree;
		//获取纤袋4号白数量
		int fibelBagWhiteFour=Common.nullOrEmptyToInt(yoke.getFibelBagFourWhiteTextField().getText().trim());
		date[ELEVEN]=fibelBagWhiteFour;
		//获取纤袋5号白数量
		int fibelBagWhiteClothFive=Common.nullOrEmptyToInt(yoke.getFibelBagFiveWhiteTextField().getText().trim());
		date[TWELVE]=fibelBagWhiteClothFive;
		//获取纤袋6号白数量
		int fibelBagWhiteClothSix=Common.nullOrEmptyToInt(yoke.getFibelBagSixWhiteTextField().getText().trim());
		date[THIRTEEN]=fibelBagWhiteClothSix;
		
		//设置WaybillPanelVo的参数
		setWaybillPanelVo(bean,date);
		
		//将费用存入bean中
		setWaybillPanelVo(bean);
	}
	
	/**
	 * 将数据存入bean,指的是价格
	 * 依次为：纸箱总价，纤袋总价，合计总价
	 */
	private void setWaybillPanelVo(WaybillInfoVo bean) {
		PackingYokeVo vo=yoke.getVo();
		bean.setPaperBoxTotlePrice(vo.getPaperBoxTotlePrice());
		bean.setFibelBagTotlePrice(vo.getFibelBagTotlePrice());
		bean.setBufferPrice(vo.getBuffer());
		bean.setDiscountRate(vo.getDiscount());
//		bean.setPackingTotle(vo.getTotle());
	}

	/**
	 * 将数据存入bean,指的是数量
	 * @param bean
	 * @param date
	 */
	private void setWaybillPanelVo(WaybillInfoVo bean,int[] date) {
		bean.setPaperBoxOne(date[0]);
		bean.setPaperBoxTwo(date[1]);
		bean.setPaperBoxThree(date[2]);
		bean.setPaperBoxFour(date[THREE]);
		bean.setFibelBagBlueOne(date[FOUR]);
		bean.setFibelBagBlueTwo(date[FIVE]);
		bean.setFibelBagBlueThree(date[SIX]);
		bean.setFibelBagBlueFour(date[SEVEN]);
		bean.setFibelBagWhiteOne(date[EIGHT]);
		bean.setFibelBagWhiteTwo(date[NINE]);
		bean.setFibelBagWhiteThree(date[TEN]);
		bean.setFibelBagWhiteFour(date[ELEVEN]);
		bean.setFibelBagWhiteClothFive(date[TWELVE]);
		bean.setFibelBagWhiteClothSix(date[THIRTEEN]);
	}

	@Override
	public void setIInjectUI(EnterPackingInfoDialog yoke) {
		// TODO Auto-generated method stub
		this.yoke=yoke;
	}
	
	
}
