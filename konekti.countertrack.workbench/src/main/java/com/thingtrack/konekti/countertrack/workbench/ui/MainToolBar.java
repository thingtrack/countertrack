package com.thingtrack.konekti.countertrack.workbench.ui;

import com.thingtrack.konekti.countertrack.workbench.MainApplication;
import com.thingtrack.konekti.countertrack.workbench.view.ConfigurationView;
import com.thingtrack.konekti.countertrack.workbench.view.LocaleComponent;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MainToolBar extends LocaleComponent {

	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout separator;
	@AutoGenerated
	private Button btnLogout;
	@AutoGenerated
	private Button btnConfiguration;
	@AutoGenerated
	private VerticalLayout verticalLayout_1;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
				
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	@SuppressWarnings("deprecation")
	public MainToolBar() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		btnConfiguration.setStyleName(Button.STYLE_LINK);
		btnConfiguration.addStyleName("toplink-right");
		btnConfiguration.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {								
				ConfigurationView view = new ConfigurationView();
				MainApplication.get().addWorkbenchWindow("Configuration", view);	
				
				//MainApplication.get().setMainToolbarItem(MainApplication.MAIN_TOOLBAR_ITEM.CONFIGURATION);
				MainApplication.get().setMainToolbarItem(MainApplication.MAIN_TOOLBAR_ITEM.LOGOUT);
			}
		});
		
		btnLogout.setStyleName(Button.STYLE_LINK);
		btnLogout.addStyleName("toplink-right");
		btnLogout.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {				
				MainApplication.get().initializeLayout();
				MainApplication.get().setMainToolbarItem(MainApplication.MAIN_TOOLBAR_ITEM.LOGOUT);
			}
		});
		
		separator.setVisible(false);				
		
	}
		
	@Override
	protected void updateLabels() {
		btnConfiguration.setCaption(getI18N().getMessage("btnConfiguration.caption"));
		btnConfiguration.setDescription(getI18N().getMessage("btnConfiguration.description"));
		btnLogout.setCaption(getI18N().getMessage("btnLogout.caption"));
		btnLogout.setDescription(getI18N().getMessage("btnLogout.description"));
	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// verticalLayout_1
		verticalLayout_1 = new VerticalLayout();
		verticalLayout_1.setImmediate(false);
		verticalLayout_1.setWidth("100.0%");
		verticalLayout_1.setHeight("-1px");
		verticalLayout_1.setMargin(false);
		mainLayout.addComponent(verticalLayout_1);
		mainLayout.setExpandRatio(verticalLayout_1, 1.0f);
		mainLayout.setComponentAlignment(verticalLayout_1, new Alignment(34));
		
		// btnConfiguration
		btnConfiguration = new Button();
		btnConfiguration.setCaption("Configuration");
		btnConfiguration.setImmediate(false);
		btnConfiguration.setWidth("-1px");
		btnConfiguration.setHeight("-1px");
		mainLayout.addComponent(btnConfiguration);
		mainLayout.setComponentAlignment(btnConfiguration, new Alignment(34));
		
		// btnLogout
		btnLogout = new Button();
		btnLogout.setCaption("Logout");
		btnLogout.setImmediate(true);
		btnLogout.setWidth("-1px");
		btnLogout.setHeight("-1px");
		mainLayout.addComponent(btnLogout);
		mainLayout.setComponentAlignment(btnLogout, new Alignment(34));
		
		// separator
		separator = new VerticalLayout();
		separator.setImmediate(false);
		separator.setWidth("12px");
		separator.setHeight("100.0%");
		separator.setMargin(false);
		mainLayout.addComponent(separator);
		
		return mainLayout;
	}

}
