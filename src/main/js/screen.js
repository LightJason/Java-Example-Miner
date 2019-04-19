import React from 'react';

class Screen extends React.Component {

    componentDidMount() {
        new Phaser.Game( 800, 400, Phaser.AUTO, "phaser-container", {
            preload: this.enginepreload,
            create: this.enginecreate,
            transparent: true
        } );
    }

    enginepreload() {}

    enginecreate() {
        this.stage.backgroundColor = "#eeeeee";
    }

	render() {
		return (<div id="screen"></div>);
	}
}

export default Screen;
