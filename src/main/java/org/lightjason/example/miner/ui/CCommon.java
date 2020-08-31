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

import cern.colt.matrix.tdouble.DoubleMatrix1D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.apache.commons.io.IOUtils;
import org.lightjason.example.miner.CApplication;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * common class for screen
 */
public final class CCommon
{

    /**
     * ctor
     */
    private CCommon()
    {}

    /**
     * set position to sprite
     *
     * @param p_sprite sprite
     * @param p_position position vector
     */
    public static void setSprite( @Nonnull final Sprite p_sprite, @Nonnull final DoubleMatrix1D p_position )
    {
        p_sprite.setPosition(
            org.lightjason.example.miner.scenario.CCommon.xposition( p_position ).intValue(),
            org.lightjason.example.miner.scenario.CCommon.yposition( p_position ).intValue()
        );
    }

    /**
     * copy a file from the jar to the gdx local storage
     *
     * @param p_input path within the jar
     * @return gdx file handle
     */
    public static FileHandle copyToGdxLocal( @Nonnull final String p_input )
    {
        final FileHandle l_handle = Gdx.files.local( p_input.replace( "/", "_" ) );
        if ( l_handle.exists() )
            return l_handle;

        try
            (
                final InputStream l_inputstream = CApplication.class.getResourceAsStream( p_input );
                final OutputStream l_outputstream = Files.newOutputStream( Paths.get( l_handle.path() ) )
            )
        {
            IOUtils.copy( l_inputstream, l_outputstream );
            return l_handle;
        }
        catch ( final IOException l_exception )
        {
            throw new UncheckedIOException( l_exception );
        }
    }
}
