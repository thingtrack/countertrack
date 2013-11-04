package com.thingtrack.konekti.countertrack.workbench.view;

import javax.mail.MessagingException;

import com.thingtrack.konekti.countertrack.workbench.MainApplication;
import com.thingtrack.countertrack.domain.User;
import com.thingtrack.countertrack.service.api.UserService;
import com.thingtrack.konekti.countertrack.workbench.service.MailService;
import com.thingtrack.konekti.countertrack.workbench.service.MailServiceImpl;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class ForgotView extends LocaleComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Label lblCopyRight;
	@AutoGenerated
	private Panel pnMainPanel;
	@AutoGenerated
	private VerticalLayout securityLayout;
	@AutoGenerated
	private HorizontalLayout toolbarLayout;
	@AutoGenerated
	private Button btnResendPassword;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Label lblSeparator;
	@AutoGenerated
	private VerticalLayout logonLayout;
	@AutoGenerated
	private TextField emailText;
	@AutoGenerated
	private Embedded logo;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	
	private UserService userService;
	private MailService mailService = new MailServiceImpl();
	
	private User userRecovery;
	
	private ClickConfirmForgotButtonListener listenerConfirmForgotButton = null;
	
	private static final String SUBJECT = "Password Recovery Email";
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ForgotView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		getServices();

		pnMainPanel.addStyleName("login");

		emailText.addStyleName("login");
		
		logonLayout.addStyleName("forgot");
		securityLayout.addStyleName("security");

		btnResendPassword.setStyleName(Button.STYLE_LINK);
		btnResendPassword.addStyleName("login");

		btnResendPassword.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (emailText.getValue() == null || emailText.getValue().toString().isEmpty()) {
					getApplication().getMainWindow().showNotification("Email is a required field", Notification.TYPE_ERROR_MESSAGE);
						
					return;								
					
				}
				
				// get User from username
				try {
					userRecovery = userService.getUserRecovery(emailText.getValue().toString());
					
					if (!userRecovery.getActive()) {
						getApplication().getMainWindow().showNotification("The user is unactive. Please check your email confirmation", Notification.TYPE_WARNING_MESSAGE);
						
						return;
					}
				} catch (Exception e) {
					getApplication().getMainWindow().showNotification("The user not exist", Notification.TYPE_ERROR_MESSAGE);
					
					return;
				}
				
				// send confirmation email to the user
				sendEmail();
				
				// confirmation message
				getApplication().getMainWindow().showNotification("Password sended!", Notification.TYPE_HUMANIZED_MESSAGE);
				
				if (listenerConfirmForgotButton != null)
					listenerConfirmForgotButton.buttonClick(event);
				
			}
		});

		btnCancel.setStyleName(Button.STYLE_LINK);
		btnCancel.addStyleName("login");

		btnCancel.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				
				if (listenerConfirmForgotButton != null)
					listenerConfirmForgotButton.buttonClick(event);
				
			}
		});
		
	}

	private void sendEmail() {
		StringBuffer payload = new StringBuffer("<p><p>The password for the email <b>" + userRecovery.getEmail() + "<b> is: <b>" + userRecovery.getPassword() + "</b>");		
					
		try {
			mailService.sendEmailSSL(SUBJECT, payload.toString(), userRecovery.getEmail());
		} catch (MessagingException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
	
	private void getServices() {
		this.userService = MainApplication.get().getUserService();

	}

	public void addListenerConfirmForgotButton(ClickConfirmForgotButtonListener listener) {
		this.listenerConfirmForgotButton = listener;
		
	}
	
	public interface ClickConfirmForgotButtonListener extends ClickListener {

    }
	
	@Override
	protected void updateLabels() {
		emailText.setInputPrompt(getI18N().getMessage("emailText.inputPrompt"));
		emailText.setDescription(getI18N().getMessage("emailText.description"));
		btnResendPassword.setCaption(getI18N().getMessage("btnResendPassword.caption"));
		btnResendPassword.setDescription(getI18N().getMessage("btnResendPassword.description"));
		btnCancel.setCaption(getI18N().getMessage("btnCancel.caption"));
		btnCancel.setDescription(getI18N().getMessage("btnCancel.description"));
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// logo
		logo = new Embedded();
		logo.setImmediate(false);
		logo.setWidth("-1px");
		logo.setHeight("-1px");
		logo.setSource(new ThemeResource("../countertrack/logo/LogoWeb.png"));
		logo.setType(1);
		logo.setMimeType("image/png");
		mainLayout.addComponent(logo);
		mainLayout.setComponentAlignment(logo, new Alignment(60));
		
		// pnMainPanel
		pnMainPanel = buildPnMainPanel();
		mainLayout.addComponent(pnMainPanel);
		mainLayout.setExpandRatio(pnMainPanel, 1.0f);
		mainLayout.setComponentAlignment(pnMainPanel, new Alignment(48));
		
		// lblCopyRight
		lblCopyRight = new Label();
		lblCopyRight.setStyleName("copyright");
		lblCopyRight.setImmediate(false);
		lblCopyRight.setWidth("-1px");
		lblCopyRight.setHeight("-1px");
		lblCopyRight.setValue("©Copyright Konekti 2010-2013. <a href='http://www.thingtrack.com' target='_blank'> thingtrack.com </a>");
		lblCopyRight.setContentMode(3);
		mainLayout.addComponent(lblCopyRight);
		mainLayout.setExpandRatio(lblCopyRight, 1.0f);
		mainLayout.setComponentAlignment(lblCopyRight, new Alignment(20));
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildPnMainPanel() {
		// common part: create layout
		pnMainPanel = new Panel();
		pnMainPanel.setImmediate(false);
		pnMainPanel.setWidth("360px");
		pnMainPanel.setHeight("220px");
		
		// securityLayout
		securityLayout = buildSecurityLayout();
		pnMainPanel.setContent(securityLayout);
		
		return pnMainPanel;
	}

	@AutoGenerated
	private VerticalLayout buildSecurityLayout() {
		// common part: create layout
		securityLayout = new VerticalLayout();
		securityLayout.setImmediate(false);
		securityLayout.setWidth("100.0%");
		securityLayout.setHeight("-1px");
		securityLayout.setMargin(true);
		securityLayout.setSpacing(true);
		
		// logonLayout
		logonLayout = buildLogonLayout();
		securityLayout.addComponent(logonLayout);
		securityLayout.setExpandRatio(logonLayout, 1.0f);
		
		// lblSeparator
		lblSeparator = new Label();
		lblSeparator.setImmediate(false);
		lblSeparator.setWidth("100.0%");
		lblSeparator.setHeight("-1px");
		lblSeparator.setValue("<hr/>");
		lblSeparator.setContentMode(3);
		securityLayout.addComponent(lblSeparator);
		
		// toolbarLayout
		toolbarLayout = buildToolbarLayout();
		securityLayout.addComponent(toolbarLayout);
		
		return securityLayout;
	}

	@AutoGenerated
	private VerticalLayout buildLogonLayout() {
		// common part: create layout
		logonLayout = new VerticalLayout();
		logonLayout.setImmediate(false);
		logonLayout.setWidth("100.0%");
		logonLayout.setHeight("100.0%");
		logonLayout.setMargin(true);
		logonLayout.setSpacing(true);
		
		// emailText
		emailText = new TextField();
		emailText.setImmediate(false);
		emailText.setDescription("Your email");
		emailText.setWidth("100.0%");
		emailText.setHeight("40px");
		logonLayout.addComponent(emailText);
		
		return logonLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildToolbarLayout() {
		// common part: create layout
		toolbarLayout = new HorizontalLayout();
		toolbarLayout.setImmediate(false);
		toolbarLayout.setWidth("100.0%");
		toolbarLayout.setHeight("50px");
		toolbarLayout.setMargin(false);
		toolbarLayout.setSpacing(true);
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancel");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		toolbarLayout.addComponent(btnCancel);
		toolbarLayout.setExpandRatio(btnCancel, 1.0f);
		toolbarLayout.setComponentAlignment(btnCancel, new Alignment(34));
		
		// btnResendPassword
		btnResendPassword = new Button();
		btnResendPassword.setCaption("Resend Password");
		btnResendPassword.setImmediate(true);
		btnResendPassword.setWidth("-1px");
		btnResendPassword.setHeight("-1px");
		toolbarLayout.addComponent(btnResendPassword);
		toolbarLayout.setComponentAlignment(btnResendPassword,
				new Alignment(34));
		
		return toolbarLayout;
	}

}