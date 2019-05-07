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

package org.lightjason.example.miner.runtime;

import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;


/**
 * trader agent
 */
public final class CAgentTrader extends IBaseScenarioAgent<CAgentTrader>
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 1180220544453108361L;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_execution execution service
     */
    public CAgentTrader( @Nonnull final IAgentConfiguration<CAgentTrader> p_configuration, @Nonnull final ExecutorService p_execution )
    {
        super( p_configuration, p_execution );
    }

}
