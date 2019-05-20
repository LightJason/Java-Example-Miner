import React from 'react';
import { Controlled as CodeMirror } from 'react-codemirror2';
import Modal from 'react-bootstrap/Modal';

// https://stackoverflow.com/questions/31612598/call-a-react-component-method-from-outside
// https://github.com/scniro/react-codemirror2/issues/83

export default class Editor extends React.Component {

     constructor(props) {
        super(props);

        window.Editor = this;
        this.handleClose = this.handleClose.bind(this);

        this.state = {
            show: false,
            url: "",
            value: "",
            name: ""
        };
     }

    handleClose() {
        fetch(this.state.url, {
            method: "PUT",
            headers: {"Content-Type": "text/plain"},
            body: this.state.value
        })
        .then( result => { this.setState({ show: false }); } );
    }

    show(agentname, source, puturl) {
        this.setState({ show: true, value: source, url: puturl, name: agentname });
    }

    render() {
        return(
            <Modal size="lg" aria-labelledby="contained-modal-title-vcenter" centered show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">Agent Code Editor &mdash; {this.state.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CodeMirror
                        value={this.state.value}

                        options={{
                            theme: "cobalt",
                            lineNumbers: true,
                            indentUnit: 4
                        }}

                        onBeforeChange={(editor, data, value) => {
                            this.setState({value});
                        }}
                    />
                </Modal.Body>
            </Modal>
        );
    }

}
