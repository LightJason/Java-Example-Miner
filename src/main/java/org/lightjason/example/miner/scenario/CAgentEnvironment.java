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

package org.lightjason.example.miner.scenario;

import cern.colt.matrix.tobject.ObjectMatrix2D;
import cern.colt.matrix.tobject.impl.SparseObjectMatrix2D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.example.miner.runtime.IRuntime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * environment agent
 */
@IAgentAction
public final class CAgentEnvironment extends IBaseScenarioAgent implements IScenarioEnvironment
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 5950067237160399078L;
    /**
     * grid
     */
    private final AtomicReference<ObjectMatrix2D> m_grid = new AtomicReference<>();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_agentstorage agent storage
     * @param p_runtime execution runtime
     */
    private CAgentEnvironment( @Nonnull final IAgentConfiguration<IScenarioAgent> p_configuration, @Nonnull final Set<IScenarioAgent> p_agentstorage,
                               @Nonnull final IRuntime p_runtime )
    {
        super( p_configuration, p_agentstorage, p_runtime );
    }

    @Nonnull
    @Override
    public ObjectMatrix2D grid()
    {
        return Objects.requireNonNull( m_grid.get() );
    }

    /**
     * build a stream with valid coordinates
     *
     * @param p_xcenter x-center
     * @param p_ycenter y-center
     * @param p_size size
     * @return stream with coordinates (y, x)
     */
    private Stream<Pair<Number, Number>> clip( @Nonnull final Number p_xcenter, @Nonnull final Number p_ycenter, @Nonnull final Number p_size )
    {
        Objects.requireNonNull( m_grid.get() );
        return IntStream.range( p_xcenter.intValue() - p_size.intValue(), p_xcenter.intValue() + p_size.intValue() )
                        .parallel()
                        .boxed()
                        .filter( x -> x >= 0 && x < m_grid.get().columns() )
                        .flatMap( x -> IntStream.range( p_ycenter.intValue() - p_size.intValue(), p_ycenter.intValue() + p_size.intValue() )
                                                 .parallel()
                                                 .boxed()
                                                 .filter( y -> y >= 0 && y < m_grid.get().rows() )
                                                 .filter( y -> Objects.nonNull( m_grid.get().getQuick( y, x ) ) )
                                                 .map( y -> new ImmutablePair<>( y, x ) ) );
    }

    /**
     * creates the world map
     *
     * @param p_width width
     * @param p_height height
     */
    @IAgentActionFilter
    @IAgentActionName( name = "world/create" )
    private void generateWorld( @Nonnull final Number p_width, @Nonnull final Number p_height )
    {
        m_grid.set( new SparseObjectMatrix2D( p_height.intValue(), p_width.intValue() ) );
    }

    /**
     * adds a mine
     *
     * @param p_gem gem name
     * @param p_xcenter x-center value
     * @param p_ycenter y-center value
     * @param p_size size of the mine
     */
    @IAgentActionFilter
    @IAgentActionName( name = "mine/create" )
    private void addmine( @Nonnull final String p_gem, @Nonnull final Number p_xcenter, @Nonnull final Number p_ycenter, @Nonnull final Number p_size )
    {
        final EGem l_gem = EGem.valueOf( p_gem.trim().toUpperCase( Locale.ROOT ) );

        // https://homepages.inf.ed.ac.uk/rbf/HIPR2/gsmooth.htm
        // https://en.wikipedia.org/wiki/Gaussian_blur

        this.clip( p_xcenter, p_ycenter, p_size )
            .filter( i -> ThreadLocalRandom.current().nextDouble() <= gaussian( i, p_xcenter, p_ycenter, 1 ).doubleValue() )
            .forEach( i -> m_grid.get().setQuick( i.getLeft().intValue(), i.getRight().intValue(), l_gem.get() ) );
    }

    /**
     * gaussian blur
     *
     * @param p_value value
     * @param p_xcenter x-center
     * @param p_ycenter y-center
     * @param p_sigma sigma
     * @return gaussian value
     */
    private static Number gaussian( @Nonnull final Pair<Number, Number> p_value,
                                    @Nonnull final Number p_xcenter, @Nonnull final Number p_ycenter, @Nonnull final Number p_sigma )
    {
        final double l_sigma = 2 * Math.pow( p_sigma.doubleValue(), 2 );
        return 1 / ( Math.PI * l_sigma )
               * Math.exp(
                    ( Math.pow( p_ycenter.doubleValue() - p_value.getLeft().doubleValue(), 2 )
                      + Math.pow( p_xcenter.doubleValue() - p_value.getRight().doubleValue(), 2 ) ) / l_sigma
               );
    }

    /**
     * get agent energy
     *
     * @param p_agent agent
     * @return engery level
     */
    @IAgentActionFilter
    @IAgentActionName( name = "energy/get" )
    private Number getEnergy( @Nonnull final IScenarioAgent p_agent )
    {
        return p_agent.get().energy();
    }

    /**
     * take energy from the agent
     *
     * @param p_agent agent
     * @param p_value value
     */
    @IAgentActionFilter
    @IAgentActionName( name = "energy/take" )
    private void takeEnergy( @Nonnull final IScenarioAgent p_agent, @Nonnull final Number p_value )
    {
        p_agent.get().energy( -p_value.doubleValue() );
        this.get().energy( p_value );
    }

    /**
     * add energy to the agent
     *
     * @param p_agent agent
     * @param p_value value
     */
    @IAgentActionFilter
    @IAgentActionName( name = "energy/add" )
    private void addEnergy( @Nonnull final IScenarioAgent p_agent, @Nonnull final Number p_value )
    {
        p_agent.get().energy( p_value );
        this.get().energy( -p_value.doubleValue() );

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
         * @param p_runtime runtime
         */
        public CGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                           @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final Set<IScenarioAgent> p_agentstorage,
                           @Nonnull final IRuntime p_runtime )
        {
            super( p_asl, p_actions, p_lambda, p_agentstorage, p_runtime );
        }

        @Nonnull
        @Override
        public IScenarioAgent generatesingle( @Nullable final Object... p_objects )
        {
            return new CAgentEnvironment( m_configuration, m_agentstorage, m_runtime );
        }
    }
}
