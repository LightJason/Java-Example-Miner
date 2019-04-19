const React = require('react');
const ReactDOM = require('react-dom');


class App extends React.Component {
	render() {
		return (<p><strong>LightJason - Miner</strong></p>);
	}
}


ReactDOM.render( <App />, document.getElementById('react') )