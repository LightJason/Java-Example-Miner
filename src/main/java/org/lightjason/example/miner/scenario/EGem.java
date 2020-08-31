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
import cern.colt.matrix.tobject.ObjectMatrix2D;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.example.miner.ui.CScreen;
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;


/**
 * gem factory
 */
public enum EGem implements IGemFactory
{
    // red
    RUBY,
    // blue
    SAPPHIRINE,
    // violette
    AMETHYST;

    /**
     * texture reference
     */
    private final AtomicReference<Texture> m_texture = new AtomicReference<>();
    /**
     * unit value
     */
    private final AtomicReference<Float> m_unit = new AtomicReference<>();
    /**
     * visible objects
     */
    private Set<ISprite> m_visibleobjects;


    @Override
    public IGem apply( @Nonnull final DoubleMatrix1D p_position, @Nonnull final ObjectMatrix2D p_grid )
    {
        final Sprite l_sprite = new Sprite( Objects.requireNonNull( m_texture.get() ) );

        l_sprite.setSize(
            CScreen.SCREEN.get().tilemap().cellsize() * 0.75f,
            CScreen.SCREEN.get().tilemap().cellsize() * 0.75f
        );
        l_sprite.setOrigin( m_unit.get() + 0.1f, m_unit.get() );
        l_sprite.setScale( m_unit.get() );

        final IGem l_gem = new CGem( l_sprite, this, ThreadLocalRandom.current().nextDouble() );

        if ( !CCommon.setGrid( p_grid, p_position, l_gem ) )
            throw new RuntimeException(
                MessageFormat.format(
                "gem cannot be set into [{0} / {1}], position not empty",
                    CCommon.xposition( p_position ).intValue(),
                    CCommon.yposition( p_position ).intValue()
                )
            );

        org.lightjason.example.miner.ui.CCommon.setSprite( l_sprite, p_position );
        m_visibleobjects.add( l_gem );
        return l_gem;
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
        m_visibleobjects = p_sprites;
        m_unit.compareAndSet( null, p_unit );
        m_texture.compareAndSet( null, new Texture(
            org.lightjason.example.miner.ui.CCommon.copyToGdxLocal( this.name().toLowerCase( Locale.ROOT ) + ".png" )
        ) );
    }

    @Override
    public void dispose()
    {
        Objects.requireNonNull( m_texture.get() ).dispose();
    }

    /**
     * check casting for gems
     *
     * @param p_object any object
     * @return gem
     */
    @Nullable
    public static IGem cast( @Nonnull final Object p_object )
    {
        return p_object instanceof IGem
            ? (IGem) p_object
            : null;
    }


    /**
     * gem
     */
    private static final class CGem implements IGem
    {
        /**
         * sprite
         */
        private final Sprite m_sprite;
        /**
         * type
         */
        private final EGem m_type;
        /**
         * value
         */
        private final Number m_value;

        /**
         * ctor
         *
         * @param p_type tpe
         * @param p_value value
         */
        private CGem( @Nonnull final Sprite p_sprite, final EGem p_type, final Number p_value )
        {
            m_type = p_type;
            m_value = p_value;
            m_sprite = p_sprite;
            m_sprite.setAlpha( 0.5f + 0.5f * p_value.floatValue() );
        }

        @Nonnull
        @Override
        public Number value()
        {
            return m_value;
        }

        @Nonnull
        @Override
        public EGem type()
        {
            return m_type;
        }

        @Override
        public String toString()
        {
            return MessageFormat.format( "{0}({1})", m_type, m_value );
        }

        @Override
        public Sprite sprite()
        {
            return m_sprite;
        }

        /**
         * factory
         *
         * @param p_value name
         * @return gem instance
         */
        public static EGem of( @Nonnull final String p_value )
        {
            return EGem.valueOf( p_value.toUpperCase( Locale.ROOT ) );
        }
    }
}
