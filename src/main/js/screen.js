import React from 'react';

class Screen extends React.Component {

    constructor(props) {
        super(props);
        this.game = null;
    }

    componentDidMount() {
        this.game = new Phaser.Game( 800, 400, Phaser.AUTO, "phaser-container" );
    }

	render() {
		return (<div id="screen"></div>);
	}
}

export default Screen;
