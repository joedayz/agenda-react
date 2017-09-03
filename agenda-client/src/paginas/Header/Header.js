import React from 'react';
import { Link } from 'react-router';

var Header = React.createClass({
	render: function() {
		
		return (
			<div className="header">
				<p className="header-info">
					Agenda React App
				</p>
				<div className="menu">
					<Link to="/empleados" className="menu-link-item" activeClassName="active">Empleados</Link>
					<Link to="/departamentos" className="menu-link-item" activeClassName="active">Departamentos</Link>
					<Link to="/reuniones" className="menu-link-item" activeClassName="active">Reuniones</Link>
				</div>
			</div>

		);
	}
});

export default Header;