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

import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.example.miner.runtime.IRuntime;
import org.lightjason.example.miner.runtime.ISleeper;
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Set;


/**
 * scenario base agent
 *
 * @tparam T agent type
 */
public abstract class IBaseAgentScenario<T extends IAgentScenario<?>> extends IBaseAgent<T> implements IAgentScenario<T>
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 4159418649578529062L;
    /**
     * visible objects
     */
    protected Set<ISprite> m_visibleobjects;
    /**
     * agent properties
     */
    private final IAgentProperties m_properties = new CAgentProperties();
    /**
     * execution runtime
     */
    private final IRuntime m_runtime;
    /**
     * sleeper object
     */
    private final ISleeper m_sleeper;

    /**
     * ctor
     *  @param p_configuration agent configuration
     * @param p_runtime execution runtime
     */
    public IBaseAgentScenario( @Nonnull final IAgentConfiguration<T> p_configuration, @Nonnull final IRuntime p_runtime, @Nonnull final ISleeper p_sleeper )
    {
        super( p_configuration );
        m_runtime = p_runtime;
        m_sleeper = p_sleeper;
    }

    @Override
    public T call() throws Exception
    {
        m_sleeper.sleep();

        //if ( !m_runtime.apply( this ) && Objects.nonNull( m_visibleobjects ) )
        //    m_visibleobjects.remove( this );

        m_runtime.apply( super.call() );
        return this.raw();
    }

    @Override
    public final IAgentProperties get()
    {
        return m_properties;
    }

    /**
     * agent generator
     *
     * @tparam V agent type
     */
    protected abstract static class IBaseScenarioAgentGenerator<T extends IAgentScenario<?>> extends IBaseAgentGenerator<T>
    {
        /**
         * execution runtime
         */
        protected final IRuntime m_runtime;
        /**
         * sleeper
         */
        protected final ISleeper m_sleeper;
        /**
         * visible objects
         */
        protected Set<ISprite> m_visibleobjects;


        /**
         * ctor
         *
         * @param p_asl asl
         * @param p_actions actions
         * @param p_lambda lambdas
         * @param p_runtime execution pool
         * @param p_sleeper sleeper object
         */
        protected IBaseScenarioAgentGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                                               @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final IRuntime p_runtime,
                                               @Nonnull final ISleeper p_sleeper )
        {
            super( p_asl, p_actions, p_lambda );
            m_runtime = p_runtime;
            m_sleeper = p_sleeper;
        }
    }
}
