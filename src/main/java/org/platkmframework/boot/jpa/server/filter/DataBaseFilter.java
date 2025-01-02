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
package org.platkmframework.boot.jpa.server.filter;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.platkmframework.jpa.persistence.PersistenceManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 */
public class DataBaseFilter implements Filter {

    /**
     * DataBaseFilter
     */
    private static Logger logger = LoggerFactory.getLogger(DataBaseFilter.class);

    /**
     * DataBaseFilter
     */
    public DataBaseFilter() {
        super();
    }
    
    /**
     * destroy
     */
    @Override
    public void destroy() {
        /*  document why this method is empty */
    }

    /**
     * doFilter
     * @param request request
     * @param response response
     * @param chain chain
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            logger.info(req.getPathInfo());
            PersistenceManager.instance().begin();
            chain.doFilter(request, resp);
            PersistenceManager.instance().commit();
        } catch (Exception e) {
            PersistenceManager.instance().rollback();
            throw e;
        } finally {
            PersistenceManager.instance().close();
        }
    }

    /**
     * init
     * @param arg0 arg0
     * @throws ServletException ServletException
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        /* document why this method is empty */
    }


}
