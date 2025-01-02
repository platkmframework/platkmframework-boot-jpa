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

/**
*   Author:
*     Eduardo Iglesias
*   Contributors:
*   	Eduardo Iglesias - initial API and implementation
*/
public class JPAStart extends BaseStart {

    /**
     * start
     * @throws IoDCException IoDCException
     * @throws RMIException RMIException
     */
    @Override
    public void start() throws IoDCException, RMIException {
        applyIoD(new JPABootInversionOfControl());
        initJson();
        initJPA();
        initProxyProcessorFactory();
        initBootInitializer();
    }

    /**
     * initJPA
     */
    private void initJPA() {
        PersistenceManager.instance().init();
    }

    /**
     * applyIoD
     * @param bootInversionOfControl bootInversionOfControl
     * @throws IoDCException IoDCException
     */
    @Override
    protected void applyIoD(BootInversionOfControl bootInversionOfControl) throws IoDCException {
        String javaClassPath = System.getProperty("java.class.path");
        String packagesPrefix = ProjectContent.instance().getAppProperties().getProperty(ContentPropertiesConstant.ORG_PLATKMFRAMEWORK_CONFIGURATION_PACKAGE_PREFIX);
        packagesPrefix += ",org.platkmframework";
        //CustomIoDprocess customIoDprocess = new CustomIoDprocess();
        ObjectReferece objectReferece = new ObjectReferece();
        objectReferece.setProp(ProjectContent.instance().getAppProperties());
        bootInversionOfControl.process(javaClassPath, packagesPrefix.split(","), objectReferece);
        ObjectContainer.instance().setReference(objectReferece);
    }

    /**
     * initProxyProcessorFactory
     * @throws RMIException RMIException
     */
    @Override
    protected void initProxyProcessorFactory() throws RMIException {
        ProxyProcessorFactory.instance().register(HttpRest.class.getName(), new HttpRestProxyProcessor());
        ProxyProcessorFactory.instance().register(NativeQuery.class.getName(), new NativeQueryProxyProcessor());
        RMIServerManager.instance().runAllOnStart();
        SchedulerManager.instance().runAllOnStart();
    }

    /**
     * JPAStart
     */
    public JPAStart() {
        super();
    }
}
