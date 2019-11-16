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

package org.lightjason.example.evaluation.scenario;

import cern.colt.matrix.tobject.ObjectMatrix2D;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.example.evaluation.runtime.IRuntime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Set;


/**
 * miner agent
 */
public final class CAgentMiner extends IBaseMovingAgent
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -5782229014102610571L;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_agentstorage agent storage
     * @param p_runtime execution runtime
     * @param p_grid world grid
     */
    private CAgentMiner( @Nonnull final IAgentConfiguration<IScenarioAgent> p_configuration, @Nonnull final Set<IScenarioAgent> p_agentstorage,
                         @Nonnull final IRuntime p_runtime, @Nonnull final ObjectMatrix2D p_grid )
    {
        super( p_configuration, p_agentstorage, p_runtime, p_grid );
    }


    /**
     * agent generator
     */
    public static final class CGenerator extends IBaseScenarioAgentGenerator
    {

        /**
         * ctor
         *
         * @param p_asl asl
         * @param p_actions actions
         * @param p_lambda lambdas
         * @param p_agentstorage agent storage
         * @param p_runtime execution runtime
         */
        public CGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                           @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final Set<IScenarioAgent> p_agentstorage,
                           @Nonnull final IRuntime p_runtime )
        {
            super( p_asl, p_actions, p_lambda, p_agentstorage, p_runtime );
        }

        @Nonnull
        @Override
        public IScenarioAgent generatesingle( @Nullable final Object... p_objects )
        {
            return new CAgentMiner( m_configuration, m_agentstorage, m_runtime, (ObjectMatrix2D) p_objects[0] );
        }
    }
}
