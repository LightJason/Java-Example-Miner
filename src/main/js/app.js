import React from 'react';
import ReactDOM from 'react-dom';
import Menu from "./menu.js"
import Content from "./content.js"

// https://spring.io/guides/tutorials/react-and-spring-data-rest/


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

ReactDOM.render( <App />, document.getElementById('app') );
