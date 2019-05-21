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

import org.junit.Assert;
import org.junit.Test;
import org.lightjason.agentspeak.generator.CActionStaticGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.example.miner.runtime.IRuntime;

import java.util.Collections;


/**
 * test environment agent
 */
public final class TestCEnvironment
{

    /**
     * test grid generating
     *
     * @throws Exception on execution error
     */
    @Test
    public void create() throws Exception
    {
        final CAgentEnvironment l_env = new CAgentEnvironment.CGenerator(
            CCommon.toInputStream( "!run. +!run <- .world/create(55, 45)." ),
            new CActionStaticGenerator( org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CAgentEnvironment.class ) ),
            ILambdaStreamingGenerator.EMPTY,
            Collections.emptySet(),
            IRuntime.EMPTY
        ).generatesingle().call().raw();

        Assert.assertEquals( 45, l_env.grid().rows() );
        Assert.assertEquals( 55, l_env.grid().columns() );
    }

    /**
     * test mine generating
     *
     * @throws Exception on execution error
     */
    @Test
    public void mine() throws Exception
    {
        final CAgentEnvironment l_env = new CAgentEnvironment.CGenerator(
            CCommon.toInputStream( "!run. +!run <- .world/create(50, 50); .mine/create( 'topaz',  25,25,5)." ),
            new CActionStaticGenerator( org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CAgentEnvironment.class ) ),
            ILambdaStreamingGenerator.EMPTY,
            Collections.emptySet(),
            IRuntime.EMPTY
        ).generatesingle().call().call().raw();

        System.out.println( l_env.grid() );
    }

}
