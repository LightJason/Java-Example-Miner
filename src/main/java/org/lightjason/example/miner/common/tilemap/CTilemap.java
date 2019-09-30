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

package org.lightjason.example.miner.common.tilemap;

import com.google.common.collect.Streams;

import javax.annotation.Nonnull;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * tilemap
 */
public final class CTilemap implements ITilemap
{
    /**
     * tilemap height
     */
    private final Number m_height;
    /**
     * tilemap width
     */
    private final Number m_weight;

    /**
     * ctor
     *
     * @param p_height tilemap height
     * @param p_weight tilemap width
     */
    public CTilemap( @Nonnull @Positive final Number p_height, @Nonnull @Positive final Number p_weight )
    {
        m_height = p_height;
        m_weight = p_weight;
    }

    @Override
    public Map<String, Object> apply( final Stream<ILayer> p_layer, final Stream<ITileset> p_tileset )
    {
        // http://labs.phaser.io/assets/tilemaps/maps/cybernoid.json
        // https://doc.mapeditor.org/en/stable/reference/json-map-format/
        // https://dev.to/jorbascrumps/loading-server-generated-tilemaps-with-phaser-4mm7

        // https://doc.mapeditor.org/en/stable/reference/json-map-format/#map
        final Map<String, Object> l_map = new HashMap<>();

        l_map.put( "version", 1.2 );
        l_map.put( "tiledversion", "1.2.4" );

        l_map.put( "tileheight", 64 );
        l_map.put( "tilewidth", 64 );
        l_map.put( "type", "map" );

        l_map.put( "orientation", "orthogonal" );
        l_map.put( "renderorder", "right-down" );
        l_map.put( "infinite", false );


        l_map.put( "height", m_height.intValue() );
        l_map.put( "weight", m_weight.intValue() );
        l_map.put( "properties", Collections.emptyList() );

        l_map.put(
            "tilesets",
            Streams.zip(
                p_tileset,
                IntStream.iterate( 1, i -> i + 1 ).boxed(),
                ( i, j ) -> {
                    final Map<String, Object> l_tileset = i.get();
                    l_tileset.put( "tilesets", j );
                    return l_tileset;
                }
            ).toArray()
        );

        l_map.put(
            "layers",
            Streams.zip(
                p_layer,
                IntStream.iterate( 1, i -> i + 1 ).boxed(),
                ( i, j ) -> {
                    final Map<String, Object> l_layer = i.get();
                    l_layer.put( "id", i );
                    return l_layer;
                }
            ).toArray()
        );

        return l_map;
    }
}
