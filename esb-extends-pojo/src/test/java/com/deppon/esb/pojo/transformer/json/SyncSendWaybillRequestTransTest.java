package com.deppon.esb.pojo.transformer.json;


import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.ecs.inteface.domain.SyncSendWaybillRequest;

/**
 * ThrowfreightRequestTrans转换测试.
 * 
 * @author HuangHua
 */
public class SyncSendWaybillRequestTransTest {

	// 转换类
	/** The trans. */
	private static SyncSendWaybillRequestTrans trans = new SyncSendWaybillRequestTrans();

	/**
	 * 先Object2String,然后String2Object.
	 * 
	 * @author HuangHua
	 * @date 2012-12-20 下午7:05:09
	 */
	@Test
	public void test() {
		
		/*SyncSendWaybillRequest sendWaybillRequest = new SyncSendWaybillRequest();
		sendWaybillRequest.setMessage(null);*/
		try {
			// Object2String
			String json = "{\"message\":\"{\"actualFreightEntity\":{\"arriveCentralizedSettlement\":\"\",\"codAmount\":0,\"deliverFee\":0,\"freight\":58,\"goodsName\":\"到达\",\"goodsQty\":1,\"insuranceValue\":0,\"invoice\":\"\",\"laborFee\":0,\"packingFee\":0,\"receiveCustomerVillageCode\":\"310108\",\"startCentralizedSettlement\":\"Y\",\"startReminderOrgCode\":\"DP00059\",\"status\":\"EFFECTIVE\",\"valueaddFee\":0,\"volume\":0.00,\"waybillNo\":\"6123456916\",\"weight\":10.00},\"waybillEntity\":{\"accountCode\":\"\",\"active\":\"Y\",\"billTime\":1467085771495,\"billWeight\":10.00,\"codAmount\":0,\"codFee\":0,\"createOrgCode\":\"W011302020515\",\"createTime\":1467085771495,\"createUserCode\":\"084544\",\"createUserDeptName\":\"上海闵行区浦江镇营业部\",\"currencyCode\":\"RMB\",\"customerPickupOrgCode\":\"W3100030401051006\",\"deliveryCustomerCode\":\"401214833\",\"deliveryCustomerContact\":\"测试\",\"deliveryCustomerMobilephone\":\"15215412111\",\"deliveryCustomerName\":\"FREIGHT_METHOD_HDP\",\"deliveryGoodsFee\":0,\"forbiddenLine\":\"Y\",\"freePickupGoods\":\"Y\",\"freightMethod\":\"MIDDLE_SHIFT\",\"goodsName\":\"到达\",\"goodsQtyTotal\":1,\"goodsVolumeTotal\":0.00,\"goodsWeightTotal\":10.00,\"id\":\"522edd94397d448694a60d8c993dec8e\",\"insuranceAmount\":0,\"insuranceFee\":0,\"internalStaffCode\":\"\",\"lastLoadOrgCode\":\"W3100030401051006\",\"loadOrgCode\":\"null\",\"otherFee\":0,\"packageFee\":0,\"paidMethod\":\"FC\",\"pickupCentralized\":\"Y\",\"pickupFee\":0,\"pickupToDoor\":\"Y\",\"prePayAmount\":0,\"preciousGoods\":\"N\",\"productCode\":\"DEAP\",\"promotionsFee\":32,\"receiveCustomerAddress\":\"1\",\"receiveCustomerCityCode\":\"310000-1\",\"receiveCustomerCode\":\"E2016062812628847\",\"receiveCustomerContact\":\"IE付\",\"receiveCustomerDistCode\":\"310108\",\"receiveCustomerMobilephone\":\"15511245222\",\"receiveCustomerName\":\"\",\"receiveCustomerPhone\":\"15511245222\",\"receiveCustomerProvCode\":\"310000\",\"receiveMethod\":\"PICKUP_TO_DOOR\",\"receiveOrgCode\":\"W011302020515\",\"receiveOrgName\":\"上海闵行区浦江镇营业部\",\"secretPrepaid\":\"N\",\"serviceFee\":0,\"shipperCustomerCode\":\"401214833\",\"specialShapedGoods\":\"N\",\"stayPayCardAmount\":0,\"targetOrgCode\":\"W3100020616\",\"toPayAmount\":58,\"totalFee\":58,\"transportFee\":58,\"unitPrice\":4,\"valueAddFee\":0,\"waybillNo\":\"6123456916\",\"waybillNos\":[null],\"wholeVehicleAppfee\":0},\"waybillExpressEntity\":{\"billTime\":1467085771495,\"createOrgCode\":\"W011302020515\",\"isAddCode\":\"Y\",\"originalWaybillNo\":\"\",\"waybillNo\":\"6123456916\"}}\"}";
			//{"message":"{\"actualFreightEntity\":{\"arriveCentralizedSettlement\":\"\",\"codAmount\":0,\"deliverFee\":0,\"freight\":58,\"goodsName\":\"到达\",\"goodsQty\":1,\"insuranceValue\":0,\"invoice\":\"\",\"laborFee\":0,\"packingFee\":0,\"receiveCustomerVillageCode\":\"310108\",\"startCentralizedSettlement\":\"Y\",\"startReminderOrgCode\":\"DP00059\",\"status\":\"EFFECTIVE\",\"valueaddFee\":0,\"volume\":0.00,\"waybillNo\":\"6123456916\",\"weight\":10.00},\"waybillEntity\":{\"accountCode\":\"\",\"active\":\"Y\",\"billTime\":1467085771495,\"billWeight\":10.00,\"codAmount\":0,\"codFee\":0,\"createOrgCode\":\"W011302020515\",\"createTime\":1467085771495,\"createUserCode\":\"084544\",\"createUserDeptName\":\"上海闵行区浦江镇营业部\",\"currencyCode\":\"RMB\",\"customerPickupOrgCode\":\"W3100030401051006\",\"deliveryCustomerCode\":\"401214833\",\"deliveryCustomerContact\":\"测试\",\"deliveryCustomerMobilephone\":\"15215412111\",\"deliveryCustomerName\":\"FREIGHT_METHOD_HDP\",\"deliveryGoodsFee\":0,\"forbiddenLine\":\"Y\",\"freePickupGoods\":\"Y\",\"freightMethod\":\"MIDDLE_SHIFT\",\"goodsName\":\"到达\",\"goodsQtyTotal\":1,\"goodsVolumeTotal\":0.00,\"goodsWeightTotal\":10.00,\"id\":\"522edd94397d448694a60d8c993dec8e\",\"insuranceAmount\":0,\"insuranceFee\":0,\"internalStaffCode\":\"\",\"lastLoadOrgCode\":\"W3100030401051006\",\"loadOrgCode\":\"null\",\"otherFee\":0,\"packageFee\":0,\"paidMethod\":\"FC\",\"pickupCentralized\":\"Y\",\"pickupFee\":0,\"pickupToDoor\":\"Y\",\"prePayAmount\":0,\"preciousGoods\":\"N\",\"productCode\":\"DEAP\",\"promotionsFee\":32,\"receiveCustomerAddress\":\"1\",\"receiveCustomerCityCode\":\"310000-1\",\"receiveCustomerCode\":\"E2016062812628847\",\"receiveCustomerContact\":\"IE付\",\"receiveCustomerDistCode\":\"310108\",\"receiveCustomerMobilephone\":\"15511245222\",\"receiveCustomerName\":\"\",\"receiveCustomerPhone\":\"15511245222\",\"receiveCustomerProvCode\":\"310000\",\"receiveMethod\":\"PICKUP_TO_DOOR\",\"receiveOrgCode\":\"W011302020515\",\"receiveOrgName\":\"上海闵行区浦江镇营业部\",\"secretPrepaid\":\"N\",\"serviceFee\":0,\"shipperCustomerCode\":\"401214833\",\"specialShapedGoods\":\"N\",\"stayPayCardAmount\":0,\"targetOrgCode\":\"W3100020616\",\"toPayAmount\":58,\"totalFee\":58,\"transportFee\":58,\"unitPrice\":4,\"valueAddFee\":0,\"waybillNo\":\"6123456916\",\"waybillNos\":[null],\"wholeVehicleAppfee\":0},\"waybillExpressEntity\":{\"billTime\":1467085771495,\"createOrgCode\":\"W011302020515\",\"isAddCode\":\"Y\",\"originalWaybillNo\":\"\",\"waybillNo\":\"6123456916\"}}"}
			Assert.assertNotNull(json);
			// String2Object
			SyncSendWaybillRequest result = trans.toMessage(json);
			Assert.assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
