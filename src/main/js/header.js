import React from 'react';
import { Navbar, NavbarBrand } from 'reactstrap';


class Header extends React.Component {
	render() {
		return (
            <Navbar dark color="dark" expand="w-100">
                <NavbarBrand><span id="title">LightJason - Miner</span></NavbarBrand>
            </Navbar>
		);
	}
}

export default Header;