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

    componentDidMount() {
        fetch(this.props.fetch)
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
                        <span className="spacepadright menu-item"><i className="fas fa-code"></i> {this.props.name}</span>
                        {l_upload}
                        {l_download}
                        {l_create}
                    </div>
                    <ul>
                        {Object.values(this.state).map(i => (<li>{i}</li>))}
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
