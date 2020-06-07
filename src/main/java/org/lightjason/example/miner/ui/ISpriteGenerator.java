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

package org.lightjason.example.miner.ui;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Set;


/**
 * interface to create the sprite object
 */
public interface ISpriteGenerator
{

    /**
     * sprite initialize for correct painting initialization
     * @param p_sprites sprite set
     * @param p_cellsize cellsize
     * @param p_unit unit value
     */
    void spriteinitialize( @Nonnull final Set<ISprite> p_sprites, @Nonnegative final int p_cellsize, @Nonnegative final float p_unit );

    /**
     * calls the dispose of the ui object
     */
    void dispose();
}
