import React from 'react';

class Screen extends React.Component {

    componentDidMount() {
        // https://tutorialzine.com/2015/06/making-your-first-html5-game-with-phaser
        // https://phaser.io/examples/v2/category/tilemaps
        // https://www.zephyrcoding.com/phaser3-with-react/

        let l_game = new Phaser.Game( 800, 800, Phaser.AUTO, "phaser-container", {
            transparent: true
        } );
    }

	render() {
		return (<div id="screen"></div>);
	}
}

export default Screen;
