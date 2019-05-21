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

import cern.colt.matrix.tdouble.DoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.example.miner.runtime.IRuntime;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * agent with moving structure
 */
@IAgentAction
public abstract class IMovingAgent extends IBaseScenarioAgent
{
    /**
     * grid structure
     */
    private final ObjectMatrix2D m_grid;
    /**
     * current route
     */
    private final List<DoubleMatrix1D> m_route = Collections.synchronizedList( new ArrayList<>() );
    /**
     * current position
     */
    private final DoubleMatrix1D m_position = new DenseDoubleMatrix1D( 2 );


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_agentstorage agent storage
     * @param p_runtime execution runtime
     * @param p_grid world grid
     */
    protected IMovingAgent( @Nonnull final IAgentConfiguration<IScenarioAgent> p_configuration,
                            @Nonnull final Set<IScenarioAgent> p_agentstorage,
                            @Nonnull final IRuntime p_runtime, @Nonnull final ObjectMatrix2D p_grid )
    {
        super( p_configuration, p_agentstorage, p_runtime );
        m_grid = p_grid;
    }

    @Override
    public IScenarioAgent call() throws Exception
    {
        if ( this.runningplans().isEmpty() )
            m_grid.setQuick(
                CCommon.toNumber( m_position.getQuick( 0 ) ).intValue(),
                CCommon.toNumber( m_position.getQuick( 1 ) ).intValue(),
                null
            );

        return super.call();
    }

    /**
     * build route from the current position and clears an existing route
     */
    @IAgentActionFilter
    @IAgentActionName( name = "route/find" )
    private void routefind()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "route/append" )
    private void routefappend()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "route/clear" )
    private void routeclear()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/forward" )
    private void walkstraight()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/left" )
    private void walkleft()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/right" )
    private void walkright()
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "walk/backward" )
    private void walkbackward()
    {

    }
}
