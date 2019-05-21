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

import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.SparseDoubleMatrix1D;
import org.junit.Assert;
import org.junit.Test;
import org.lightjason.agentspeak.action.grid.routing.EDistance;


/**
 * test common
 */
public final class TestCCommon
{

    /**
     * test gaussian
     */
    @Test
    public void gaussian()
    {
        Assert.assertArrayEquals(
            new Object[]{
                1.0104542167073785e-14,
                1.826944081672919e-11,
                1.826944081672919e-11,
                1.826944081672919e-11,
                1.826944081672919e-11,
                1.2151765699646572e-8,
                1.2151765699646572e-8,
                1.2151765699646572e-8,
                1.2151765699646572e-8,
                1.2151765699646572e-8,
                1.2151765699646572e-8,
                1.2151765699646572e-8,
                1.2151765699646572e-8,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.9734390294685958e-6,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                2.6766045152977074e-4,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.008863696823876015,
                0.10798193302637613,
                0.10798193302637613,
                0.10798193302637613,
                0.10798193302637613,
                0.10798193302637613,
                0.10798193302637613,
                0.10798193302637613,
                0.10798193302637613,
                0.48394144903828673,
                0.48394144903828673,
                0.48394144903828673,
                0.48394144903828673,
                0.7978845608028654,
            },

            CCommon.coordinates( 0, 0, 4, x -> true, y -> true )
                   .sequential()
                   .map( i -> CCommon.gaussian(
                       EDistance.MANHATTAN.apply(
                               new SparseDoubleMatrix1D( 2 ),
                               new DenseDoubleMatrix1D( new double[]{i.getLeft().doubleValue(), i.getRight().doubleValue()} )
                           ),
                           1, 0, 2
                       ).doubleValue()
                   )
                   .sorted()
                   .toArray()
        );
    }

}
