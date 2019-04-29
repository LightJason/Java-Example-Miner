import React from 'react';
import ReactDOM from 'react-dom';
import Header from "./header.js"
import Content from "./content.js"


class App extends React.Component {
	render() {
		return (
		    <div>
                <Header/>
                <Content/>
            </div>
		);
	}
}

ReactDOM.render( <App />, document.getElementById('react') );
