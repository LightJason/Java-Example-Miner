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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

import org.springframework.http.MediaType;


/**
 * agentspeak rest controller
 */
@RestController
@RequestMapping( "/agentspeak" )
public final class CAgentSpeakController
{

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

}
