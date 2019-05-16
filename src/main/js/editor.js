import React from 'react';
import { Controlled as CodeMirror } from 'react-codemirror2';
import Modal from 'react-bootstrap/Modal';

// https://stackoverflow.com/questions/31612598/call-a-react-component-method-from-outside

export default class Editor extends React.Component {

     constructor(props) {
        super(props);

        window.Editor = this;
        this.handleClose = this.handleClose.bind(this);

        this.state = {
          show: false,
          agentname: ""
        };
     }

    handleClose() {
        this.setState({ show: false });
    }

    show(name, url) {
        fetch(encodeURI(url+"/"+name))
        .then( result => { return result.text(); } )
        .then( data => { console.log(data); } );

        this.setState({ show: true, agentname: name });
    }

    render() {
        return(
            <Modal size="lg" aria-labelledby="contained-modal-title-vcenter" centered show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">Code Editor &mdash; {this.state.agentname}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CodeMirror value={ "foo" } />
                </Modal.Body>
            </Modal>
        );
    }

}
