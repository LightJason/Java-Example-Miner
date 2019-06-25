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

import java.util.Map;
import java.util.stream.Stream;


/**
 * tilemap
 */
public final class CTilemap implements ITilemap
{
    private final Number m_height;
    private final Number m_weight;
    private final boolean m_infinite;
    private final IOrientation m_orientation;

    public CTilemap( final Number p_height, final Number p_weight, final boolean p_infinite,
                     final IOrientation p_orientation
    )
    {
        m_height = p_height;
        m_weight = p_weight;
        m_infinite = p_infinite;
        m_orientation = p_orientation;
    }

    @Override
    public Map<String, Object> apply( final Stream<ILayer> p_iLayerStream )
    {
        // http://labs.phaser.io/assets/tilemaps/maps/cybernoid.json
        // https://doc.mapeditor.org/en/stable/reference/json-map-format/
        return null;
    }
}
