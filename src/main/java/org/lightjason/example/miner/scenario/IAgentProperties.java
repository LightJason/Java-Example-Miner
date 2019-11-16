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

import javax.annotation.Nonnull;


/**
 * agent properties
 */
public interface IAgentProperties
{
    /**
     * returns the current energy level of the agent
     *
     * @return energy agent
     */
    @Nonnull
    Number energy();

    /**
     * updates the energy level of the agent
     *
     * @param p_value update value
     * @return self-reference
     */
    @Nonnull
    IAgentProperties energy( @Nonnull final Number p_value );

    /**
     * gem rating value
     *
     * @param p_gem gem type
     * @return rating value
     */
    @Nonnull
    Number gemrating( @Nonnull final EGem p_gem );

    /**
     * update gem rating value
     *
     * @param p_gem gem
     * @param p_value value
     * @return self-reference
     */
    @Nonnull
    IAgentProperties gemrating( @Nonnull final EGem p_gem, @Nonnull final Number p_value );

}
