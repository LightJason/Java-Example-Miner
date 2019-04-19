import React from 'react';

class Screen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            engine: null
        };
    }

    componentDidMount() {
        this.state.engine = new Phaser.Game( 800, 400, Phaser.AUTO, "phaser-container", {
            preload: this.preload,
            create: this.enginecreate
        } );

        //this.state.engine.stage.backgroundColor = "#ffffff";
    }

    enginecreate() {}
    enginepreload() {}

	render() {
		return (<div id="screen"></div>);
	}
}

export default Screen;
