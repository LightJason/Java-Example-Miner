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

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * web socket configuration
 */
@Configuration
@EnableWebSocketMessageBroker
public class CWebSocketConfiguration implements WebSocketMessageBrokerConfigurer
{
    /**
     * beliefbase channel
     */
    public static final String BELIEFBASE = "/beliefbase";
    /**
     * world channel
     */
    public static final String WORLD = "/world";
    /**
     * miner channel
     */
    public static final String MINER = "/miner";

    /**
     * message channel
     */
    public static final String MESSAGE = "/message";

    @Override
    public void configureMessageBroker( final MessageBrokerRegistry p_config )
    {
        p_config.enableSimpleBroker( MESSAGE, WORLD, MINER, BELIEFBASE );
        p_config.setApplicationDestinationPrefixes( "/" );
    }

    @Override
    public void registerStompEndpoints( final StompEndpointRegistry p_registry )
    {
        p_registry.addEndpoint( "/mas" ).withSockJS();
    }

}
