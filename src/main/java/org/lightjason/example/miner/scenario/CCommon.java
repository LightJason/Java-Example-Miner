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
import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import cern.colt.matrix.tdouble.algo.DoubleFormatter;
import cern.colt.matrix.tobject.ObjectMatrix2D;
import cern.jet.math.tdouble.DoubleFunctions;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.example.miner.ui.CScreen;
import org.lightjason.example.miner.ui.ISprite;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
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
     * checks if a position is within the grid
     *
     * @param p_grid grid
     * @param p_xposition x-position
     * @param p_yposition y-position
     * @return boolean if the position is within the grid definition
     */
    public static boolean isInGrid( @Nonnull final ObjectMatrix2D p_grid, @Nonnull final Number p_xposition, @Nonnull final Number p_yposition )
    {
        return p_xposition.intValue() >= 0 && p_xposition.intValue() < p_grid.columns()
            && p_yposition.intValue() >= 0 && p_yposition.intValue() < p_grid.rows();
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
     * checks a position if it contains a gem
     *
     * @param p_grid grid instance
     * @param p_position position
     * @return gem or null
     */
    @Nullable
    @SuppressWarnings( "unchecked" )
    public static IGem positionHasAndGetGem( @Nonnull final ObjectMatrix2D p_grid, @Nonnull final DoubleMatrix1D p_position,
                                             @Nonnull final Set<ISprite> p_visibles )
    {
        final Object l_object = getGrid( p_grid, p_position );
        if ( Objects.nonNull( l_object ) && l_object instanceof IGem )
        {
            p_grid.setQuick( yposition( p_position ).intValue(), xposition( p_position ).intValue(), null );
            p_visibles.remove( l_object );
            return (IGem) l_object;
        }

        return null;
    }

    /**
     * creates a stream based on the position vector over all position tuples
     *
     * @param p_position center position
     * @param p_range range
     * @return stream with coordinate tuples
     */
    @Nonnull
    public static Stream<Pair<Number, Number>> positionStream( @Nonnull final DoubleMatrix1D p_position, @Nonnull final Number p_range )
    {
        final Number l_xleftrange = xposition( p_position ).intValue() - p_range.intValue();
        final Number l_ytoprange = yposition( p_position ).intValue() - p_range.intValue();

        final Number l_xrightrange = xposition( p_position ).intValue() + p_range.intValue();
        final Number l_ybottomrange = yposition( p_position ).intValue() + p_range.intValue();

        return Stream.concat(
            Stream.concat(
                IntStream.range( l_xleftrange.intValue(), 0 )
                         .boxed()
                         .flatMap( x -> Stream.of( new ImmutablePair<>( x, l_ytoprange ), new ImmutablePair<>( x, l_ybottomrange ) ) ),

                IntStream.range( 1, l_xrightrange.intValue() )
                         .boxed()
                         .flatMap( x -> Stream.of( new ImmutablePair<>( x, l_ytoprange ), new ImmutablePair<>( x, l_ybottomrange ) ) )
            ),

            Stream.concat(
                IntStream.range( l_ytoprange.intValue(), 0 )
                         .boxed()
                         .flatMap( y -> Stream.of( new ImmutablePair<>( l_xleftrange, y ), new ImmutablePair<>( l_xrightrange, y ) ) ),

                IntStream.range( 1, l_ybottomrange.intValue() )
                         .boxed()
                         .flatMap( y -> Stream.of( new ImmutablePair<>( l_xleftrange, y ), new ImmutablePair<>( l_xrightrange, y ) ) )
            )
        );
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
    @Nullable
    public static Object getGrid( @Nonnull final ObjectMatrix2D p_grid, @Nonnull final DoubleMatrix1D p_position )
    {
        return p_grid.getQuick( yposition( p_position ).intValue(), xposition( p_position ).intValue() );
    }

    /**
     * gets an object from the grid
     *
     * @param p_grid grid
     * @param p_xvalue x-position
     * @param p_yvalue y-position
     * @return object or null
     */
    @Nullable
    public static Object getGrid( @Nonnull final ObjectMatrix2D p_grid, @Nonnull final Number p_xvalue, @Nonnull final Number p_yvalue )
    {
        return p_grid.getQuick( p_yvalue.intValue(), p_xvalue.intValue() );
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
     * calclulates the 1-norm between two vectors
     *
     * @param p_one first vector
     * @param p_second second vector
     * @return 1-norm value
     */
    public static Number norm1( @Nonnull final DoubleMatrix1D p_one, @Nonnull final DoubleMatrix1D p_second )
    {
        return DenseDoubleAlgebra.DEFAULT.norm1( p_one.copy().assign( p_second, DoubleFunctions.minus ) );
    }

    /**
     * calclulates the 2-norm between two vectors
     *
     * @param p_one first vector
     * @param p_second second vector
     * @return 2-norm value
     */
    public static Number norm2( @Nonnull final DoubleMatrix1D p_one, @Nonnull final DoubleMatrix1D p_second )
    {
        return DenseDoubleAlgebra.DEFAULT.norm2( p_one.copy().assign( p_second, DoubleFunctions.minus ) );
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
