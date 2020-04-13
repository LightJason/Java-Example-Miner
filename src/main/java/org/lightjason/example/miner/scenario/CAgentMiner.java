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

import cern.colt.matrix.tobject.ObjectMatrix2D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IActionGenerator;
import org.lightjason.agentspeak.generator.ILambdaStreamingGenerator;
import org.lightjason.example.miner.runtime.IRuntime;
import org.lightjason.example.miner.ui.ISprite;
import org.lightjason.example.miner.ui.ITileMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;


/**
 * miner agent
 */
public final class CAgentMiner extends IBaseAgentMoving
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -5782229014102610571L;
    /**
     * sprite image name
     */
    private static final String IMAGE = "miner.png";

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_sprite sprite of the agent
     * @param p_visibleobjects visible objects
     * @param p_runtime execution runtime
     * @param p_grid world grid
     */
    private CAgentMiner( @Nonnull final IAgentConfiguration<IAgentMoving> p_configuration, @Nonnull final Sprite p_sprite,
                         @Nonnull final Set<ISprite> p_visibleobjects, @Nonnull final IRuntime p_runtime, @Nonnull final ObjectMatrix2D p_grid )
    {
        super( p_configuration, p_sprite, p_visibleobjects, p_runtime, p_grid );
    }

    /**
     * agent generator
     */
    public static final class CGenerator extends IBaseMovingAgentGenerator
    {

        /**
         * ctor
         *
         * @param p_asl asl
         * @param p_actions actions
         * @param p_lambda lambdas
         * @param p_runtime execution runtime
         */
        public CGenerator( @Nonnull final InputStream p_asl, @Nonnull final IActionGenerator p_actions,
                           @Nonnull final ILambdaStreamingGenerator p_lambda, @Nonnull final IRuntime p_runtime )
        {
            super( p_asl, p_actions, p_lambda, p_runtime, IMAGE );
        }

        @Nonnull
        @Override
        public IAgentMoving generatesingle( @Nullable final Object... p_objects )
        {
            Objects.requireNonNull( p_objects );
            Objects.requireNonNull( p_objects[0] );
            Objects.requireNonNull( p_objects[1] );


            final IAgentMoving l_agent = new CAgentMiner(
                m_configuration,
                this.generateSprite( (ITileMap) p_objects[1] ),
                m_visibleobjects,
                m_runtime,
                (ObjectMatrix2D) p_objects[0]
            );

            m_visibleobjects.add( l_agent );
            return l_agent;
        }
    }
}
