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

    }

    @Override
    public void dispose()
    {
    }
}
