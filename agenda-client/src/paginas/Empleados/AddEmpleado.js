import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { Button, Modal, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';

var AddEmpleado = React.createClass({

	getInitialState: function() {

		return {
			addObject: {
				id: '', 
				nombre: '',
				apellido: '',
				salario: '',
				departamentoId: ''
			}
		}
    },

	render: function() {

		return (
			<Modal show={this.props.parent.state.showAddModal}>
				<Modal.Header>
					<Modal.Title>Agregar Empleado</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<form>
						<FormGroup>
							<ControlLabel>Nombre Empleado</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese Nombre"
								value={this.state.addObject.nombre}
								onChange={this.onAddEmpleadoNombreChange} />
							<br />
							
							<ControlLabel>Apellidos</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese Apellidos"
								value={this.state.addObject.apellido}
								onChange={this.onAddEmpleadoApellidoChange} />
							<br />
							
							<ControlLabel>Salario</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese Salario"
								value={this.state.addObject.salario}
								onChange={this.onAddEmpleadoSalarioChange} />
							<br />
							
							<ControlLabel>Departamento</ControlLabel>
							<Select
								name="departamentosField"
								value={this.state.addObject.departamentoId}
								options={this.props.parent.getDepartamentoOptions()}
								onChange={this.onAddEmpleadoDepartamentoChange} />
						</FormGroup>
					</form>						
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.parent.closeAddModal}>Cerrar</Button>
					<Button bsStyle="primary" onClick={this.onAddBtnClicked}>Agregar</Button>
				</Modal.Footer>				
			</Modal>
		);
	},

	clearAddObject: function() {
		
		this.state.addObject.id = '';
		this.state.addObject.nombre = '';
		this.state.addObject.apellido = '';
		this.state.addObject.salario = '';
		this.state.addObject.departamentoId = '';
	},

	//Input changes
	onAddEmpleadoNombreChange: function(event) {
		this.state.addObject.nombre = event.target.value;
		this.forceUpdate();
	},

	onAddEmpleadoApellidoChange: function(event) {
		this.state.addObject.apellido = event.target.value;
		this.forceUpdate();
	},

	onAddEmpleadoSalarioChange: function(event) {
		this.state.addObject.salario = event.target.value;
		this.forceUpdate();
	},

	onAddEmpleadoDepartamentoChange: function(selection) {

		if(selection === null) {
			this.state.addObject.departamentoId = null;
		}else {		
			this.state.addObject.departamentoId = selection.value;
		}

		this.forceUpdate();
	},
	
	onAddBtnClicked: function() {

		//Save empleado
		axios.post('http://localhost:8080/empleados/', this.state.addObject)
			.then(function (response) {
				this.props.parent.closeAddModal();
				this.props.parent.refreshTable();
				console.log(response);
			}.bind(this))
			.catch(function (error) {
				console.log(error);
			});
	}
});

export default AddEmpleado;