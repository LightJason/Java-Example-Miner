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

import edu.umd.cs.findbugs.annotations.NonNull;
import org.lightjason.example.miner.scenario.IScenarioAgent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * runtime definitions
 */
public enum ERuntime implements IRuntime
{
    STEALING( Executors.newWorkStealingPool() ),
    CACHED( Executors.newCachedThreadPool() ),
    SINGLETHREAD( Executors.newSingleThreadExecutor() );

    /**
     * pool definition
     */
    private final ExecutorService m_pool;

    /**
     * ctor
     *
     * @param p_pool pool object
     */
    ERuntime( @NonNull final ExecutorService p_pool )
    {
        m_pool = p_pool;
    }


    @Override
    public Boolean apply( @NonNull final IScenarioAgent p_agent )
    {
        if ( m_pool.isShutdown() || m_pool.isTerminated() )
            return false;

        m_pool.submit( p_agent );
        return true;
    }

    @Override
    public void shutdown()
    {
        m_pool.shutdown();
    }
}
