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
import cern.colt.matrix.tdouble.algo.DoubleFormatter;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.example.miner.ui.CScreen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * scenario common methods
 */
public final class CCommon
{

    /**
     * formatter definition
     */
    public static final DoubleFormatter FORMATTER;

    static
    {
        FORMATTER = new DoubleFormatter();
        FORMATTER.setRowSeparator( "; " );
        FORMATTER.setColumnSeparator( " " );
        FORMATTER.setPrintShape( false );
    }

    /**
     * ctor
     */
    private CCommon()
    {
    }

    /**
     * returns the x position of the position vector
     *
     * @param p_position position vector
     * @return x position
     */
    public static Number xposition( @Nonnull final DoubleMatrix1D p_position )
    {
        return p_position.getQuick( 1 );
    }

    /**
     * returns the y position of the position vector
     *
     * @param p_position position vector
     * @return y position
     */
    public static Number yposition( @Nonnull final DoubleMatrix1D p_position )
    {
        return p_position.getQuick( 0 );
    }

    /**
     * set the new position values into the position vector
     * @param p_position position vector
     * @param p_xvalue new x value
     * @param p_yvalue new y value
     * @return position vector
     */
    public static DoubleMatrix1D setPosition( @Nonnull final DoubleMatrix1D p_position, @Nonnull final Number p_xvalue, @Nonnull final Number p_yvalue )
    {
        p_position.setQuick( 0, p_yvalue.doubleValue() );
        p_position.setQuick( 1, p_xvalue.doubleValue() );
        return p_position;
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
        return p_grid.getQuick( yposition( p_position ).intValue(), xposition( p_position ).intValue() );
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
        if ( Objects.nonNull( p_grid.getQuick( yposition( p_position ).intValue(), xposition( p_position ).intValue() ) ) )
            return false;

        p_grid.setQuick( yposition( p_position ).intValue(), xposition( p_position ).intValue(), p_object );
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
        return setPosition(
            p_position,
            ThreadLocalRandom.current().nextInt( p_grid.rows() ),
            ThreadLocalRandom.current().nextInt( p_grid.columns() )
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

    /**
     * wait until objects are initialize
     */
    public static void waitForInitialize( @Nonnull final Supplier<Object> p_getter )
    {
        int l_loop = 0;

        while ( Objects.isNull( p_getter.get() ) && l_loop < CScreen.WAITLOOPS )
        {
            l_loop++;
            try
            {
                Thread.sleep( CScreen.WAITTIME );
            }
            catch ( final InterruptedException l_exception )
            {
                // ignore any error
            }
        }

        Objects.requireNonNull( p_getter.get() );
    }
}
