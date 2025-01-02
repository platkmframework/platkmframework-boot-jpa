/**
 * ****************************************************************************
 *  Copyright(c) 2023 the original author Eduardo Iglesias Taylor.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  	 https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *  	Eduardo Iglesias Taylor - initial API and implementation
 * *****************************************************************************
 */
package org.platkmframework.jpa.persistence;

import java.lang.reflect.Field;
import org.apache.commons.lang3.StringUtils;
import org.platkmframework.boot.ioc.BootInversionOfControl;
import org.platkmframework.content.project.ProjectContent;
import org.platkmframework.doi.exception.IoDCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.PersistenceContext;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 */
public class JPABootInversionOfControl extends BootInversionOfControl {

    /**
     * JPABootInversionOfControl
     */
    private static Logger logger = LoggerFactory.getLogger(JPABootInversionOfControl.class);

    /**
     * JPABootInversionOfControl
     */
    public JPABootInversionOfControl() {
        super();
    }
    
    /**
     * checkCustomIoD
     * @param ob ob
     * @param f f
     * @throws IoDCException IoDCException
     */
    @Override
    protected void checkCustomIoD(Object ob, Field f) throws IoDCException {
        try {
            if (f.isAnnotationPresent(PersistenceContext.class)) {
                PersistenceContext persistenceContext = f.getAnnotation(PersistenceContext.class);
                if (StringUtils.isBlank(persistenceContext.unitName())) {
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
