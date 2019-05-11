import {Controlled as CodeMirror} from 'react-codemirror2'

export default class Editor extends React.Component {

     constructor(props) {
        super(props);
     }

    render() {
        return (
            <CodeMirror value={ "foo" } />
        );
    }

}
