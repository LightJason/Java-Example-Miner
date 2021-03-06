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

import org.lightjason.example.miner.common.tilemap.CLayer;
import org.lightjason.example.miner.common.tilemap.CTilemap;
import org.lightjason.example.miner.common.tilemap.CTileset;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Stream;


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
        // <map version="1.0" orientation="orthogonal" width="30" height="15" tilewidth="64" tileheight="64">
        // <tileset firstgid="1" source="sprites.tsx"/>
         // <layer name="bg" width="30" height="15">

        return new CTilemap(
            10,
            10
        ).apply(
            Stream.of(
                new CLayer( "World" )
            ),
            Stream.of(
                new CTileset( "/assets/tileset.png", 715, 715, 0, 1 )
            )
        );
    }

}
