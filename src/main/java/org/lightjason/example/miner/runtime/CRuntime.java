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

import org.apache.commons.io.IOUtils;
import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.fuzzy.IFuzzyValue;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
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
    private final Set<IScenarioAgent> m_trader = new CopyOnWriteArraySet<>();
    /**
     * miner
     */
    private final Set<IScenarioAgent> m_miner = new CopyOnWriteArraySet<>();


    /**
     * ctor
     *
     * @param p_aslenvironment environment asl
     * @param p_aslminer miner map asl
     * @param p_asltrader trader map asl
     */
    public CRuntime( @Nonnull final String p_aslenvironment, @Nonnull final Map<String, String> p_aslminer,
                     @Nonnull final Map<String, String> p_asltrader )
    {
        // build action list
        // build lambda list
        // build generators by parsing source code -> action for trader & miner generating
        // execute environment -> environment generates world and m_agents

        new CActionAgentSet( CPath.of( "list/traders" ), m_trader );

        new CActionAgentSet( CPath.of( "list/miners" ), m_miner );

        new CAgentGenerator(
            CPath.of( "generate/miners" ),
            m_miner,
            generators(
                p_aslminer,
                i -> new CAgentMiner.CGenerator( toInputStream( i ), IActionGenerator.EMPTY, ILambdaStreamingGenerator.EMPTY, m_runtime )
            )
        );

        new CAgentGenerator(
            CPath.of( "generate/traders" ),
            m_trader,
            generators(
                p_asltrader,
                i -> new CAgentTrader.CGenerator( toInputStream( i ), IActionGenerator.EMPTY, ILambdaStreamingGenerator.EMPTY, m_runtime )
            )
        );


        // parser and run environment
        m_runtime.submit(
            Objects.requireNonNull(
                new CAgentEnvironment.CGenerator(
                    toInputStream( p_aslenvironment ),
                    IActionGenerator.EMPTY,
                    ILambdaStreamingGenerator.EMPTY,
                    m_runtime
                ).generatesingle()
            )
        );
    }

    /**
     * convert string to input stream
     *
     * @param p_string string
     * @return input stream
     */
    @Nonnull
    private static InputStream toInputStream( @Nonnull final String p_string )
    {
        try
        {
            return IOUtils.toInputStream( p_string, "UTF-8" );
        }
        catch ( final IOException l_exception )
        {
            throw new UncheckedIOException( l_exception );
        }
    }

    /**
     * create generators
     *
     * @param p_asl map with asl
     * @param p_generator generator function
     * @return map
     */
    private Map<String, IBaseAgentGenerator<IScenarioAgent>> generators( @Nonnull final Map<String, String> p_asl,
                                                                         @Nonnull final Function<String, IBaseAgentGenerator<IScenarioAgent>> p_generator )
    {
        return Collections.unmodifiableMap(
            p_asl.entrySet().parallelStream().collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    i -> p_generator.apply( i.getValue() ),
                    ( i, j ) -> i,
                    () -> new TreeMap<>( String.CASE_INSENSITIVE_ORDER )
                )
            )
        );
    }

    /**
     * action to generate agents
     */
    private static final class CAgentGenerator extends IBaseAction
    {
        /**
         * serial id
         */
        private static final long serialVersionUID = -3665565552914217150L;
        /**
         * action name
         */
        private final IPath m_name;
        /**
         * target set
         */
        private final Set<IScenarioAgent> m_target;
        /**
         * map with generators
         */
        private final Map<String, IBaseAgentGenerator<IScenarioAgent>> m_generator;


        /**
         * ctor
         *
         * @param p_name action name
         * @param p_target target agent set
         * @param p_generator map with generators
         */
        CAgentGenerator( @Nonnull final IPath p_name, @Nonnull final Set<IScenarioAgent> p_target,
                         @Nonnull final Map<String, IBaseAgentGenerator<IScenarioAgent>> p_generator )
        {
            m_name = p_name;
            m_target = p_target;
            m_generator = p_generator;
        }


        @Nonnull
        @Override
        public IPath name()
        {
            return m_name;
        }

        @Override
        public int minimalArgumentNumber()
        {
            return 1;
        }

        @Nonnull
        @Override
        public Stream<IFuzzyValue<?>> execute( final boolean p_parallel, @Nonnull final IContext p_context, @Nonnull final List<ITerm> p_arguments,
                                               @Nonnull final List<ITerm> p_return )
        {
            final IBaseAgentGenerator<IScenarioAgent> l_generator = m_generator.get( p_arguments.get( 0 ).<String>raw() );
            if ( Objects.isNull( l_generator ) )
                throw new RuntimeException( MessageFormat.format( "agent [{0}] not found", p_arguments.get( 0 ).<String>raw() ) );

            if ( p_arguments.size() == 0 )
                m_target.add( l_generator.generatesingle() );
            else
                l_generator.generatemultiple( p_arguments.get( 1 ).<Number>raw().intValue() ).forEach( m_target::add );

            return Stream.of();
        }
    }

    /**
     * action returns the agent list
     */
    private static final class CActionAgentSet extends IBaseAction
    {
        /**
         * serial id
         */
        private static final long serialVersionUID = -3051651479352596295L;
        /**
         * action name
         */
        private final IPath m_name;
        /**
         * agent set
         */
        private final Set<IScenarioAgent> m_agents;

        /**
         * ctor
         *
         * @param p_name action name
         * @param p_agents agent set
         */
        CActionAgentSet( @Nonnull final IPath p_name, @Nonnull final Set<IScenarioAgent> p_agents )
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
