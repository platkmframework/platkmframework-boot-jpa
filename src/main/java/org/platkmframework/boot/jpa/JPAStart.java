package org.platkmframework.boot.jpa;

import org.platkmframework.boot.BaseStart;
import org.platkmframework.content.ObjectContainer;
import org.platkmframework.content.project.ContentPropertiesConstant;
import org.platkmframework.content.project.ProjectContent;
import org.platkmframework.core.rmi.RMIException;
import org.platkmframework.doi.data.ObjectReferece;
import org.platkmframework.doi.exception.IoDCException;
import org.platkmframework.jpa.persistence.CustomIoDprocess;
import org.platkmframework.jpa.persistence.reader.PlatkmPersistenceFileParse;

public class JPAStart extends BaseStart {
	
	
	public void start() throws IoDCException, RMIException {
		applyIoD();
		initJson();
		PlatkmPersistenceFileParse.parse();
		initProxyProcessorFactory();
		initBootInitializer();
	}
	
	protected void applyIoD() throws IoDCException {
		
		String javaClassPath  = System.getProperty("java.class.path"); 
		String packagesPrefix = ProjectContent.instance().getAppProperties().getProperty(ContentPropertiesConstant.ORG_PLATKMFRAMEWORK_CONFIGURATION_PACKAGE_PREFIX);
		packagesPrefix+=",org.platkmframework";
		
		CustomIoDprocess customIoDprocess = new CustomIoDprocess();
		ObjectReferece objectReferece = new ObjectReferece();
		objectReferece.setProp(ProjectContent.instance().getAppProperties());
		customIoDprocess.process(javaClassPath, packagesPrefix.split(","), objectReferece);
		ObjectContainer.instance().setReference(objectReferece);
	}
}
