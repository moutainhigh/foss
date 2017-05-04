package com.deppon.foss.module.init.client.sync.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.exception.security.UserNotLoginException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.init.client.sync.service.IDownloadTokenService;
import com.deppon.foss.module.login.shared.hessian.ILoginHessianRemoting;
import com.deppon.foss.module.login.shared.vo.DownloadTokenEntity;

/**
 * 下载令牌服务类
 * zxy 20140306 MANA-2018
 * @author 157229-zxy
 *
 */
public class DownloadTokenService implements IDownloadTokenService{
	private static final Log LOG = LogFactory.getLog(DownloadTokenService.class);
	private boolean connectSuccess;				//连接成功标志,用来判断令牌服务的可用性
	private ILoginHessianRemoting loginHessianRemoting;	//foss远程服务
	//异步任务管理
	private ExecutorService threadPool = Executors.newFixedThreadPool(NumberConstants.NUMBER_5); //
	private boolean beatRunning = false;		//是否心跳开始
	private boolean requestTokenOver = false;	//是否请求令牌结束
	private DownloadTokenService self;
	private int requestInterval;						//请求间隔时间
	private int beatInterval;						//心跳间隔时间
	private ITaskContext context;				//消息上下文
	private String title;						//任务标题
	private String message;						//任务消息
	private int cirCount;						
	private DownloadTokenEntity tokenEntity ;	//令牌
		
	public DownloadTokenService(ITaskContext context,String title, String message, int requestInterval, int beatInterval){
		this.requestInterval = requestInterval;
		this.beatInterval = beatInterval;
		cirCount = 0;
		self = this;
		this.title = title;
		this.message = message;
		this.context = context;
		connectSuccess = false;
		loginHessianRemoting = DefaultRemoteServiceFactory.getInstance().getRemoteServer().getService(ILoginHessianRemoting.class);
	}

	@Override
	public void run() throws Exception{
		//设置任务标题
		context.setTitle(title);
		//轮询时设置消息
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				while(!requestTokenOver){
					if(cirCount % NumberConstants.NUMBER_4 == 0)
						context.setMessage(message);
					else{
						context.setMessage(message + ".......".substring(0, cirCount % NumberConstants.NUMBER_4));
					}
					cirCount ++;
					try {
						Thread.sleep(NumberConstants.NUMBER_2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		//轮询线程
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				do{
					try{
						String token = (String)SessionContext.get("_TOKEN_UUID");
						String departCode = getCurrentUserDeptCode();
						boolean downloadSwitch = loginHessianRemoting.getDownloadSwitch(departCode);
						if(downloadSwitch){
							DownloadTokenEntity downloadTokenEntity = loginHessianRemoting.requestDownloadToken(token); //检测连接，同时保持心跳存活
							if(downloadTokenEntity != null && StringUtils.isNotBlank(downloadTokenEntity.getDownloadToken())){
								synchronized(self){
									self.notifyAll();
								}
								requestTokenOver = true;
								beatRunning = true;
								tokenEntity = downloadTokenEntity;
								connectSuccess = true;
							}
							try {
								Thread.sleep(requestInterval);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						//
						else{
							LOG.info("下载开关已关闭.");
							requestTokenOver = true;
							beatRunning = false;
							tokenEntity = null;
							synchronized(self){
								self.notifyAll();
							}
						}
					}catch(Exception e){
						LOG.info("请求令牌异常,稍后再试.", e);
						try {
							Thread.sleep(requestInterval);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						//如果异常，则设置tokenEntity为null,并且唤醒等待，否则程序始终等待了。一般是foss服务器终止导致请求异常
//						requestTokenOver = true;
//						tokenEntity = null;
//						beatRunning = false;
//						synchronized(self){
//							self.notifyAll();
//						}
					}
					
				}while(!requestTokenOver);
			}
		});
		
		//当心跳未开始，暂停后续服务，直到检测到心跳后才开始执行
		synchronized (self) {
			try {
				self.wait();//当请求道令牌后，执行以下代码
				//执行心跳
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						while(beatRunning){
							self.beat();
						}
					}
				});
			} catch (InterruptedException e) {
				throw e;
			}
		}
	}

	@Override
	public void cancelBeat() {
		String token = (String)SessionContext.get("_TOKEN_UUID");
		loginHessianRemoting.forceReleaseDownloadToken(token);
		beatRunning = false;
		threadPool.shutdownNow();
	}

	public boolean isConnectSuccess() {
		return connectSuccess;
	}

	public void setConnectSuccess(boolean connectSuccess) {
		this.connectSuccess = connectSuccess;
	}

	@Override
	public DownloadTokenEntity getTokenEntity() {
		return tokenEntity;
	}

	@Override
	public void releaseTokenEntity() {
		String token = (String)SessionContext.get("_TOKEN_UUID");
		loginHessianRemoting.releaseDownloadToken(token);
	}

	@Override
	public void beat(){
		String token = (String)SessionContext.get("_TOKEN_UUID");
		try {
			String departCode = getCurrentUserDeptCode();
			boolean bSurvival = loginHessianRemoting.beat(token); //检测连接，同时保持心跳存活
			if(bSurvival){
				boolean isSwitchOn = loginHessianRemoting.getDownloadSwitch(departCode);
				connectSuccess = isSwitchOn;
				if(!isSwitchOn)
					LOG.info("下载开关已关闭,停止下载");
			}else{
				connectSuccess = false;		//服务不可用
				beatRunning = false;		//终止心跳
			}
			Thread.sleep(beatInterval);
		} catch (Exception e) {
			e.printStackTrace();
			connectSuccess = false;	//服务不可用
			beatRunning = false;	//终止心跳
		}
	}
	
	/**
	 * 获取当前部门code
	 * @return
	 * @throws Exception 
	 */
	public String getCurrentUserDeptCode() throws Exception{
		String departCode = StringUtils.EMPTY;	
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		EmployeeEntity emp = null;
		if(user != null){
			emp = (EmployeeEntity)user.getEmployee();
			departCode = emp.getDepartment().getCode();
		}else
			throw new UserNotLoginException("当前用户session信息为空");
		return departCode;
	}
}
