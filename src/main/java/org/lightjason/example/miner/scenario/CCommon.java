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
import cern.colt.matrix.tobject.ObjectMatrix2D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
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
     * number casting
     *
     * @param p_value any number value
     * @return number object
     */
    public static Number toNumber( @Nonnull final Number p_value )
    {
        return p_value;
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

    /**
     * gets an object from the grid
     *
     * @param p_grid grid
     * @param p_position position vector
     * @return object or null
     */
    @Nonnull
    public static Object getGrid( @Nonnull final ObjectMatrix2D p_grid, @Nonnull final DoubleMatrix1D p_position )
    {
        return p_grid.getQuick(
                CCommon.toNumber( p_position.getQuick( 0 ) ).intValue(),
                CCommon.toNumber( p_position.getQuick( 1 ) ).intValue()
        );
    }

    /**
     * sets an object to the grid if position is empty
     * @param p_grid grid
     * @param p_position position vector
     * @param p_object object or null
     * @return is object can be placed
     */
    public static boolean setGrid( @Nonnull final ObjectMatrix2D p_grid, @Nonnull final DoubleMatrix1D p_position, @Nullable final Object p_object )
    {
        synchronized ( p_grid )
        {
            if ( Objects.nonNull( p_grid.getQuick(
                    CCommon.toNumber( p_position.getQuick( 0 ) ).intValue(),
                    CCommon.toNumber( p_position.getQuick( 1 ) ).intValue()
            ) ) )
                return false;

            p_grid.setQuick(
                    CCommon.toNumber( p_position.getQuick( 0 ) ).intValue(),
                    CCommon.toNumber( p_position.getQuick( 1 ) ).intValue(),
                    p_object
            );
        }
        return true;
    }

    /**
     * sets a random position based on grid size
     *
     * @param p_grid grid
     * @param p_position position vector
     * @return modified input vector
     */
    public static DoubleMatrix1D randomPostion( @Nonnull final ObjectMatrix2D p_grid, @Nonnull final DoubleMatrix1D p_position )
    {
        p_position.setQuick( 0, ThreadLocalRandom.current().nextInt( p_grid.rows() ) );
        p_position.setQuick( 1, ThreadLocalRandom.current().nextInt( p_grid.columns() ) );
        return p_position;
    }

}
