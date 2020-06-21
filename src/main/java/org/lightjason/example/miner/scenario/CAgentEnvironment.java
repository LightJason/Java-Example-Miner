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
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.execution.instantiable.plan.trigger.ITrigger;
import org.lightjason.example.miner.runtime.IRuntime;
import org.lightjason.example.miner.runtime.ISleeper;
import org.lightjason.example.miner.ui.CScreen;
import org.lightjason.example.miner.ui.CTileMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


/**
 * environment agent
 */
@IAgentAction
public final class CAgentEnvironment extends IBaseAgentScenario<IAgentEnvironment> implements IAgentEnvironment
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 5950067237160399078L;
    /**
     * trigger for empty world
     */
    private static final ITrigger TRIGGEREMPTY = ITrigger.EType.ADDGOAL.builddefault( CLiteral.of( "world/empty" ) );
    /**
     * trigger for world starts
     */
    private static final ITrigger TRIGGERWORLDSTART = ITrigger.EType.ADDGOAL.builddefault( CLiteral.of( "world/start" ) );
    /**
     * functor of trigger for iteration
     */
    private static final String TRIGGERITERATION = "world/iteration";
    /**
     * iteration count
     */
    private final AtomicLong m_iteration = new AtomicLong();
    /**
     * grid
     */
    private final AtomicReference<ObjectMatrix2D> m_grid = new AtomicReference<>();
    /**
     * agent generator for miners
     */
    private final IAgentMovingGenerator m_minergenerator;
    /**
     * solid generator
     */
    private final ISolid.ISolidGenerator m_solidgenerator = new CSolid.CGenerator( 0.92, 0.64, 0.2 );

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_runtime execution runtime
     * @param p_sleeper sleeper object
     */
    private CAgentEnvironment( @Nonnull final IAgentConfiguration<IAgentEnvironment> p_configuration,
                               @Nonnull final IRuntime p_runtime, @Nonnull final ISleeper p_sleeper,
                               @Nonnull final IAgentMovingGenerator p_minergenerator )
    {
        super( p_configuration, p_runtime, p_sleeper );
        m_visibleobjects = new CopyOnWriteArraySet<>();
        m_minergenerator = p_minergenerator;
        p_runtime.apply( this );
    }

    @Nonnull
    @Override
    public ObjectMatrix2D grid()
    {
        return Objects.requireNonNull( m_grid.get() );
    }

    /**
     * runs the world
     *
     * @param p_width screen width
     * @param p_height screen height
     */
    @IAgentActionFilter
    @IAgentActionName( name = "world/start" )
    private void worldstart( @Nonnull final Number p_width, @Nonnull final Number p_height )
    {
        Objects.requireNonNull( m_grid.get() );
        m_iteration.set( 0 );
        CScreen.open(
            p_width,
            p_height,
            m_visibleobjects,
            new CTileMap( m_grid.get().rows(), m_grid.get().columns(), 20 ),
            m_minergenerator,
            m_solidgenerator
        );

        this.trigger( TRIGGERWORLDSTART );
    }

    /**
     * creates the world map
     *
     * @param p_width world width
     * @param p_height world height
     */
    @IAgentActionFilter
    @IAgentActionName( name = "world/create" )
    private void worldcreate( @Nonnull final Number p_width, @Nonnull final Number p_height )
    {
        m_grid.set( new SparseObjectMatrix2D( p_height.intValue(), p_width.intValue() ) );
    }

    /**
     * adds a miner
     */
    @IAgentActionFilter
    @IAgentActionName( name = "miner/create" )
    private void minercreate()
    {
        m_minergenerator.generatesingle( m_grid.get() );
    }

    /**
     * creates a solid element
     *
     * @param p_xupperleft x-position upper-left corner
     * @param p_yupperleft y-position upper-left corner
     * @param p_width p_width
     * @param p_height height
     */
    @IAgentActionFilter
    @IAgentActionName( name = "solid/create" )
    public void solidcreate( @Nonnull final Number p_xupperleft, @Nonnull final Number p_yupperleft,
                             @Nonnull final Number p_width, @Nonnull final Number p_height  )
    {
        Objects.requireNonNull( m_grid.get() );
        m_solidgenerator.generate( p_xupperleft, p_yupperleft, p_width, p_height )
                        .gridposition( m_grid.get() );
    }

    /**
     * get agent energy
     *
     * @param p_agent agent
     * @return engery level
     */
    @IAgentActionFilter
    @IAgentActionName( name = "energy/get" )
    private Number energyget( @Nonnull final IAgentScenario<?> p_agent )
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
    private void energytake( @Nonnull final IAgentScenario<?> p_agent, @Nonnull final Number p_value )
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
    private void anergyadd( @Nonnull final IAgentScenario<?> p_agent, @Nonnull final Number p_value )
    {
        p_agent.get().energy( p_value );
        this.get().energy( -p_value.doubleValue() );
    }

    @Override
    public IAgentEnvironment call() throws Exception
    {
        super.call();

        if ( m_visibleobjects.isEmpty() )
            this.trigger( TRIGGEREMPTY );

        this.trigger( ITrigger.EType.ADDGOAL.builddefault( CLiteral.of( TRIGGERITERATION, CRawTerm.of( m_iteration.getAndIncrement() ) ) ) );
        return this;
    }

    /**
     * agent generator
     */
    public static final class CGenerator extends IBaseScenarioAgentGenerator<IAgentEnvironment>
    {
        /**
         * agent miner generator
         */
        private final IAgentMovingGenerator m_minergenerator;

        /**
         * ctor
         *
         * @param p_asl asl string
         * @param p_actions actions
         * @param p_lambda lambdas
         * @param p_runtime runtime
         * @param p_sleeper sleeper object
         */
        public CGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                           @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final IRuntime p_runtime, @Nonnull final ISleeper p_sleeper,
                           @Nonnull final IAgentMovingGenerator p_minergenerator )
        {
            super( p_asl, p_actions, p_lambda, p_runtime, p_sleeper );
            m_minergenerator = p_minergenerator;
        }

        @Nonnull
        @Override
        public IAgentEnvironment generatesingle( @Nullable final Object... p_objects )
        {
            return new CAgentEnvironment( m_configuration, m_runtime, m_sleeper, m_minergenerator );
        }
    }
}
