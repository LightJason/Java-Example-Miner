import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import Screen from "./screen.js";

/**
 * content definition with the screen
 */
export default class Content extends React.Component {

	render() {
		return (
            <main id="content">
                <Screen/>
            </main>
		);
	}

}
