import React from 'react';
import ReactDOM from 'react-dom';
import { Navbar, NavbarBrand, Container, Row, Col } from 'reactstrap';


class App extends React.Component {
	render() {
		return (
		    <div>
		        <Navbar dark color="dark" expand="w-100">
                    <NavbarBrand href="#">LightJason - Miner</NavbarBrand>
                </Navbar>
                <Container>
                    <Row>LightJason Application</Row>
                </Container>
            </div>
		);
	}
}


ReactDOM.render( <App />, document.getElementById('react') )