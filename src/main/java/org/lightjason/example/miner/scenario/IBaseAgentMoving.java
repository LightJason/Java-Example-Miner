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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.action.grid.EMovementDirection;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.example.miner.CApplication;
import org.lightjason.example.miner.runtime.IRuntime;
import org.lightjason.example.miner.ui.CScreen;
import org.lightjason.example.miner.ui.ISprite;
import org.lightjason.example.miner.ui.ITileMap;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;


/**
 * agent with moving structure
 */
@IAgentAction
public abstract class IBaseAgentMoving extends IBaseAgentScenario<IAgentMoving> implements IAgentMoving
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
    private final Sprite m_sprite;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_sprite sprite of the agent
     * @param p_visibleobjects visible objects
     * @param p_runtime execution runtime
     * @param p_grid world grid
     */
    protected IBaseAgentMoving( @Nonnull final IAgentConfiguration<IAgentMoving> p_configuration,
                                @Nonnull final Sprite p_sprite, @Nonnull final Set<ISprite> p_visibleobjects,
                                @Nonnull final IRuntime p_runtime, @Nonnull final ObjectMatrix2D p_grid )
    {
        super( p_configuration, p_runtime );
        m_grid = p_grid;
        m_sprite = p_sprite;
        m_visibleobjects = p_visibleobjects;

        synchronized ( m_grid )
        {

            int l_xpos = ThreadLocalRandom.current().nextInt( m_grid.columns() );
            int l_ypos = ThreadLocalRandom.current().nextInt( m_grid.rows() );

            while ( Objects.nonNull( m_grid.getQuick( l_ypos, l_xpos ) ) )
            {
                l_xpos = ThreadLocalRandom.current().nextInt( m_grid.columns() );
                l_ypos = ThreadLocalRandom.current().nextInt( m_grid.rows() );
            }

            m_grid.setQuick( l_ypos, l_xpos, this );
            m_position.set( 0, l_ypos );
            m_position.set( 1, l_xpos );
        }

        this.position2sprite();
    }

    /**
     * sets the position vector to sprite
     */
    private void position2sprite()
    {
        m_sprite.setPosition( CCommon.toNumber( m_position.getQuick( 0 ) ).intValue(), CCommon.toNumber( m_position.getQuick( 1 ) ).intValue() );
    }

    @Override
    public final Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public final IAgentMoving viewrange( @Nonnull final Number p_value )
    {
        m_viewrange.getAndUpdate( i -> i.doubleValue() + p_value.doubleValue() );
        return this;
    }

    @Override
    public IAgentMoving call() throws Exception
    {
        if ( this.runningplans().isEmpty() )
            this.removePosition();

        return super.call();
    }

    /**
     * creates a random route a target position within the view range
     * and clears an existing route
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/random" )
    private void routerandom()
    {
        int l_xpos = ThreadLocalRandom.current().nextInt( m_viewrange.get().intValue() / 2 );
        int l_ypos = ThreadLocalRandom.current().nextInt( m_viewrange.get().intValue() / 2 );


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
    private void routeappend()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "route/clear" )
    private void routeclear()
    {
        m_route.clear();
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


        synchronized ( m_grid )
        {
            this.removePosition();
            m_position.assign( p_direction.apply( m_position, m_route.get( 0 ), 1.5 ) );

            m_grid.setQuick(
                CCommon.toNumber( m_position.getQuick( 0 ) ).intValue(),
                CCommon.toNumber( m_position.getQuick( 1 ) ).intValue(),
                this
            );
        }

        this.position2sprite();
    }

    /**
     * remove position
     */
    protected final void removePosition()
    {
        m_grid.setQuick(
            CCommon.toNumber( m_position.getQuick( 0 ) ).intValue(),
            CCommon.toNumber( m_position.getQuick( 1 ) ).intValue(),
            null
        );
    }

    /**
     * agent generator
     *
     * @tparam V agent type
     */
    protected abstract static class IBaseMovingAgentGenerator extends IBaseScenarioAgentGenerator<IAgentMoving> implements IAgentMovingGenerator
    {
        /**
         * texture reference
         */
        protected final AtomicReference<Texture> m_texture = new AtomicReference<>();
        /**
         * filename of the texture
         */
        private final String m_image;

        /**
         * ctor
         *
         * @param p_asl asl
         * @param p_actions actions
         * @param p_lambda lambdas
         * @param p_runtime execution pool
         * @param p_image sprite image name
         */
        protected IBaseMovingAgentGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                                             @Nonnull final ILambdaStreamingGenerator p_lambda,
                                             @Nonnull final IRuntime p_runtime, @Nonnull final String p_image )
        {
            super( p_asl, p_actions, p_lambda, p_runtime );
            m_image = p_image;
        }

        /**
         * generate sprite
         *
         * @param p_map tilemap
         * @return sprite
         */
        @Nonnull
        protected final Sprite generateSprite( @Nonnull final ITileMap p_map )
        {
            final Sprite l_sprite = new Sprite( Objects.requireNonNull( m_texture.get() ) );

            l_sprite.setSize( p_map.cellsize() * 1.1f, p_map.cellsize() * 1.1f );
            l_sprite.setOrigin( 1.0f / p_map.cellsize() - 0.2f, 1.0f / p_map.cellsize() - 0.15f );
            l_sprite.setScale( 1.0f / p_map.cellsize() );

            return l_sprite;
        }

        /**
         * overwritten for initialization
         * @param p_sprites set with sprites
         * @bug not working in a Jar file because path is incorrect, must be refactored for Maven build
         * @todo refactoring for Maven build
         */
        @Override
        public final void spriteinitialize( @Nonnull final Set<ISprite> p_sprites )
        {
            // https://github.com/libgdx/libgdx/wiki/File-handling#file-storage-types

            try
            {
                m_visibleobjects = p_sprites;
                m_texture.compareAndSet( null, new Texture(
                    Gdx.files.absolute(
                        CApplication.getPath( "org/lightjason/example/miner/" + m_image ).toAbsolutePath().toString()
                    )
                ) );

            }
            catch ( final URISyntaxException l_exception )
            {
                throw new RuntimeException( l_exception );
            }
        }

        /**
         * wait until visualization object list
         */
        protected final void waitforvisualization()
        {
            int l_loop = 0;

            while ( Objects.isNull( m_visibleobjects ) && l_loop < CScreen.WAITLOOPS )
            {
                l_loop++;
                try
                {
                    Thread.sleep( CScreen.WAITTIME );
                }
                catch ( final InterruptedException l_exception )
                {
                    // ignore any error
                }
            }

            Objects.requireNonNull( m_visibleobjects );
        }
    }
}
