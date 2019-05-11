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

import org.apache.commons.io.IOUtils;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;


/**
 * scenario base agent
 *
 * @tparam T agent type
 */
public abstract class IBaseScenarioAgent<U extends IBaseScenarioAgent<?>> extends IBaseAgent<U> implements IEnergyAgent
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 4159418649578529062L;
    /**
     * energy level
     */
    private final AtomicReference<Number> m_energy = new AtomicReference<>( 0 );
    /**
     * execution service
     */
    private final ExecutorService m_execution;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_execution execution service
     */
    public IBaseScenarioAgent( @Nonnull final IAgentConfiguration<U> p_configuration, @Nonnull final ExecutorService p_execution )
    {
        super( p_configuration );
        m_execution = p_execution;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final U call() throws Exception
    {
        super.call();

        if ( !this.runningplans().isEmpty() )
            m_execution.submit( this );

        return (U) this;
    }

    @Override
    public void accept( @Nonnull final Number p_number )
    {
        m_energy.set( p_number );
    }

    @Override
    public Number get()
    {
        return m_energy.get();
    }

    /**
     * agent generator
     *
     * @tparam V agent type
     */
    protected abstract static class IBaseScenarioAgentGenerator<V extends IBaseScenarioAgent<?>> extends IBaseAgentGenerator<V>
    {
        /**
         * ctor
         *
         * @param p_asl asl string
         * @param p_actions actions
         * @param p_lambda lambdas
         *
         * @throws IOException on encoding error
         */
        protected IBaseScenarioAgentGenerator( @Nonnull final String p_asl, @Nonnull final IActionGenerator p_actions,
                                               @Nonnull final ILambdaStreamingGenerator p_lambda ) throws IOException
        {
            super( IOUtils.toInputStream( p_asl, "UTF-8" ), p_actions, p_lambda );
        }
    }
}
