/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-19, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.example.miner.configuration;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * session agent asl source code storage
 */
public class CSessionAgentSource implements Serializable
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -8840787108768940215L;
    /**
     * asl code of the environment agent
     */
    private String m_evironment = "!run. \\n +!run <- generic/print('i am the environment agent').";
    /**
     * asl source code of the miner agents
     */
    private Map<String, String> m_miners = new ConcurrentHashMap<>();


    /**
     * ctor
     */
    public CSessionAgentSource()
    {
        m_miners.put( "Defaultminier", "!do.\\n+!+do <- generic/print('hello, i am a miner')." );
    }

    /**
     * returns the environment source
     *
     * @return source
     */
    public String getEnvironment()
    {
        return m_evironment;
    }

    /**
     * returns the miners source
     *
     * @return mapw ith miners and source
     */
    public Map<String, String> getMiners()
    {
        return m_miners;
    }

    /**
     * sets the environment asl source code
     *
     * @param p_source source
     */
    public void setEnvironment( @Nonnull final String p_source )
    {
        m_evironment = p_source;
    }
}
