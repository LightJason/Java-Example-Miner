import React from 'react';

// https://blog.hellojs.org/fetching-api-data-with-react-js-460fe8bbf8f2
// https://www.robinwieruch.de/react-fetching-data/

export default class Agents extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        fetch('/agent/miners')
        .then( result => { return result.json(); } )
        .then( data => {
            let l_list = Object.values( data );
            l_list.sort();
            this.setState(l_list);
        } );

    }

    render()
    {
        if (this.state)
            return (
                <>
                    <span className="menu-item"><i className="fas fa-code"></i> Agents</span>
                    <ul>
                        <li>Environment</li>
                        {Object.values(this.state).map(i => (<li>{i}</li>))}
                    </ul>
                </>
            );

        return (<span className="menu-item"><i className="fas fa-code"></i> Agents</span>);
    }

}
