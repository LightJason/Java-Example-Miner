import React from 'react';
import { Container, Row, Col } from 'reactstrap';
import Screen from "./screen.js"


class Content extends React.Component {
	render() {
		return (
            <Container>
                <Row>LightJason Application</Row>
                <Row><Screen/></Row>
            </Container>
		);
	}
}

export default Content;
