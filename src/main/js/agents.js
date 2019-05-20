import React from 'react';
import FileSaver from 'file-saver';

// https://blog.hellojs.org/fetching-api-data-with-react-js-460fe8bbf8f2
// https://www.robinwieruch.de/react-fetching-data/
// https://stackoverflow.com/questions/35206589/how-to-download-fetch-response-in-react-as-file
// https://medium.com/yellowcode/download-api-files-with-react-fetch-393e4dae0d9e
// https://jsfiddle.net/cowboy/hHZa9/
// https://gist.github.com/yiwenl/8f2b735a2263bc93ee33

/**
 * agent submenu
 */
export default class Agents extends React.Component {

    constructor(props) {
        super(props);

        this.downloadClick = this.downloadClick.bind(this);
        this.uploadClick = this.uploadClick.bind(this);
        this.createClick = this.createClick.bind(this);
        this.createClick = this.createClick.bind(this);
        this.openEditorClick = this.openEditorClick.bind(this);
    }

    downloadClick() {
        FileSaver( this.props.download, this.props.name + ".json" );
    }

    uploadClick() {
        console.log("upload");
    }

    createClick() {
        console.log("create");
    }

    deleteClick() {
        console.log("delete");
    }

    openEditorClick(name, url) {
        const l_url = encodeURI( url + "/" + name )

        fetch( l_url )
        .then( result => { return result.text(); } )
        .then( data => { window.Editor.show(name, data, l_url); })
    }

    componentDidMount() {
        fetch(this.props.list)
        .then( result => { return result.json(); } )
        .then( data => {
            let l_list = Object.values( data );
            l_list.sort();
            this.setState(l_list);
        } );
    }

    render()
    {
        const l_upload = this.props.upload ? <a onClick={this.uploadClick}><i className="spacepad fas fa-upload"></i></a> : <></>;
        const l_download = this.props.download ? <a onClick={this.downloadClick}><i className="spacepad fas fa-download"></i></a> : <></>;
        const l_create = this.props.create ? <a onClick={this.createClick}><i className="spacepad fas fa-plus-square"></i></a> : <></>;

        if (this.state)
            return (
                <>
                    <div>
                        <span className="menu-item"><i className="fas fa-code"></i> {this.props.name}</span>
                        {l_upload}
                        {l_download}
                        {l_create}
                    </div>
                    <ul className="agents">
                        {Object.values(this.state).map(i => (
                            <li>
                                <a onClick={() => this.openEditorClick(i, this.props.source)}>{i}</a>
                                <a onClick={() => this.deleteClick(i, this.props.source)}><i className="spacepad fas fa-unlink"></i></a>
                            </li>
                        ))}
                    </ul>
                </>
            );

        return (
        <div>
            <span className="menu-item"><i className="fas fa-code"></i> {this.props.name}</span>
            {l_upload}
            {l_download}
            {l_create}
        </div>);
    }

}
