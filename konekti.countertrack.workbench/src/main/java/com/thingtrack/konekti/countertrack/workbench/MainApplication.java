package com.thingtrack.konekti.countertrack.workbench;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.dellroad.stuff.vaadin.ContextApplication;
import org.dellroad.stuff.vaadin.SpringContextApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.vaadin.artur.icepush.ICEPush;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.ResourceBundleI18N;

import com.thingtrack.konekti.countertrack.workbench.message.SensorMessage;
import com.thingtrack.konekti.countertrack.workbench.service.MetadataBaseService;
import com.thingtrack.konekti.countertrack.workbench.view.ConfirmationView;
import com.thingtrack.konekti.countertrack.workbench.view.ForgotView;
import com.thingtrack.konekti.countertrack.workbench.view.RegistrationView;
import com.thingtrack.konekti.countertrack.workbench.view.SecurityView;
import com.thingtrack.konekti.countertrack.workbench.view.WorkbenchView;
import com.thingtrack.konekti.countertrack.workbench.view.ConfirmationView.ClickCancelButtonListener;
import com.thingtrack.konekti.countertrack.workbench.view.ConfirmationView.ClickLoginButtonListener;
import com.thingtrack.konekti.countertrack.workbench.view.ForgotView.ClickConfirmForgotButtonListener;
import com.thingtrack.konekti.countertrack.workbench.view.RegistrationView.ClickConfirmRegistrationButtonListener;
import com.thingtrack.konekti.countertrack.workbench.view.SecurityView.ClickForgotButtonListener;
import com.thingtrack.konekti.countertrack.workbench.view.SecurityView.ClickRegistrationButtonListener;
import com.thingtrack.countertrack.domain.Configuration;
import com.thingtrack.countertrack.domain.Context;
import com.thingtrack.countertrack.domain.Counter;
import com.thingtrack.countertrack.domain.User;
import com.thingtrack.countertrack.service.api.ConfigurationService;
import com.thingtrack.countertrack.service.api.UserService;
import com.thingtrack.countertrack.service.api.CounterService;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ParameterHandler;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class MainApplication extends SpringContextApplication implements ParameterHandler, MessageListener, ExceptionListener, Serializable {
	private MainWindow window;
	
	private VerticalLayout mainLayout;
		
	private SecurityView securityView;
	private WorkbenchView workbenchView;

	private User user;

	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CounterService counterService;
	
	@Autowired
	private SensorMessage sensorMessage;
	
	@Autowired
	private MetadataBaseService metadataBaseService;
	
	@Autowired
	private Context context;
	
	@Autowired
	@Qualifier("jmsConsumerConnectionFactory")
	private SingleConnectionFactory jmsConsumerConnectionFactory;
	private final static String TOPIC = "countertrack.jms.topic.web";
	
	private MAIN_TOOLBAR_ITEM MainToolbarItem;
	
	private I18N i18n;
		
	private ICEPush pusher = new ICEPush();
	
	private static final String DEVICE_NAME_KEY = "DEVICE_NAME";
	private static final String INTEGRATION_TOPIC_KEY = "INTEGRATION_TOPIC";
	
	public enum MAIN_TOOLBAR_ITEM {
		ALARM,
		CONFIGURATION,
		HELP,
		LOGOUT
		
	}

	private final String DEF_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private final String BASE_URI = "http://localhost:8080/konekti.countertrack.workbench";
	
	@Override
	protected void initSpringApplication(ConfigurableWebApplicationContext context) {
		setTheme("countertrack");

		Locale esLocale = new Locale("es");
		Locale enLocale = new Locale("en");
		Locale zhLocale = new Locale("zh");
		
		i18n = new ResourceBundleI18N("com/thingtrack/konekti/countertrack/workbench/i18n/messages", esLocale, enLocale, zhLocale);
		i18n.setCurrentLocale(zhLocale);
		
	    window = new MainWindow("Konekti CounterTrack Workbench", i18n);
	    setMainWindow(window);
	    
		subscribeTopic();
		
		buildMainLayout();
        
	}
    
	private void subscribeTopic() {
		if (jmsConsumerConnectionFactory == null)
			return;
		
		jmsConsumerConnectionFactory.setReconnectOnException(true);
		
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		
		simpleMessageListenerContainer.setConnectionFactory(jmsConsumerConnectionFactory);
		simpleMessageListenerContainer.setPubSubDomain(true);
		simpleMessageListenerContainer.setDestinationName(TOPIC);
		simpleMessageListenerContainer.setMessageListener(this);
		simpleMessageListenerContainer.setExceptionListener(this);
		
		simpleMessageListenerContainer.start();
	}
	
    public static MainApplication get() {
        return ContextApplication.get(MainApplication.class);
        
    }

    public SensorMessage getSensorMessage() {
    	return get().sensorMessage;
    	        
    }
    
    public User getUser() {
    	return get().user;
    	        
    }
    
    public void setUser() {
    	get().user = user;
    	        
    }
    
    public String getBaseURI() {
    	return get().BASE_URI;
    	        
    }
    public String getDefDateFormat() {
    	return get().DEF_DATE_FORMAT;
    	        
    }
    public I18N getI18n() {
    	return get().i18n;
    	        
    }
    public ConfigurationService getConfigurationService() {
    	return get().configurationService;
    	        
    }
    public UserService getUserService() {
    	return get().userService;
    	        
    }
    public CounterService getCounterService() {
    	return get().counterService;
    	        
    }
    public MetadataBaseService getMetadataBaseService() {
    	return get().metadataBaseService;
    	        
    }
    
    public MAIN_TOOLBAR_ITEM getMainToolbarItem() {
    	return get().MainToolbarItem;
    	        
    }
    public void setMainToolbarItem(MAIN_TOOLBAR_ITEM item) {
    	get().MainToolbarItem = item;
    	        
    }   
    
    public Context getAppContext() {
    	return get().context;
    	        
    }    
 
    public void logonWorkbench() {
    	workbenchView = new WorkbenchView(pusher);
    	workbenchView.setSizeFull();
    	
	    mainLayout.removeAllComponents();
	    mainLayout.addComponent(workbenchView);
    }
    
    public void initializeLayout() {
    	securityView = new SecurityView();
    	
    	securityView.addListenerRegistrationButton(new ClickRegistrationButtonListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				RegistrationView registrationView = new RegistrationView();
								
				registrationView.addListenerConfirmRegistrationButton(new ClickConfirmRegistrationButtonListener() {					
					@Override
					public void buttonClick(ClickEvent event) {
						mainLayout.removeAllComponents();
						mainLayout.addComponent(securityView);
						
					}
				});
				
				mainLayout.removeAllComponents();
				mainLayout.addComponent(registrationView);
			}
		});
    	
    	securityView.addListenerForgotButton(new ClickForgotButtonListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				ForgotView forgotView = new ForgotView();
				
				forgotView.addListenerConfirmForgotButton(new ClickConfirmForgotButtonListener() {					
					@Override
					public void buttonClick(ClickEvent event) {
						mainLayout.removeAllComponents();
						mainLayout.addComponent(securityView);
						
					}
				});				
				
				mainLayout.removeAllComponents();
				mainLayout.addComponent(forgotView);							
				
			}
		});
    	
    	// initialize context
    	intializeContext();
    	
	    mainLayout.removeAllComponents();
	    mainLayout.addComponent(securityView);
	    
    }
    public void removeAllWorkbenchComponents() {
    	workbenchView.getSensorManagementLayout().removeAllComponents();
    	
    }
    public void addWorkbenchView(CustomComponent view) {
    	workbenchView.getSensorManagementLayout().addComponent(view);
    	
    }
    public void addWorkbenchWindow(String caption, CustomComponent view) {
    	Window workbenchWindow = new Window(caption, view);
    	workbenchWindow.setResizable(false);
    	workbenchWindow.setClosable(false);
    	workbenchWindow.setModal(true);
    	workbenchWindow.center();
    	workbenchWindow.setBorder(Window.BORDER_MINIMAL);
    	
    	getMainWindow().addWindow(workbenchWindow);
    	
    }
    
    private void intializeContext() {
		Configuration deviceNameConfiguration = null;
		
		try {
			deviceNameConfiguration = configurationService.getByKey(DEVICE_NAME_KEY);
			context.setDeviceName(deviceNameConfiguration.getValue());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		 
		Configuration integrationTopicConfiguration = null;
		
		try {
			integrationTopicConfiguration = configurationService.getByKey(INTEGRATION_TOPIC_KEY);
			context.setIntegrationTopic(integrationTopicConfiguration.getValue());
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildMainLayout() {
		// set Main Counter Track Layout
		mainLayout = (VerticalLayout)window.getContent();
	    mainLayout.setSizeFull();
	    mainLayout.setMargin(true);	    
	    mainLayout.setStyleName("applicationBackground");

    	// set URI parameter handler
	    window.addParameterHandler(this);
	    
	    initializeLayout();
	    
	}

	@Override
	public void handleParameters(Map<String, String[]> parameters) {
		if (parameters.containsKey("registrationCode")) {
			String registrationCode = ((String[])parameters.get("registrationCode"))[0];
			
			User userRegistered = null;
			try {
				userRegistered = userService.getUserByRegistrationCode(registrationCode);
				
				// activate the user
				userRegistered.setActive(true);
				userService.save(userRegistered);
			} catch (Exception e) {
				
			}
			
			// show confirmation view and login
			ConfirmationView confirmationView = new ConfirmationView(userRegistered);			
			confirmationView.addListenerLoginButton(new ClickLoginButtonListener() {				
				@Override
				public void buttonClick(ClickEvent event) {
					getMainWindow().open(new ExternalResource(BASE_URI));
					
				}
			});					
				
			confirmationView.addListenerCancelButton(new ClickCancelButtonListener() {				
				@Override
				public void buttonClick(ClickEvent event) {
					getMainWindow().open(new ExternalResource(BASE_URI));
					
				}
			});
			
			mainLayout.removeAllComponents();
			mainLayout.addComponent(confirmationView);
		}
	}

	@Override
	public void onMessage(Message message) {
		// parser counter register
		Counter counter = null;
		
		try {
			counter = parseCounter(message);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// refresh counts
		if (workbenchView != null) {
			workbenchView.refreshCounts(counter);
		
			// Push the changes to WorkbenchView
			pusher.push();
		}
		
	}

	private Counter parseCounter(Message message) throws JMSException, UnsupportedEncodingException {
		Counter counter = null;
		
		if (message != null && message instanceof BytesMessage) {
			BytesMessage bytesMessage = (BytesMessage) message;
            String payload = null;
			
			byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(bytes);
			payload = new String(bytes, "UTF-8");				

			String[] nodes = payload.split("-");
			
			counter = new Counter();
			counter.setDeviceName(nodes[0]);
			counter.setWay(Integer.parseInt(nodes[1]));
			
			long dateLong = Long.parseLong(nodes[2]);
			dateLong *= 1000;			
			Date countDate = new Date(dateLong);
			
			counter.setCountDate(countDate);
        } 
				
		return counter;
	}
	
	@Override
	public void onException(JMSException jMSException) {
		getMainWindow().open(new ExternalResource(jMSException.getMessage()));
		
	}

}
