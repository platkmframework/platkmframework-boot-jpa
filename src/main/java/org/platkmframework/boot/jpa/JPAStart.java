package org.platkmframework.boot.jpa;

import org.platkmframework.annotation.HttpRest;
import org.platkmframework.annotation.db.NativeQuery;
import org.platkmframework.boot.BaseStart;
import org.platkmframework.boot.ioc.BootInversionOfControl;
import org.platkmframework.content.ObjectContainer;
import org.platkmframework.content.project.ContentPropertiesConstant;
import org.platkmframework.content.project.ProjectContent;
import org.platkmframework.core.rmi.RMIException;
import org.platkmframework.core.rmi.RMIServerManager;
import org.platkmframework.core.scheduler.SchedulerManager;
import org.platkmframework.doi.data.ObjectReferece;
import org.platkmframework.doi.exception.IoDCException;
import org.platkmframework.httpclient.proxy.HttpRestProxyProcessor;
import org.platkmframework.jpa.persistence.JPABootInversionOfControl;
import org.platkmframework.jpa.persistence.PersistenceManager;
import org.platkmframework.jpa.proxy.NativeQueryProxyProcessor;
import org.platkmframework.proxy.ProxyProcessorFactory;

public class JPAStart extends BaseStart {
	
	@Override
	public void start() throws IoDCException, RMIException {
		applyIoD(new JPABootInversionOfControl());
		initJson();
		initJPA();
		initProxyProcessorFactory();
		initBootInitializer();
	}
	
	private void initJPA() {
		PersistenceManager.instance().init();
	}

	@Override
	protected void applyIoD(BootInversionOfControl bootInversionOfControl ) throws IoDCException {
		
		String javaClassPath  = System.getProperty("java.class.path"); 
		String packagesPrefix = ProjectContent.instance().getAppProperties().getProperty(ContentPropertiesConstant.ORG_PLATKMFRAMEWORK_CONFIGURATION_PACKAGE_PREFIX);
		packagesPrefix+=",org.platkmframework";
		
		//CustomIoDprocess customIoDprocess = new CustomIoDprocess();
		ObjectReferece objectReferece = new ObjectReferece();
		objectReferece.setProp(ProjectContent.instance().getAppProperties());
		bootInversionOfControl.process(javaClassPath, packagesPrefix.split(","), objectReferece);
		ObjectContainer.instance().setReference(objectReferece);
	}
	
	@Override
	protected void initProxyProcessorFactory() throws RMIException {
		
		ProxyProcessorFactory.instance().register(HttpRest.class.getName(), new HttpRestProxyProcessor());
		ProxyProcessorFactory.instance().register(NativeQuery.class.getName(), new NativeQueryProxyProcessor());
		
		RMIServerManager.instance().runAllOnStart();
		SchedulerManager.instance().runAllOnStart();
				
	}
	
}
