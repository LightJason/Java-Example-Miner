import React from 'react';
import { Navbar, NavbarBrand } from 'reactstrap';
import { scaleRotate as NavMenu } from 'react-burger-menu'

// https://reactstrap.github.io/components/navbar/
// https://bootstrapious.com/p/bootstrap-sidebar
// https://stackoverflow.com/questions/39974486/accordion-sidebar-menu-using-nav-components-with-react-bootstrap
// https://reactjsexample.com/a-ready-to-use-menu-component-for-react/
// http://negomi.github.io/react-burger-menu/

export default class Menu extends React.Component {

    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false
        };
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
                <NavMenu pageWrapId={ "content" } outerContainerId={ "app" }>
                    <a id="about" className="menu-item" href="/about">About</a>
                    <a id="contact" className="menu-item" href="https://lightjason.org/contact/">Contact</a>
                    <a onClick={ this.showSettings } className="menu-item--small" href="">Settings</a>
                </NavMenu>
		    //</div>
		);
	}
}
