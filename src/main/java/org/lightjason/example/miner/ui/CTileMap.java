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

package org.lightjason.example.miner.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.stream.IntStream;


/**
 * tilemap object
 */
public final class CTileMap implements ITileMap
{
    /**
     * highlight color
     */
    private static final float HIGHLIGHT = 0.75f;
    /**
     * darklight color
     */
    private static final float DARKLIGHT = 0.5f;
    /**
     * color alpha value
     */
    private static final float ALPHA = 0.4f;
    /**
     * cell size
     */
    private int m_cellsize;
    /**
     * number of rows
     */
    private int m_rows;
    /**
     * number of columns
     */
    private int m_columns;

    /**
     * ctor
     *
     * @param p_rows number of rows
     * @param p_columns number of columns
     * @param p_cellsize cellsize
     */
    public CTileMap( final int p_rows, final int p_columns, final int p_cellsize )
    {
        m_rows = p_rows;
        m_columns = p_columns;
        m_cellsize = p_cellsize;
    }


    @Override
    public TiledMap get()
    {

        // create background checkerboard with a tile map
        final Pixmap l_pixmap = new Pixmap( 2 * m_cellsize, m_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( new Color( HIGHLIGHT, HIGHLIGHT, HIGHLIGHT, ALPHA ) );
        l_pixmap.fillRectangle( 0, 0, m_cellsize, m_cellsize );
        l_pixmap.setColor( new Color( DARKLIGHT, DARKLIGHT, DARKLIGHT, ALPHA ) );
        l_pixmap.fillRectangle( m_cellsize, 0, m_cellsize, m_cellsize );

        final Texture l_texture = new Texture( l_pixmap );
        l_pixmap.dispose();

        final TiledMapTile l_region1 = new StaticTiledMapTile( new TextureRegion( l_texture, 0, 0, m_cellsize, m_cellsize ) );
        final TiledMapTile l_region2 = new StaticTiledMapTile( new TextureRegion( l_texture, m_cellsize, 0, m_cellsize, m_cellsize ) );

        // create tilemap
        final TiledMap l_map = new TiledMap();
        final TiledMapTileLayer l_layer = new TiledMapTileLayer( m_columns, m_rows, m_cellsize, m_cellsize );
        l_map.getLayers().add( l_layer );

        IntStream
            .range( 0, m_columns )
            .forEach( x ->
            {
                IntStream
                    .range( 0, m_rows )
                    .forEach( y ->
                    {
                        final TiledMapTileLayer.Cell l_cell = new TiledMapTileLayer.Cell();
                        l_layer.setCell( x, y, l_cell );
                        l_cell.setTile(
                            y % 2 != 0
                            ? x % 2 != 0 ? l_region1 : l_region2
                            : x % 2 != 0 ? l_region2 : l_region1
                        );
                    } );
            } );

        return l_map;
    }

    @Override
    public int cellsize()
    {
        return m_cellsize;
    }

    @Override
    public int rows()
    {
        return m_rows;
    }

    @Override
    public int columns()
    {
        return m_columns;
    }
}
