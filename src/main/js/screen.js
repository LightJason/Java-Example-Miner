import React from 'react';

export default class Screen extends React.Component {

    componentDidMount() {
        // https://tutorialzine.com/2015/06/making-your-first-html5-game-with-phaser
        // https://phaser.io/examples/v2/category/tilemaps
        // https://www.zephyrcoding.com/phaser3-with-react/

        new Phaser.Game({
            type: Phaser.AUTO,
            width: 800,
            height: 800,
            parent: "screen"
            //scene: [ExampleScene]
        });
    }

    shouldComponentUpdate() {
        return false
    }

	render() {
		return (<div id="screen" className="w-75 h-75"></div>);
	}
}
