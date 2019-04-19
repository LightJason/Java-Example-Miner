import React from 'react';
import ReactDOM from 'react-dom';
import { Navbar, NavbarBrand, Container, Row, Col } from 'reactstrap';


class App extends React.Component {
	render() {
		return (
		    <div>
		        <Navbar light color="light" expand="w-100">
                    <NavbarBrand>LightJason - Miner</NavbarBrand>
                </Navbar>
                <Container>
                    <Row>LightJason Application</Row>
                </Container>
            </div>
		);
	}
}


ReactDOM.render( <App />, document.getElementById('react') )