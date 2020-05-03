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

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.lightjason.example.miner.scenario.IAgentMovingGenerator;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


/**
 * screen entry point, all graphical components
 * based on the LibGDX library
 *
 * @note with "s" a screenshot can be created
 * @warning rendering elements must be set within the create call for avoid instantiation error
 * @see <a href="https://libgdx.badlogicgames.com/"></a>
 * @see <a href="https://github.com/libgdx/libgdx/wiki/Tile-maps"></a>
 * @see <a href="http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx"></a>
 */
public final class CScreen extends ApplicationAdapter implements IScreen, InputProcessor
{
    /**
     * instance
     */
    public static final AtomicReference<IScreen> SCREEN = new AtomicReference<>();
    /**
     * wait loops for visualization
     */
    public static final int WAITLOOPS = 1000;
    /**
     * time for each wait loop
     */
    public static final int WAITTIME = 1000;
    /**
     * zoom speed
     */
    private static final int ZOOMSPEED = 2;
    /**
     * drag speed
     */
    private static final int DRAGSPEED = 80;
    /**
     * environment tilemap reference
     */
    private final ITileMap m_environment;
    /**
     * sprite list
     */
    private final Set<ISprite> m_sprites;
    /**
     * last camera position
     */
    private final Vector3 m_lasttouch = new Vector3();
    /**
     * camera definition
     */
    private OrthographicCamera m_camera;
    /**
     * sprite batch painting
     */
    private SpriteBatch m_spritebatch;
    /**
     * renderer
     */
    private OrthogonalTiledMapRenderer m_render;
    /**
     * simulation step
     */
    private int m_iteration;
    /**
     * flag for disposed screen
     */
    private volatile boolean m_isdisposed;
    /**
     * flag for taking a screenshot
     */
    private volatile boolean m_screenshottake;
    /**
     * agent generator for visuablity
     */
    private final IAgentMovingGenerator m_minergenerator;



    /**
     * ctor
     *
     * @param p_sprites list with executables
     * @param p_environment environment reference
     */
    private CScreen( @Nonnull final Set<ISprite> p_sprites, @Nonnull final ITileMap p_environment, @Nonnull final IAgentMovingGenerator p_minergenerator )
    {
        m_environment = p_environment;
        m_sprites = p_sprites;
        m_minergenerator = p_minergenerator;
    }

    @Override
    public final void create()
    {
        // create orthogonal camera perspective
        final float l_unit = 1.0f / m_environment.cellsize();

        // create execution structure for painting
        m_spritebatch = new SpriteBatch();

        // create environment view and put all objects in it
        m_render = new OrthogonalTiledMapRenderer( m_environment.get(), l_unit, m_spritebatch );

        m_camera = new OrthographicCamera( m_environment.columns(), m_environment.rows() );
        m_camera.setToOrtho( false, m_environment.columns() * l_unit, m_environment.rows() * l_unit );
        m_camera.position.set( m_environment.columns() / 2f, m_environment.rows() / 2f, 0 );
        m_camera.zoom = m_environment.cellsize();
        m_render.setView( m_camera );

        // initialize agent visiblility structure
        m_minergenerator.spriteinitialize( m_sprites, l_unit );

        // set input processor
        Gdx.input.setInputProcessor( this );
    }

    @Override
    public final void render()
    {
        // camera update must be the first for reaction on input device events
        m_camera.update();

        // create black background
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // environment tilemap painting
        m_render.setView( m_camera );
        m_render.render();


        // object sprite painting
        m_spritebatch.setProjectionMatrix( m_camera.combined );
        m_spritebatch.begin();
        m_sprites.forEach( i -> i.sprite().draw( m_spritebatch ) );
        m_spritebatch.end();


        // take screenshot at the rendering end
        this.screenshot();
    }

    @Override
    public final void dispose()
    {
        // dispose flag is set to stop parallel simulation execution outside the screen
        m_isdisposed = true;
        m_spritebatch.dispose();
        m_render.dispose();
        super.dispose();
    }

    /**
     * returns if the screen is disposed
     *
     * @return disposed flag
     */
    public final boolean isDisposed()
    {
        return m_isdisposed;
    }


    /**
     * sets the iteration value
     *
     * @param p_iteration iteration value
     * @return screen reference
     */
    public final CScreen iteration( final int p_iteration )
    {
        m_iteration = p_iteration;
        return this;
    }

    @Override
    public final boolean keyDown( final int p_key )
    {
        switch ( p_key )
        {

            // r key
            case 46:
                m_camera.position.set( m_environment.columns() / 2f, m_environment.rows() / 2f, 0 );
                m_camera.zoom = m_environment.cellsize();
                return false;

            // s key
            case 47:
                m_screenshottake = true;
                return false;

            // + key
            case 72:
                m_camera.zoom *= 1 - ZOOMSPEED;
                return false;

            // - key
            case 76:
                m_camera.zoom *= 1 + ZOOMSPEED;
                return false;

            default:
                return false;
        }

    }

    @Override
    public final boolean keyUp( final int p_key )
    {
        return false;
    }

    @Override
    public final boolean keyTyped( final char p_char )
    {
        return false;
    }

    @Override
    public final boolean touchDown( final int p_screenx, final int p_screeny, final int p_pointer, final int p_button )
    {
        m_lasttouch.set( p_screenx, p_screeny, 0 );
        return false;
    }

    @Override
    public final boolean touchUp( final int p_xposition, final int p_yposition, final int p_pointer, final int p_button )
    {
        return false;
    }

    @Override
    public final boolean touchDragged( final int p_screenx, final int p_screeny, final int p_pointer )
    {
        m_camera.translate(
            new Vector3().set( p_screenx, p_screeny, 0 )
                         .sub( m_lasttouch )
                         .scl( -DRAGSPEED, DRAGSPEED, 0 )
                         .scl( m_camera.zoom )
        );
        m_lasttouch.set( p_screenx, p_screeny, 0 );
        return false;
    }

    @Override
    public final boolean mouseMoved( final int p_xposition, final int p_yposition )
    {
        return false;
    }

    @Override
    public final boolean scrolled( final int p_amount )
    {
        m_camera.zoom *= p_amount > 0
                         ? 1 + ZOOMSPEED
                         : 1 - ZOOMSPEED;
        return false;
    }

    /**
     * takes a screenshot
     *
     * @return self reference
     */
    public CScreen screenshot()
    {
        if ( !m_screenshottake )
            return this;

        m_screenshottake = false;
        final byte[] l_pixels = ScreenUtils.getFrameBufferPixels( 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true );
        final Pixmap l_pixmap = new Pixmap( Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888 );

        BufferUtils.copy( l_pixels, 0, l_pixmap.getPixels(), l_pixels.length );
        PixmapIO.writePNG(
            new FileHandle( MessageFormat.format( "image{0}{1}", String.format( "%08d", m_iteration ), ".png" ) ),
            l_pixmap
        );
        l_pixmap.dispose();
        return this;
    }

    @Override
    public ITileMap tilemap()
    {
        return m_environment;
    }

    /**
     * factory to create a screen
     *
     * @param p_width window width
     * @param p_height window height
     * @param p_sprites set with sprites / agents
     * @param p_environment environment agent
     */
    public static void open( @Nonnull final Number p_width, @Nonnull final Number p_height,
                             @NonNull final Set<ISprite> p_sprites, @NonNull final ITileMap p_environment,
                             @Nonnull final IAgentMovingGenerator p_minergenerator )
    {
        // force-exit must be disabled for avoid error exiting
        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();

        l_config.forceExit = true;
        l_config.width = p_width.intValue();
        l_config.height = p_height.intValue();

        final CScreen l_screen = new CScreen( p_sprites, p_environment, p_minergenerator );
        SCREEN.compareAndSet( null,  l_screen );
        new LwjglApplication( l_screen, l_config );
    }

}
