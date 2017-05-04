/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.util;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * @author ibm-foss-sxw
 *
 */
public class ExpCommonUtils {

	public static boolean verdictPickUpSelf(String pickupType) {
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.SELF_PICKUP.equals(type) || WaybillConstants.INNER_PICKUP.equals(type) || WaybillConstants.AIR_PICKUP_FREE.equals(type)
				|| WaybillConstants.AIRPORT_PICKUP.equals(type) || WaybillConstants.AIR_SELF_PICKUP.equals(type)) {
			return true;
		} else {
			return false;
		}
	}
}
