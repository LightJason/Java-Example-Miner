import React from 'react';
import ReactDOM from 'react-dom';
import Menu from "./menu.js"
import Content from "./content.js"
import Editor from "./editor.js";

// https://spring.io/guides/tutorials/react-and-spring-data-rest/


/**
 * main application
 */
class App extends React.Component {

	render() {
		return (
		    <>
		        <Editor/>
                <Menu/>
                <Content/>
            </>
		);
	}

}

ReactDOM.render( <App/>, document.getElementById('app') );
