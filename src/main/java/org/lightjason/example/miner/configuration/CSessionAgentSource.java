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
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;


/**
 * session agent asl source code storage
 */
public class CSessionAgentSource implements Serializable
{
    /**
     * name of the map key of the environment
     */
    public static final String ENVIRONMENTNAME = "World";
    /**
     * serial id
     */
    private static final long serialVersionUID = -8840787108768940215L;
    /**
     * asl source of environment agent
     */
    private String m_evironment = "!run. \n\n +!run <- .generic/print('i am the environment agent').";
    /**
     * asl source of miner agents
     */
    private Map<String, String> m_miner = Collections.synchronizedMap( new TreeMap<>( String.CASE_INSENSITIVE_ORDER ) );
    /**
     * asl source of trader agents
     */
    private Map<String, String> m_trader = Collections.synchronizedMap( new TreeMap<>( String.CASE_INSENSITIVE_ORDER ) );


    /**
     * ctor
     */
    public CSessionAgentSource()
    {
        m_miner.put( "Default",  "!run. \n\n +!+run <- .generic/print('hello, i am a miner agent')." );
        m_trader.put( "Default", "!run. \n\n +!+run <- .generic/print('hello, i am a trader agent')." );
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
     * returns the traders source
     *
     * @return map ith miners and source
     */
    public Map<String, String> getTrader()
    {
        return m_trader;
    }

    /**
     * returns the miners source
     *
     * @return map ith miners and source
     */
    public Map<String, String> getMiner()
    {
        return m_miner;
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
