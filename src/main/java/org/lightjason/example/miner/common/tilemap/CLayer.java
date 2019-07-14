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

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;


/**
 * tilemap layer structure
 */
public final class CLayer implements ILayer
{
    /**
     * layer name
     */
    private final String m_name;

    /**
     * ctor
     *
     * @param p_name name
     */
    public CLayer( @Nonnull final String p_name )
    {
        m_name = p_name;
    }

    @Override
    public Map<String, Object> get()
    {
        // https://doc.mapeditor.org/en/stable/reference/json-map-format/#json-tileset
        final Map<String, Object> l_layer = new HashMap<>();

        l_layer.put( "name", m_name );
        l_layer.put( "type", "tileset" );

        return l_layer;
    }
}
