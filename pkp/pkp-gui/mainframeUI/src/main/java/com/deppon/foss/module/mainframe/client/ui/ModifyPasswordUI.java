package com.deppon.foss.module.mainframe.client.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.util.CryptoUtil;
import com.deppon.foss.module.login.shared.exception.LoginException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ModifyPasswordUI extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtuser;
	private JPasswordField txtcurrentPassword;
	private JPasswordField txtmodifyPassword;
	private JPasswordField txtpasswordAgen;
	// 日志对象
	protected final static Logger LOG = LoggerFactory
			.getLogger(ModifyPasswordUI.class.getName());
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(ModifyPasswordUI.class);
	private JLabel currentTextField;
	private JLabel txtmodifyTextField;
	private JLabel textFieldAgen;
	private JLabel lblNewLabel_1;

	public ModifyPasswordUI(Frame frame) {
		super(frame,true);
		init();
	}

	public ModifyPasswordUI() {
		getContentPane().setForeground(Color.RED);
		init();
	}

	void init() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(156dlu;default):grow"),
				ColumnSpec.decode("max(4dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("15dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("15dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("15dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel label = new JLabel(i18n.get("modifyUI.user"));
		getContentPane().add(label, "2, 2, left, default");

		txtuser = new JTextField();
		getContentPane().add(txtuser, "4, 2, fill, default");
		txtuser.setColumns(NumberConstants.NUMBER_10);

		JLabel label1 = new JLabel(i18n.get("modifyUI.current"));
		getContentPane().add(label1, "2, 4, left, default");

		txtcurrentPassword = new JPasswordField();
		txtcurrentPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String message = currentPasswordCheck();
				if(!message.equals("success")){
					txtcurrentPassword.setBorder(new LineBorder(Color.RED));
					currentTextField.setText(message);
				}else{
					txtcurrentPassword.setBorder(new LineBorder(Color.gray));
					currentTextField.setText("");
				}
			}
		});
		getContentPane().add(txtcurrentPassword, "4, 4, fill, default");
		
		currentTextField = new JLabel();
		getContentPane().add(currentTextField, "4, 5, fill, default");

		JLabel lblNewLabel = new JLabel(i18n.get("modifyUI.newPassword"));
		getContentPane().add(lblNewLabel, "2, 6, left, default");

		txtmodifyPassword = new JPasswordField();
		txtmodifyPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String message = modifyPasswordCheck();
				if(!message.equals("success")){
					txtmodifyPassword.setBorder(new LineBorder(Color.RED));
					txtmodifyTextField.setText(message);
				}else{
					txtmodifyPassword.setBorder(new LineBorder(Color.gray));
					txtmodifyTextField.setText("");
				}
			}
		});
		getContentPane().add(txtmodifyPassword, "4, 6, fill, default");
		
		
		txtmodifyTextField = new JLabel();
		txtmodifyTextField.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(txtmodifyTextField, "4, 7, fill, default");

		JLabel label2 = new JLabel(i18n.get("modifyUI.modifyCheck"));
		getContentPane().add(label2, "2, 8, left, default");

		txtpasswordAgen = new JPasswordField();
		txtpasswordAgen.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!passwordAgain()){
					txtpasswordAgen.setBorder(new LineBorder(Color.RED));
					textFieldAgen.setText(i18n.get("modifyUI.passwordNotSame"));
				}else{
					txtpasswordAgen.setBorder(new LineBorder(Color.GRAY));
					textFieldAgen.setText("");
				}
			}
		});
		getContentPane().add(txtpasswordAgen, "4, 8, fill, default");
		
		textFieldAgen = new JLabel();
		textFieldAgen.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(textFieldAgen, "4, 9, fill, default");
		
		lblNewLabel_1 = new JLabel(i18n.get("modifyUI.passwordTip"));
		lblNewLabel_1.setForeground(Color.RED);
		getContentPane().add(lblNewLabel_1, "4, 10");

		JPanel panel = new JPanel();
		panel.setBorder(null);
		getContentPane().add(panel, "2, 12, 3, 1, fill, fill");

		JButton btnsave = new JButton(i18n.get("modifyUI.save"));

		UserEntity user = (UserEntity) SessionContext.getCurrentUser();

		txtuser.setText(user.getUserName());
		txtuser.setEnabled(false);
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//校验当前密码
					String message = currentPasswordCheck();
					if(!message.equals("success")){
						txtcurrentPassword.setBorder(new LineBorder(Color.RED));
						currentTextField.setText(message);
						throw new LoginException(message);
					}else{
						txtcurrentPassword.setBorder(new LineBorder(Color.gray));
						currentTextField.setText("");
					}
					//校验新密码
					message = modifyPasswordCheck();
					if(!message.equals("success")){
						txtmodifyPassword.setBorder(new LineBorder(Color.RED));
						txtmodifyTextField.setText(message);
						throw new LoginException(message);
					}else{
						txtmodifyPassword.setBorder(new LineBorder(Color.gray));
						txtmodifyTextField.setText("");
					}
					//校验确认密码
					if(!passwordAgain()){
						txtpasswordAgen.setBorder(new LineBorder(Color.RED));
						textFieldAgen.setText(i18n.get("modifyUI.passwordNotSame"));
						throw new LoginException(i18n.get("modifyUI.passwordNotSame"));
					}else{
						txtpasswordAgen.setBorder(new LineBorder(Color.GRAY));
						textFieldAgen.setText("");
					}
//					String currentPassword = new String(txtcurrentPassword
//							.getPassword());
//					String pwd = CryptoUtil.digestByMD5(currentPassword);
//
					UserEntity user = (UserEntity) SessionContext
							.getCurrentUser();
//
//					if (!pwd.equals(user.getPassword())) {
//
//						throw new LoginException("modifyUI.passwordWrong");
//
//					}

					String modifyPassword = new String(txtmodifyPassword
							.getPassword());
//					String passwordAgen = new String(txtpasswordAgen
//							.getPassword());
//
//					if (!modifyPassword.equals(passwordAgen)) {
//						throw new LoginException("modifyUI.twiceWrong");
//					}

					IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory
							.getService(IWaybillHessianRemoting.class);

					waybillRemotingService.modifyPassword(user.getUserName(),
							modifyPassword);
					MsgBox.showInfo(i18n.get("modify.saveSuccess"));
					txtcurrentPassword.setText("");
					txtmodifyPassword.setText("");
					txtpasswordAgen.setText("");

				} catch (BusinessException w) {
					MsgBox.showInfo(w.getErrorCode());
				}
			}
		});
		
		panel.add(btnsave);
	}
	//当前密码校验
	public String currentPasswordCheck(){
		String password = new String(txtcurrentPassword.getPassword());
		if(StringUtils.isBlank(password)){
			return i18n.get("modifyUI.passwordNotEmpty");
		}else{
			password = CryptoUtil.digestByMD5(password);
			UserEntity user = (UserEntity) SessionContext
					.getCurrentUser();
			if(!user.getPassword().equals(password)){
				return i18n.get("modifyUI.passwordError");
			}
		}
		return "success";
	}
	//新密码校验
	public String modifyPasswordCheck(){
		String regex="^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,16}$";
		String password = new String(txtmodifyPassword.getPassword());
		if(StringUtils.isBlank(password)){
			return i18n.get("modifyUI.passwordNotEmpty");
		}else if(!password.matches(regex)){
			return i18n.get("modifyUI.passwordFormatError");
		}else{
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			password = CryptoUtil.digestByMD5(password);
			if(password.equals(user.getPassword())){
				return i18n.get("modifyUI.passwordSame");
			}
		}
		return "success";
	}
	//确认密码校验
	public boolean passwordAgain(){
		String password1 = new String(txtmodifyPassword.getPassword());
		String password2 = new String(txtpasswordAgen.getPassword());
		if(!password1.equals(password2)){
			return false;
		}
		return true;
	}
}
