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

import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.fuzzy.IFuzzyValue;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;


/**
 * runtime class
 */
public final class CRuntime
{
    // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/concurrent/ScheduledExecutorService.html
    // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/concurrent/Executors.html

    /**
     * execution service
     */
    private final ExecutorService m_runtime = Executors.newWorkStealingPool();
    /**
     * trader
     */
    private final Set<IEnergyAgent> m_trader = new CopyOnWriteArraySet<>();
    /**
     * miner
     */
    private final Set<IEnergyAgent> m_miner = new CopyOnWriteArraySet<>();


    /**
     * ctor
     *
     * @param p_aslenvironment environment asl
     * @param p_aslminer miner map asl
     * @param p_asltrader trader map asl
     * @throws IOException on encoding error
     */
    public CRuntime( @Nonnull final String p_aslenvironment, @Nonnull final Map<String, String> p_aslminer,
                     @Nonnull final Map<String, String> p_asltrader ) throws IOException
    {
        // build action list
        // build lambda list
        // build generators by parsing source code -> action for trader & miner generating
        // execute environment -> environment generates world and m_agents

        new CActionAgents( CPath.of( "traders" ), m_trader );
        new CActionAgents( CPath.of( "miners" ), m_miner );

        // parser and run environment
        m_runtime.submit(
            Objects.requireNonNull(
                new CAgentEnvironment.CGenerator( p_aslenvironment, IActionGenerator.EMPTY, ILambdaStreamingGenerator.EMPTY ).generatesingle()
            )
        );
    }

    /**
     * action returns the agent list
     */
    private static final class CActionAgents extends IBaseAction
    {
        /**
         * action name
         */
        private final IPath m_name;
        /**
         * agent set
         */
        private final Set<IEnergyAgent> m_agents;

        /**
         * ctor
         *
         * @param p_name action name
         * @param p_agents agent set
         */
        CActionAgents( @Nonnull final IPath p_name, @Nonnull final Set<IEnergyAgent> p_agents )
        {
            m_name = p_name;
            m_agents = p_agents;
        }

        @Nonnull
        @Override
        public IPath name()
        {
            return m_name;
        }

        @Nonnull
        @Override
        public Stream<IFuzzyValue<?>> execute( final boolean p_parallel, @Nonnull final IContext p_context, @Nonnull final List<ITerm> p_arguments,
                                               @Nonnull final List<ITerm> p_return )
        {
            p_return.add( CRawTerm.of( Collections.unmodifiableSet( m_agents ) ) );
            return Stream.of();
        }
    }

}
