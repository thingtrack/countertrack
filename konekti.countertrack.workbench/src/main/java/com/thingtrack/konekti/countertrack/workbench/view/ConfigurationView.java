package com.thingtrack.konekti.countertrack.workbench.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.thingtrack.countertrack.domain.Configuration;
import com.thingtrack.countertrack.domain.Context;
import com.thingtrack.countertrack.service.api.ConfigurationService;
import com.thingtrack.konekti.countertrack.workbench.MainApplication;
import com.thingtrack.konekti.countertrack.workbench.message.SensorMessage;
import com.thingtrack.konekti.countertrack.workbench.service.MetadataBaseService;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class ConfigurationView extends LocaleComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Panel pnMainPanel;
	@AutoGenerated
	private VerticalLayout verticalLayout_2;
	@AutoGenerated
	private HorizontalLayout toolBarLayout;
	@AutoGenerated
	private Button btnOk;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Label lblSeparator;
	@AutoGenerated
	private VerticalLayout configurationLayout;
	@AutoGenerated
	private VerticalLayout verticalLayout_3;
	@AutoGenerated
	private Button btnDownloadDatabase;
	@AutoGenerated
	private TextField topicNameText;
	@AutoGenerated
	private HorizontalLayout horizontalLayoutTopicURI;
	@AutoGenerated
	private TextField topicPortText;
	@AutoGenerated
	private Label lblSep;
	@AutoGenerated
	private TextField topicServerText;
	@AutoGenerated
	private TextField deviceNameText;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
		
	private SensorMessage sensorMessage;
    private ConfigurationService configurationService;
    private MetadataBaseService metadataBaseService;
    
    private Configuration deviceName;
    private Configuration integrationTopic;
    
    private static final String DEVICE_NAME_KEY = "DEVICE_NAME"; 
    private static final String INTEGRATION_TOPIC_KEY = "INTEGRATION_TOPIC";  
    
    private Context context;
    
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	@SuppressWarnings("deprecation")
	public ConfigurationView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here		
		pnMainPanel.addStyleName("login");
		
		pnMainPanel.addStyleName("login");
		deviceNameText.addStyleName("login");
		topicServerText.addStyleName("login");
		topicPortText.addStyleName("login");
		topicNameText.addStyleName("login");
		
		btnOk.setStyleName(Button.STYLE_LINK);
		btnOk.addStyleName("login");		
		btnOk.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				// save the device name
				if (deviceNameText.getValue() == null || deviceNameText.getValue().equals("")) {
					getApplication().getMainWindow().showNotification("The device name is ", Notification.TYPE_WARNING_MESSAGE);
					return;
					
				}
				
				deviceName.setValue(deviceNameText.getValue().toString());
				
				try {
					configurationService.save(deviceName);
				} catch (Exception e) {
					getApplication().getMainWindow().showNotification(getI18N().getMessage("errorSavingDeviceName.notification"), Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				
				context.setDeviceName(deviceNameText.getValue().toString());
				
				// save the integration topic
				if (topicServerText.getValue() != null && !topicServerText.getValue().equals("")) { 									
					String topic = topicServerText.getValue().toString() + ":" + topicPortText.getValue().toString() + "/" + topicNameText.getValue().toString();
					
					integrationTopic.setValue(topic);
																									
					try {
						configurationService.save(integrationTopic);
					} catch (Exception e) {
						getApplication().getMainWindow().showNotification(getI18N().getMessage("errorSavingTopicName.notification"), Notification.TYPE_WARNING_MESSAGE);
						return;
					}
					
					// refresh Message Listener configuration topi
					sensorMessage.setIntegrationTopic(topic);
					
					// refresh App Context
					context.setIntegrationTopic(topic);
				}
				
				getApplication().getMainWindow().removeWindow(ConfigurationView.this.getWindow());
			}
		});
		
		btnCancel.setStyleName(Button.STYLE_LINK);
		btnCancel.addStyleName("login");		
		btnCancel.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {;
				getApplication().getMainWindow().removeWindow(ConfigurationView.this.getWindow());
			
			}
		});
	
		// get services
		getServices();
		
		// load configuration data
		try {
			loadConfiguration();
		} catch (Exception e) {
			getApplication().getMainWindow().showNotification("Error loading configuration data", Notification.TYPE_WARNING_MESSAGE);
			return;
		}
		
		deviceNameText.setNullRepresentation("");		
		topicServerText.setNullRepresentation("");

		btnDownloadDatabase.setStyleName(Button.STYLE_LINK);
		btnDownloadDatabase.addStyleName("ok");
		btnDownloadDatabase.addListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				// get url database from datasource
				File database = new File(metadataBaseService.getUrl());
				final byte[] bDatabaseFile = new byte[(int) database.length()];
				
				//convert file into array of bytes
				FileInputStream databaseInputStream = null;
				try {
					databaseInputStream = new FileInputStream(database);
				} catch (FileNotFoundException e) {
					getApplication().getMainWindow().showNotification(getI18N().getMessage("fileNotFound.notification"), Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				
				try {
					databaseInputStream.read(bDatabaseFile);
					databaseInputStream.close();
				} catch (IOException e) {
					getApplication().getMainWindow().showNotification(getI18N().getMessage("convertFile.notification"), Notification.TYPE_WARNING_MESSAGE);
					return;
				}
			    
				// download database from web
				StreamSource ssDatabase = new StreamSource() {
			            InputStream is = new ByteArrayInputStream(bDatabaseFile);

			            @Override
			            public InputStream getStream() {
			                return is;
			            }
			    };
			     			     
			    StreamResource sr = new StreamResource(ssDatabase, "countertrack.db", getApplication());
			    getApplication().getMainWindow().open(sr, "_blank");
			}

		});
				
	}
		
	private void getServices() {
		this.sensorMessage = MainApplication.get().getSensorMessage();
		this.configurationService = MainApplication.get().getConfigurationService();
		this.metadataBaseService = MainApplication.get().getMetadataBaseService();
		this.context = MainApplication.get().getAppContext();
	}
	
	private void loadConfiguration() throws Exception {
		deviceName = configurationService.getByKey(DEVICE_NAME_KEY);		
		deviceNameText.setValue(deviceName.getValue());
		context.setDeviceName(deviceName.getValue());
		
		integrationTopic = configurationService.getByKey(INTEGRATION_TOPIC_KEY);

		if (integrationTopic.getValue() != null) {
			String[] uri = integrationTopic.getValue().toString().split("/");
						
			topicNameText.setValue(uri[1]);
			
			String[] uriTopic = uri[0].split(":");
			
			topicServerText.setValue(uriTopic[0]);
			topicPortText.setValue(uriTopic[1]);
			
			context.setIntegrationTopic(integrationTopic.getValue());
		}
	}
	
	@Override
	protected void updateLabels() {
		deviceNameText.setCaption(getI18N().getMessage("deviceNameText.caption"));
		topicServerText.setCaption(getI18N().getMessage("topicServerText.caption"));
		topicPortText.setCaption(getI18N().getMessage("topicPortText.caption"));
		topicNameText.setCaption(getI18N().getMessage("topicNameText.caption"));
		
		btnOk.setCaption(getI18N().getMessage("btnAcept.caption"));
		btnOk.setDescription(getI18N().getMessage("btnAcept.description"));
		btnCancel.setCaption(getI18N().getMessage("btnCancel.caption"));
		btnCancel.setDescription(getI18N().getMessage("btnCancel.description"));
		btnDownloadDatabase.setCaption(getI18N().getMessage("btnDownloadDatabase.caption"));
		btnDownloadDatabase.setDescription(getI18N().getMessage("btnDownloadDatabase.description"));
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		// pnMainPanel
		pnMainPanel = buildPnMainPanel();
		mainLayout.addComponent(pnMainPanel);
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildPnMainPanel() {
		// common part: create layout
		pnMainPanel = new Panel();
		pnMainPanel.setImmediate(false);
		pnMainPanel.setWidth("360px");
		pnMainPanel.setHeight("-1px");
		
		// verticalLayout_2
		verticalLayout_2 = buildVerticalLayout_2();
		pnMainPanel.setContent(verticalLayout_2);
		
		return pnMainPanel;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_2() {
		// common part: create layout
		verticalLayout_2 = new VerticalLayout();
		verticalLayout_2.setImmediate(false);
		verticalLayout_2.setWidth("100.0%");
		verticalLayout_2.setHeight("-1px");
		verticalLayout_2.setMargin(true);
		
		// configurationLayout
		configurationLayout = buildConfigurationLayout();
		verticalLayout_2.addComponent(configurationLayout);
		
		// lblSeparator
		lblSeparator = new Label();
		lblSeparator.setImmediate(false);
		lblSeparator.setWidth("100.0%");
		lblSeparator.setHeight("-1px");
		lblSeparator.setValue("<hr/>");
		lblSeparator.setContentMode(3);
		verticalLayout_2.addComponent(lblSeparator);
		verticalLayout_2.setComponentAlignment(lblSeparator, new Alignment(9));
		
		// toolBarLayout
		toolBarLayout = buildToolBarLayout();
		verticalLayout_2.addComponent(toolBarLayout);
		verticalLayout_2
				.setComponentAlignment(toolBarLayout, new Alignment(48));
		
		return verticalLayout_2;
	}

	@AutoGenerated
	private VerticalLayout buildConfigurationLayout() {
		// common part: create layout
		configurationLayout = new VerticalLayout();
		configurationLayout.setImmediate(false);
		configurationLayout.setWidth("100.0%");
		configurationLayout.setHeight("-1px");
		configurationLayout.setMargin(false);
		configurationLayout.setSpacing(true);
		
		// deviceNameText
		deviceNameText = new TextField();
		deviceNameText.setCaption("Device Name");
		deviceNameText.setImmediate(false);
		deviceNameText.setWidth("320px");
		deviceNameText.setHeight("40px");
		deviceNameText.setRequired(true);
		configurationLayout.addComponent(deviceNameText);
		
		// horizontalLayoutTopicURI
		horizontalLayoutTopicURI = buildHorizontalLayoutTopicURI();
		configurationLayout.addComponent(horizontalLayoutTopicURI);
		
		// topicNameText
		topicNameText = new TextField();
		topicNameText.setCaption("Topic Name");
		topicNameText.setImmediate(false);
		topicNameText.setWidth("320px");
		topicNameText.setHeight("40px");
		configurationLayout.addComponent(topicNameText);
		
		// verticalLayout_3
		verticalLayout_3 = buildVerticalLayout_3();
		configurationLayout.addComponent(verticalLayout_3);
		
		return configurationLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayoutTopicURI() {
		// common part: create layout
		horizontalLayoutTopicURI = new HorizontalLayout();
		horizontalLayoutTopicURI.setImmediate(false);
		horizontalLayoutTopicURI.setWidth("-1px");
		horizontalLayoutTopicURI.setHeight("-1px");
		horizontalLayoutTopicURI.setMargin(false);
		
		// topicServerText
		topicServerText = new TextField();
		topicServerText.setCaption("Server");
		topicServerText.setImmediate(false);
		topicServerText.setWidth("250px");
		topicServerText.setHeight("40px");
		horizontalLayoutTopicURI.addComponent(topicServerText);
		
		// lblSep
		lblSep = new Label();
		lblSep.setImmediate(false);
		lblSep.setWidth("10px");
		lblSep.setHeight("-1px");
		lblSep.setValue("<h2>:</h2>");
		lblSep.setContentMode(3);
		horizontalLayoutTopicURI.addComponent(lblSep);
		horizontalLayoutTopicURI.setComponentAlignment(lblSep,
				new Alignment(48));
		
		// topicPortText
		topicPortText = new TextField();
		topicPortText.setCaption("Port");
		topicPortText.setImmediate(false);
		topicPortText.setWidth("60px");
		topicPortText.setHeight("40px");
		horizontalLayoutTopicURI.addComponent(topicPortText);
		
		return horizontalLayoutTopicURI;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_3() {
		// common part: create layout
		verticalLayout_3 = new VerticalLayout();
		verticalLayout_3.setImmediate(false);
		verticalLayout_3.setWidth("320px");
		verticalLayout_3.setHeight("100px");
		verticalLayout_3.setMargin(false);
		
		// btnDownloadDatabase
		btnDownloadDatabase = new Button();
		btnDownloadDatabase.setCaption("Get Database");
		btnDownloadDatabase.setImmediate(true);
		btnDownloadDatabase.setWidth("320px");
		btnDownloadDatabase.setHeight("40px");
		verticalLayout_3.addComponent(btnDownloadDatabase);
		verticalLayout_3.setComponentAlignment(btnDownloadDatabase,
				new Alignment(48));
		
		return verticalLayout_3;
	}

	@AutoGenerated
	private HorizontalLayout buildToolBarLayout() {
		// common part: create layout
		toolBarLayout = new HorizontalLayout();
		toolBarLayout.setImmediate(false);
		toolBarLayout.setWidth("100.0%");
		toolBarLayout.setHeight("65px");
		toolBarLayout.setMargin(false);
		toolBarLayout.setSpacing(true);
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancel");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		toolBarLayout.addComponent(btnCancel);
		toolBarLayout.setExpandRatio(btnCancel, 1.0f);
		toolBarLayout.setComponentAlignment(btnCancel, new Alignment(34));
		
		// btnOk
		btnOk = new Button();
		btnOk.setCaption("Ok");
		btnOk.setImmediate(true);
		btnOk.setWidth("-1px");
		btnOk.setHeight("-1px");
		toolBarLayout.addComponent(btnOk);
		toolBarLayout.setComponentAlignment(btnOk, new Alignment(34));
		
		return toolBarLayout;
	}

}