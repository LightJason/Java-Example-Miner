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
import java.util.stream.IntStream;


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
        ).generatesingle().call().raw();

        Assert.assertTrue( l_env.grid().cardinality() > 0 );
    }

    /**
     * test horizontal solid
     *
     * @throws Exception on execution error
     */
    @Test
    public void horizontalsolid() throws Exception
    {
        final CAgentEnvironment l_env = new CAgentEnvironment.CGenerator(
            CCommon.toInputStream( "!run. +!run <- .world/create(10, 10); .solid/create/horizontal( 'wall', 0,0,5)." ),
            new CActionStaticGenerator( org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CAgentEnvironment.class ) ),
            ILambdaStreamingGenerator.EMPTY,
            Collections.emptySet(),
            IRuntime.EMPTY
        ).generatesingle().call().raw();

        Assert.assertEquals( 5, l_env.grid().cardinality() );
        Assert.assertTrue( IntStream.range( 0, 5 ).mapToObj( i -> l_env.grid().getQuick( 0, i ) ).allMatch( i -> i instanceof ISolid ) );
    }

    /**
     * test vertical solid
     *
     * @throws Exception on execution error
     */
    @Test
    public void verticalsolid() throws Exception
    {
        final CAgentEnvironment l_env = new CAgentEnvironment.CGenerator(
            CCommon.toInputStream( "!run. +!run <- .world/create(10, 10); .solid/create/vertical( 'wall', 2,2,4)." ),
            new CActionStaticGenerator( org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CAgentEnvironment.class ) ),
            ILambdaStreamingGenerator.EMPTY,
            Collections.emptySet(),
            IRuntime.EMPTY
        ).generatesingle().call().raw();

        Assert.assertEquals( 4, l_env.grid().cardinality() );
        Assert.assertTrue( IntStream.range( 2, 5 ).mapToObj( i -> l_env.grid().getQuick( i, 2 ) ).allMatch( i -> i instanceof ISolid ) );
    }

}
