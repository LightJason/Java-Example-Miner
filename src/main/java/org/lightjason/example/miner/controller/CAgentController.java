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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://spring.io/guides/tutorials/rest/

/**
 * agentspeak rest controller
 */
@RestController
@RequestMapping( "/mas/agent" )
public final class CAgentController
{
    /**
     * agent source session
     */
    @Resource( name = "sessionAgent" )
    private CSessionAgentSource m_sessionagent;


    /**
     * get all miners with source code
     *
     * @return map with name and source
     */
    @GetMapping( value = "/download/miner", produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, String> downloadMiner()
    {
        return m_sessionagent.getMiner();
    }

    /**
     * get all traders with source code
     *
     * @return map with name and source
     */
    @GetMapping( value = "/download/trader", produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, String> downloadTrader()
    {
        return m_sessionagent.getTrader();
    }

    /**
     * get environment with source code
     *
     * @return map with name and source
     */
    @GetMapping( value = "/download/environment", produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, String> downloadEnvironment()
    {
        return Stream.of( m_sessionagent.getEnvironment() ).collect( Collectors.toMap( i -> CSessionAgentSource.ENVIRONMENTNAME, i -> i ) );
    }



    /**
     * returns a list with existing actions of the miner agent
     *
     * @return action names
     */
    @GetMapping( value = "/action/miner", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> getActionsMiner()
    {
        return Collections.emptySet();
    }

    /**
     * returns a list with existing actions of the trader agent
     *
     * @return action names
     */
    @GetMapping( value = "/action/trader", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> getActionsTrader()
    {
        return Collections.emptySet();
    }

    /**
     * returns a list with existing actions of the environment agent
     *
     * @return action names
     */
    @GetMapping( value = "/action/environment", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> getActionsEnvironment()
    {
        return Collections.emptySet();
    }



    /**
     * returns a list with miner names
     *
     * @return list with miner names
     */
    @GetMapping( value = "/miners" )
    public Set<String> getMiners()
    {
        return m_sessionagent.getMiner().keySet();
    }

    /**
     * returns a list with trader names
     *
     * @return list with trader names
     */
    @GetMapping( value = "/traders" )
    public Set<String> getTraders()
    {
        return m_sessionagent.getTrader().keySet();
    }

    /**
     * returns a list with environemnt agent names
     *
     * @return list with environments agent names
     */
    @GetMapping( value = "/environments" )
    public String[] getEnvironments()
    {
        return Stream.of( CSessionAgentSource.ENVIRONMENTNAME ).toArray( String[]::new );
    }



    /**
     * creates a new empty miner if not exist
     *
     * @param p_name name
     */
    @PutMapping( value = "/miner/{name}" )
    public void createMiner( @PathVariable( name = "name" ) final String p_name )
    {
        m_sessionagent.getMiner().putIfAbsent( p_name, "" );
    }

    /**
     * creates a new empty miner if not exist
     *
     * @param p_name name
     */
    @PutMapping( value = "/trader/{name}" )
    public void createTrader( @PathVariable( name = "name" ) final String p_name )
    {
        m_sessionagent.getTrader().putIfAbsent( p_name, "" );
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
        m_sessionagent.getMiner().remove( p_name );
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
        m_sessionagent.getTrader().remove( p_name );
    }



    /**
     * returns the miners agent code
     *
     * @return miners and asl code
     */
    @GetMapping( value = "/miner/{name}", produces = MediaType.TEXT_PLAIN_VALUE )
    public String getSourceMiners( @PathVariable( name = "name" ) final String p_name )
    {
        return m_sessionagent.getMiner().get( p_name );
    }

    /**
     * returns the trader agent code
     *
     * @return trader and asl code
     */
    @GetMapping( value = "/trader/{name}", produces = MediaType.TEXT_PLAIN_VALUE )
    public String getSourceTraders( @PathVariable( name = "name" ) final String p_name )
    {
        return m_sessionagent.getTrader().get( p_name );
    }

    /**
     * returns the source of the environment agent
     *
     * @return agent environment asl code
     */
    @GetMapping( value = "/environment/{name}", produces = MediaType.TEXT_PLAIN_VALUE )
    public String getSourceEnvironment( @PathVariable( name = "name" ) final String p_name )
    {
        return m_sessionagent.getEnvironment();
    }



    /**
     * puts the source of a miner agent into the session
     *
     * @param p_name miner name
     * @param p_source miner source
     */
    @PutMapping( value = "/miner/{name}", consumes = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceMiner( @PathVariable( name = "name" ) final String p_name, @RequestBody final String p_source )
    {
        m_sessionagent.getMiner().put( p_name, p_source );
    }

    /**
     * puts the source of a trader agent into the session
     *
     * @param p_name trader name
     * @param p_source trader source
     */
    @PutMapping( value = "/trader/{name}", consumes = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceTrader( @PathVariable( name = "name" ) final String p_name, @RequestBody final String p_source )
    {
        m_sessionagent.getTrader().put( p_name, p_source );
    }

    /**
     * puts the source of the environment agent into the session
     *
     * @param p_source agent environemnt source
     */
    @PutMapping( value = "/environment/{name}", consumes = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceEnvironment( @PathVariable( name = "name" ) final String p_name, @RequestBody final String p_source )
    {
        m_sessionagent.setEnvironment( p_source );
    }

}
