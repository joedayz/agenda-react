import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, browserHistory } from 'react-router'

import MainTemplate from './paginas/MainTemplate';
import Empleados from './paginas/Empleados';
import Departamentos from './paginas/Departamentos';
import Reuniones from './paginas/Reuniones';

ReactDOM.render(
	<Router history={browserHistory}>
		<Route path="/" component={MainTemplate}>
			<Route path="empleados" components={{main: Empleados}} />
			<Route path="departamentos" components={{main: Departamentos}} />
			<Route path="reuniones" components={{main: Reuniones}} />
		</Route>
	</Router>,
	document.getElementById('root')
);
