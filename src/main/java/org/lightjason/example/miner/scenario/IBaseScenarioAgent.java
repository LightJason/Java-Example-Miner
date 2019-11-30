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
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Set;


/**
 * scenario base agent
 *
 * @tparam T agent type
 */
public abstract class IBaseScenarioAgent extends IBaseAgent<IScenarioAgent> implements IScenarioAgent
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 4159418649578529062L;
    /**
     * visible objects
     */
    protected final Set<? extends ISprite> m_visibleobjects;
    /**
     * agent properties
     */
    private final IAgentProperties m_properties = new CAgentProperties();
    /**
     * execution runtime
     */
    private final IRuntime m_runtime;

    /**
     * ctor
     *  @param p_configuration agent configuration
     * @param p_visibleobjects visible objects
     * @param p_runtime execution runtime
     */
    public IBaseScenarioAgent( @Nonnull final IAgentConfiguration<IScenarioAgent> p_configuration,
                               @Nonnull final Set<? extends ISprite> p_visibleobjects, @Nonnull final IRuntime p_runtime
    )
    {
        super( p_configuration );
        m_runtime = p_runtime;
        m_visibleobjects = p_visibleobjects;

        this.toruntime();
    }

    /**
     * pushs the agent explicit to the runtime
     */
    protected final IScenarioAgent toruntime()
    {
        if ( !m_runtime.apply( this ) )
            m_visibleobjects.remove( this );

        return this;
    }

    @Override
    public IScenarioAgent call() throws Exception
    {
        super.call();

        if ( this.runningplans().isEmpty() )
            m_visibleobjects.remove( this );
        else
            this.toruntime();

        return this;
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
    protected abstract static class IBaseScenarioAgentGenerator extends IBaseAgentGenerator<IScenarioAgent>
    {
        /**
         * execution runtime
         */
        protected final IRuntime m_runtime;
        /**
         * svisible objects
         */
        protected final Set<? extends ISprite> m_visibleobjects;

        /**
         * ctor
         *
         * @param p_asl asl
         * @param p_actions actions
         * @param p_lambda lambdas
         * @param p_visibleobjects visible objects
         * @param p_runtime execution pool;
         */
        protected IBaseScenarioAgentGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                                               @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final Set<? extends ISprite> p_visibleobjects,
                                               @Nonnull final IRuntime p_runtime )
        {
            super( p_asl, p_actions, p_lambda );
            m_runtime = p_runtime;
            m_visibleobjects = p_visibleobjects;
        }
    }
}
