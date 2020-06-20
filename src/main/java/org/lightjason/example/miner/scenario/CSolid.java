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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;


/**
 * solid block definition
 */
public final class CSolid implements ISolid
{
    /**
     * sprite
     */
    private final Sprite m_sprite;
    /**
     * x-upper postion
     */
    private final Number m_xupperleft;
    /**
     * y-upper postion
     */
    private final Number m_yupperleft;
    /**
     * width
     */
    private final Number m_width;
    /**
     * height
     */
    private final Number m_height;

    /**
     * ctor
     * @param p_sprite sprite
     */
    private CSolid( @Nonnull final Sprite p_sprite, @Nonnull final Number p_xupperleft,
                    @Nonnull final Number p_yupperleft, @Nonnull final Number p_width,
                    @Nonnull final Number p_height )
    {
        m_sprite = p_sprite;
        m_xupperleft = p_xupperleft;
        m_yupperleft = p_yupperleft;
        m_width = p_width;
        m_height = p_height;
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public ISolid gridposition( @Nonnull final ObjectMatrix2D p_grid )
    {
        IntStream.range( m_xupperleft.intValue(), m_xupperleft.intValue() + m_width.intValue() )
                 .forEach( i -> IntStream.range( m_yupperleft.intValue(), m_yupperleft.intValue() + m_height.intValue() )
                                         .forEach( j -> p_grid.setQuick( j, i, this ) ) );
        return this;
    }


    /**
     * solid generator
     */
    public static final class CGenerator implements ISolidGenerator
    {
        /**
         * color of the solid block
         */
        private final Color m_color;
        /**
         * pixmap
         */
        private Pixmap m_pixmap;
        /**
         * texture
         */
        private Texture m_texture;
        /**
         * cellsize
         */
        private Number m_cellsize;
        /**
         * unit
         */
        private Number m_unit;

        /**
         * ctor
         *
         * @param p_red red in [0,1]
         * @param p_green green in [0,1]
         * @param p_blue blue in [0,1]
         */
        public CGenerator( @Nonnegative final Number p_red, @Nonnegative final Number p_green,
                           @Nonnegative final Number p_blue )
        {
            this( p_red, p_green, p_blue, 1 );
        }

        /**
         * ctor
         *
         * @param p_red red in [0,1]
         * @param p_green green in [0,1]
         * @param p_blue blue in [0,1]
         * @param p_alpha alpha in [0,1]
         */
        public CGenerator( @Nonnegative final Number p_red, @Nonnegative final Number p_green,
                           @Nonnegative final Number p_blue, @Nonnegative final Number p_alpha )
        {
            m_color = new Color( p_red.floatValue(), p_green.floatValue(), p_blue.floatValue(), p_alpha.floatValue() );
        }

        @Override
        public void spriteinitialize( @Nonnull final Set<ISprite> p_sprites, final int p_cellsize, final float p_unit )
        {
            m_unit = p_unit;
            m_cellsize = p_cellsize;

            m_pixmap = new Pixmap( m_cellsize.intValue(), m_cellsize.intValue(), Pixmap.Format.RGBA8888 );
            m_pixmap.setColor( m_color );
            m_pixmap.fillRectangle( 0, 0, m_cellsize.intValue(), m_cellsize.intValue() );

            m_texture = new Texture( m_pixmap );
        }

        @Override
        public void dispose()
        {
            Objects.requireNonNull( m_texture ).dispose();
            Objects.requireNonNull( m_pixmap ).dispose();
        }

        @Override
        public ISolid generate( @Nonnull final Number p_xupperleft, @Nonnull final Number p_yupperleft,
                                @Nonnull final Number p_width, @Nonnull final Number p_height )
        {
            final Sprite l_sprite = new Sprite( m_texture, 0, 0, m_cellsize.intValue(), m_cellsize.intValue() );
            l_sprite.setSize( m_cellsize.floatValue() * p_width.floatValue(), m_cellsize.floatValue() * p_height.floatValue() );
            l_sprite.setOrigin( 1.5f / m_cellsize.floatValue(), 1.5f / m_cellsize.floatValue() );
            l_sprite.setPosition( p_xupperleft.floatValue(), p_yupperleft.floatValue() );
            l_sprite.setScale( m_unit.floatValue() );

            return new CSolid( l_sprite, p_xupperleft, p_yupperleft, p_width, p_height );
        }
    }
}
