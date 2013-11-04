package com.thingtrack.konekti.countertrack.workbench.view;

import com.thingtrack.countertrack.domain.User;
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
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ConfirmationView extends LocaleComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Label lblCopyRight;
	@AutoGenerated
	private Panel pnMainPanel;
	@AutoGenerated
	private VerticalLayout securityLayout;
	@AutoGenerated
	private HorizontalLayout toolBarLayout;
	@AutoGenerated
	private Button btnLogin;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Label lblSeparator;
	@AutoGenerated
	private VerticalLayout logonLayout;
	@AutoGenerated
	private Label lblConfirmationMessage;
	@AutoGenerated
	private Embedded logo;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private User user;
	
	private ClickLoginButtonListener listenerLoginButton = null;
	private ClickCancelButtonListener listenerCancelButton = null;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ConfirmationView(User user) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		this.user = user;
		
		pnMainPanel.addStyleName("login");
		
		//logonLayout.addStyleName("createaccount");
		securityLayout.addStyleName("security");
		lblConfirmationMessage.setContentMode(Label.CONTENT_XHTML);
		
		btnLogin.setStyleName(Button.STYLE_LINK);
		btnLogin.addStyleName("login");		
		btnLogin.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (listenerLoginButton != null)
					listenerLoginButton.buttonClick(event);
				
			}
		});
		
		btnCancel.setStyleName(Button.STYLE_LINK);
		btnCancel.addStyleName("login");		
		btnCancel.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if (listenerCancelButton != null)
					listenerCancelButton.buttonClick(event);
				
			}
		});
		
		showConfirmationMessage();
	}
	
	private void showConfirmationMessage() {
		if (user != null) {
			logonLayout.addStyleName("confirmation-ok");
			btnCancel.setVisible(false);			
			lblConfirmationMessage.setValue("The user: " + user.getFullName() + "<p>is activated. Login now");
		}
		else {
			logonLayout.addStyleName("confirmation-refuse");
			btnLogin.setVisible(false);
			lblConfirmationMessage.setValue("Not exist any user for this activation code.<p>Call to your provideer");
		}
		
	}
	
	public void addListenerLoginButton(ClickLoginButtonListener listener) {
		this.listenerLoginButton = listener;
		
	}
	
	public interface ClickLoginButtonListener extends ClickListener {
    }
	
	public void addListenerCancelButton(ClickCancelButtonListener listener) {
		this.listenerCancelButton = listener;
		
	}
	
	public interface ClickCancelButtonListener extends ClickListener {
    }
	
	@Override
	protected void updateLabels() {
		btnLogin.setDescription(getI18N().getMessage("btnLogin.caption"));
		btnCancel.setCaption(getI18N().getMessage("btnCancel.caption"));
		
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
		lblCopyRight
				.setValue("©Copyright Konekti 2010-2013. <a href='http://www.thingtrack.com' target='_blank'> thingtrack.com </a>");
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
		pnMainPanel.setWidth("400px");
		pnMainPanel.setHeight("250px");
		
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
		
		// toolBarLayout
		toolBarLayout = buildToolBarLayout();
		securityLayout.addComponent(toolBarLayout);
		
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
		
		// lblConfirmationMessage
		lblConfirmationMessage = new Label();
		lblConfirmationMessage.setImmediate(false);
		lblConfirmationMessage.setWidth("-1px");
		lblConfirmationMessage.setHeight("-1px");
		lblConfirmationMessage.setValue("Label");
		logonLayout.addComponent(lblConfirmationMessage);
		
		return logonLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildToolBarLayout() {
		// common part: create layout
		toolBarLayout = new HorizontalLayout();
		toolBarLayout.setImmediate(false);
		toolBarLayout.setWidth("100.0%");
		toolBarLayout.setHeight("50px");
		toolBarLayout.setMargin(false);
		toolBarLayout.setSpacing(true);
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancel");
		btnCancel.setImmediate(false);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		toolBarLayout.addComponent(btnCancel);
		toolBarLayout.setExpandRatio(btnCancel, 1.0f);
		toolBarLayout.setComponentAlignment(btnCancel, new Alignment(34));
		
		// btnLogin
		btnLogin = new Button();
		btnLogin.setCaption("Log in");
		btnLogin.setImmediate(true);
		btnLogin.setWidth("-1px");
		btnLogin.setHeight("-1px");
		toolBarLayout.addComponent(btnLogin);
		toolBarLayout.setComponentAlignment(btnLogin, new Alignment(34));
		
		return toolBarLayout;
	}

}