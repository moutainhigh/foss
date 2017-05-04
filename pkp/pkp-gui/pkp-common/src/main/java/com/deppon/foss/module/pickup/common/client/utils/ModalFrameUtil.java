package com.deppon.foss.module.pickup.common.client.utils;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.swing.JFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ModalFrameUtil {

	private boolean iconifiedFlag = false;
	private Log log = LogFactory.getLog(getClass());

	private ModalFrameUtil() {
	}

	public static ModalFrameUtil getInstance() {
		return new ModalFrameUtil();
	}

	static class EventPump implements InvocationHandler {
		Frame frame;

		public EventPump(Frame frame) {
			this.frame = frame;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			return frame.isShowing() ? Boolean.TRUE : Boolean.FALSE;
		}

		// when the reflection calls in this method has to be
		// replaced once Sun provides a public API to pump events.
		public void start() throws Exception {
			Class<?> clazz = Class.forName("java.awt.Conditional");
			Object conditional = Proxy.newProxyInstance(clazz.getClassLoader(),
					new Class[] { clazz }, this);
			Method pumpMethod = Class.forName("java.awt.EventDispatchThread")
					.getDeclaredMethod("pumpEvents", new Class[] { clazz });
			pumpMethod.setAccessible(true);
			pumpMethod.invoke(Thread.currentThread(),
					new Object[] { conditional });
		}
	}

	// 调用方法
	public void showAsModal(final JFrame frame, final Frame owner) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.setResizable(false);
		frame.setLocationRelativeTo(owner);
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				owner.setEnabled(true);
				frame.toFront();
			}

			public void windowClosed(WindowEvent e) {
				owner.setEnabled(true);
				owner.removeWindowListener(this);
				owner.setExtendedState(JFrame.MAXIMIZED_BOTH);
				owner.toFront();
			}
			
		});
		frame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if (e.getOldState() != e.getNewState()) {
					switch (e.getNewState()) {
					case Frame.MAXIMIZED_BOTH:
						// 最大化
						log.debug("最大化");
						owner.setEnabled(false);
						frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
						frame.toFront();
						break;
					case Frame.ICONIFIED:
						// 最小化
						log.debug("最小化" + iconifiedFlag);
						iconifiedFlag = true;
						owner.setEnabled(true);
						owner.setExtendedState(JFrame.MAXIMIZED_BOTH);
						owner.toFront();
						frame.setVisible(false);
						break;
					case Frame.NORMAL:
						log.debug("恢复 :" + iconifiedFlag);
						// 恢复
						owner.setEnabled(false);
						frame.setExtendedState(JFrame.NORMAL);
						frame.toFront();
						break;
					default:
						break;
					}
				}
			}
		});
		owner.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				owner.setExtendedState(JFrame.MAXIMIZED_BOTH);
				if (frame.isShowing() && !iconifiedFlag) {
					frame.toFront();
					iconifiedFlag = false;
				} else {
					owner.setEnabled(true);
					owner.removeWindowListener(this);
					owner.toFront();
				}
			}
		});
		frame.setVisible(true);
		frame.setEnabled(true);
		frame.toFront();
		try {
			new EventPump(frame).start();
		} catch (Throwable throwable) {
			log.error(throwable.getMessage(), throwable);
			throw new RuntimeException(throwable);
		}
	}
}
