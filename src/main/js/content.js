import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import Screen from "./screen.js";
import Editor from "./editor.js";

/**
 * content definition with the screen
 */
export default class Content extends React.Component {

	render() {
		return (
		    <>
                <Editor/>
                <main id="content">
                    <Screen/>
                </main>
            </>
		);
	}

}
