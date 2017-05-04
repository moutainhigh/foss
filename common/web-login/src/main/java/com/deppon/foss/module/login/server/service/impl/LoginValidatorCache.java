
package com.deppon.foss.module.login.server.service.impl;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.deppon.foss.framework.cache.storage.exception.KeyIsNotFoundException;
import com.deppon.foss.framework.cache.storage.exception.RedisConnectionException;
import com.deppon.foss.framework.cache.storage.exception.ValueIsBlankException;
import com.deppon.foss.framework.cache.storage.exception.ValueIsNullException;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 登录验证码 缓存服务
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:268984,date:2016-9-6 上午8:37:47,content:TODO </p>
 * @author 268984 
 * @date 2016-9-6 上午8:37:47
 * @since
 * @version
 */
public class LoginValidatorCache extends FossTTLCache<String>{
	
	private static final Log LOG = LogFactory.getLog(LoginValidatorCache.class);
	
	public String getUUID() {
		return BSE_LOGIN_VALIDATOR;
	}
	
	@Override
    public String get(String key) {
        if(StringUtils.isBlank(key)) {
            throw new BusinessException("获取用户名失败","");
        }
        String value = null;
        try {
            value = cacheStorage.get(getKey(key));
        } catch(ValueIsBlankException e) {
            LOG.warn("缓存["+getUUID()+"]，key["+key+"]存在，value为空串，返回结果[null]");
            //key存在，value为空串
            throw new BusinessException("验证码已失效!",e.getMessage());
        } catch(ValueIsNullException ex) {
            //key存在，value为null
            LOG.warn("缓存["+getUUID()+"]，key["+key+"]存在，value为null，返回结果[null]");
            throw new BusinessException("验证码已失效!",ex.getMessage());
        } catch(KeyIsNotFoundException ex1) {
            //key不存在
        	   LOG.warn("缓存["+getUUID()+"]，key["+key+"]不存在]");
             throw new BusinessException("未发送验证码,请重新发送!",ex1.getMessage());
        } catch(RedisConnectionException exx) {
            //redis 连接出现异常
           // value = cacheProvider.get(key);
           LOG.warn("redis连接出现异常!");
           throw new BusinessException("验证码校验异常！",exx.getMessage());
        }
        return value;
	}
	public boolean set(String key ,String value){
		         try {
		        	 LOG.info("用户:"+key+",登录验证码加入缓存。时间："+System.currentTimeMillis());
		        	  return   cacheStorage.set(getKey(key), value, timeOut);
				} catch (Exception e) {
					 throw new BusinessException("服务器验证码存储失败，请尝试重新发送！",e.getMessage());
		}
	}
	
	public void remove(String key){
	    try {
       	 LOG.info("作废缓存中的验证码,key:"+key);
       	     cacheStorage.remove(getKey(key));
		} catch (Exception e) {
			LOG.info("服务器验证码作废失败！key:"+key);
			// throw new BusinessException("服务器验证码作废失败！",e.getMessage());
     }
	}
}
