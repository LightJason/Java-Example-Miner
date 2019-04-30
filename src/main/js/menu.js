import React from 'react';
import { Navbar, NavbarBrand, NavbarToggler, Nav, NavItem, NavLink, Collapse, UncontrolledDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

// https://reactstrap.github.io/components/navbar/

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
            <Navbar bg="dark" variant="dark">
                <NavbarBrand>
                  <img
                    alt="LightJason Logo"
                    src="assets/logo.png"
                    width="30"
                    height="30"
                    className="d-inline-block align-top"
                  />
                  {' LightJason - Miner '}
                  <NavbarToggler onClick={this.toggle} />
                </NavbarBrand>
                <Collapse isOpen={this.state.isOpen} navbar>
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <NavLink href="/components/">Components</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink href="https://github.com/reactstrap/reactstrap">GitHub</NavLink>
                        </NavItem>
                        <UncontrolledDropdown nav inNavbar>
                            <DropdownToggle nav caret>Options</DropdownToggle>
                                <DropdownMenu right>
                                <DropdownItem>Option 1</DropdownItem>
                                <DropdownItem>Option 2</DropdownItem>
                                <DropdownItem divider />
                                <DropdownItem>Reset</DropdownItem>
                            </DropdownMenu>
                      </UncontrolledDropdown>
                    </Nav>
                </Collapse>
            </Navbar>
		);
	}
}
