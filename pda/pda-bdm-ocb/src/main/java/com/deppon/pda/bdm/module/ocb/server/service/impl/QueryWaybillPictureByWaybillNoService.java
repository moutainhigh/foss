/**   
* @Title: QueryWaybillPictureByWaybillNo.java 
* @Package com.deppon.pda.bdm.module.ocb.server.service.impl 
* @Description: 
* @author 183272
* @date 2014年11月5日 上午9:48:57 
* @version V1.0   
*/
package com.deppon.pda.bdm.module.ocb.server.service.impl;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/** 
 * @ClassName: QueryWaybillPictureByWaybillNo 
 * @Description: 
 * @author 183272
 * @date 2014年11月5日 上午9:48:57 
 *  
 */
public class QueryWaybillPictureByWaybillNoService implements IBusinessService<String, Void> {
	//private static final Log LOG = LogFactory.getLog(QueryWaybillPictureByWaybillNoService.class);
	private IPdaWaybillService pdaWaybillService;
	
	/** 
	 * @param pdaWaybillService 要设置的 pdaWaybillService 
	 */
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/** 
	* @Title: parseBody 
	* @Description: 
	* @author 183272
	* @date 2014年11月5日 上午9:50:11 
	* @param @param asyncMsg
	* @param @return
	* @param @throws PdaBusiException    设定文件 
	* @throws 
	*/
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// TODO Auto-generated method stub
		return null;
	}


	/** 
	* @Title: getOperType 
	* @Description: 
	* @author 183272
	* @date 2014年11月5日 上午9:50:11 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String getOperType() {
		return OcbConstant.OPER_TYPE_OCB_QWBP_WBN.VERSION;
	}

	/** 
	* @Title: isAsync 
	* @Description: 
	* @author 183272
	* @date 2014年11月5日 上午9:50:11 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}


	/** 
	* @Title: service 
	* @Description: 
	* @author 183272
	* @date 2014年11月17日 下午4:19:55 
	* @param @param asyncMsg
	* @param @param param
	* @param @return
	* @param @throws PdaBusiException    设定文件 
	* @throws 
	*/
	@Override
	public String service(AsyncMsg asyncMsg, Void param) throws PdaBusiException {
		String waybillNo = asyncMsg.getContent();
		String rtn = "失败：";
		if(StringUtils.isNotEmpty(waybillNo)){
				rtn = pdaWaybillService.queryWaybillPictureByWaybillNo(waybillNo);
		}else{
			rtn += "运单号为空";
		}

		return rtn;
	}

}
