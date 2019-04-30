import React from 'react';
import { Container, Row, Col } from 'reactstrap';
import Screen from "./screen.js"


export default class Content extends React.Component {
	render() {
		return (
            <Container>
                <Screen/>
            </Container>
		);
	}
}
