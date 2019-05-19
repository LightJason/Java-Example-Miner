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
          agentname: "",
          sourceurl: ""
        };
     }

    handleClose() {
        this.setState({ show: false });
    }

    show(name, url) {
        this.setState({ show: true, agentname: name, sourceurl: url });
    }

    render() {
        return(
            <Modal size="lg" aria-labelledby="contained-modal-title-vcenter" centered show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">Code Editor</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CodeMirror
                        options={{
                            theme: "cobalt",
                            lineNumbers: true,
                            indentUnit: 4
                        }}

                        editorDidMount={editor => {
                            fetch( encodeURI( this.state.sourceurl + "/" + this.state.agentname) )
                            .then( result => { return result.text(); } )
                            .then( data => { console.log("mount: " + data); editor.setValue(data); } );
                        }}

                        editorDidConfigure={editor => {
                            console.log("configured");
                        }}

                        editorWillUnmount={editor => {
                            console.log("unmount");
                        }}
                    />
                </Modal.Body>
            </Modal>
        );
    }

}
