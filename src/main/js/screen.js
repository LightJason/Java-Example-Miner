import React from 'react';

// https://medium.com/@michaelwesthadley/modular-game-worlds-in-phaser-3-tilemaps-1-958fc7e6bbd6

// https://gameartpartners.com/downloads/the-mines-platformer-tileset/
// https://gameartpartners.com/downloads/the-huge-super-tiny-bundle-48-hour-sale/

// https://gameartpartners.com/downloads/super-tiny-gold-miners-3-pack-of-characters/
// https://gameartpartners.com/downloads/super-tiny-cowboys/

// https://gameartpartners.com/downloads/indiana-adventurer/




// https://tutorialzine.com/2015/06/making-your-first-html5-game-with-phaser
// https://phaser.io/examples/v2/category/tilemaps
// https://www.zephyrcoding.com/phaser3-with-react/

// https://www.youtube.com/watch?v=ZhBhlOOtHPQ


/**
 * screen with phaser
 **/
export default class Screen extends React.Component {

    componentDidMount() {

        new Phaser.Game({
            type: Phaser.AUTO,
            width: 800,
            height: 800,
            parent: "screen",
            scene: {
                preload: this.preload,
                create: this.create,
                update: this.update
            }
        });
    }

    shouldComponentUpdate() {
        return false
    }

    preload() {
        console.log("preload");

        this.load.image( "tiles", "/assets/level.png" );
        this.load.tilemapTiledJSON("map", "/mas/environment/map");
    }

    create() {
        console.log("create");

        const l_map = this.make.tilemap({ key: "map" });
        const l_scenario = l_map.addTilesetImage("scenario", "scenario");
    }

    update() {
        //console.log("update");
    }

	render() {
		return (<div id="screen" className="w-75 h-75"></div>);
	}
}
