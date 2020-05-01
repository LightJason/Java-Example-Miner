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

package org.lightjason.example.miner.runtime;

import javax.annotation.Nonnegative;


/**
 * sleeper with millisecond
 */
public final class CSleeper implements ISleeper
{
    /**
     * delay time
     */
    private final long m_time;

    /**
     * ctor
     *
     * @param p_time wait time
     */
    public CSleeper( @Nonnegative final long p_time )
    {
        m_time = p_time;
    }

    @Override
    public void sleep()
    {
        try
        {
            Thread.sleep( m_time );
        }
        catch ( final InterruptedException l_exception )
        {
        }
    }
}
