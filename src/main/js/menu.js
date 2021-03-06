import React from 'react';
import { Navbar, NavbarBrand } from 'react-bootstrap';
import { scaleRotate as NavMenu } from 'react-burger-menu'
import Agents from './agents.js'

// https://reactstrap.github.io/components/navbar/
// https://bootstrapious.com/p/bootstrap-sidebar
// https://stackoverflow.com/questions/39974486/accordion-sidebar-menu-using-nav-components-with-react-bootstrap
// https://reactjsexample.com/a-ready-to-use-menu-component-for-react/
// http://negomi.github.io/react-burger-menu/

/**
 * menu structure
 */
export default class Menu extends React.Component {

    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.setState({
            isOpen: false
        });
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

	render() {
		return (
		/*
		    <div id="navigation">
		        <Navbar bg="dark" variant="dark">
                    <NavbarBrand>
                      <img
                        alt="LightJason Logo"
                        src="assets/logo.png"
                        width="30"
                        height="30"
                        className="d-inline-block align-top"
                      />
                      {' LightJason - Miner'}
                     </NavbarBrand>
                </Navbar>
        */
                <NavMenu pageWrapId={ "content" } outerContainerId={ "app" } customBurgerIcon={ <i className="fas fa-bars"></i> }>
                    <Agents name="Miners"      list="/mas/agent/miners"       source="/mas/agent/miner"       download="/mas/agent/download/miner"       create="/mas/agent/miner"  remove="/mas/agent/miner"  deletable={true} />
                    <Agents name="Traders"     list="/mas/agent/traders"      source="/mas/agent/trader"      download="/mas/agent/download/trader"      create="/mas/agent/trader" remove="/mas/agent/trader" deletable={true} />
                    <Agents name="Environment" list="/mas/agent/environments" source="/mas/agent/environment" download="/mas/agent/download/environment" />
                    <a className="menu-item" href="https://lightjason.org/contact/"><i className="fas fa-envelope"></i> Contact</a>
                </NavMenu>
		    //</div>
		);
	}
}
