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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnull;
import java.util.Set;


/**
 * solid block definition
 */
public final class CSolid implements ISolid
{
    /**
     * color of the solid block
     */
    private static final Color COLOR = new Color( 1f, 0.73f, 0.2f, 1f );

    @Override
    public Sprite sprite()
    {
        return null;
    }

    @Override
    public void spriteinitialize( @Nonnull final Set<ISprite> p_sprites, final float p_unit )
    {
        // variables are defined as size1 = x-size & size2 = y-size
        final float l_size1 = p_cellsize * (int) m_position.getQuick( 3 );
        final float l_size2 = p_cellsize * (int) m_position.getQuick( 2 );

        // create a colored block of the item
        final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( m_color );
        l_pixmap.fillRectangle( 0, 0, (int) l_size2, (int) l_size1 );

        // add the square to a sprite (for visualization) and use 100% of cell size
        m_sprite = new Sprite( new Texture( l_pixmap ), 0, 0, (int) l_size2, (int) l_size1 );
        m_sprite.setSize( l_size1, l_size2 );
        m_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );
        m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );
        m_sprite.setScale( p_unit );
    }

    @Override
    public void dispose()
    {
    }
}
