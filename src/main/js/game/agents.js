import React from 'react';

// https://blog.hellojs.org/fetching-api-data-with-react-js-460fe8bbf8f2
// https://www.robinwieruch.de/react-fetching-data/

export default class Agents extends React.Component {

    constructor(props) {
        super(props);
        //this.state = [];
    }

    componentDidMount() {
        fetch('/agent/miners')
        .then( result => { return result.json(); } )
        .then( data => {
            console.log(data);
            //this.setState( data.results.map( i => { return (<li>{i}</li>); } ) );
        } );
    }

    render()
    {
        return (<ul><li>test</li></ul>);
    }

}
