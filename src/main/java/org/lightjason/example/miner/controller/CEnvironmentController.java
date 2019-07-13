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

package org.lightjason.example.miner.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * environment controller
 */
@RestController
@RequestMapping( "/mas/environment" )
public final class CEnvironmentController
{

    /**
     * returns the tilemap
     *
     * @return phaser tilemap structure
     * @see https://doc.mapeditor.org/en/stable/reference/json-map-format/
     */
    @GetMapping( value = "/map", produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, Object> tilemap()
    {
        // https://dev.to/jorbascrumps/loading-server-generated-tilemaps-with-phaser-4mm7
        final Map<String, Object> l_map = new HashMap<>();


        l_map.put( "version", 1.2 );
        l_map.put( "tiledversion", "1.2.4" );

        l_map.put( "tileheight", 64 );
        l_map.put( "tilewidth", 64 );
        l_map.put( "type", "map" );

        l_map.put( "orientation", "orthogonal" );
        l_map.put( "renderorder", "right-down" );
        l_map.put( "infinite", false );


        l_map.put( "background", "#ffffff" );
        l_map.put( "height", 10 );
        l_map.put( "weight", 10 );
        l_map.put( "layers", Collections.emptyList() );
        l_map.put( "properties", Collections.emptyList() );
        l_map.put( "tilesets", Collections.emptyList() );

        return l_map;
    }

}
