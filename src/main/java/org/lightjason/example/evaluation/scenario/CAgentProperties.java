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

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * agent properties
 */
public class CAgentProperties implements IAgentProperties
{
    /**
     * energy value
     */
    private final AtomicReference<Number> m_energy;
    /**
     * gem rating values
     */
    private final Map<EGem, Number> m_gemrating;

    /**
     * ctor
     */
    public CAgentProperties()
    {
        this( 0, Stream.empty() );
    }

    /**
     * ctor
     * @param p_energy initialize energy value
     */
    public CAgentProperties( @Nonnull final Number p_energy )
    {
        this( p_energy, Stream.empty() );
    }

    /**
     * ctor
     *
     * @param p_energy initialize energy value
     * @param p_gemrating gem rating values
     */
    public CAgentProperties( @Nonnull final Number p_energy, @Nonnull final Stream<Map.Entry<EGem, Number>> p_gemrating )
    {
        m_energy = new AtomicReference<>( p_energy );
        m_gemrating = p_gemrating.collect( Collectors.toConcurrentMap( Map.Entry::getKey, Map.Entry::getValue ) );
    }

    @Nonnull
    @Override
    public Number energy()
    {
        return m_energy.get();
    }

    @Nonnull
    @Override
    public IAgentProperties energy( @Nonnull final Number p_value )
    {
        m_energy.getAndUpdate( i -> i.doubleValue() + p_value.doubleValue() );
        return this;
    }

    @Nonnull
    @Override
    public Number gemrating( @Nonnull final EGem p_gem )
    {
        return m_gemrating.getOrDefault( p_gem, 0 );
    }

    @Nonnull
    @Override
    public IAgentProperties gemrating( @Nonnull final EGem p_gem, @Nonnull final Number p_value )
    {
        m_gemrating.compute( p_gem, ( k, v ) -> Objects.isNull( k ) ? p_value : v.doubleValue() + p_value.doubleValue() );
        return this;
    }
}
