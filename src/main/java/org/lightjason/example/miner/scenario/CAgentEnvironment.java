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
import com.badlogic.gdx.maps.tiled.TiledMap;
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
import org.lightjason.example.miner.ui.CScreen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;


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
     * trigger for empty world
     */
    private static final ITrigger TRIGGEREMPTY = ITrigger.EType.ADDGOAL.builddefault( CLiteral.of( "world/empty" ) );
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
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_runtime execution runtime
     */
    private CAgentEnvironment( @Nonnull final IAgentConfiguration<IScenarioAgent> p_configuration,
                               @Nonnull final IRuntime p_runtime )
    {
        super( p_configuration, new CopyOnWriteArraySet<>(), p_runtime );
    }

    @Nonnull
    @Override
    public ObjectMatrix2D grid()
    {
        return Objects.requireNonNull( m_grid.get() );
    }

    /**
     * must be fixed
     * @return tilemap
     * @todo must be fixed
     */
    @Override
    public TiledMap map()
    {
        return null;
    }

    /**
     * must be fixed
     * @return rows
     * @todo must be fixed
     */
    @Override
    public int rows()
    {
        return 0;
    }

    /**
     * must be fixed
     * @return columns
     * @todo must be fixed
     */
    @Override
    public int columns()
    {
        return 0;
    }

    /**
     * must be fixed
     * @return cellsize
     * @todo must be fixed
     */
    @Override
    public int cellsize()
    {
        return 0;
    }

    /**
     * runs the world
     */
    @IAgentActionFilter
    @IAgentActionName( name = "world/start" )
    private void worldstart()
    {
        CScreen.open();
    }

    /**
     * creates the world map
     *
     * @param p_width width
     * @param p_height height
     */
    @IAgentActionFilter
    @IAgentActionName( name = "world/create" )
    private void worldcreate( @Nonnull final Number p_width, @Nonnull final Number p_height )
    {
        m_iteration.set( 0 );
        m_grid.set( new SparseObjectMatrix2D( p_height.intValue(), p_width.intValue() ) );
    }

    /**
     * adds a miner
     *
     * @param p_gem gem name
     * @param p_xcenter x-center value
     * @param p_ycenter y-center value
     * @param p_size size of the mine
     */
    @IAgentActionFilter
    @IAgentActionName( name = "miner/create" )
    private void minercreate( @Nonnull final String p_gem, @Nonnull final Number p_xcenter, @Nonnull final Number p_ycenter, @Nonnull final Number p_size )
    {
        /*
        Objects.requireNonNull( m_grid.get() );
        final DoubleMatrix1D l_center = new SparseDoubleMatrix1D( new double[]{p_ycenter.doubleValue(), p_xcenter.doubleValue()} );
        final EGem l_gem = EGem.valueOf( p_gem.trim().toUpperCase( Locale.ROOT ) );

        CCommon.coordinates(
            p_xcenter, p_ycenter, p_size,
            x -> x.intValue() >= 0 && x.intValue() < m_grid.get().columns(),
            y -> y.intValue() >= 0 && y.intValue() < m_grid.get().rows()
        )
            .filter( i -> Objects.isNull( m_grid.get().getQuick( i.getLeft().intValue(), i.getRight().intValue() ) ) )
               .filter( i -> ThreadLocalRandom.current().nextDouble() <= CCommon.gaussian(
                   EDistance.MANHATTAN.apply( l_center, new DenseDoubleMatrix1D( new double[]{i.getLeft().doubleValue(), i.getRight().doubleValue()} ) ),
                1, 0, p_size.doubleValue() ).doubleValue()
               )
            .forEach( i -> m_grid.get().setQuick( i.getLeft().intValue(), i.getRight().intValue(), l_gem.get() ) );

         */
    }

    /**
     * creates a horizontal solid
     *
     * @param p_solid solid
     * @param p_xstart start x-position
     * @param p_ystart start y-position
     * @param p_size x-size
     */
    @IAgentActionFilter
    @IAgentActionName( name = "solid/horizontal" )
    public void solidhorizontalcreate( @Nonnull final String p_solid, @Nonnull final Number p_xstart, @Nonnull final Number p_ystart, @Nonnull final Number p_size )
    {
        Objects.requireNonNull( m_grid.get() );
        final ISolid l_solid = ESolid.valueOf( p_solid.trim().toUpperCase( Locale.ROOT ) );

        IntStream.range( p_xstart.intValue(), p_xstart.intValue() + p_size.intValue() )
                 .parallel()
                 .filter( x -> x >= 0 && x < m_grid.get().columns() )
                 .forEach( x -> m_grid.get().setQuick( p_ystart.intValue(), x, l_solid ) );
    }

    /**
     * creates a vertical solid
     *
     * @param p_solid solid
     * @param p_xstart start x-position
     * @param p_ystart start y-position
     * @param p_size y-size
     */
    @IAgentActionFilter
    @IAgentActionName( name = "solid/vertical" )
    public void solidverticalcreate( @Nonnull final String p_solid, @Nonnull final Number p_xstart, @Nonnull final Number p_ystart, @Nonnull final Number p_size )
    {
        Objects.requireNonNull( m_grid.get() );
        final ISolid l_solid = ESolid.valueOf( p_solid.trim().toUpperCase( Locale.ROOT ) );

        IntStream.range( p_ystart.intValue(), p_ystart.intValue() + p_size.intValue() )
                 .parallel()
                 .filter( y -> y >= 0 && y < m_grid.get().rows() )
                 .forEach( y -> m_grid.get().setQuick( y, p_xstart.intValue(), l_solid ) );
    }

    /**
     * get agent energy
     *
     * @param p_agent agent
     * @return engery level
     */
    @IAgentActionFilter
    @IAgentActionName( name = "energy/get" )
    private Number energyget( @Nonnull final IScenarioAgent p_agent )
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
    private void energytake( @Nonnull final IScenarioAgent p_agent, @Nonnull final Number p_value )
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
    private void anergyadd( @Nonnull final IScenarioAgent p_agent, @Nonnull final Number p_value )
    {
        p_agent.get().energy( p_value );
        this.get().energy( -p_value.doubleValue() );
    }

    @Override
    public IScenarioAgent call() throws Exception
    {
        super.call();

        if ( m_agentstorage.isEmpty() )
            this.trigger( TRIGGEREMPTY );

        this.trigger( ITrigger.EType.ADDGOAL.builddefault( CLiteral.of( TRIGGERITERATION, CRawTerm.of( m_iteration.getAndIncrement() ) ) ) );
        return this.toruntime();
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
         * @param p_runtime runtime
         */
        public CGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                           @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final IRuntime p_runtime )
        {
            super( p_asl, p_actions, p_lambda, Collections.emptySet(), p_runtime );
        }

        @Nonnull
        @Override
        public IScenarioAgent generatesingle( @Nullable final Object... p_objects )
        {
            return new CAgentEnvironment( m_configuration, m_runtime );
        }
    }
}
