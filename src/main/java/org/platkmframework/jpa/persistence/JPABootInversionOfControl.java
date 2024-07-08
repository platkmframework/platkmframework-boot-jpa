package org.platkmframework.jpa.persistence;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.platkmframework.boot.ioc.BootInversionOfControl;
import org.platkmframework.content.project.ProjectContent;
import org.platkmframework.doi.exception.IoDCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.PersistenceContext;

public class JPABootInversionOfControl extends BootInversionOfControl {

	private static Logger logger = LoggerFactory.getLogger(JPABootInversionOfControl.class);
	
	@Override
	protected void checkCustomIoD(Object ob, Field f) throws IoDCException {

		try {
			if(f.isAnnotationPresent(PersistenceContext.class)) { 
				PersistenceContext persistenceContext = f.getAnnotation(PersistenceContext.class);
				if(StringUtils.isBlank(persistenceContext.unitName())) {
					logger.error("debe poner el nombre de la unidad en el fichero de configuración");
					System.exit(-1);
				}  
				 
				boolean accessValue = f.canAccess(ob);
				f.setAccessible(true);
				f.set(ob, new PlatkmEntityManagerProxy(ProjectContent.instance().parseValue(persistenceContext.unitName())));
				f.setAccessible(accessValue);
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e) { 
			e.printStackTrace();
			throw new IoDCException(e.getMessage());
		} 
	}
}


