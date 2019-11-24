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

package org.lightjason.example.miner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.lightjason.agentspeak.common.CCommon;

import javax.annotation.Nonnull;

/**
 * main application
 */
public final class CApplication
{
    /**
    private ctor
    */
    private CApplication()
    {
    }


    /**
     * main method
     *
     * @param p_args arguments
     */
    //Checkstyle:OFF:UncommentedMain
    public static void main( @Nonnull final String[] p_args )
    {
        final Options l_clioptions = new Options();
        l_clioptions.addOption( "help", false, CCommon.languagestring( CApplication.class, "help" ) );
        l_clioptions.addOption( "miner", true, CCommon.languagestring( CApplication.class, "miner" ) );
        l_clioptions.addOption( "trader", false, CCommon.languagestring( CApplication.class, "trader" ) );
        l_clioptions.addOption( "environment", true, CCommon.languagestring( CApplication.class, "environment" ) );
        l_clioptions.addOption( "port", true, CCommon.languagestring( CApplication.class, "port" ) );
        l_clioptions.addOption( "host", true, CCommon.languagestring( CApplication.class, "host" ) );

        final CommandLine l_cli;
        try
        {
            l_cli = new DefaultParser().parse( l_clioptions, p_args );
        }
        catch ( final Exception l_exception )
        {
            System.err.println( CCommon.languagestring( CApplication.class, "parseerror", l_exception.getLocalizedMessage() ) );
            System.exit( -1 );
            return;
        }


        // --- process CLI arguments and push configuration ------------------------------------------------------------
        if ( l_cli.hasOption( "help" ) )
        {
            final HelpFormatter l_formatter = new HelpFormatter();
            l_formatter.printHelp( new java.io.File( CApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getName(), l_clioptions );
            System.exit( 0 );
        }
        System.out.println( "start" );
    }
    //Checkstyle:ON:UncommentedMain

}
