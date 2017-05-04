package com.deppon.foss.esb.util;

import javax.xml.ws.Holder;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.util.UUIDUtils;

/**
 * 对于不是和OA进行交互的webservice接口，FOSS服务端的实现程序可以通过此帮助类来设置返回给ESB的头信息；
 * 参考：com.deppon.foss.esb.sync.test.SyncDataServiceImpl
 * 
 * @author zhengwl
 *
 */
public class ESBHeaderUtil {
	public static void processEsbHeaderForResponse(Holder<ESBHeader> esbHeader) {
		esbHeader.value.setResponseId(UUIDUtils.getUUID());
	}
}
 