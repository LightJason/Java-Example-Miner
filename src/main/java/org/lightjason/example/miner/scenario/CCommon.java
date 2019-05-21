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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * scenario common methods
 */
public final class CCommon
{

    /**
     * ctor
     */
    private CCommon()
    {
    }

    /**
     * convert string to input stream
     *
     * @param p_string string
     * @return input stream
     */
    @Nonnull
    public static InputStream toInputStream( @Nonnull final String p_string )
    {
        try
        {
            return IOUtils.toInputStream( p_string, "UTF-8" );
        }
        catch ( final IOException l_exception )
        {
            throw new UncheckedIOException( l_exception );
        }
    }


    /**
     * create generators
     *
     * @param p_asl map with asl
     * @param p_generator generator function
     * @return map
     */
    @Nonnull
    public static Map<String, IBaseAgentGenerator<IScenarioAgent>> generators( @Nonnull final Map<String, String> p_asl,
                                                                               @Nonnull final Function<String, IBaseAgentGenerator<IScenarioAgent>> p_generator )
    {
        return Collections.unmodifiableMap(
            p_asl.entrySet().parallelStream().collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    i -> p_generator.apply( i.getValue() ),
                    ( i, j ) -> i,
                    () -> new TreeMap<>( String.CASE_INSENSITIVE_ORDER )
                )
            )
        );
    }


    /**
     * gaussian blur
     *
     * @param p_value value
     * @param p_sigma sigma
     * @param p_mu mu
     * @param p_scale scaling (half-size is a good value)
     * @return gaussian value
     */
    public static Number gaussian( @Nonnull final Number p_value, @Nonnull final Number p_sigma, @Nonnull final Number p_mu, @Nullable final Number... p_scale )
    {
        return Math.exp( -0.5 * Math.pow( ( p_value.doubleValue() - p_mu.doubleValue() ) / p_sigma.doubleValue(), 2 ) )
            / ( p_sigma.doubleValue() * Math.sqrt( 2 * Math.PI ) ) * ( Objects.nonNull( p_scale ) && p_scale.length > 0 ? p_scale[0].doubleValue() : 1 );
    }

    /**
     * generate coordinates
     *
     * @param p_xcenter x-center
     * @param p_ycenter y-center
     * @param p_size size
     * @param p_xfilter x-coordinate filter
     * @param p_yfilter y-coordinate filter
     * @return stream with number pairs
     */
    public static Stream<Pair<Number, Number>> coordinates( @Nonnull final Number p_xcenter, @Nonnull final Number p_ycenter, @Nonnull final Number p_size,
                                                             @Nonnull final Predicate<Number> p_xfilter, @Nonnull final Predicate<Number> p_yfilter )
    {
        return IntStream.range( p_xcenter.intValue() - p_size.intValue(), p_xcenter.intValue() + p_size.intValue() )
                        .parallel()
                        .boxed()
                        .filter( p_xfilter )
                        .flatMap( x -> IntStream.range( p_ycenter.intValue() - p_size.intValue(), p_ycenter.intValue() + p_size.intValue() )
                                                .parallel()
                                                .boxed()
                                                .filter( p_yfilter )
                                                .map( y -> new ImmutablePair<>( y, x ) ) );
    }

}
