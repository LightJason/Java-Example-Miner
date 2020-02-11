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

import cern.colt.matrix.tdouble.DoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.action.grid.EMovementDirection;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.example.miner.runtime.IRuntime;
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


/**
 * agent with moving structure
 */
@IAgentAction
public abstract class IBaseMovingAgent extends IBaseScenarioAgent implements IMovingAgent
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 4912224902488924621L;
    /**
     * grid structure
     */
    private final ObjectMatrix2D m_grid;
    /**
     * current route
     */
    private final List<DoubleMatrix1D> m_route = Collections.synchronizedList( new ArrayList<>() );
    /**
     * current position
     */
    private final DoubleMatrix1D m_position = new DenseDoubleMatrix1D( 2 );
    /**
     * view range
     */
    private final AtomicReference<Number> m_viewrange = new AtomicReference<>( 5 );
    /**
     * sprite
     */
    private Sprite m_sprite;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_visibleobjects visible objects
     * @param p_runtime execution runtime
     * @param p_grid world grid
     */
    protected IBaseMovingAgent( @Nonnull final IAgentConfiguration<IScenarioAgent> p_configuration,
                                @Nonnull final Set<? extends ISprite> p_visibleobjects,
                                @Nonnull final IRuntime p_runtime, @Nonnull final ObjectMatrix2D p_grid )
    {
        super( p_configuration, p_visibleobjects, p_runtime );
        m_grid = p_grid;
    }

    @Override
    public final Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public final void spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
    {
        /*
        if ( m_texture == null )
            m_texture = new Texture( Gdx.files.internal( m_texturepath ) );

        m_spritecellsize = p_cellsize;
        m_spriteunitsize = p_unit;

        final Sprite l_sprite = new Sprite( m_texture );
        l_sprite.setSize( m_spritecellsize, m_spritecellsize );
        l_sprite.setOrigin( 1.5f / m_spritecellsize, 1.5f / m_spritecellsize );
        l_sprite.setScale( m_spriteunitsize );
        */
    }

    @Override
    public final IMovingAgent viewrange( @Nonnull final Number p_value )
    {
        m_viewrange.getAndUpdate( i -> i.doubleValue() + p_value.doubleValue() );
        return this;
    }

    @Override
    public IScenarioAgent call() throws Exception
    {
        if ( this.runningplans().isEmpty() )
            this.setcurrentposition( null );

        return super.call();
    }

    /**
     * creates a random round to a target position within the view range
     * and clears an existing route
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/random" )
    private void routerandom()
    {
        m_route.clear();
    }

    /**
     * build route from the current position and clears an existing route
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/find" )
    private void routefind()
    {
        m_route.clear();
    }

    @IAgentActionFilter
    @IAgentActionName( name = "route/append" )
    private void routefappend()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "route/clear" )
    private void routeclear()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/forward" )
    private void walkstraight()
    {
        this.walk( EMovementDirection.FORWARD );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/left" )
    private void walkleft()
    {
        this.walk( EMovementDirection.LEFT );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/right" )
    private void walkright()
    {
        this.walk( EMovementDirection.RIGHT );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/backward" )
    private void walkbackward()
    {
        this.walk( EMovementDirection.BACKWARD );
    }

    /**
     * walk
     *
     * @param p_direction direction
     */
    private void walk( @Nonnull final EMovementDirection p_direction )
    {
        if ( m_route.isEmpty() )
            throw new RuntimeException( "empty route" );


        this.setcurrentposition( null );
        m_position.assign( p_direction.apply( m_position, m_route.get( 0 ), 1.5 ) );
        this.setcurrentposition( this );
    }

    /**
     * set current position
     *
     * @param p_object grid value
     */
    private void setcurrentposition( @Nullable final Object p_object )
    {
        m_grid.setQuick(
            CCommon.toNumber( m_position.getQuick( 0 ) ).intValue(),
            CCommon.toNumber( m_position.getQuick( 1 ) ).intValue(),
            p_object
        );
    }
}
