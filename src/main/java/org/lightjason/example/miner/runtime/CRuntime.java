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

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class CRuntime
{
    // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/concurrent/ScheduledExecutorService.html

    // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/concurrent/Executors.html

    private final ExecutorService m_runtime = Executors.newWorkStealingPool();



    public CRuntime( @Nonnull final String p_aslenvironment, @Nonnull final Map<String, String> p_aslminer )
    {
        // build action list
        // build lambda list
        // build generators by parsing source code
        // execute environment -> environment generates world and agents
        // put generated agents into execution queue
    }

}
