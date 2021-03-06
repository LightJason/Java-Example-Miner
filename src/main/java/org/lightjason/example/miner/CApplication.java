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


package org.lightjason.example.miner;

import org.lightjason.example.miner.configuration.CSessionAgentSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Nonnull;


/**
 * main application
 */
//Checkstyle:OFF:HideUtilityClassConstructor
@SpringBootApplication
@EnableAutoConfiguration
public class CApplication
{

    /**
     * main method
     *
     * @param p_args arguments
     */
    //Checkstyle:OFF:UncommentedMain
    public static void main( @Nonnull final String[] p_args )
    {
        SpringApplication.run( CApplication.class, p_args );
    }
    //Checkstyle:ON:UncommentedMain

    /**
     * session storage of agent source code
     *
     * @return agent session object
     */
    @Bean
    @Scope( value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS )
    public CSessionAgentSource sessionAgent()
    {
        return new CSessionAgentSource();
    }

    // https://www.baeldung.com/spring-session
    // https://elements.heroku.com/addons/rediscloud
    // https://spring.io/projects/spring-session
    // https://www.javadevjournal.com/spring/spring-session/
    // https://codeboje.de/spring-session-tutorial/
    // https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html

    // https://reactstrap.github.io/s
    // https://spring.io/guides/tutorials/react-and-spring-data-rest/
    // https://codeboje.de/spring-session-tutorial/

    // https://www.baeldung.com/spring-security-session
    // https://www.novatec-gmbh.de/en/blog/session-handling-with-spring/
    // https://www.baeldung.com/spring-bean-scopes
    // https://www.baeldung.com/spring-mvc-session-attributes

    // https://www.baeldung.com/spring-boot-crud-thymeleaf
    // https://stackoverflow.com/questions/36531131/i18n-in-spring-boot-thymeleaf

}
//Checkstyle:ON:HideUtilityClassConstructor
