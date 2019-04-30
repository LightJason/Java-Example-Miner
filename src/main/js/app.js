import React from 'react';
import ReactDOM from 'react-dom';
import Header from "./menu.js"
import Content from "./content.js"


class App extends React.Component {
	render() {
		return (
		    <>
                <Menu/>
                <Content/>
            </>
		);
	}
}

ReactDOM.render( <App />, document.getElementById('react') );
