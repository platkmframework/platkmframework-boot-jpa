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
package org.platkmframework.boot.jpa.server.runner;

import org.eclipse.jetty.webapp.WebAppContext;
import org.platkmframework.boot.jpa.server.filter.DataBaseFilter;
import org.platkmframework.boot.server.runner.StartServerProcessor;
import jakarta.servlet.Filter;

/**
*   Author:
*     Eduardo Iglesias
*   Contributors:
*   	Eduardo Iglesias - initial API and implementation
*/
public class JPAStartServerProcessor extends StartServerProcessor {

    /**
     * addSystemCustomFilter
     * @param webapp webapp
     * @param patterns patterns
     */
    public void addSystemCustomFilter(WebAppContext webapp, String[] patterns) {
        Filter filter = new DataBaseFilter();
        webapp.getServletHandler().addFilter(newFilterHolder(filter, true), newFilterMapping(filter, patterns));
    }

    /**
     * JPAStartServerProcessor
     */
    public JPAStartServerProcessor() {
        super();
    }
}
