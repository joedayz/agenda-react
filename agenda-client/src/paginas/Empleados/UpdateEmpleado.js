import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { Button, Modal, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';

var UpdateEmpleado = React.createClass({

	getInitialState: function() {

		return {
			updateObject: {
				id: '', 
				nombre: '',
				apellido: '',
				salario: '',
				departamentoId: ''
			}
		}
    },

    shouldComponentUpdate: function() {

    	return true;
    },

	render: function() {
		
		return (
			<Modal show={this.props.parent.state.showUpdateModal}>
				<Modal.Header>
					<Modal.Title>Actualizar Empleado</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<form>
						<FormGroup>
							<ControlLabel>Nombre Empleado</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese nombre"
								value={this.state.updateObject.nombre}
								onChange={this.onUpdateEmpleadoNombreChange} />
							<br />
							
							<ControlLabel>Apellido</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese apellido"
								value={this.state.updateObject.apellido}
								onChange={this.onUpdateEmpleadoApellidoChange} />
							<br />
							
							<ControlLabel>Salario</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese Salario"
								value={this.state.updateObject.salario}
								onChange={this.onUpdateEmpleadoSalarioChange} />
							<br />
							
							<ControlLabel>Departamento</ControlLabel>
							<Select
								name="departamentosField"
								value={this.state.updateObject.departamentoId}
								options={this.props.parent.getDepartamentoOptions()}
								onChange={this.onUpdateEmpleadoDepartamentoChange} />
						</FormGroup>
					</form>						
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.parent.closeUpdateModal}>Cerrar</Button>
					<Button bsStyle="primary" onClick={this.onUpdateBtnClicked}>Actualizar</Button>
				</Modal.Footer>
			</Modal>
		);
	},

	fillUpdateObject: function() {

    	var selectedEmpleado = this.props.parent.getEmpleadoById(this.props.parent.state.selectedEmpleadoId);

		this.state.updateObject = {
			id: selectedEmpleado.id,
			nombre: selectedEmpleado.nombre,
			apellido: selectedEmpleado.apellido,
			salario: selectedEmpleado.salario,
			departamentoId: selectedEmpleado.departamentoId
		}
	},

	clearUpdateObject: function() {

		this.state.updateObject.id = '';
		this.state.updateObject.nombre = '';
		this.state.updateObject.apellido = '';
		this.state.updateObject.salario = '';
		this.state.updateObject.departamentoId = '';
	},

	//Input changes
	onUpdateEmpleadoNombreChange: function(event) {
		this.state.updateObject.nombre = event.target.value;
		this.forceUpdate();
	},

	onUpdateEmpleadoApellidoChange: function(event) {
		this.state.updateObject.apellido = event.target.value;
		this.forceUpdate();
	},

	onUpdateEmpleadoSalarioChange: function(event) {
		this.state.updateObject.salario = event.target.value;
		this.forceUpdate();		
	},

	onUpdateEmpleadoDepartamentoChange: function(selection) {

		if(selection === null) {
			this.state.updateObject.departamentoId = null;
		}else {
			this.state.updateObject.departamentoId = selection.value;
		}
		
		this.forceUpdate();		
	},
			
	onUpdateBtnClicked: function() {
		
		//Update empleado
		axios.put('http://localhost:8080/empleados/' + this.state.updateObject.id, this.state.updateObject)
			.then(function (response) {
				this.props.parent.closeUpdateModal();
				this.props.parent.refreshTable();
				console.log(response);
			}.bind(this))
			.catch(function (error) {
				console.log(error);
			});
	}
});

export default UpdateEmpleado;