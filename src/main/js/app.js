import React from 'react';
import ReactDOM from 'react-dom';
import { Container, Row, Col } from 'reactstrap';


class App extends React.Component {
	render() {
		return (<Container><Row>LightJason Application</Row></Container>);
	}
}


ReactDOM.render( <App />, document.getElementById('react') )