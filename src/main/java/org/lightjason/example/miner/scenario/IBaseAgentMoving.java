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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.action.grid.EMovementDirection;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.execution.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.execution.instantiable.plan.trigger.ITrigger;
import org.lightjason.example.miner.CApplication;
import org.lightjason.example.miner.runtime.IRuntime;
import org.lightjason.example.miner.runtime.ISleeper;
import org.lightjason.example.miner.ui.ISprite;
import org.lightjason.example.miner.ui.ITileMap;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;


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
     * current goal position
     */
    private final DoubleMatrix1D m_goal = new DenseDoubleMatrix1D( 2 );
    /**
     * current position
     */
    private final DoubleMatrix1D m_position = new DenseDoubleMatrix1D( 2 );
    /**
     * view range
     */
    private final AtomicReference<Number> m_viewrange = new AtomicReference<>( 1 );
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
                                @Nonnull final IRuntime p_runtime, @Nonnull final ISleeper p_sleeper, @Nonnull final ObjectMatrix2D p_grid )
    {
        super( p_configuration, p_runtime, p_sleeper );
        m_grid = p_grid;
        m_sprite = p_sprite;
        m_visibleobjects = p_visibleobjects;

        /*
        CCommon.randomPostion( m_grid, m_position );
        CCommon.setGrid( m_grid, m_position, this );
        while ( !CCommon.setGrid( m_grid, m_position, this ) )
            CCommon.randomPostion( m_grid, m_position );
        */
        m_position.setQuick( 0, m_grid.rows() - 1 );
        m_position.setQuick( 1, m_grid.columns() - 1 );
        CCommon.setGrid( m_grid, m_position, this );

        org.lightjason.example.miner.ui.CCommon.setSprite( m_sprite, m_position );
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
         CCommon.positionStream( m_position, m_viewrange.get() )
         .filter( i -> CCommon.isInGrid( m_grid, i.getRight(), i.getLeft() ) )
         .map( i -> new ImmutablePair<>( i, CCommon.getGrid( m_grid, i.getRight(), i.getLeft() ) ) )
         .filter( i -> Objects.nonNull( i.getRight() ) )
         .forEach( i -> this.triggerobject( i.getLeft().getRight(), i.getLeft().getLeft(), i.getRight() ) );

        return super.call();
    }

    private void triggerobject( @Nonnull final Number p_xposition, @Nonnull final Number p_yposition, @Nonnull final Object p_object  )
    {
        final IGem l_gem = EGem.cast( p_object );
        if ( Objects.nonNull( l_gem ) )
        {
            this.trigger(
                CTrigger.of(
                    ITrigger.EType.ADDGOAL,
                    CLiteral.of(
                        "found/gem",
                        CLiteral.of(
                            "position",
                            CRawTerm.of( p_xposition ),
                            CRawTerm.of( p_yposition )
                        ),
                        CLiteral.of( "type", CRawTerm.of( l_gem.type().name() ) ),
                        CLiteral.of( "value", CRawTerm.of( l_gem.value() ) )
                    )
                )
            );
        }
    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/goal" )
    private void setgoal( @Nonnull final Number p_xpos, @Nonnull final Number p_ypos )
    {
        if ( p_xpos.intValue() < 0 || p_xpos.intValue() >= m_grid.columns() || p_ypos.intValue() < 0 || p_ypos.intValue() >= m_grid.rows() )
            throw new RuntimeException( "position outside grid" );

        final DoubleMatrix1D l_new = new DenseDoubleMatrix1D( new double[]{p_ypos.doubleValue(), p_xpos.doubleValue()} );

        //if ( DenseDoubleAlgebra.DEFAULT.norm2( l_new.copy().assign( m_position, DoubleFunctions.minus ) ) > m_viewrange.get().doubleValue() )
        //    throw new RuntimeException( "position outside of the view range" );

        m_goal.assign( l_new );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "move/forward" )
    private void walkstraight()
    {
        this.walk( EMovementDirection.FORWARD );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "move/left" )
    private void walkleft()
    {
        this.walk( EMovementDirection.LEFT );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "move/right" )
    private void walkright()
    {
        this.walk( EMovementDirection.RIGHT );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "move/backward" )
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
        final DoubleMatrix1D l_new = p_direction.apply( m_position, m_goal, 0.3 );

        synchronized ( m_goal )
        {
            final IGem l_gem = CCommon.positionHasAndGetGem( m_grid, l_new, m_visibleobjects );
            if ( Objects.nonNull( l_gem ) )
                System.out.println( "gem taken: " + l_gem );

            if ( !CCommon.setGrid( m_grid, l_new, this ) )
                throw new RuntimeException(
                    MessageFormat.format( "postion [{0} / {1}] not empty", l_new.getQuick( 0 ), l_new.getQuick( 1 ) )
                );

            CCommon.setGrid( m_grid, m_position, null );
        }

        m_position.assign( l_new );
        org.lightjason.example.miner.ui.CCommon.setSprite( m_sprite, m_position );
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
        private final AtomicReference<Texture> m_texture = new AtomicReference<>();
        /**
         * unit value
         */
        private final AtomicReference<Float> m_unit = new AtomicReference<>();
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
         * @param p_sleeper sleeper object
         * @param p_image sprite image name
         */
        protected IBaseMovingAgentGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                                             @Nonnull final ILambdaStreamingGenerator p_lambda,
                                             @Nonnull final IRuntime p_runtime, @Nonnull final ISleeper p_sleeper, @Nonnull final String p_image
        )
        {
            super( p_asl, p_actions, p_lambda, p_runtime, p_sleeper );
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
            l_sprite.setOrigin( m_unit.get() - 0.2f, m_unit.get() - 0.15f );
            l_sprite.setScale( m_unit.get() );

            return l_sprite;
        }

        /**
         * overwritten for initialization
         *
         * @param p_sprites set with sprites
         * @param p_cellsize cellsize
         * @param p_unit unit scale
         * @bug not working in a Jar file because path is incorrect, must be refactored for Maven build
         * @todo refactoring for Maven build
         */
        @Override
        public final void spriteinitialize( @Nonnull final Set<ISprite> p_sprites, @Nonnegative final int p_cellsize, @Nonnegative final float p_unit )
        {
            // https://github.com/libgdx/libgdx/wiki/File-handling#file-storage-types

            try
            {
                m_visibleobjects = p_sprites;
                m_unit.compareAndSet( null, p_unit );
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

        @Override
        public void dispose()
        {
            Objects.requireNonNull( m_texture.get() ).dispose();
        }

    }
}
