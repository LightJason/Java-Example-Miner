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

package org.lightjason.example.miner.controller;

import org.lightjason.example.miner.configuration.CSessionAgentSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

// https://spring.io/guides/tutorials/rest/

/**
 * agentspeak rest controller
 */
@RestController
@RequestMapping( "/agent" )
public final class CAgentController
{
    /**
     * agent source session
     */
    @Resource( name = "sessionAgent" )
    private CSessionAgentSource m_sessionagent;



    /**
     * creates a new empty miner if not exist
     *
     * @param p_name name
     */
    @PutMapping( value = "/miner/{name}" )
    public void createMiner( @PathVariable( name = "name" ) final String p_name )
    {
        m_sessionagent.getMiners().putIfAbsent( p_name, "" );
    }

    /**
     * creates a new empty miner if not exist
     *
     * @param p_name name
     */
    @PutMapping( value = "/trader/{name}" )
    public void createTrader( @PathVariable( name = "name" ) final String p_name )
    {
        m_sessionagent.getTraders().putIfAbsent( p_name, "" );
    }



    /**
     * deletes a miner
     *
     * @param p_name name
     * @todo error handling if only one agent exist
     */
    @DeleteMapping( value = "/miner/{name}" )
    public void deleteMiner( @PathVariable( name = "name" ) final String p_name )
    {
        m_sessionagent.getMiners().remove( p_name );
    }

    /**
     * deletes a trader
     *
     * @param p_name name
     * @todo error handling if only one agent exist
     */
    @DeleteMapping( value = "/trader/{name}" )
    public void deleteTrader( @PathVariable( name = "name" ) final String p_name )
    {
        m_sessionagent.getTraders().remove( p_name );
    }



    /**
     * returns a list with miner names
     *
     * @return list with miner names
     */
    @GetMapping( value = "/miners" )
    public Set<String> getMiners()
    {
        return m_sessionagent.getMiners().keySet();
    }

    /**
     * returns a list with trader names
     *
     * @return list with trader names
     */
    @GetMapping( value = "/traders" )
    public Set<String> getTraders()
    {
        return m_sessionagent.getTraders().keySet();
    }



    /**
     * returns a list with existing actions of the miner agent
     *
     * @return action names
     */
    @RequestMapping( value = "/action/miner", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> getActionsMiner()
    {
        return Collections.emptySet();
    }

    /**
     * returns a list with existing actions of the trader agent
     *
     * @return action names
     */
    @RequestMapping( value = "/action/trader", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> getActionsTrader()
    {
        return Collections.emptySet();
    }

    /**
     * returns a list with existing actions of the environment agent
     *
     * @return action names
     */
    @RequestMapping( value = "/action/environment", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> getActionsEnvironment()
    {
        return Collections.emptySet();
    }



    /**
     * returns the miners agent code
     *
     * @return miners and asl code
     */
    @GetMapping( value = "/source/miner/{name}", produces = MediaType.TEXT_PLAIN_VALUE )
    public String getSourceMiners( @PathVariable( name = "name" ) final String p_name )
    {
        return m_sessionagent.getMiners().get( p_name );
    }

    /**
     * returns the trader agent code
     *
     * @return trader and asl code
     */
    @GetMapping( value = "/source/trader/{name}", produces = MediaType.TEXT_PLAIN_VALUE )
    public String getSourceTraders( @PathVariable( name = "name" ) final String p_name )
    {
        return m_sessionagent.getTraders().get( p_name );
    }

    /**
     * returns the source of the environment
     *
     * @return environment asl code
     */
    @GetMapping( value = "/source/environment", produces = MediaType.TEXT_PLAIN_VALUE )
    public String getSourceEnvironment()
    {
        return m_sessionagent.getEnvironment();
    }



    /**
     * puts the source of a miner agent into the session
     *
     * @param p_name miner name
     * @param p_source miner source
     */
    @PutMapping( value = "/source/miner/{name}", consumes = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceMiner( @PathVariable( name = "name" ) final String p_name, @RequestBody final String p_source )
    {
        m_sessionagent.getMiners().put( p_name, p_source );
    }

    /**
     * puts the source of a trader agent into the session
     *
     * @param p_name trader name
     * @param p_source trader source
     */
    @PutMapping( value = "/source/trader/{name}", consumes = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceTrader( @PathVariable( name = "name" ) final String p_name, @RequestBody final String p_source )
    {
        m_sessionagent.getTraders().put( p_name, p_source );
    }

    /**
     * puts the source of the environment into the session
     *
     * @param p_source environemnt source
     */
    @PutMapping( value = "/source/environment", consumes = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceEnvironment( @RequestBody final String p_source )
    {
        m_sessionagent.setEnvironment( p_source );
    }

}
