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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.http.MediaType;

import javax.annotation.Resource;


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
    CSessionAgentSource m_sessionagent;

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
     * returns a list with existing actions of the miner agent
     *
     * @return action name
     */
    @RequestMapping( value = "/action/miner", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> mineractions()
    {
        return Collections.emptySet();
    }

    /**
     * returns a list with existing actions of the world agent
     *
     * @return action name
     */
    @RequestMapping( value = "/action/world", produces = MediaType.APPLICATION_JSON_VALUE )
    public Collection<String> worldactions()
    {
        return Collections.emptySet();
    }

    /**
     * returns the miners agent code
     *
     * @return map with miners and asl code
     */
    @GetMapping( value = "/source/miner/{name}", produces = MediaType.APPLICATION_JSON_VALUE )
    public String getSourceMiners( @PathVariable( name = "name" ) String p_name )
    {
        return m_sessionagent.getMiners().get( p_name );
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
     * puts the source of the environment into the session
     *
     * @param p_source environemnt source
     */
    @PutMapping( value = "/source/environment", produces = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceEnvironment( @RequestBody String p_source )
    {
        m_sessionagent.setEnvironment( p_source );
    }

    /**
     * puts the source of a miner agent into the session
     *
     * @param p_name miner name
     * @param p_source miner source
     */
    @PutMapping( value = "/source/miner/{name}", consumes = MediaType.TEXT_PLAIN_VALUE )
    public void setSourceEnvironment( @PathVariable( name = "name" ) String p_name, @RequestBody String p_source )
    {
        m_sessionagent.getMiners().put( p_name, p_source );
    }

}
