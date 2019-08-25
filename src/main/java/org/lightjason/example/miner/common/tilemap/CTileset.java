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
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.Map;


/**
 * tileset definition
 */
public final class CTileset implements ITileset
{
    /**
     * image path
     */
    private final String m_image;
    /**
     * image height
     */
    private final Number m_height;
    /**
     * image width
     */
    private final Number m_weight;
    /**
     * image margin
     */
    private final Number m_margin;
    /**
     * image spacing
     */
    private final Number m_spacing;

    /**
     * ctor
     *
     * @param p_image image path
     * @param p_height image height
     * @param p_width image width
     * @param p_margin image margin
     * @param p_spacing image spacing
     */
    public CTileset( @Nonnull final String p_image,
                     @Nonnull @Positive final Number p_height, @Nonnull @Positive final Number p_width,
                     @Nonnull @PositiveOrZero final Number p_margin, @Nonnull @PositiveOrZero final Number p_spacing )
    {
        m_image = p_image;
        m_height = p_height;
        m_weight = p_width;
        m_spacing = p_spacing;
        m_margin = p_margin;
    }


    @Override
    public Map<String, Object> get()
    {
        // https://doc.mapeditor.org/en/stable/reference/json-map-format/#tileset-example
        final Map<String, Object> l_map = new HashMap<>();

        l_map.put( "image", m_image );
        l_map.put( "imagewidth", m_weight );
        l_map.put( "imageheight", m_height );
        l_map.put( "spacing", m_spacing );
        l_map.put( "margin", m_margin );

        return l_map;
    }
}
