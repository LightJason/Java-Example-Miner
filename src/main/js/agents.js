import React from 'react';
import AgentsGlobalAction from './agentsglobalaction.js'

// https://blog.hellojs.org/fetching-api-data-with-react-js-460fe8bbf8f2
// https://www.robinwieruch.de/react-fetching-data/

/**
 * agent submenu
 */
export default class Agents extends React.Component {

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
                    <div>
                        <span className="spacepadright menu-item"><i className="fas fa-code"></i> Agents</span>
                        <AgentsGlobalAction/>
                    </div>
                    <ul>
                        <li>Environment</li>
                        {Object.values(this.state).map(i => (<li>{i}</li>))}
                    </ul>
                </>
            );

        return (<div><span className="menu-item"><i className="fas fa-code"></i> Agents</span><AgentsGlobalAction/></div>);
    }

}
