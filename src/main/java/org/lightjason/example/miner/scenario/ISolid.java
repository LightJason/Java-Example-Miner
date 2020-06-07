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

import org.lightjason.example.miner.ui.ISprite;
import org.lightjason.example.miner.ui.ISpriteGenerator;

import javax.annotation.Nonnull;


/**
 * solid interface
 */
public interface ISolid extends ISprite
{

    /**
     * solid generator
     */
    interface ISolidGenerator extends ISpriteGenerator
    {

        /**
         * generates a new solid element
         * @param p_xupperleft x-position upper-left corner
         * @param p_yupperleft y-position upper-left corner
         * @param p_width p_width
         * @param p_height height
         * @return solid element
         */
        ISolid generate( @Nonnull final Number p_xupperleft, @Nonnull final Number p_yupperleft,
                         @Nonnull final Number p_width, @Nonnull final Number p_height );

    }
}
