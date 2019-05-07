import Component from 'react';

// https://blog.hellojs.org/fetching-api-data-with-react-js-460fe8bbf8f2

export default class Miners extends Component {

    constructor(props) {
        super(props);
        this.state = [];
    }

    componentDidMount() {
        fetch('/agent/miners')
        .then( result => { return result.json(); } )
        .then( data => {
            this.setState( data.results.map( i => { return (<li>{i}</li>); } ) );
        } );
    }

    render()
    {
        return (
            <ul>
            {this.state.miners}
            </ul>
        );
    }

}
