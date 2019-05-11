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

import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.ExecutorService;


/**
 * environment agent
 */
@IAgentAction
public final class CAgentEnvironment extends IBaseScenarioAgent
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 5950067237160399078L;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_agentstorage agent storage
     * @param p_runtime execution runtime
     */
    private CAgentEnvironment( @Nonnull final IAgentConfiguration<IScenarioAgent> p_configuration, @Nonnull final Set<IScenarioAgent> p_agentstorage,
                               @Nonnull final ExecutorService p_runtime )
    {
        super( p_configuration, p_agentstorage, p_runtime );
    }

    /**
     * get agent energy
     *
     * @param p_agent agent
     * @return engery level
     */
    @IAgentActionName( name = "energy/get" )
    private Number getEnergy( @Nonnull final IScenarioAgent p_agent )
    {
        return p_agent.get();
    }

    /**
     * take energy
     *
     * @param p_agent agent
     * @param p_value value
     */
    @IAgentActionName( name = "energy/take" )
    private void takeEnergy( @Nonnull final IScenarioAgent p_agent, @Nonnull final Number p_value )
    {
        p_agent.accept( i -> i.doubleValue() - p_value.doubleValue() );
        this.accept( i -> i.doubleValue() + p_value.doubleValue() );
    }

    /**
     * add energy
     *
     * @param p_agent agent
     * @param p_value value
     */
    @IAgentActionName( name = "energy/add" )
    private void addEnergy( @Nonnull final IScenarioAgent p_agent, @Nonnull final Number p_value )
    {
        this.accept( i -> i.doubleValue() - p_value.doubleValue() );
        p_agent.accept( i -> i.doubleValue() + p_value.doubleValue() );
    }

    /**
     * agent generator
     */
    public static final class CGenerator extends IBaseScenarioAgentGenerator
    {
        /**
         * ctor
         *
         * @param p_asl asl string
         * @param p_actions actions
         * @param p_lambda lambdas
         * @param p_agentstorage agent storage
         * @param p_pool execution runtime
         */
        public CGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                           @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final Set<IScenarioAgent> p_agentstorage,
                           @Nonnull final ExecutorService p_pool )
        {
            super( p_asl, p_actions, p_lambda, p_agentstorage, p_pool );
        }

        @Nonnull
        @Override
        public IScenarioAgent generatesingle( @Nullable final Object... p_objects )
        {
            return new CAgentEnvironment( m_configuration, m_agentstorage, m_runtime );
        }
    }
}
