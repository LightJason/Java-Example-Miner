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

import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


/**
 * gem factory
 */
public enum EGem implements IGemFactory
{
    // red
    RUBY,
    // blue
    SAPPHIRINE,
    // violette
    AMETHYST;


    @Override
    public IGem get()
    {
        return new CGem( this, ThreadLocalRandom.current().nextDouble() );
    }

    @Override
    public void spriteinitialize( @Nonnull final Set<ISprite> p_sprites, final float p_unit )
    {
    }



    /**
     * gem
     */
    private static final class CGem implements IGem
    {
        /**
         * type
         */
        private final EGem m_type;
        /**
         * value
         */
        private final Number m_value;

        /**
         * ctor
         *
         * @param p_type tpe
         * @param p_value value
         */
        private CGem( final EGem p_type, final Number p_value )
        {
            m_type = p_type;
            m_value = p_value;
        }

        @Nonnull
        @Override
        public Number value( @Nonnull final IAgentScenario<?> p_agent )
        {
            return m_value;
        }

        @Nonnull
        @Override
        public EGem type()
        {
            return m_type;
        }

        @Override
        public String toString()
        {
            return MessageFormat.format( "{0}({1})", m_type, m_value );
        }

        @Override
        public Sprite sprite()
        {
            return null;
        }
    }
}
